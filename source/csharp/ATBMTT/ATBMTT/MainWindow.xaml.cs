using System.Numerics;
using System.Security.Cryptography;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;


namespace ATBMTT
{
    public partial class MainWindow : Window
    {
        private BigInteger p; 
        private BigInteger g;
        private BigInteger privateKey;
        private BigInteger publicKey;

        public MainWindow()
        {
            InitializeComponent();
        }

        private void btnGenerateKeys_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(txtPrime.Text) || string.IsNullOrWhiteSpace(txtPrimitiveRoot.Text))
                {
                    MessageBox.Show("Vui lòng nhập số nguyên tố và phần tử nguyên thủy!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                p = BigInteger.Parse(txtPrime.Text);
                g = BigInteger.Parse(txtPrimitiveRoot.Text);

                if (!IsPrime(p))
                {
                    MessageBox.Show("Số nhập vào không phải số nguyên tố!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                privateKey = GenerateRandomBigInteger(p - 2) + 1;
                publicKey = BigInteger.ModPow(g, privateKey, p);

                txtPrivateKey.Text = privateKey.ToString();
                txtPublicKey.Text = $"{p}, {g}, {publicKey}";
                MessageBox.Show("Khóa đã được tạo thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void btnRandomKeys_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                p = GenerateLargePrime();
                g = FindPrimitiveRoot(p);
                privateKey = GenerateRandomBigInteger(p - 2) + 1;
                publicKey = BigInteger.ModPow(g, privateKey, p);

                txtPrime.Text = p.ToString();
                txtPrimitiveRoot.Text = g.ToString();
                txtPrivateKey.Text = privateKey.ToString();
                txtPublicKey.Text = $"{p}, {g}, {publicKey}";
                MessageBox.Show("Khóa ngẫu nhiên đã được tạo thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void btnEncrypt_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(txtPublicKeyInput.Text) || txtPublicKeyInput.Text.Split(',').Length != 3)
                {
                    MessageBox.Show("Vui lòng nhập khóa công khai đúng định dạng: p,g,y.", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                var publicKeyInput = txtPublicKeyInput.Text.Split(',');
                BigInteger p = BigInteger.Parse(publicKeyInput[0]);
                BigInteger g = BigInteger.Parse(publicKeyInput[1]);
                BigInteger y = BigInteger.Parse(publicKeyInput[2]);

                string message = txtMessage.Text;
                string encrypted = EncryptLongMessage(message, p, g, y);

                if (!string.IsNullOrWhiteSpace(encrypted))
                {
                    txtEncrypted.Text = encrypted;
                    MessageBox.Show("Mã hóa thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void btnDecrypt_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(txtEncryptedInput.Text))
                {
                    MessageBox.Show("Vui lòng nhập văn bản mã hóa.", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                string encryptedMessage = txtEncryptedInput.Text;
                string decryptedMessage = DecryptLongMessage(encryptedMessage, p, privateKey);

                if (!string.IsNullOrWhiteSpace(decryptedMessage))
                {
                    txtDecrypted.Text = decryptedMessage;
                    MessageBox.Show("Giải mã thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private string ComputeSHA256Hash(string input)
        {
            using (SHA256 sha256 = SHA256.Create())
            {
                byte[] inputBytes = Encoding.UTF8.GetBytes(input);
                byte[] hashBytes = sha256.ComputeHash(inputBytes);
                return BitConverter.ToString(hashBytes).Replace("-", "").ToLower();
            }
        }

        private string EncryptLongMessage(string message, BigInteger p, BigInteger g, BigInteger y)
        {
            List<string> encryptedBlocks = new List<string>();
            byte[] messageBytes = Encoding.UTF8.GetBytes(message);
            int blockSize = Math.Max((int)Math.Floor(BigInteger.Log(p, 256)) - 1, 1);
            int offset = 0;
            while (offset < messageBytes.Length)
            {
                byte[] blockBytes = messageBytes.Skip(offset).Take(blockSize).ToArray();
                //offset += blockSize;
                offset++;

                BigInteger m = new BigInteger(blockBytes.Reverse().ToArray());
                if (m >= p)
                {
                    MessageBox.Show("Khối dữ liệu quá lớn để mã hóa!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }

                BigInteger k = GenerateRandomBigInteger(p - 2) + 1;
                BigInteger c1 = BigInteger.ModPow(g, k, p);
                BigInteger c2 = (m * BigInteger.ModPow(y, k, p)) % p;

                encryptedBlocks.Add($"{c1},{c2}");
            }

            return string.Join(";", encryptedBlocks);
        }

        private string DecryptLongMessage(string encryptedMessage, BigInteger p, BigInteger privateKey)  
        {
            var blocks = encryptedMessage.Split(';');
            List<byte> messageBytes = new List<byte>();

            foreach (var block in blocks)
            {
                var parts = block.Split(',');
                BigInteger c1 = BigInteger.Parse(parts[0]);
                BigInteger c2 = BigInteger.Parse(parts[1]);

                BigInteger s = BigInteger.ModPow(c1, privateKey, p);
                BigInteger sInverse = ModInverse(s, p);
                BigInteger m = (c2 * sInverse) % p;

                byte[] blockBytes = m.ToByteArray();

                Array.Reverse(blockBytes);
                messageBytes.Add(blockBytes[0]);
            }

            return Encoding.UTF8.GetString(messageBytes.ToArray()).TrimEnd('\0');
        }

        //private string DecryptLongMessage(string encryptedMessage, BigInteger p, BigInteger privateKey)
        //{
        //    var blocks = encryptedMessage.Split(';');
        //    List<byte> messageBytes = new List<byte>();

        //    foreach (var block in blocks)
        //    {
        //        if (string.IsNullOrWhiteSpace(block)) continue;

        //        var parts = block.Split(',');
        //        BigInteger c1 = BigInteger.Parse(parts[0]);
        //        BigInteger c2 = BigInteger.Parse(parts[1]);


        //        BigInteger s = BigInteger.ModPow(c1, privateKey, p);
        //        BigInteger sInverse = ModInverse(s, p);
        //        BigInteger m = (c2 * sInverse) % p;


        //        byte[] blockBytes = m.ToByteArray();
        //        if (blockBytes.Length > 1 && blockBytes[^1] == 0)
        //        {
        //            blockBytes = blockBytes.Take(blockBytes.Length - 1).ToArray();
        //        }

        //        messageBytes.AddRange(blockBytes.Reverse());
        //    }
        //    return Encoding.UTF8.GetString(messageBytes.ToArray()).TrimEnd('\0');
        //}

        private bool IsPrime(BigInteger n)
        {
            if (n < 2) return false;
            for (BigInteger i = 2; i * i <= n; i++)
                if (n % i == 0)
                    return false;
            return true;
        }

        private BigInteger GenerateLargePrime()
        {
            Random rand = new Random();
            BigInteger prime;
            do
            {
                prime = new BigInteger(rand.Next());
            } while (!IsPrime(prime));
            return prime;
        }

        private BigInteger FindPrimitiveRoot(BigInteger p)
        {
            for (BigInteger g = 2; g < p; g++)
                if (IsPrimitiveRoot(g, p))
                    return g;

            throw new Exception("Không tìm thấy phần tử nguyên thủy.");
        }

        private bool IsPrimitiveRoot(BigInteger g, BigInteger p)
        {
            BigInteger phi = p - 1;
            var factors = GetPrimeFactors(phi);
            foreach (var factor in factors)
            {
                if (BigInteger.ModPow(g, phi / factor, p) == 1)
                    return false;
            }
            return true;
        }

        private List<BigInteger> GetPrimeFactors(BigInteger n)
        {
            var factors = new List<BigInteger>();
            for (BigInteger i = 2; i * i <= n; i++)
            {
                while (n % i == 0)
                {
                    factors.Add(i);
                    n /= i;
                }
            }
            if (n > 1)
                factors.Add(n);
            return factors;
        }

        private BigInteger ModInverse(BigInteger a, BigInteger m)
        {
            BigInteger m0 = m, t, q;
            BigInteger x0 = 0, x1 = 1;

            while (a > 1)
            {
                q = a / m;
                t = m;
                m = a % m;
                a = t;
                t = x0;
                x0 = x1 - q * x0;
                x1 = t;
            }

            if (x1 < 0)
                x1 += m0;

            return x1;
        }

        private BigInteger GenerateRandomBigInteger(BigInteger max)
        {
            byte[] bytes = max.ToByteArray();
            BigInteger randomNumber;

            do
            {
                RandomNumberGenerator.Fill(bytes);
                randomNumber = new BigInteger(bytes);
            } while (randomNumber < 0 || randomNumber >= max);

            return randomNumber;
        }
    }
}