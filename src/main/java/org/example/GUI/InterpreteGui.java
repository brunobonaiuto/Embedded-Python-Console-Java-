package org.example.GUI;

import org.example.GUI.frames.mainFrame.MainFrame;
import org.example.Input;
import org.example.Output;

public class InterpreteGui {
    public static void initializeWindowComponents(Input inputPanel, Output outputPanel){
        MainFrame mainFrame = new MainFrame(inputPanel, outputPanel);
        mainFrame.addContainers();
    }
}
