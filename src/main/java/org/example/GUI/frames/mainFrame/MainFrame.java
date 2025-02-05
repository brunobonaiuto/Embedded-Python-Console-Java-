package org.example.GUI.frames.mainFrame;

import org.example.GUI.MyJFrame;
import org.example.GUI.ContainerSize;
import org.example.GUI.frames.mainFrame.io.InputPanel;
import org.example.GUI.frames.mainFrame.io.OutputPanel;
import org.example.Input;
import org.example.Output;


import java.awt.*;

public class MainFrame {
    private final MyJFrame consoleFrame;
    private final InputPanel inputPanel;
    private final OutputPanel outputPanel;
    private static final String PYTHON_LOGO = "C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\frames\\mainFrame\\icons\\pythonLogo.png";

    public MainFrame(Input inputChanel, Output outputChanel) {
        consoleFrame = new MyJFrame("Python Console", new ContainerSize(1150, 650), new BorderLayout());
        consoleFrame.addIcon(PYTHON_LOGO);
        outputPanel = (OutputPanel) outputChanel;
        outputPanel.addContainers();
        inputPanel = (InputPanel) inputChanel;
        inputPanel.addContainers();
    }

    public void addContainers(){
        consoleFrame.add(outputPanel.get(), BorderLayout.CENTER);
        consoleFrame.add(inputPanel.get(), BorderLayout.SOUTH);
        consoleFrame.setVisible(true);
        consoleFrame.pack();
    }
}
