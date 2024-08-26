package org.example.interprete.GUI;

import org.example.interprete.GUI.frames.mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InterpreteGui {
    public static void initializeWindowComponents(){
        MainFrame mainFrame = new MainFrame();
        mainFrame.addContainers();
    }
}
