package com.mycompany.elgamal_java_project.model;

import java.math.BigInteger;

public class PublicKeyModal {
    
    public final BigInteger p; 
    public final BigInteger a; 
    public final BigInteger y; 
    
    // Constructor
    public PublicKeyModal(BigInteger _p, BigInteger _a, BigInteger _y){
        this.p = _p;
        this.a = _a;
        this.y = _y;
    }
    
    public PublicKeyModal(String publicKeyInput){
        try {
           // Cắt chuỗi thành các phần tử và chuyển thành BigInteger
           String[] parts = publicKeyInput.split(",");
           if (parts.length != 3) {
               throw new IllegalArgumentException("Dữ liệu khoá công khai không hợp lệ. Phải gồm 3 phần: q, a, y.");
           }
           BigInteger p = new BigInteger(parts[0].trim());
           BigInteger a = new BigInteger(parts[1].trim());
           BigInteger y = new BigInteger(parts[2].trim());

           // Kiểm tra giá trị khóa công khai
           if (!p.isProbablePrime(100)) {
               throw new IllegalArgumentException("Giá trị q phải là số nguyên tố.");
           }
           if (a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(p) >= 0) {
               throw new IllegalArgumentException("Giá trị a phải thuộc khoảng (1, q).");
           }
           if (y.compareTo(BigInteger.ONE) <= 0 || y.compareTo(p) >= 0) {
               throw new IllegalArgumentException("Giá trị y phải thuộc khoảng (1, q).");
           }

           this.p = p;
           this.a = a;
           this.y = y;
       } catch (Exception e) {
           throw new IllegalArgumentException("Lỗi khi phân tích khoá công khai: " + e.getMessage());
       }
    }
    
    public String ToString(){
        return p.toString() + "," + a.toString() + "," + y.toString();
    }
}
