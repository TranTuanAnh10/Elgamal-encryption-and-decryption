package com.mycompany.elgamal_java_project.view;

import com.mycompany.elgamal_java_project.model.PublicKeyModal;
import com.mycompany.elgamal_java_project.utils.Utils;
import javax.swing.*;
import java.awt.*;

public class ElGamalScreen {
    private static JButton saveKeyButton;
    private static JButton createKeyButton;
    private static JButton randomKeyButton;
    private static JButton encryptButton;
    private static JButton decodeButton;
    private static JButton uploadFileEncryptButton;
    private static JButton uploadFileDecryptButton;
    private static JButton saveFileEncryptButton;
    private static JButton saveFileDecryptButton;
    private static JTextArea privateKeyArea;
    private static JTextArea publicKeyArea;
    private static JTextField publicKeyInput;
    private static JTextField privateKeyInput;
    private static JTextArea textArea;
    private static JTextArea encryptArea;
    private static JTextArea decryptArea;
    private static JTextField pField;   
    private static JTextField aField;
    private static JTextArea cipherTextInput;




    public ElGamalScreen() {
        // Tạo khung chính
        JFrame frame = new JFrame("ElGamal Encryption & Decryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Utils.FRAME_WIDTH, Utils.FRAME_HEIGHT); // Đặt kích thước cửa sổ
        frame.setLayout(new BorderLayout(10, 10)); // Sử dụng BorderLayout để chia cửa sổ thành 5 khu vực

        // Tạo các panel cho mã hóa, giải mã và tạo khóa
        JPanel decryptionPanel = createDecryptionPanel();   // Panel giải mã
        JPanel encryptionPanel = createEncryptionPanel();   // Panel mã hóa
        JPanel generateKeyPanel = createGenerateKeyPanel(); // Panel tạo khóa

//        generateKeyPanel.get
        
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.add(encryptionPanel, BorderLayout.NORTH); // Mã hóa bên trên
        rightPanel.add(decryptionPanel, BorderLayout.CENTER); // Giải mã bên dưới

        // Thêm các panel vào các khu vực của BorderLayout
        frame.add(generateKeyPanel, BorderLayout.WEST);     // West: Tạo khóa
        frame.add(rightPanel, BorderLayout.CENTER); 

        // Hiển thị khung
        frame.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        frame.setVisible(true);
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

        privateKeyInput = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(privateKeyInput, gbc);

        JLabel aLabel = new JLabel("Văn bản cần giải mã:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(aLabel, gbc);

        cipherTextInput = new JTextArea(5, 40);
        cipherTextInput.setLineWrap(true);
        cipherTextInput.setWrapStyleWord(true);
        cipherTextInput.setLineWrap(true); 
        JScrollPane textAreaScrollPane = new JScrollPane(cipherTextInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(textAreaScrollPane, gbc);
        
        JButton uploadFileDecrypt = new JButton("Chọn file");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(uploadFileDecrypt, gbc);

        decodeButton = new JButton("Giải mã");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(decodeButton, gbc);

        JLabel bLabel = new JLabel("Kết quả:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(bLabel, gbc);

        decryptArea = new JTextArea(5, 40);
        decryptArea.setEditable(false);
        decryptArea.setLineWrap(true); 
        decryptArea.setWrapStyleWord(true);
        JScrollPane ansAreaScrollPane = new JScrollPane(decryptArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(ansAreaScrollPane, gbc);
        
        JButton saveFileDecrypt = new JButton("Lưu file");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(saveFileDecrypt, gbc);

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

        publicKeyInput = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(publicKeyInput, gbc);

        JLabel aLabel = new JLabel("Văn bản cần mã hóa:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(aLabel, gbc);

        textArea = new JTextArea(5, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true); 
        JScrollPane textAreaScrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(textAreaScrollPane, gbc);

        encryptButton = new JButton("Mã hóa");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(encryptButton, gbc);
        
        JButton uploadFilePlantext = new JButton("Chọn file");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(uploadFilePlantext, gbc);

        JLabel bLabel = new JLabel("Kết quả:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(bLabel, gbc);

        encryptArea = new JTextArea(5, 40);
        encryptArea.setEditable(false);
        encryptArea.setLineWrap(true); 
        encryptArea.setWrapStyleWord(true);
        JScrollPane ansAreaScrollPane = new JScrollPane(encryptArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(ansAreaScrollPane, gbc);
        
        JButton saveFileEncrypt = new JButton("Lưu file");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(saveFileEncrypt, gbc);

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

        pField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(pField, gbc);

        JLabel aLabel = new JLabel("Phần tử nguyên thủy của a:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(aLabel, gbc);

        aField = new JTextField();
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

        privateKeyArea = new JTextArea(3, 20);  
        privateKeyArea.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(new JScrollPane(privateKeyArea), gbc);

        JLabel publicKeyLabel = new JLabel("Khóa công khai:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(publicKeyLabel, gbc);

        publicKeyArea = new JTextArea(3, 20);  
        publicKeyArea.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(new JScrollPane(publicKeyArea), gbc);

//        saveKeyButton = new JButton("Lưu khóa");
//        gbc.gridx = 0;
//        gbc.gridy = 6;
//        gbc.gridwidth = 2;
//        panel.add(saveKeyButton, gbc);

        return panel;
    }
    
    //Public method to update jframe
    public void updateKeys(String privateKey, PublicKeyModal publicKey) {
        System.out.println("updateKeys");
        privateKeyArea.setText(privateKey);
        publicKeyArea.setText(publicKey.ToString());
    }
    public String getPInput(){
        return pField.getText();
    }
    public String getAInput(){
        return aField.getText();
    }
    
    public void setPInput(String p){
       pField.setText(p);
    }
    public void setAInput(String a){
        aField.setText(a);
    }
    
    public String getPublicKey(){
        return publicKeyInput.getText();
    }

    public String getPlainText() {
        
        return textArea.getText(); 
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message); 
    }

    public void setCipherText(String cipherText) {
        encryptArea.setText(cipherText); 
    }
    
    public String GetPrivateKey(){
        return privateKeyInput.getText();
    }
    public String GetCipherText(){
        return cipherTextInput.getText();
    }
    public void setPlainText(String plainText){
        decryptArea.setText(plainText);
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
    
    public void addUploadDecryptButtonListener(java.awt.event.ActionListener listener) {
        decodeButton.addActionListener(listener);
    }
    public void addUploadEncryptButtonListener(java.awt.event.ActionListener listener) {
        decodeButton.addActionListener(listener);
    }
    public void addSaveDecryptButtonListener(java.awt.event.ActionListener listener) {
        decodeButton.addActionListener(listener);
    }
    public void addSaveEncryptButtonListener(java.awt.event.ActionListener listener) {
        decodeButton.addActionListener(listener);
    }
    
    public void showEncryptedText(String encryptedText) {
        encryptArea.setText(encryptedText);
    }
}
