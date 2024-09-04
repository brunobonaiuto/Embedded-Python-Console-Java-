package org.example;

import org.example.interprete.engine.EmbeddedPython;

import org.example.interprete.GUI.InterpreteGui;
import org.example.interprete.GUI.frames.mainFrame.io.InputPanel;
import org.example.interprete.GUI.frames.mainFrame.io.OutputPanel;
import org.example.interprete.Input;
import org.example.interprete.Output;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        //create input and output from outside
//        Output outputPanel = new DefaultOutput();
//        Input inputPanel = new DefaultInput();

        Output outputPanel = new OutputPanel();
        Input inputPanel = new InputPanel(outputPanel);

        SwingUtilities.invokeLater(() -> InterpreteGui.initializeWindowComponents(inputPanel, outputPanel));

        EmbeddedPython embeddedPython = new EmbeddedPython(inputPanel,outputPanel);
        embeddedPython.initialize();
    }
}