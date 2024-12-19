/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;

public class ElGamalEncryptor {
    private PublicKeyModal publicKey;
    private SecureRandom random = new SecureRandom(); // Sử dụng SecureRandom để sinh số ngẫu nhiên

    // Constructor
    public ElGamalEncryptor(PublicKeyModal publicKey) {
        this.publicKey = publicKey;
    }

    // Phương thức mã hoá
    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            BigInteger m = BigInteger.valueOf((int) ch); // Chuyển ký tự thành mã ASCII
            BigInteger r = new BigInteger(publicKey.p.bitLength() - 1, random); // Sinh số ngẫu nhiên r
            BigInteger c1 = publicKey.a.modPow(r, publicKey.p);
            BigInteger c2 = (m.multiply(publicKey.y.modPow(r, publicKey.p))).mod(publicKey.p);

            // Thêm cặp (c1, c2) vào kết quả
            if (ciphertext.length() > 0) {
                ciphertext.append(";"); // Ngăn cách các cặp bằng dấu phẩy
            }
            ciphertext.append(c1).append(",").append(c2);
        }

        return ciphertext.toString(); // Trả về chuỗi mã hóa
    }
}



