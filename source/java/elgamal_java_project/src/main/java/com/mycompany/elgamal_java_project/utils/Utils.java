/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elgamal_java_project.utils;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;


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
    public static boolean IsPrimitiveRoot(BigInteger a, BigInteger p) {
        BigInteger one = BigInteger.ONE;
        BigInteger phi = p.subtract(one);
        BigInteger[] factors = findPrimeFactors(phi);

        for (BigInteger factor : factors) {
            if (a.modPow(phi.divide(factor), p).equals(one)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Find the primitive root of a prime number.
     *
     * @param prime Prime number to find primitive root
     * @return BigInteger primitive root
     */
    public static BigInteger FindPrimitiveRoot(BigInteger p) {
        BigInteger one = BigInteger.ONE;
        BigInteger phi = p.subtract(one);
        BigInteger two = BigInteger.TWO;

        BigInteger[] factors = findPrimeFactors(phi);

        for (BigInteger g = two; g.compareTo(p) < 0; g = g.add(one)) {
            boolean isPrimitive = true;
            for (BigInteger factor : factors) {
                if (g.modPow(phi.divide(factor), p).equals(one)) {
                    isPrimitive = false;
                    break;
                }
            }
            if (isPrimitive) return g;
        }
        throw new IllegalArgumentException("Không tìm thấy phần tử nguyên thủy của " + p);
    }
    
    /**
     * Get prime factors of n.
     *
     * @param n Number to factorize
     * @return Set<BigInteger> of prime factors
     */
    public static BigInteger[] findPrimeFactors(BigInteger n) {
        BigInteger two = BigInteger.TWO;
        java.util.List<BigInteger> factors = new java.util.ArrayList<>();
        while (n.mod(two).equals(BigInteger.ZERO)) {
            if (!factors.contains(two)) factors.add(two);
            n = n.divide(two);
        }
        BigInteger i = BigInteger.valueOf(3);
        while (i.multiply(i).compareTo(n) <= 0) {
            while (n.mod(i).equals(BigInteger.ZERO)) {
                if (!factors.contains(i)) factors.add(i);
                n = n.divide(i);
            }
            i = i.add(two);
        }
        if (n.compareTo(BigInteger.ONE) > 0) factors.add(n);
        return factors.toArray(new BigInteger[0]);
    }
    
    public static String GetFile(){
        JFileChooser fileChooser = new JFileChooser();
         fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                // Cho phép các file .txt, .doc và .docx
                String extension = getFileExtension(f.getName());
                return extension.equals("txt") || extension.equals("doc") || extension.equals("docx") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Text Files (*.txt), Word Documents (*.doc, *.docx)";
            }
        });
        fileChooser.setFileSelectionMode(0);
        JFrame frame = new JFrame("Choose File");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String fileExtension = getFileExtension(fileName);
            try {
                String content = "";
                switch (fileExtension) {
                    case "txt":
                        content = readTxtFile(selectedFile);
                        break;
                    case "doc":
                        //content = readDocFile(selectedFile);
                        break;
                    case "docx":
                        //content = readDocxFile(selectedFile);
                        break;
                    default:
                        throw new IllegalArgumentException(fileExtension + " không được hỗ trợ");
                }
                return content;
            } catch (IOException ex) {
                throw new IllegalArgumentException("Không thể mở file");
            }
        }
        return null;
    }

    private static String getFileExtension(String fileName) {
    int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }
    private static String readTxtFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    public static String SaveFile(String textToSave){
        JFileChooser fileChooser = new JFileChooser();
        JFrame frame = new JFrame("Choose File");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        int saveReturnValue = fileChooser.showSaveDialog(frame);
        if (saveReturnValue == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String fileName = fileToSave.getName();
            System.out.println("fileName: " + fileName);
            if(!fileName.endsWith(".txt") && !fileName.endsWith(".doc") && !fileName.endsWith(".docs")){
                fileToSave = new File(fileToSave.getParentFile(), fileName + ".txt");
            }
            try (FileWriter fileWriter = new FileWriter(fileToSave)) {
                fileWriter.write(textToSave);
                return "Lưu file thành công!";
            } catch (IOException e) {
                throw new IllegalArgumentException("Lỗi lưu file");
            }
        }
        return null;
    }
}
