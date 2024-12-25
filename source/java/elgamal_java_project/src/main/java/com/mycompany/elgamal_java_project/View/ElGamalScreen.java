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
        frame.setSize(Utils.FRAME_WIDTH, Utils.FRAME_HEIGHT);
        frame.setLayout(new GridLayout(1, 3));

        // Tạo các panel cho mã hóa, giải mã và tạo khóa
        JPanel generateKeyPanel = createGenerateKeyPanel();
        JPanel encryptionPanel = createEncryptionPanel();
        JPanel decryptionPanel = createDecryptionPanel();

        // Thêm các panel vào khung
        frame.add(generateKeyPanel);
        frame.add(encryptionPanel);
        frame.add(decryptionPanel);

        // Hiển thị khung
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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
        panel.add(pLabel, gbc);

        pField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(pField, gbc);

        JLabel aLabel = new JLabel("Phần tử nguyên thủy a:");
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
        panel.add(createKeyButton, gbc);

        return panel;
    }

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

        JLabel publicKeyLabel = new JLabel("Khóa công khai:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(publicKeyLabel, gbc);

        publicKeyInput = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(publicKeyInput, gbc);

        JLabel messageLabel = new JLabel("Văn bản cần mã hóa:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(messageLabel, gbc);

        textArea = new JTextArea(5, 20);
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(textAreaScrollPane, gbc);

        uploadFileEncryptButton = new JButton("Chọn file");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(uploadFileEncryptButton, gbc);

        encryptButton = new JButton("Mã hóa");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(encryptButton, gbc);

        JLabel resultLabel = new JLabel("Kết quả:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(resultLabel, gbc);

        encryptArea = new JTextArea(5, 20);
        encryptArea.setEditable(false);
        JScrollPane encryptAreaScrollPane = new JScrollPane(encryptArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(encryptAreaScrollPane, gbc);

        saveFileEncryptButton = new JButton("Lưu file");
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(saveFileEncryptButton, gbc);

        return panel;
    }

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

        JLabel privateKeyLabel = new JLabel("Khóa bí mật:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(privateKeyLabel, gbc);

        JTextField privateKeyInput = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(privateKeyInput, gbc);

        JLabel cipherTextLabel = new JLabel("Văn bản cần giải mã:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(cipherTextLabel, gbc);

        cipherTextInput = new JTextArea(5, 20);
        JScrollPane cipherTextAreaScrollPane = new JScrollPane(cipherTextInput);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(cipherTextAreaScrollPane, gbc);

        uploadFileDecryptButton = new JButton("Chọn file");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(uploadFileDecryptButton, gbc);

        decodeButton = new JButton("Giải mã");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(decodeButton, gbc);

        JLabel decryptedResultLabel = new JLabel("Kết quả:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(decryptedResultLabel, gbc);

        decryptArea = new JTextArea(5, 20);
        decryptArea.setEditable(false);
        JScrollPane decryptAreaScrollPane = new JScrollPane(decryptArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(decryptAreaScrollPane, gbc);

        saveFileDecryptButton = new JButton("Lưu file");
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(saveFileDecryptButton, gbc);

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
        uploadFileDecryptButton.addActionListener(listener);
    }
    public void addUploadEncryptButtonListener(java.awt.event.ActionListener listener) {
        uploadFileEncryptButton.addActionListener(listener);
    }
    public void addSaveDecryptButtonListener(java.awt.event.ActionListener listener) {
        saveFileDecryptButton.addActionListener(listener);
    }
    public void addSaveEncryptButtonListener(java.awt.event.ActionListener listener) {
        saveFileEncryptButton.addActionListener(listener);
    }
    
    public void showEncryptedText(String encryptedText) {
        encryptArea.setText(encryptedText);
    }
}
