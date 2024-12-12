/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;

import com.mycompany.elgamal_java_project.utils.Utils;
import java.math.BigInteger;
import java.security.SecureRandom;


public class ElGamalEncryptor {
    /**
     * Initializes the encrypt with public key.
     *
   
     */
    public ElGamalEncryptor() {
        //update soon
    }
    /**
     * Encrypts a message
     *
     * @param message
     * @param publicKey
     * @return CipherText
     * @throws java.lang.Exception 
     */
    public static CipherText encrypt(String message, PublicKeyModal publicKey) throws Exception {
        BigInteger painTextHash = Utils.hashMessage(message);
        SecureRandom random = new SecureRandom();
        BigInteger k = new BigInteger(publicKey.p.bitLength() - 1, random).mod(publicKey.p.subtract(BigInteger.ONE));
        BigInteger c1 = publicKey.a.modPow(k, publicKey.p);
        BigInteger c2 = painTextHash.multiply(publicKey.y.modPow(k, publicKey.p)).mod(publicKey.p);
        return new CipherText(c1, c2);
    }
}
