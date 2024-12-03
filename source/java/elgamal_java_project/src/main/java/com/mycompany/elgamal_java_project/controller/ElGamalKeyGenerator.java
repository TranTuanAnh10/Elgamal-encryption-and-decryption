/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.controller;

import com.mycompany.elgamal_java_project.model.ElGamalKey;


public class ElGamalKeyGenerator {
    /**
    * Generated keys.
    *
    * @param bitLength Length of the prime number in bits.
    */
    public ElGamalKey GenerateKey(int p, int g){
        //update soon
        ElGamalKey elgamalKey = new ElGamalKey(p, g, 1, 1);
        return elgamalKey;
    }
}
