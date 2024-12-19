package com.mycompany.elgamal_java_project.controller;

import com.mycompany.elgamal_java_project.model.ElGamalDecryptor;
import com.mycompany.elgamal_java_project.model.ElGamalKey;
import com.mycompany.elgamal_java_project.model.ElGamalKeyGenerator;
import com.mycompany.elgamal_java_project.model.ElGamalEncryptor;
import com.mycompany.elgamal_java_project.view.ElGamalScreen;
import com.mycompany.elgamal_java_project.model.PublicKeyModal;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class ElGamalController {
    private ElGamalScreen view;
    private ElGamalKeyGenerator keyGenerator;
    private ElGamalKey currentKey;

    public ElGamalController(ElGamalScreen view) {
        this.view = view;

        // Thêm các sự kiện cho các nút
        //this.view.addSaveKeyListener(new SaveKeyListener());
        this.view.addCreateKeyListener(new CreateKeyListener());
        this.view.addRandomKeyListener(new RandomKeyListener());
        this.view.addEncryptButtonListener(new EncryptButtonListener());
        this.view.addDecodeButtonListener(new DecodeButtonListener());
        keyGenerator = new ElGamalKeyGenerator();
    }

    // Lớp sự kiện cho SaveKeyButton
//    class SaveKeyListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            // Xử lý khi nhấn Save Key
//            System.out.println("Save Key");
//        }
//    }

    // Lớp sự kiện cho CreateKeyButton
    class CreateKeyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Xử lý khi nhấn Create Key
            System.out.println("Create Key");
            try {
               String pText = view.getPInput();
                String aText = view.getAInput();
                BigInteger p = new BigInteger(pText);
                BigInteger a = new BigInteger(aText);
                ElGamalKey key = keyGenerator.GenerateKey(p, a);
                if(key == null){
                    view.showMessage("Create Key Fail");
                }
                else{
                    view.updateKeys(key.getPrivateKey().toString(), key.getPublicKey());
                } 
            } catch (IllegalArgumentException ex) {
                view.showMessage(ex.getMessage());
            }
            
        }
    }

    // Lớp sự kiện cho RandomKeyButton
    class RandomKeyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Xử lý khi nhấn Random Key
            System.out.println("Random Key");
            ElGamalKey key =  keyGenerator.GenerateKey();
            view.setAInput(key.getA().toString());
            view.setPInput(key.getP().toString());
            view.updateKeys(key.getPrivateKey().toString(), key.getPublicKey());
        }
    }

    // Lớp sự kiện cho EncryptButton
    class EncryptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Xử lý khi nhấn EncryptButton
            System.out.println("EncryptButton");

            try {
                // Lấy khóa công khai từ giao diện
                String publicKeyInput = view.getPublicKey();
                PublicKeyModal publicKey = new PublicKeyModal(publicKeyInput);

                // Lấy thông điệp từ giao diện
                String message = view.getPlainText();
                if (message.isEmpty()) {
                    view.showMessage("Vui lòng nhập thông điệp cần mã hóa.");
                    return;
                }

                // Mã hóa thông điệp
                ElGamalEncryptor encryptor = new ElGamalEncryptor(publicKey);
                String ciphertext = encryptor.encrypt(message);

                // Hiển thị kết quả mã hóa
                view.setCipherText(ciphertext);
            } catch (Exception ex) {
                view.showMessage("Lỗi mã hóa: " + ex.getMessage());
            }
        }
    }

    // Lớp sự kiện cho DecodeButton
    class DecodeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("DecodeButton");
            try {
                // get private key from ui
                String privateKeyInput = view.GetPrivateKey();
                BigInteger p = keyGenerator.getKey().getP();
                BigInteger publicKey = new BigInteger(privateKeyInput);
                String message = view.GetCipherText();
                if (message.isEmpty()) {
                    view.showMessage("Vui lòng nhập thông điệp cần giải mã.");
                    return;
                }
                ElGamalDecryptor decryptor = new ElGamalDecryptor(p, publicKey);
                String plantext = decryptor.decrypt(message);
                view.setPlainText(plantext);
            } catch (Exception ex) {
                view.showMessage("Lỗi giải mã: " + ex.getMessage());
            }
        }
    }
}
