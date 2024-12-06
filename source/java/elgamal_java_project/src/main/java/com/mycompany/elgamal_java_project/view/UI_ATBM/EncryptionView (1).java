package com.mycompany.elgamal_java_project.view;

import com.mycompany.elgamal_java_project.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class EncryptionView {
    public static void main(String[] args) {
        // Tạo khung chính
        JFrame frame = new JFrame("Mã hóa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Utils.FRAME_WIDTH, Utils.FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = new JLabel("Mã hóa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        // Khoá công khai
        JLabel pLabel = new JLabel("Khoá công khai:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(pLabel, gbc);

        JTextField pField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(pField, gbc);

        // Văn bản cần mã hóa
        JLabel aLabel = new JLabel("Văn bản cần mã hóa:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(aLabel, gbc);

        JTextArea textArea = new JTextArea(3, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(true);
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(textAreaScrollPane, gbc);

        // Nút Mã hóa
        JButton encryptButton = new JButton("Mã hóa");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        encryptButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa
        frame.add(encryptButton, gbc);

        // Kết quả
        JLabel bLabel = new JLabel("Kết quả:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(bLabel, gbc);

        JTextArea ansArea = new JTextArea(3, 20);
        ansArea.setLineWrap(true);
        ansArea.setWrapStyleWord(true);
        ansArea.setEditable(false); // Không cho chỉnh sửa kết quả
        JScrollPane ansAreaScrollPane = new JScrollPane(ansArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(ansAreaScrollPane, gbc);

        // Hiển thị khung
        frame.setVisible(true);
    }
}
