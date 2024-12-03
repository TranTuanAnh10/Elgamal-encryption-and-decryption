/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;


public class ElGamalDecryptor {
    /**
     * Initializes the decrypt with private parameters.
     *
     * @param p Prime number.
     * @param privateKey The private key.
     */
    
    private final int p;
    private final int privateKey;

    public ElGamalDecryptor(int p, int privateKey) {
        this.p = p;
        this.privateKey = privateKey;
    }
    /**
     * Decrypts a cipher text using the private key.
     *
     * @param ciphertext An array containing the cipher text: {c1, c2}.
     * @return The decrypted plain text message.
     */
    public void decrypt(int[] ciphertext) {
        //update soon
    }
}
