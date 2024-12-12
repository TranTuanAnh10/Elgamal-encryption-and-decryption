/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;

import java.math.BigInteger;

/**
 *
 * @author PC
 */
public class CipherText {
    public BigInteger c1;  // g^k mod p
        public BigInteger c2;  // (message * y^k) mod p

        public CipherText(BigInteger c1, BigInteger c2) {
            this.c1 = c1;
            this.c2 = c2;
        }
}
