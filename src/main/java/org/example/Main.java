package org.example;

import org.example.GUI.InterpreteGui;
import org.example.GUI.frames.mainFrame.io.InputPanel;
import org.example.GUI.frames.mainFrame.io.OutputPanel;
import org.example.interprete.engine.EmbeddedPython;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
//       create input and output from outside
//       Input inputPanel = new InputConsole();
//       Output outputPanel  = new OutputConsole();

        Output outputPanel = new OutputPanel();
        Input inputPanel = new InputPanel(outputPanel);

        SwingUtilities.invokeLater(() -> InterpreteGui.initializeWindowComponents(inputPanel, outputPanel));

        new EmbeddedPython(inputPanel, outputPanel).initialize();
    }
}