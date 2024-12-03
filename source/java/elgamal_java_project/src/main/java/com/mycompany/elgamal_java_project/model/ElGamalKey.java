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
        return publicKey.q;
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
}
