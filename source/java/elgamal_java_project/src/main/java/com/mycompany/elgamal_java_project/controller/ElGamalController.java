package com.mycompany.elgamal_java_project.controller;

import com.mycompany.elgamal_java_project.model.ElGamalKey;
import com.mycompany.elgamal_java_project.model.ElGamalKeyGenerator;
import com.mycompany.elgamal_java_project.view.ElGamalScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElGamalController {
    private ElGamalScreen view;
    private ElGamalKeyGenerator keyGenerator;

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
            ElGamalKey key = keyGenerator.GenerateKey();
            view.updateKeys(key.getPrivateKey().toString(), key.getPublicKey());
        }
    }

    // Lớp sự kiện cho RandomKeyButton
    class RandomKeyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Xử lý khi nhấn Random Key
            System.out.println("Random Key");
            ElGamalKey key =  keyGenerator.GenerateKey();
            view.updateKeys(key.getPrivateKey().toString(), key.getPublicKey());
        }
    }

    // Lớp sự kiện cho EncryptButton
    class EncryptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Xử lý khi nhấn EncryptButton
            System.out.println("EncryptButton");

        }
    }

    // Lớp sự kiện cho DecodeButton
    class DecodeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Xử lý khi nhấn DecodeButton
            System.out.println("DecodeButton");

        }
    }
}
