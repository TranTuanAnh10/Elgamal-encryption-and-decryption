package com.mycompany.elgamal_java_project.model;

import com.mycompany.elgamal_java_project.utils.Utils;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class ElGamalKeyGenerator {

    private ElGamalKey elgamalKey;
    private static final SecureRandom RANDOM = new SecureRandom();

    
    public ElGamalKey getKey() {
        return elgamalKey;
    }
    /**
     * Generated keys.
     *
     * @param q Prime modulus
     * @param a Primitive root
     * @return ElGamalKey contain private key and public key
     */
    public ElGamalKey GenerateKey(BigInteger q, BigInteger a) {
        if (!Utils.IsPrimitiveRoot(a, q)) {
            throw new IllegalArgumentException("Giá trị a không phải phần tử nguyên thủy của p");
        }
        if(!Utils.IsPrime(q)){
            throw new IllegalArgumentException("Giá trị p không phải số nguyên tố");
        }
        BigInteger x = GeneratePrivateKey(q);
        if (x.equals(BigInteger.ZERO)) { // Ensure private key is non-zero
            x = BigInteger.ONE;
        }
        BigInteger y = a.modPow(x, q);
        elgamalKey = new ElGamalKey(q, a, x, y);
        return elgamalKey;
    }

    /**
    * Generated keys with random q and a.
    *
    * @return ElGamalKey contain private key and public key
    */
    
    public ElGamalKey GenerateKey() {
        BigInteger q = BigInteger.probablePrime(32, RANDOM);
        try {
            BigInteger a = FindPrimitiveRoot(q);
            return GenerateKey(q, a);
        } catch (IllegalArgumentException ex) {
            System.err.println("Error: No primitive root found for q = " + q);
            return null;
        }
    }

    /**
     * GeneratePrivateKey.
     *
     * @param q Prime modulus
     * @return BigInteger private key
     */
    private BigInteger GeneratePrivateKey(BigInteger q) {
        return new BigInteger(q.bitLength() - 1, RANDOM).mod(q.subtract(BigInteger.TWO)).add(BigInteger.TWO);
    }

    /**
     * Find the primitive root of a prime number.
     *
     * @param prime Prime number to find primitive root
     * @return BigInteger primitive root
     */
    private BigInteger FindPrimitiveRoot(BigInteger prime) {
        BigInteger phi = prime.subtract(BigInteger.ONE);
        Set<BigInteger> primeFactors = Utils.GetPrimeFactors(phi);
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
}
