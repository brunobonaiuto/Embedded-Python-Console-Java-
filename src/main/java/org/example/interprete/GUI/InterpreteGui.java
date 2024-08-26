package org.example.interprete.GUI;

import org.example.interprete.GUI.frames.mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InterpreteGui {
    private static final Border greenBorder = BorderFactory.createLineBorder(Color.GRAY, 4);;
    private static final String PYTHON_LOGO = "C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\pythonLogo.png";


    public static void initializeWindowComponents(){
        MainFrame mainFrame = new MainFrame();
        mainFrame.addContainers();


    }
}
