package com.mycompany.elgamal_java_project.view;

import javax.swing.*;
import java.awt.*;

public class ElGamalScreen {
    private static JButton saveKeyButton;
    private static JButton createKeyButton;
    private static JButton randomKeyButton;
    private static JButton encryptButton;
    private static JButton decodeButton;

    public ElGamalScreen() {
        // Tạo khung chính
        JFrame frame = new JFrame("ElGamal Encryption & Decryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Đặt chế độ cửa sổ mở toàn màn hình (Maximized)
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Mở cửa sổ ở chế độ full màn hình
        frame.setUndecorated(true); // Tùy chọn bỏ thanh tiêu đề, nếu muốn toàn màn hình thực sự
        frame.setLayout(new BorderLayout(10, 10)); // Sử dụng BorderLayout để chia cửa sổ thành 5 khu vực

        // Tạo các panel cho mã hóa, giải mã và tạo khóa
        JPanel decryptionPanel = createDecryptionPanel();   // Panel giải mã
        JPanel encryptionPanel = createEncryptionPanel();   // Panel mã hóa
        JPanel generateKeyPanel = createGenerateKeyPanel(); // Panel tạo khóa

        // Thêm các panel vào các khu vực của BorderLayout
        frame.add(decryptionPanel, BorderLayout.CENTER);   // Center: Giải mã
        frame.add(encryptionPanel, BorderLayout.WEST);     // West: Mã hóa
        frame.add(generateKeyPanel, BorderLayout.EAST);    // East: Tạo khóa

        // Cài đặt khung để tự động điều chỉnh kích thước khi thay đổi kích thước cửa sổ
        frame.pack(); // Đảm bảo các thành phần trong cửa sổ có kích thước đúng
        frame.setVisible(true); // Hiển thị khung
    }

    // Phương thức tạo JPanel cho giải mã
    private static JPanel createDecryptionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Giải mã", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel pLabel = new JLabel("Khoá bí mật:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(pLabel, gbc);

        JTextField pField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(pField, gbc);

        JLabel aLabel = new JLabel("Văn bản cần giải mã:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(aLabel, gbc);

        JTextArea textArea = new JTextArea(3, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(textAreaScrollPane, gbc);

        decodeButton = new JButton("Giải mã");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(decodeButton, gbc);

        JLabel bLabel = new JLabel("Kết quả:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(bLabel, gbc);

        JTextArea ansArea = new JTextArea(3, 20);
        ansArea.setEditable(false);
        JScrollPane ansAreaScrollPane = new JScrollPane(ansArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(ansAreaScrollPane, gbc);

        return panel;
    }

    // Phương thức tạo JPanel cho mã hóa
    private static JPanel createEncryptionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Mã hóa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel pLabel = new JLabel("Khoá công khai:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(pLabel, gbc);

        JTextField pField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(pField, gbc);

        JLabel aLabel = new JLabel("Văn bản cần mã hóa:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(aLabel, gbc);

        JTextArea textArea = new JTextArea(3, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(textAreaScrollPane, gbc);

        encryptButton = new JButton("Mã hóa");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(encryptButton, gbc);

        JLabel bLabel = new JLabel("Kết quả:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(bLabel, gbc);

        JTextArea ansArea = new JTextArea(3, 20);
        ansArea.setEditable(false);
        JScrollPane ansAreaScrollPane = new JScrollPane(ansArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(ansAreaScrollPane, gbc);

        return panel;
    }

    // Phương thức tạo JPanel cho tạo khóa
    private static JPanel createGenerateKeyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Tạo khóa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel pLabel = new JLabel("Số nguyên tố p:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(pLabel, gbc);

        JTextField pField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(pField, gbc);

        JLabel aLabel = new JLabel("Phần tử nguyên thủy của a:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(aLabel, gbc);

        JTextField aField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(aField, gbc);

        createKeyButton = new JButton("Tạo khóa");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(createKeyButton, gbc);

        randomKeyButton = new JButton("Tạo khóa ngẫu nhiên");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(randomKeyButton, gbc);

        JLabel privateKeyLabel = new JLabel("Khóa bí mật:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(privateKeyLabel, gbc);

        JTextArea privateKeyArea = new JTextArea(3, 20);
        privateKeyArea.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(new JScrollPane(privateKeyArea), gbc);

        JLabel publicKeyLabel = new JLabel("Khóa công khai:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(publicKeyLabel, gbc);

        JTextArea publicKeyArea = new JTextArea(3, 20);
        publicKeyArea.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(new JScrollPane(publicKeyArea), gbc);

        saveKeyButton = new JButton("Lưu khóa");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(saveKeyButton, gbc);

        return panel;
    }

    // Các phương thức setter và getter cho các nút
    public void addSaveKeyListener(java.awt.event.ActionListener listener) {
        saveKeyButton.addActionListener(listener);
    }

    public void addCreateKeyListener(java.awt.event.ActionListener listener) {
        createKeyButton.addActionListener(listener);
    }

    public void addRandomKeyListener(java.awt.event.ActionListener listener) {
        randomKeyButton.addActionListener(listener);
    }

    public void addEncryptButtonListener(java.awt.event.ActionListener listener) {
        encryptButton.addActionListener(listener);
    }

    public void addDecodeButtonListener(java.awt.event.ActionListener listener) {
        decodeButton.addActionListener(listener);
    }

}
