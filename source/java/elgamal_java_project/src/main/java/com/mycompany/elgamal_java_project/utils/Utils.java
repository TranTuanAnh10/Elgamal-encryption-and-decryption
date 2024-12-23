/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;


public class Utils {
    public static final int FRAME_WIDTH = 1100;
    public static final int FRAME_HEIGHT = 700;
    
    /**
     * Check if a number is prime.
     *
     * @param n Number to check
     * @return true if prime
     */
    public static boolean IsPrime(BigInteger n) {
        return n.isProbablePrime(100);
    }
    
    /**
     * Check if a is a primitive root of q.
     *
     * @param a Number to check
     * @param q Prime modulus
     * @return true if a is a primitive root of q
     */
    public static boolean IsPrimitiveRoot(BigInteger a, BigInteger q) {
        BigInteger phi = q.subtract(BigInteger.ONE);
        Set<BigInteger> primeFactors = GetPrimeFactors(phi);

        for (BigInteger factor : primeFactors) {
            if (a.modPow(phi.divide(factor), q).equals(BigInteger.ONE)) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Get prime factors of n.
     *
     * @param n Number to factorize
     * @return Set<BigInteger> of prime factors
     */
    public static Set<BigInteger> GetPrimeFactors(BigInteger n) {
        Set<BigInteger> factors = new HashSet<>();
        BigInteger factor = BigInteger.TWO;
        BigInteger sqrtN = n.sqrt(); // Tính căn bậc hai một lần

        while (n.mod(factor).equals(BigInteger.ZERO)) {
            factors.add(factor);
            n = n.divide(factor);
            sqrtN = n.sqrt(); // Cập nhật sqrt(n) sau khi chia
        }

        factor = BigInteger.valueOf(3);

        while (factor.compareTo(sqrtN) <= 0) { // Sử dụng sqrt(n) để giới hạn vòng lặp
            while (n.mod(factor).equals(BigInteger.ZERO)) {
                factors.add(factor);
                n = n.divide(factor);
                sqrtN = n.sqrt(); // Cập nhật sqrt(n) sau khi chia
            }
            factor = factor.add(BigInteger.TWO); // Tăng thêm 2 (chỉ kiểm tra số lẻ)
        }

        if (n.compareTo(BigInteger.ONE) > 0) {
            factors.add(n); // n là số nguyên tố
        }

        return factors;
    }
}
