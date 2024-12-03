/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.model;

import com.mycompany.elgamal_java_project.model.ElGamalKey;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;


public class ElGamalKeyGenerator {
    
    private ElGamalKey elgamalKey;
    /**
    * Generated keys.
    *
    * @param q     
    * @param a 
    * 
    * @return ElGamalKey contain private key and public key
    */
    public ElGamalKey GenerateKey(BigInteger q, BigInteger a){
        if(IsPrimitiveRoot(a, q) == false || IsPrime(q) == false){
            return null;
        }
        BigInteger x = GeneratePrivateKey(q);
        BigInteger y = a.modPow(x, q);
        
        elgamalKey = new ElGamalKey(q, a, x, y);
        return elgamalKey;
    }
    /**
    * Generated keys with random q and a .
    * @return ElGamalKey contain private key and public key
    */
    public ElGamalKey GenerateKey(){
        //update soon
        SecureRandom random = new SecureRandom();
        BigInteger q = BigInteger.probablePrime(128, random);
        try{
            BigInteger a = FindPrimitiveRoot(q);
            return GenerateKey(q, a);
        }
        catch (NumberFormatException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
    
    /**
    * GeneratePrivateKey.
    * @param q 
    * @return BigInteger private key
    */
    private BigInteger GeneratePrivateKey(BigInteger q){
        SecureRandom random = new SecureRandom();
        return new BigInteger(q.bitLength() - 1, random);
    }
    
    /**
    * Find the primitive root of a .
    * @param prime number to find primitive root
    * @return BigInteger primitive root
    */
    private BigInteger FindPrimitiveRoot(BigInteger prime) {
        BigInteger phi = prime.subtract(BigInteger.ONE);
        Set<BigInteger> primeFactors = GetPrimeFactors(phi);

        for (BigInteger candidate = BigInteger.TWO; candidate.compareTo(prime) < 0; candidate = candidate.add(BigInteger.ONE)) {
            boolean isPrimitiveRoot = true;

            for (BigInteger factor : primeFactors) {
                if (candidate.modPow(phi.divide(factor), prime).equals(BigInteger.ONE)) {
                    isPrimitiveRoot = false;
                    break;
                }
            }

            if (isPrimitiveRoot) {
                return candidate;
            }
        }

        throw new IllegalArgumentException("No primitive root found.");
    }
    private Set<BigInteger> GetPrimeFactors(BigInteger n) {
        Set<BigInteger> factors = new HashSet<>();
        BigInteger factor = BigInteger.TWO;

        while (n.mod(factor).equals(BigInteger.ZERO)) {
            factors.add(factor);
            n = n.divide(factor);
        }

        factor = BigInteger.valueOf(3);

        while (factor.multiply(factor).compareTo(n) <= 0) {
            while (n.mod(factor).equals(BigInteger.ZERO)) {
                factors.add(factor);
                n = n.divide(factor);
            }
            factor = factor.add(BigInteger.TWO);
        }

        if (n.compareTo(BigInteger.ONE) > 0) {
            factors.add(n);
        }

        return factors;
    }
    /**
    * Check if a is a primitive root of q.
    * @param a Number to check
    * @param q Number to check
    * @return true or false
    */
    private boolean IsPrimitiveRoot(BigInteger a, BigInteger q) {
        BigInteger phi = q.subtract(BigInteger.ONE);
        Set<BigInteger> primeFactors = GetPrimeFactors(phi);

        // Check if a^phi/q mod p != 1 for all prime factors q of phi
        for (BigInteger factor : primeFactors) {
            if (a.modPow(phi.divide(factor), q).equals(BigInteger.ONE)) {
                return false;
            }
        }

        return true;
    }
    
    /**
    * Check prime number
    * @param a Number to check
    * @return true or false
    */
    private boolean IsPrime(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0) {
          return false;
        }
        if (n.compareTo(BigInteger.TWO) <= 0) {
          return true;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
          return false;
        }
        for (BigInteger i = BigInteger.valueOf(3);
             i.multiply(i).compareTo(n) <= 0;
             i = i.add(BigInteger.TWO)) {
          if (n.mod(i).equals(BigInteger.ZERO)) {
            return false;
          }
        }
        return true;
      }
}
