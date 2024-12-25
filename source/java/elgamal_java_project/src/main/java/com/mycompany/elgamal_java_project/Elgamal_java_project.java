package com.mycompany.elgamal_java_project;

import com.mycompany.elgamal_java_project.View.ElgamalView;
import com.mycompany.elgamal_java_project.controller.ElGamalController;
import com.mycompany.elgamal_java_project.view.ElGamalScreen;

public class Elgamal_java_project {

    public static void main(String[] args) {
        ElgamalView view = new ElgamalView();
        view.show();
        ElGamalController controller = new ElGamalController(view);
        view.SetController(controller);
    }
}
