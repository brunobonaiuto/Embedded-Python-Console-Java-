package org.example;

import org.example.interprete.EmbeddedPython;

import org.example.interprete.GUI.InterpreteGui;
import org.example.interprete.GUI.frames.mainFrame.InputPanel;
import org.example.interprete.GUI.frames.mainFrame.OutputPanel;
import org.example.interprete.io.DefaultInput;
import org.example.interprete.io.DefaultOutput;
import org.example.interprete.io.Input;
import org.example.interprete.io.Output;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        //create input and output from outside
        Output outputPanel = new DefaultOutput();
        Input inputPanel = new DefaultInput();

//        Output outputPanel = new OutputPanel();
//        Input inputPanel = new InputPanel(outputPanel);

//        SwingUtilities.invokeLater(() -> InterpreteGui.initializeWindowComponents(inputPanel, outputPanel));

        EmbeddedPython embeddedPython = new EmbeddedPython(inputPanel,outputPanel);
        embeddedPython.initialize();
    }
}