/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;


public class ElGamalKey {
    private final int p; 
    private final int g; 
    private int privateKey; 
    private int publicKey; 
    
    public ElGamalKey(int _p, int _g, int _privateKey, int _publicKey){
        this.p = _p;
        this.g = _g;
        this.privateKey = _privateKey;
    }
    public int getP() {
        return p;
    }

    public int getG() {
        return g;
    }

    public int getPrivateKey() {
        return privateKey;
    }

    public int getPublicKey() {
        return publicKey;
    }
}
