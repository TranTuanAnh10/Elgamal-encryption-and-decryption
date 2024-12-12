/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.utils;

import java.math.BigInteger;
import java.security.MessageDigest;


public class Utils {
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;
    
    public static BigInteger hashMessage(String message) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(message.getBytes("UTF-8"));
        return new BigInteger(1, hash);
    }
}
