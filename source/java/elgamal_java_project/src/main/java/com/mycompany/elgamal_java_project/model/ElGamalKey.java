/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;

import java.math.BigInteger;


public class ElGamalKey {
    private final BigInteger privateKey; 
    private final PublicKeyModal publicKey; 
    
    public ElGamalKey(BigInteger _q, BigInteger _a, BigInteger _x, BigInteger _y){
        this.privateKey = _x;
        this.publicKey = new PublicKeyModal(_q, _a, _y);
    }
    public BigInteger getP() {
        return publicKey.p;
    }

    public BigInteger getA() {
        return publicKey.a;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public PublicKeyModal getPublicKey() {
        return publicKey;
    }
    // Thêm phương thức parsePublicKey()
    public static PublicKeyModal parsePublicKey(String publicKeyStr) {
        // Giả sử chuỗi publicKeyStr có định dạng như "p,a,y"
        String[] parts = publicKeyStr.split(",");
        
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid public key format");
        }

        try {
            BigInteger p = new BigInteger(parts[0].trim()); // p là số nguyên tố
            BigInteger a = new BigInteger(parts[1].trim()); // a là phần tử nguyên thủy
            BigInteger y = new BigInteger(parts[2].trim()); // y là giá trị khóa công khai

            // Trả về đối tượng PublicKeyModal với các giá trị p, a, y
            return new PublicKeyModal(p, a, y);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid public key format: " + e.getMessage());
        }
    }
}
