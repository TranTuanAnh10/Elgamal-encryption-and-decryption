using System.Numerics;
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
using System.Numerics;

namespace Elgamal
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            GenerateKeys();
        }
        // Biến lưu trữ khóa ElGamal
        private BigInteger p, g, y, x;

        #region ElGamal Algorithm Implementation

        // Tạo khóa ElGamal
        private static (BigInteger p, BigInteger g, BigInteger y, BigInteger x) GenerateKeys(int keySize)
        {
            BigInteger p = GeneratePrime(keySize);
            BigInteger g = GetPrimitiveRoot(p);
            BigInteger x = GenerateRandomBigInt(2, p - 2); // Khóa riêng
            BigInteger y = BigInteger.ModPow(g, x, p);     // Khóa công khai

            return (p, g, y, x);
        }

        // Mã hóa
        private static (BigInteger c1, BigInteger c2) Encrypt(BigInteger p, BigInteger g, BigInteger y, BigInteger m)
        {
            BigInteger k = GenerateRandomBigInt(1, p - 1);
            BigInteger c1 = BigInteger.ModPow(g, k, p);
            BigInteger c2 = (m * BigInteger.ModPow(y, k, p)) % p;
            return (c1, c2);
        }

        // Giải mã
        private static BigInteger Decrypt(BigInteger p, BigInteger x, BigInteger c1, BigInteger c2)
        {
            BigInteger s = BigInteger.ModPow(c1, x, p);
            BigInteger sInverse = BigInteger.ModPow(s, p - 2, p);
            return (c2 * sInverse) % p;
        }

        // Tạo số nguyên tố ngẫu nhiên
        private static BigInteger GeneratePrime(int bitLength)
        {
            Random rand = new Random();
            while (true)
            {
                BigInteger p = GenerateRandomBigInt((BigInteger.One << (bitLength - 1)), (BigInteger.One << bitLength) - 1);
                if (IsProbablyPrime(p)) return p;
            }
        }

        // Kiểm tra số nguyên tố
        private static bool IsProbablyPrime(BigInteger n, int iterations = 10)
        {
            if (n < 2) return false;
            if (n == 2 || n == 3) return true;
            if (n % 2 == 0) return false;

            BigInteger d = n - 1;
            int s = 0;
            while (d % 2 == 0)
            {
                d /= 2;
                s++;
            }

            Random rand = new Random();
            for (int i = 0; i < iterations; i++)
            {
                BigInteger a = GenerateRandomBigInt(2, n - 2);
                BigInteger x = BigInteger.ModPow(a, d, n);
                if (x == 1 || x == n - 1) continue;

                bool continueLoop = false;
                for (int r = 1; r < s; r++)
                {
                    x = BigInteger.ModPow(x, 2, n);
                    if (x == n - 1)
                    {
                        continueLoop = true;
                        break;
                    }
                }

                if (!continueLoop) return false;
            }
            return true;
        }

        // Tạo căn nguyên thủy
        private static BigInteger GetPrimitiveRoot(BigInteger p)
        {
            for (BigInteger g = 2; g < p; g++)
            {
                if (BigInteger.ModPow(g, p - 1, p) == 1) return g;
            }
            throw new Exception("Không tìm thấy căn nguyên thủy!");
        }

        // Tạo số nguyên ngẫu nhiên lớn
        private static BigInteger GenerateRandomBigInt(BigInteger min, BigInteger max)
        {
            Random rand = new Random();
            byte[] bytes = max.ToByteArray();
            BigInteger randomValue;
            do
            {
                rand.NextBytes(bytes);
                bytes[^1] &= 0x7F;
                randomValue = new BigInteger(bytes);
            } while (randomValue < min || randomValue > max);
            return randomValue;
        }

        #endregion



        // Tạo khóa ElGamal
        private void GenerateKeys()
        {
            int keySize = 512; // Kích thước khóa
            (p, g, y, x) = GenerateKeys(keySize);

            // Hiển thị khóa công khai và khóa riêng
            txtPublicKey.Text = $"p: {p}\ng: {g}\ny: {y}";
            txtPrivateKey.Text = $"x: {x}";
        }

        // Mã hóa
        private void btnEncrypt_Click(object sender, EventArgs e)
        {
            try
            {
                BigInteger message = BigInteger.Parse(txtMessage.Text); // Đọc tin nhắn
                var (c1, c2) = Encrypt(p, g, y, message);

                // Hiển thị kết quả mã hóa
                txtCipher.Text = $"c1: {c1}\nc2: {c2}";
            }
            catch
            {
                MessageBox.Show("Hãy nhập tin nhắn là một số nguyên hợp lệ!", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // Giải mã
        private void btnDecrypt_Click(object sender, EventArgs e)
        {
            try
            {
                string[] cipherParts = txtCipher.Text.Split(new[] { "c1: ", "c2: ", "\n" }, StringSplitOptions.RemoveEmptyEntries);
                BigInteger c1 = BigInteger.Parse(cipherParts[0]);
                BigInteger c2 = BigInteger.Parse(cipherParts[1]);

                BigInteger decryptedMessage = Decrypt(p, x, c1, c2);

                // Hiển thị kết quả giải mã
                txtDecrypted.Text = decryptedMessage.ToString();
            }
            catch
            {
                MessageBox.Show("Hãy nhập bản mã hợp lệ!", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

    }
}