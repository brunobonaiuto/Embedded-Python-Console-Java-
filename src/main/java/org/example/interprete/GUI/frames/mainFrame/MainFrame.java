package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.Size;


import java.awt.*;

public class MainFrame {
    private MyFrame consoleFrame;
    private final InputPanel inputPanel;
    private final OutputPanel outputPanel;

    public MainFrame() {
        addContainers();
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();
    }

    private static final String PYTHON_LOGO = "C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\pythonLogo.png";
    public void createJFrameWithContainers(){
        consoleFrame = new MyFrame("Python Console", new Size(1150, 650), new BorderLayout());
        consoleFrame.addIcon(PYTHON_LOGO);
    }

    private void addContainers(){
        consoleFrame.add(outputPanel.create(), BorderLayout.CENTER);
        consoleFrame.add(inputPanel.create(), BorderLayout.SOUTH);
    }
}
