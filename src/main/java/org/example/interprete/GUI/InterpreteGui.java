package org.example.interprete.GUI;

import org.example.interprete.GUI.frames.mainFrame.MainFrame;
import org.example.interprete.Input;
import org.example.interprete.Output;

public class InterpreteGui {
    public static void initializeWindowComponents(Input inputPanel, Output outputPanel){
        MainFrame mainFrame = new MainFrame(inputPanel, outputPanel);
        mainFrame.addContainers();
    }
}
