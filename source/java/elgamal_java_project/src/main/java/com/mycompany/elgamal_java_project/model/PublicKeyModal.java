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
public class PublicKeyModal {
    
    public final BigInteger p; 
    public final BigInteger a; 
    public final BigInteger y; 
    
    public PublicKeyModal(BigInteger _q, BigInteger _a, BigInteger _y){
        this.p = _q;
        this.a = _a;
        this.y = _y;
    }
    public String ToString(){
        return p.toString() + "," + a.toString() + "," + y.toString();
    }
}
