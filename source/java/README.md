# Thư mục Java
# Java Swing Coding Convention
## 1. Tổng quan dự án
* Ngôn ngữ: Java 17.
* IDE:  NetBeans.
* Build Tool: Maven.
## 2. Quy tắc đặt tên
### 2.1. Tên tệp
* Tên tệp trùng với tên class.
* Sử dụng PascalCase (viết hoa chữ cái đầu của mỗi từ).

Ví dụ:
```java
MainFrame.java
LoginController.java
```
### 2.2. Tên class
* Sử dụng PascalCase.
* Tên class nên ngắn gọn và mô tả rõ ràng vai trò.

Ví dụ:

```java
public class LoginForm {}
```
### 2.3. Tên biến
* Sử dụng camelCase.
* Tên biến phải rõ ràng và mô tả mục đích.

Ví dụ:
```java
private JButton loginButton;
private JTextField usernameField;
```
### 2.4. Tên phương thức
* Sử dụng camelCase.
* Động từ thường được sử dụng ở đầu (ví dụ: get, set, load, init).

Ví dụ:
```java
public void initUI() {}
public String getUsername() {}
```
### 2.5. Tên hằng số
* Sử dụng UPPER_CASE và phân tách từ bằng dấu gạch dưới _.
  
Ví dụ:
```java
private static final int FRAME_WIDTH = 800;
private static final int FRAME_HEIGHT = 600;
```
## 3. Quy tắc về định dạng
### 3.1. Độ dài dòng
Giới hạn dòng code không quá 120 ký tự.
### 3.2. Dấu ngoặc
* Mở ngoặc { trên cùng dòng với khai báo.
* Đóng ngoặc } ở dòng riêng biệt.

Ví dụ:
```java
if (isLoggedIn) {
    showDashboard();
}
else {
    showLoginScreen();
}
```
### 3.3. Dấu cách
* Thêm khoảng trắng xung quanh toán tử (=, +, -, *, /, ==, v.v.).
* Thêm khoảng trắng sau dấu phẩy trong danh sách tham số.

Ví dụ:
```java
int total = count + 5;
method(arg1, arg2, arg3);
```
## 4. Quy ước Java Swing
### 4.1. Tổ chức mã nguồn
* package theo cấu trúc:
  * view (UI components như JFrame, JPanel, JButton)
  * controller (xử lý logic)
  * model (lớp mô hình dữ liệu)
  * utils (công cụ chung)

Ví dụ:

```css
src/
├── model/
│   └── User.java
├── view/
│   └── LoginForm.java
├── controller/
│   └── LoginController.java
└── utils/
    └── Validator.java
```
### 4.2. Tách logic và UI
* Giữ logic trong controller, không viết trực tiếp trong view.
view chỉ dùng để hiển thị giao diện.

Ví dụ:
```java
// Trong LoginController
public boolean authenticate(String username, String password) {
    return userService.validateCredentials(username, password);
}

// Trong LoginForm
loginButton.addActionListener(e -> {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());
    if (controller.authenticate(username, password)) {
        showDashboard();
    }
    else {
        showError();
    }
});
```
### 4.3. Sử dụng Layout Manager
* Luôn sử dụng LayoutManager thay vì thiết lập vị trí cố định (absolute positioning).

Ví dụ:
```java
setLayout(new BorderLayout());
add(panel, BorderLayout.CENTER);
```
### 4.4. Sử dụng SwingWorker cho các tác vụ nền
* Tránh các thao tác nặng trong Event Dispatch Thread (EDT) để không làm treo giao diện.

Ví dụ:
```java
SwingWorker<Void, Void> worker = new SwingWorker<>() {
    @Override
    protected Void doInBackground() throws Exception {
        performHeavyTask();
        return null;
    }

    @Override
    protected void done() {
        updateUI();
    }
};
worker.execute();
```
## 5. Quy tắc khác
* Kiểm tra lỗi: Sử dụng try-catch cho các tác vụ I/O hoặc tiềm ẩn lỗi.
* Sử dụng Logger: Dùng java.util.logging.Logger thay vì System.out.println().

Ví dụ:
```java
private static final Logger logger = Logger.getLogger(MainFrame.class.getName());

try {
    // Code xử lý
}
catch (IOException e) {
    logger.severe("Error reading file: " + e.getMessage());
}
```
## 6. Quy tắc bình luận
* Sử dụng JavaDoc cho class, method, và public fields.
* Bình luận ngắn gọn, tập trung vào ý nghĩa của mã nguồn.

Ví dụ:
```java
/**
 * Hiển thị form đăng nhập.
 */
public class LoginForm extends JFrame {
    // Tạo nút đăng nhập
    private JButton loginButton;
}
```
## 7. Quy trình code review
* Kiểm tra tuân thủ coding convention.
* Đảm bảo logic đúng.
* Đảm bảo mã dễ bảo trì và tối ưu hóa.
