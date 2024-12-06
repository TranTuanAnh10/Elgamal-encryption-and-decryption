package com.mycompany.elgamal_java_project.view;

import com.mycompany.elgamal_java_project.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class GenerateKeyView {
    public static void main(String[] args) {
        // Tạo khung chính
        JFrame frame = new JFrame("Tạo khóa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Utils.FRAME_WIDTH, Utils.FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = new JLabel("Tạo khóa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        // Số nguyên tố p
        JLabel pLabel = new JLabel("Số nguyên tố p:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(pLabel, gbc);

        JTextField pField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(pField, gbc);

        // Phần tử nguyên thủy của a
        JLabel aLabel = new JLabel("Phần tử nguyên thủy của a:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(aLabel, gbc);

        JTextField aField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(aField, gbc);

        // Nút tạo khóa
        JButton createKeyButton = new JButton("Tạo khóa");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        frame.add(createKeyButton, gbc);

        // Nút tạo khóa ngẫu nhiên
        JButton randomKeyButton = new JButton("Tạo khóa ngẫu nhiên");
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(randomKeyButton, gbc);

        // Khóa bí mật
        JLabel privateKeyLabel = new JLabel("Khóa bí mật:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(privateKeyLabel, gbc);

        JTextArea privateKeyArea = new JTextArea(3, 20);
        privateKeyArea.setLineWrap(true);
        privateKeyArea.setWrapStyleWord(true);
        privateKeyArea.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(new JScrollPane(privateKeyArea), gbc);

        // Khóa công khai
        JLabel publicKeyLabel = new JLabel("Khóa công khai:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(publicKeyLabel, gbc);

        JTextArea publicKeyArea = new JTextArea(3, 20);
        publicKeyArea.setLineWrap(true);
        publicKeyArea.setWrapStyleWord(true);
        publicKeyArea.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        frame.add(new JScrollPane(publicKeyArea), gbc);

        // Nút lưu khóa
        JButton saveKeyButton = new JButton("Lưu khóa");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        frame.add(saveKeyButton, gbc);

        // Hiển thị khung
        frame.setVisible(true);
    }
}
