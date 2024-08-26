package org.example;

import org.example.interprete.GUI.InterpreteGui;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        //EmbeddedPython embeddedPython = new EmbeddedPython();
        //embeddedPython.initialize();

        SwingUtilities.invokeLater(InterpreteGui::initialize);
    }
}