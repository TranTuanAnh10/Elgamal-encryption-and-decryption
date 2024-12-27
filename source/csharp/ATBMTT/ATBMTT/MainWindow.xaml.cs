using System.Numerics;
using System.Security.Cryptography;
using System.Text;
using System.Windows;
using System.IO;
using System.Xml.Linq;
using Microsoft.Win32;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
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

        private void btnGenerateKeys_Click( object sender, RoutedEventArgs e )
        {
            try
            {
                if ( string.IsNullOrWhiteSpace(txtPrime.Text) || string.IsNullOrWhiteSpace(txtPrimitiveRoot.Text) )
                {
                    MessageBox.Show("Vui lòng nhập số nguyên tố và phần tử nguyên thủy!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                p = BigInteger.Parse(txtPrime.Text);
                g = BigInteger.Parse(txtPrimitiveRoot.Text);

                if ( !IsPrime(p) )
                {
                    MessageBox.Show("Số nhập vào không phải số nguyên tố!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                privateKey = GenerateRandomBigInteger(p - 2) + 1;
                publicKey = BigInteger.ModPow(g, privateKey, p);

                txtPrivateKey.Text = privateKey.ToString();
                txtPublicKey.Text = publicKey.ToString();
                MessageBox.Show("Khóa đã được tạo thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch ( Exception ex )
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void btnRandomKeys_Click( object sender, RoutedEventArgs e )
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
                txtPublicKey.Text = publicKey.ToString();
                MessageBox.Show("Khóa ngẫu nhiên đã được tạo thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch ( Exception ex )
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
        private void btnTransferKeys_Click( object sender, RoutedEventArgs e )
        {
            try
            {

                if ( p == 0 || g == 0 || publicKey == 0 )
                {
                    MessageBox.Show("Chưa có khóa công khai để chuyển! Hãy tạo hoặc nhập khóa trước.", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                txtP.Text = p.ToString();
                txtA.Text = g.ToString();
                txtY.Text = publicKey.ToString();
                txtPrivateKeyInput.Text = privateKey.ToString();
                MessageBox.Show("Đã chuyển các giá trị khóa công khai thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch ( Exception ex )
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
        private void btnEncrypt_Click( object sender, RoutedEventArgs e )
        {
            try
            {

                BigInteger p = BigInteger.Parse(txtP.Text);
                BigInteger g = BigInteger.Parse(txtA.Text);
                BigInteger y = BigInteger.Parse(txtY.Text);

                string message = txtMessage.Text;
                var encryptedBlocks = EncryptLongMessage(message, p, g, y);

                if ( encryptedBlocks != null )
                {
                    txtEncrypted1.Text = string.Join(";", encryptedBlocks.Select(block => block.Item1.ToString()));
                    txtEncrypted2.Text = string.Join(";", encryptedBlocks.Select(block => block.Item2.ToString()));
                    MessageBox.Show("Mã hóa thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }
            catch ( Exception ex )
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private List<(BigInteger, BigInteger)> EncryptLongMessage( string message, BigInteger p, BigInteger g, BigInteger y )
        {
            var encryptedBlocks = new List<(BigInteger, BigInteger)>();

            foreach ( char character in message )
            {
                if ( char.IsWhiteSpace(character) )
                {

                    encryptedBlocks.Add((BigInteger.Zero, BigInteger.Zero));
                    continue;
                }

                BigInteger m = new BigInteger(character);

                if ( m >= p )
                {
                    MessageBox.Show("Ký tự quá lớn để mã hóa!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return null;
                }


                BigInteger k = GenerateRandomBigInteger(p - 2) + 1;
                BigInteger c1 = BigInteger.ModPow(g, k, p);
                BigInteger c2 = ( m * BigInteger.ModPow(y, k, p) ) % p;

                encryptedBlocks.Add((c1, c2));
            }

            return encryptedBlocks;
        }
        private void btnDecrypt_Click( object sender, RoutedEventArgs e )
        {
            try
            {

                BigInteger p = BigInteger.Parse(txtP.Text);
                BigInteger privateKey = BigInteger.Parse(txtPrivateKey.Text);


                var c1Blocks = txtEncrypted1.Text.Split(';').Where(x => !string.IsNullOrWhiteSpace(x)).Select(BigInteger.Parse).ToList();
                var c2Blocks = txtEncrypted2.Text.Split(';').Where(x => !string.IsNullOrWhiteSpace(x)).Select(BigInteger.Parse).ToList();

                if ( c1Blocks.Count != c2Blocks.Count )
                {
                    MessageBox.Show("Dữ liệu mã hóa không hợp lệ!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                string decryptedMessage = DecryptLongMessage(c1Blocks, c2Blocks, p, privateKey);

                if ( !string.IsNullOrWhiteSpace(decryptedMessage) )
                {
                    txtDecrypted.Text = decryptedMessage;
                    MessageBox.Show("Giải mã thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }
            catch ( Exception ex )
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private string DecryptLongMessage( List<BigInteger> c1Blocks, List<BigInteger> c2Blocks, BigInteger p, BigInteger privateKey )
        {
            var decryptedMessage = new StringBuilder();

            for ( int i = 0; i < c1Blocks.Count; i++ )
            {
                BigInteger c1 = c1Blocks [i];
                BigInteger c2 = c2Blocks [i];

                if ( c1 == BigInteger.Zero && c2 == BigInteger.Zero )
                {

                    decryptedMessage.Append(' ');
                    continue;
                }

                BigInteger s = BigInteger.ModPow(c1, privateKey, p);
                BigInteger sInverse = ModInverse(s, p);
                BigInteger m = ( c2 * sInverse ) % p;


                char character = (char) m;
                decryptedMessage.Append(character);
            }

            return decryptedMessage.ToString();
        }

        private string ComputeSHA256Hash( string input )
        {
            using ( SHA256 sha256 = SHA256.Create() )
            {
                byte [] inputBytes = Encoding.UTF8.GetBytes(input);
                byte [] hashBytes = sha256.ComputeHash(inputBytes);
                return BitConverter.ToString(hashBytes).Replace("-", "").ToLower();
            }
        }


        private bool IsPrime( BigInteger n )
        {
            if ( n < 2 ) return false;
            for ( BigInteger i = 2; i * i <= n; i++ )
                if ( n % i == 0 )
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
            } while ( !IsPrime(prime) );
            return prime;
        }
        private BigInteger FindPrimitiveRoot( BigInteger p )
        {
            for ( BigInteger g = 2; g < p; g++ )
                if ( IsPrimitiveRoot(g, p) )
                    return g;

            throw new Exception("Không tìm thấy phần tử nguyên thủy.");
        }

        private bool IsPrimitiveRoot( BigInteger g, BigInteger p )
        {
            BigInteger phi = p - 1;
            var factors = GetPrimeFactors(phi);
            foreach ( var factor in factors )
            {
                if ( BigInteger.ModPow(g, phi / factor, p) == 1 )
                    return false;
            }
            return true;
        }

        private List<BigInteger> GetPrimeFactors( BigInteger n )
        {
            var factors = new List<BigInteger>();
            for ( BigInteger i = 2; i * i <= n; i++ )
            {
                while ( n % i == 0 )
                {
                    factors.Add(i);
                    n /= i;
                }
            }
            if ( n > 1 )
                factors.Add(n);
            return factors;
        }

        private BigInteger ModInverse( BigInteger a, BigInteger m )
        {
            BigInteger m0 = m, t, q;
            BigInteger x0 = 0, x1 = 1;

            while ( a > 1 )
            {
                q = a / m;
                t = m;
                m = a % m;
                a = t;
                t = x0;
                x0 = x1 - q * x0;
                x1 = t;
            }

            if ( x1 < 0 )
                x1 += m0;

            return x1;
        }

        private BigInteger GenerateRandomBigInteger( BigInteger max )
        {
            byte [] bytes = max.ToByteArray();
            BigInteger randomNumber;

            do
            {
                RandomNumberGenerator.Fill(bytes);
                randomNumber = new BigInteger(bytes);
            } while ( randomNumber < 0 || randomNumber >= max );

            return randomNumber;

        }
        //private bool MillerRabinTest(BigInteger n, int k)
        //{
        //    if (n < 2)
        //        return false;
        //    if (n == 2 || n == 3)
        //        return true;
        //    if (n % 2 == 0)
        //        return false;


        //    BigInteger d = n - 1;
        //    int r = 0;
        //    while (d % 2 == 0)
        //    {
        //        d /= 2;
        //        r++;
        //    }


        //    Random rng = new Random();
        //    for (int i = 0; i < k; i++)
        //    {
        //        BigInteger a = GenerateRandom(2, n - 2); 
        //        BigInteger x = BigInteger.ModPow(a, d, n);

        //        if (x == 1 || x == n - 1)
        //            continue;

        //        bool continueLoop = false;
        //        for (int j = 0; j < r - 1; j++)
        //        {
        //            x = BigInteger.ModPow(x, 2, n);
        //            if (x == n - 1)
        //            {
        //                continueLoop = true;
        //                break;
        //            }
        //        }

        //        if (!continueLoop)
        //            return false;
        //    }

        //    return true;
        //}

        //private BigInteger GenerateRandom(BigInteger min, BigInteger max)
        //{
        //    byte[] bytes = max.ToByteArray();
        //    BigInteger result;
        //    using (RandomNumberGenerator rng = RandomNumberGenerator.Create())
        //    {
        //        do
        //        {
        //            rng.GetBytes(bytes);
        //            result = new BigInteger(bytes);
        //        } while (result < min || result > max);
        //    }
        //    return BigInteger.Abs(result);
        //}
        //private BigInteger GenerateRandomBigIntege(int bits)
        //{
        //    while (true)
        //    {
        //        BigInteger prime = GenerateRandom(BigInteger.One << (bits - 1), (BigInteger.One << bits) - 1);
        //        if (MillerRabinTest(prime, 10))
        //            return prime;
        //    }
        //}

        private void btnSave_Click( object sender, RoutedEventArgs e )
        {
            try
            {
                string encrypted1 = txtEncrypted1.Text;
                string encrypted2 = txtEncrypted2.Text;

                if ( string.IsNullOrWhiteSpace(encrypted1) || string.IsNullOrWhiteSpace(encrypted2) )
                {
                    MessageBox.Show("Không có dữ liệu để lưu!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                SaveFileDialog saveFileDialog = new SaveFileDialog
                {
                    Filter = "Text files (*.txt)|*.txt|Word documents (*.docx)|*.docx|PDF files (*.pdf)|*.pdf",
                    DefaultExt = "txt"
                };

                if ( saveFileDialog.ShowDialog() == true )
                {
                    string filePath = saveFileDialog.FileName;
                    string content = $"C1: {encrypted1}\nC2: {encrypted2}";

                    if ( filePath.EndsWith(".txt") )
                        SaveToTxt(filePath, content);
                    else if ( filePath.EndsWith(".docx") )
                        SaveToDocx(filePath, content);
                    else if ( filePath.EndsWith(".pdf") )
                        SaveToPdf(filePath, content);

                    MessageBox.Show("Kết quả đã được lưu thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }
            catch ( Exception ex )
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
        private void SaveToTxt( string filePath, string content )
        {
            File.WriteAllText(filePath, content);
        }
        private void SaveToDocx( string filePath, string content )
        {
            XDocument doc = new XDocument(
                new XDeclaration("1.0", "UTF-8", "yes"),
                new XElement("w:document",
                    new XAttribute(XNamespace.Xmlns + "w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main"),
                    new XElement("w:body",
                        new XElement("w:p",
                            new XElement("w:r",
                                new XElement("w:t", content)
                            )
                        )
                    )
                )
            );

            using ( FileStream fs = new FileStream(filePath, FileMode.Create) )
            {
                doc.Save(fs);
            }
        }
        private void SaveToPdf( string filePath, string content )
        {
            using ( var writer = new StreamWriter(filePath) )
            {
                writer.Write(content);
            }
        }

        private void btnSelectfile_Click( object sender, RoutedEventArgs e )
        {
            try
            {
                OpenFileDialog openFileDialog = new OpenFileDialog
                {
                    Filter = "Text files (*.txt)|*.txt|Word documents (*.docx)|*.docx|PDF files (*.pdf)|*.pdf",
                    Title = "Chọn file để mở"
                };

                if ( openFileDialog.ShowDialog() == true )
                {
                    string filePath = openFileDialog.FileName;

                    string content = string.Empty;


                    if ( filePath.EndsWith(".txt") )
                        content = ReadFromTxt(filePath);
                    else if ( filePath.EndsWith(".docx") )
                        content = ReadFromDocx(filePath);
                    else if ( filePath.EndsWith(".pdf") )
                        content = ReadFromPdf(filePath);


                    if ( string.IsNullOrWhiteSpace(txtMessage.Text) )
                    {
                        txtMessage.Text = content;
                        MessageBox.Show("Đã nhập nội dung file vào phần mã hóa!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                    }
                    else if ( string.IsNullOrWhiteSpace(txtEncrypted1.Text) && string.IsNullOrWhiteSpace(txtEncrypted2.Text) )
                    {

                        var parts = content.Split(new [] { "\n", "\r\n" }, StringSplitOptions.RemoveEmptyEntries);
                        if ( parts.Length >= 2 )
                        {
                            txtEncrypted1.Text = parts [0].Replace("C1: ", "").Trim();
                            txtEncrypted2.Text = parts [1].Replace("C2: ", "").Trim();
                            MessageBox.Show("Đã nhập nội dung file vào phần giải mã!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                        }
                        else
                        {
                            MessageBox.Show("Dữ liệu trong file không hợp lệ!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                        }
                    }
                    else
                    {
                        MessageBox.Show("Vui lòng xóa nội dung ở các ô trước khi chọn file!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Warning);
                    }
                }
            }
            catch ( Exception ex )
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
        private string ReadFromTxt( string filePath )
        {
            return File.ReadAllText(filePath);
        }
        private string ReadFromDocx( string filePath )
        {
            var content = new StringBuilder();
            using ( var stream = File.OpenRead(filePath) )
            {
                var doc = XDocument.Load(stream);
                foreach ( var element in doc.Descendants(XName.Get("t", "http://schemas.openxmlformats.org/wordprocessingml/2006/main")) )
                {
                    content.Append(element.Value);
                }
            }
            return content.ToString();
        }
        private string ReadFromPdf( string filePath )
        {


            return File.ReadAllText(filePath);
        }

        private void btnSave1_Click( object sender, RoutedEventArgs e )
        {
            try
            {
                if ( string.IsNullOrWhiteSpace(txtDecrypted.Text) )
                {
                    MessageBox.Show("Không có nội dung để lưu!", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                SaveFileDialog saveFileDialog = new SaveFileDialog
                {
                    Filter = "Text files (*.txt)|*.txt|Word documents (*.docx)|*.docx|PDF files (*.pdf)|*.pdf",
                    Title = "Lưu kết quả giải mã"
                };

                if ( saveFileDialog.ShowDialog() == true )
                {
                    string filePath = saveFileDialog.FileName;
                    string decryptedText = txtDecrypted.Text;

                    if ( filePath.EndsWith(".txt") )
                        SaveToTxt(filePath, decryptedText);
                    else if ( filePath.EndsWith(".docx") )
                        SaveToDocx(filePath, decryptedText);
                    else if ( filePath.EndsWith(".pdf") )
                        SaveToPdf(filePath, decryptedText);

                    MessageBox.Show("Kết quả đã được lưu thành công!", "Thông báo", MessageBoxButton.OK, MessageBoxImage.Information);
                }
            }
            catch ( Exception ex )
            {
                MessageBox.Show($"Lỗi: {ex.Message}", "Lỗi", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void btnSelectfileInput_Click( object sender, RoutedEventArgs e )
        {
            try
            {
                OpenFileDialog openFileDialog = new OpenFileDialog
                {
                    Filter = "Text files (*.txt)|*.txt|All files (*.*)|*.*",
                    Title = "Chọn file đã lưu từ phần mã hóa"
                };

                if ( openFileDialog.ShowDialog() == true )
                {
                    string filePath = openFileDialog.FileName;
                    string content = File.ReadAllText(filePath);


                    var lines = content.Split(new [] { "\n", "\r\n" }, StringSplitOptions.RemoveEmptyEntries);
                    if ( lines.Length >= 2 )
                    {

                        txtDecrypted1.Text = lines [0].Replace("C1: ", "").Trim();
                        txtDecrypted2.Text = lines [1].Replace("C2: ", "").Trim();
                    }
                    else
                    {

                        txtDecrypted1.Text = "File không hợp lệ.";
                        txtDecrypted2.Text = string.Empty;
                    }
                }
            }
            catch ( Exception ex )
            {

                txtDecrypted1.Text = $"Lỗi: {ex.Message}";
                txtDecrypted2.Text = string.Empty;
            }
        }
    }
}