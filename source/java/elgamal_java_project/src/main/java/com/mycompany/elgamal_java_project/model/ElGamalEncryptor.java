/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;

import com.mycompany.elgamal_java_project.controller.ElGamalController;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class ElGamalEncryptor {
    private PublicKeyModal publicKey;
    private SecureRandom random = new SecureRandom(); // Sử dụng SecureRandom để sinh số ngẫu nhiên

    // Constructor
    public ElGamalEncryptor(PublicKeyModal publicKey) {
        this.publicKey = publicKey;
    }

    // Phương thức mã hoá
    public Map<String, String> encrypt(String plaintext, ElGamalController controller) {
        StringBuilder c1Builder = new StringBuilder();
        StringBuilder c2Builder = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            BigInteger m = BigInteger.valueOf((int) ch); // Chuyển ký tự thành mã ASCII
            BigInteger r = new BigInteger(publicKey.p.bitLength() - 1, random); // Sinh số ngẫu nhiên r
            BigInteger c1 = publicKey.a.modPow(r, publicKey.p);
            BigInteger c2 = (m.multiply(publicKey.y.modPow(r, publicKey.p))).mod(publicKey.p);

            // Thêm c1 và c2 vào chuỗi tương ứng
            if (c1Builder.length() > 0) {
                c1Builder.append(","); // Ngăn cách các giá trị c1 bằng dấu phẩy
                c2Builder.append(","); // Ngăn cách các giá trị c2 bằng dấu phẩy
            }
            c1Builder.append(c1);
            c2Builder.append(c2);
            
            controller.UpdateTextWhileEncrypt(c1Builder.toString(), c2Builder.toString());
        }

        // Đưa cả hai chuỗi c1 và c2 vào một Map
        Map<String, String> result = new HashMap<>();
        result.put("c1", c1Builder.toString());
        result.put("c2", c2Builder.toString());

        return result; // Trả về Map chứa hai chuỗi c1 và c2
    }
}



