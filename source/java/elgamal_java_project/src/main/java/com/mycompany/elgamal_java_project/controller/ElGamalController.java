package com.mycompany.elgamal_java_project.controller;

import com.mycompany.elgamal_java_project.View.ElgamalView;
import com.mycompany.elgamal_java_project.model.ElGamalDecryptor;
import com.mycompany.elgamal_java_project.model.ElGamalKey;
import com.mycompany.elgamal_java_project.model.ElGamalKeyGenerator;
import com.mycompany.elgamal_java_project.model.ElGamalEncryptor;
import com.mycompany.elgamal_java_project.view.ElGamalScreen;
import com.mycompany.elgamal_java_project.model.PublicKeyModal;
import com.mycompany.elgamal_java_project.utils.Utils;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Map;
import javax.swing.JOptionPane;

public class ElGamalController {
    private ElgamalView view;
    private ElGamalKeyGenerator keyGenerator;
    private ElGamalKey currentKey;

    public ElGamalController(ElgamalView view) {
        this.view = view;

        // Thêm các sự kiện cho các nút
        //this.view.addSaveKeyListener(new SaveKeyListener());
//        this.view.addCreateKeyListener(new CreateKeyListener());
//        this.view.addRandomKeyListener(new RandomKeyListener());
//        this.view.addEncryptButtonListener(new EncryptButtonListener());
//        this.view.addDecodeButtonListener(new DecodeButtonListener());      
//        this.view.addUploadEncryptButtonListener(new UploadEncryptButtonListener());
//        this.view.addUploadDecryptButtonListener(new UploadDecryptButtonListener());
//        this.view.addSaveEncryptButtonListener(new SaveEncryptButtonListener());   
//        this.view.addSaveDecryptButtonListener(new SaveDecryptButtonListener());


        keyGenerator = new ElGamalKeyGenerator();
    }

    // Lớp sự kiện cho SaveKeyButton
//    class SaveKeyListener implements ActionListener {
//        @Override
//        public void actionPerformed() {
//            // Xử lý khi nhấn Save Key
//            System.out.println("Save Key");
//        }
//    }

    
    public void CreateAction() {
        // Xử lý khi nhấn Create Key
        System.out.println("Create Key");
        try {
           String pText = view.pInputKey.getText();
            String aText = view.aInputKey.getText();
            System.out.println("Create Key p: " + pText);            
            System.out.println("Create Key a: " + aText);

            if(pText.isBlank() || pText.isEmpty()){
                 view.showMessage("Vui lòng nhập p");
                 return;
            }

            if(aText.isBlank() || aText.isEmpty()){
                view.showMessage("Vui lòng nhập a");
                return;
            }
            System.out.println("Create Key a: " + aText);
            BigInteger p = new BigInteger(pText);
            BigInteger a = new BigInteger(aText);
            ElGamalKey key = keyGenerator.GenerateKey(p, a);
            if(key == null){
                view.showMessage("Tạo khóa thất bại");
            }
            else{
                view.privateKeyLabel.setText(key.getPrivateKey().toString());
                view.publicKeyLabel.setText(key.getPublicKey().y.toString());
                view.showMessage("Tạo khóa thành công");
            } 
        } catch (IllegalArgumentException ex) {
            view.showMessage(ex.getMessage());
        }

    }


    public void RandomKeyAction() {
        // Xử lý khi nhấn Random Key
        try {
            System.out.println("Random Key");
            ElGamalKey key =  keyGenerator.GenerateKey();
            view.pInputKey.setText(key.getP().toString());
            view.aInputKey.setText(key.getA().toString());
            view.privateKeyLabel.setText(key.getPrivateKey().toString());
            view.publicKeyLabel.setText(key.getPublicKey().y.toString());
            view.showMessage("Tạo khóa thành công");
        } catch (IllegalArgumentException ex) {
            view.showMessage(ex.getMessage());
        }
    }

    public void EncryptAction() {
        // Xử lý khi nhấn EncryptButton
        System.out.println("EncryptButton");

        try {
            // Lấy khóa công khai từ giao diện
            String publicKeyInput = view.yInputEncypt.getText();
            String p = view.pInputEncypt.getText();
            String a = view.aInputEncrypt.getText();
            
            if(publicKeyInput.isBlank() || publicKeyInput.isEmpty()){
                view.showMessage("Vui lòng nhập p");
                return;
            }
            if(p.isBlank() || p.isEmpty()){
                view.showMessage("Vui lòng nhập p");
                return;
            }
            if(a.isBlank() || a.isEmpty()){
                view.showMessage("Vui lòng nhập a");
                return;
            }

            PublicKeyModal publicKey = new PublicKeyModal(new BigInteger(p), new BigInteger(a), new BigInteger(publicKeyInput));

            // Lấy thông điệp từ giao diện
            String message = view.paintextInput.getText();
            if (message.isEmpty()) {
                view.showMessage("Vui lòng nhập thông điệp cần mã hóa.");
                return;
            }

            // Mã hóa thông điệp
            ElGamalEncryptor encryptor = new ElGamalEncryptor(publicKey);
            Map<String, String> ciphertext = encryptor.encrypt(message, this);
            String c1 = ciphertext.get("c1");
            String c2 = ciphertext.get("c2");
            // Hiển thị kết quả mã hóa
            view.c1CipherText.setText(c1);
            view.c2CipherText.setText(c2);
            view.showMessage("Mã hóa thành công");
        } catch (Exception ex) {
            view.showMessage("Lỗi mã hóa: " + ex.getMessage());
        }
    }
    public void UpdateTextWhileEncrypt(String c1, String c2){
        view.c1CipherText.setText(c1);
        view.c2CipherText.setText(c2);
    }

