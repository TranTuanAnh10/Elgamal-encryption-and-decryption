/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ElGamalDecryptor {
    /**
     * Initializes the decrypt with private parameters.
     *
     * @param p Prime number.
     * @param privateKey The private key.
     */
    
    private final BigInteger p;
    private final BigInteger privateKey;

    public ElGamalDecryptor(BigInteger p, BigInteger privateKey) {
        this.p = p;
        this.privateKey = privateKey;
    }
    /**
     * Decrypts a cipher text using the private key.
     *
     * @param ciphertext An array containing the cipher text: {c1, c2}.
     * @return paintext String decrypted
     */
    public String decrypt(String c1String, String c2String) {
        // Tách chuỗi c1 và c2 thành mảng
        
        String[] c1Blocks = c1String.split(",");
        String[] c2Blocks = c2String.split(",");

        if (c1Blocks.length != c2Blocks.length) {
            throw new IllegalArgumentException("Văn bản đã bị thay đổi");
        }

        ByteArrayOutputStream messageBytes = new ByteArrayOutputStream();

        // Duyệt qua các cặp giá trị c1 và c2
        for (int i = 0; i < c1Blocks.length; i++) {
            // Chuyển đổi c1 và c2 từ chuỗi thành BigInteger
            BigInteger c1 = new BigInteger(c1Blocks[i].trim());
            BigInteger c2 = new BigInteger(c2Blocks[i].trim());

            // Tính toán s và sInverse
            BigInteger s = c1.modPow(privateKey, p);
            BigInteger sInverse = s.modInverse(p);

            // Tính toán thông điệp m
            BigInteger m = c2.multiply(sInverse).mod(p);

            // Chuyển đổi m thành byte[] và thêm vào thông điệp
            byte[] blockBytes = m.toByteArray();

            // Nếu byte đầu tiên là padding (do BigInteger thêm vào), loại bỏ nó
            if (blockBytes.length > 1 && blockBytes[0] == 0) {
                blockBytes = Arrays.copyOfRange(blockBytes, 1, blockBytes.length);
            }

            try {
                // Ghi trực tiếp vào ByteArrayOutputStream
                messageBytes.write(blockBytes);
            } catch (IOException e) {
                throw new IllegalArgumentException("Lỗi khi ghi byte vào thông điệp.", e);
            }
        }

        // Trả về chuỗi thông điệp được giải mã
        return new String(messageBytes.toByteArray(), StandardCharsets.UTF_8);
    }

    private void reverseArray(byte[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            byte temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }

    private BigInteger modInverse(BigInteger a, BigInteger p) {
        return a.modInverse(p); // Java's BigInteger has a built-in modInverse method.
    }
}
