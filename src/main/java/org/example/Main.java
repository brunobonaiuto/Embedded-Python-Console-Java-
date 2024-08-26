package org.example;

import org.example.interprete.EmbeddedPython;
import org.example.interprete.GUI.InterpreteGui;
import org.example.interprete.io.DefaultInput;
import org.example.interprete.io.DefaultOutput;
import org.example.interprete.io.Input;
import org.example.interprete.io.Output;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        //create input and output from outside
        Output defaultOutput = new DefaultOutput();
        Input defaultInput = new DefaultInput();

        EmbeddedPython embeddedPython = new EmbeddedPython(defaultInput,defaultOutput);
        embeddedPython.initialize();

        //SwingUtilities.invokeLater(InterpreteGui::initializeWindowComponents);
    }
}