    public void DecryptAction() {
        try {
            // get private key from ui
            String privateKeyInput = view.privateKeyDecrypt.getText();
            if(keyGenerator == null || keyGenerator.getKey() == null || keyGenerator.getKey().getP() == null){
                view.showMessage("Bạn chưa tạo khóa");
                return;
            }
            BigInteger p = keyGenerator.getKey().getP();
            if(privateKeyInput.isBlank() || privateKeyInput.isEmpty()){
                view.showMessage("Vui lòng nhập khóa bí mật");
                return;
            }
            BigInteger privateKey = new BigInteger(privateKeyInput);
            String messageC1 = view.c1CipherTextDecrypt.getText();   
            String messageC2 = view.c2CipherTextDecrypt.getText();
            if (messageC1.isEmpty() || messageC2.isEmpty()) {
                view.showMessage("Vui lòng nhập thông điệp cần giải mã.");
                return;
            }
            ElGamalDecryptor decryptor = new ElGamalDecryptor(p, privateKey);
            String plantext = decryptor.decrypt(messageC1, messageC2);
            view.resultDecrypt.setText(plantext);
            view.showMessage("Giải mã thành công");
        } catch (Exception ex) {
            view.showMessage("Lỗi giải mã: " + ex.getMessage());
        }
    }

    public void UploadDecryptButtonListener() {
        System.out.println("UploadDecryptButtonListener");
        try {
            String txt = Utils.GetFile();
            if(!txt.isEmpty()){
                String[] blocks = txt.split(";");
                view.c1CipherTextDecrypt.setText(blocks[0]);   
                view.c2CipherTextDecrypt.setText(blocks[1]);
                System.out.println("UploadDecryptButtonListener: " + txt);
            }
        } catch (IllegalArgumentException ex) {
            view.showMessage(ex.getMessage());
        }
    }
    public void UploadEncryptButtonListener() {
        System.out.println("UploadDecryptButtonListener");
        try {
            String txt = Utils.GetFile();
            if(!txt.isEmpty()){
                view.paintextInput.setText(txt);
                System.out.println("UploadDecryptButtonListener: " + txt);  
            }
        } catch (IllegalArgumentException ex) {
            view.showMessage(ex.getMessage());
        }
    }

    public void SaveEncryptButtonListener() {
        System.out.println("SaveEncryptButtonListener");
        try {
            String c1 = view.c1CipherText.getText();
            String c2 = view.c2CipherText.getText();
            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append(c1).append(";").append(c2);
            String result = Utils.SaveFile(resultBuilder.toString());
            if(result != null){
                view.showMessage(result);
            }
        } catch (IllegalArgumentException ex) {
            view.showMessage(ex.getMessage());
        }
    }

    public void SaveDecryptButtonListener() {
        System.out.println("SaveDecryptButtonListener");
        try {
            String result = Utils.SaveFile(view.resultDecrypt.getText());
            if(result != null){
                view.showMessage(result);
            }
        } catch (IllegalArgumentException ex) {
            view.showMessage(ex.getMessage());
        }
    }
    
    public void MoveKeyToEncryptAndDecrypt(){
        System.out.println("MoveKeyToEncryptAndDecrypt");
        String a = view.aInputKey.getText();
        if(a == null || a == "")
            view.showMessage("Bạn chưa nhập số a");
        String p = view.pInputKey.getText();
        if(a == null || a == "")
            view.showMessage("Bạn chưa nhập số p");
        String x = view.privateKeyLabel.getText();
        String y = view.publicKeyLabel.getText();
        if(x == null || x == "" || y == null || y == "")
            view.showMessage("Bạn chưa tạo khóa");

        view.aInputEncrypt.setText(a);       
        view.pInputEncypt.setText(p);
        view.yInputEncypt.setText(y);
        view.privateKeyDecrypt.setText(x);
    }
    
    public void resetButtonClick(){
        view.aInputEncrypt.setText("");      
        view.aInputKey.setText("");
        view.c1CipherText.setText("");
        view.c1CipherTextDecrypt.setText("");
        view.c2CipherText.setText("");
        view.c2CipherTextDecrypt.setText("");
        view.yInputEncypt.setText("");
        view.publicKeyLabel.setText("");
        view.pInputEncypt.setText("");
        view.pInputKey.setText("");
        view.paintextInput.setText("");
        view.privateKeyDecrypt.setText("");
        view.privateKeyLabel.setText("");
        view.resultDecrypt.setText("");

    }
}
