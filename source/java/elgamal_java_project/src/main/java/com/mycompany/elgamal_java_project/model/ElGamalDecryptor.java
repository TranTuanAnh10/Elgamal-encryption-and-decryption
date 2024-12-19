/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    public String decrypt(String ciphertext) {
        String[] blocks = ciphertext.split(";");
        List<Byte> messageBytes = new ArrayList<>();

        for (String block : blocks) {
            String[] parts = block.split(",");
            BigInteger c1 = new BigInteger(parts[0]);
            BigInteger c2 = new BigInteger(parts[1]);

            BigInteger s = c1.modPow(privateKey, p);
            BigInteger sInverse = modInverse(s, p);
            BigInteger m = c2.multiply(sInverse).mod(p);

            byte[] blockBytes = m.toByteArray();
            reverseArray(blockBytes);
            for (byte b : blockBytes) {
                messageBytes.add(b);
            }
        }
        byte[] resultBytes = new byte[messageBytes.size()];
        for (int i = 0; i < messageBytes.size(); i++) {
            resultBytes[i] = messageBytes.get(i);
        }

        return new String(resultBytes, StandardCharsets.UTF_8);
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
