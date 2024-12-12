/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.elgamal_java_project;

import com.mycompany.elgamal_java_project.controller.ElGamalController;
import com.mycompany.elgamal_java_project.view.ElGamalScreen;

/**
 *
 * @author Tobi - Dev
 */
public class Elgamal_java_project {

    public static void main(String[] args) {
        ElGamalScreen view = new ElGamalScreen();
        new ElGamalController(view);
    }
}
