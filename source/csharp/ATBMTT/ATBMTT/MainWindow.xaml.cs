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
                BigInteger m = ConvertMessageToBigInteger(message, p);

                if (m == -1)
                {
                    MessageBox.Show("Thông điệp quá lớn để mã hóa.", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                BigInteger k = GenerateRandomBigInteger(p - 2) + 1;
                BigInteger c1 = BigInteger.ModPow(g, k, p);
                BigInteger c2 = (m * BigInteger.ModPow(y, k, p)) % p;

                txtEncrypted.Text = $"{c1}, {c2}";
                MessageBox.Show("Mã hóa thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
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
                if (string.IsNullOrWhiteSpace(txtEncryptedInput.Text) || txtEncryptedInput.Text.Split(',').Length != 2)
                {
                    MessageBox.Show("Vui lòng nhập văn bản mã hóa đúng định dạng: c1,c2.", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                var ciphertext = txtEncryptedInput.Text.Split(',');
                BigInteger c1 = BigInteger.Parse(ciphertext[0]);
                BigInteger c2 = BigInteger.Parse(ciphertext[1]);
                BigInteger s = BigInteger.ModPow(c1, privateKey, p);
                BigInteger sInverse = ModInverse(s, p); 
                BigInteger m = (c2 * sInverse) % p;

                string decryptedMessage = ConvertBigIntegerToMessage(m);
                txtDecrypted.Text = decryptedMessage;
                MessageBox.Show("Giải mã thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
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

        private BigInteger ConvertMessageToBigInteger(string message, BigInteger p)
        {
            byte[] messageBytes = Encoding.UTF8.GetBytes(message);
            BigInteger m = new BigInteger(messageBytes.Reverse().ToArray()); 

            if (m >= p) 
            {
                return -1;
            }

            return m;
        }

        private string ConvertBigIntegerToMessage(BigInteger m)
        {
            byte[] messageBytes = m.ToByteArray();
            Array.Reverse(messageBytes);
            return Encoding.UTF8.GetString(messageBytes);
        }
        private bool IsPrime(BigInteger n)
        {
            if (n < 2) return false;
            for (BigInteger i = 2; i * i <= n; i++)
                if (n % i == 0)
                    return false;
            return true;
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