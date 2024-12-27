package com.mycompany.elgamal_java_project.model;

import com.mycompany.elgamal_java_project.utils.Utils;
import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamalKeyGenerator {

    private ElGamalKey elgamalKey;
    private static final SecureRandom RANDOM = new SecureRandom();

    
    public ElGamalKey getKey() {
        return elgamalKey;
    }
    /**
     * Generated keys.
     *
     * @param p Prime modulus
     * @param a Primitive root
     * @return ElGamalKey contain private key and public key
     */
    public ElGamalKey GenerateKey(BigInteger p, BigInteger a) {
        if (!Utils.IsPrimitiveRoot(a, p)) {
            throw new IllegalArgumentException("Giá trị a không phải phần tử nguyên thủy của p");
        }
        if(!Utils.IsPrime(p)){
            throw new IllegalArgumentException("Giá trị p không phải số nguyên tố");
        }
        if(p.compareTo(BigInteger.valueOf(2)) <= 0){
            throw new IllegalArgumentException("Vui lòng nhập số p lớn");
        }
        if(p.compareTo(BigInteger.valueOf(1)) <= 0){
            throw new IllegalArgumentException("Vui lòng nhập số p lớn");
        }
            
        BigInteger x = GeneratePrivateKey(p);
        if (x.equals(BigInteger.ZERO)) { // Ensure private key is non-zero
            x = BigInteger.ONE;
        }
        BigInteger y = a.modPow(x, p);
        elgamalKey = new ElGamalKey(p, a, x, y);
        return elgamalKey;
    }

    /**
    * Generated keys with random q and a.
    *
    * @return ElGamalKey contain private key and public key
    */
    
    public ElGamalKey GenerateKey() {
        BigInteger p = BigInteger.probablePrime(64, RANDOM);
        try {
            BigInteger a = Utils.FindPrimitiveRoot(p);
            return GenerateKey(p, a);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
    * GeneratePrivateKey.
    *
    * @param p Prime modulus
    * @return BigInteger private key
    */
    private BigInteger GeneratePrivateKey(BigInteger p) {
        return new BigInteger(p.bitLength() - 1, RANDOM).mod(p.subtract(BigInteger.TWO)).add(BigInteger.TWO);
    }
}
