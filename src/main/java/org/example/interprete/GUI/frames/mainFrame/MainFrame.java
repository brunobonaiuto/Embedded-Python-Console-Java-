package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.Size;


import java.awt.*;

public class MainFrame implements Containters {
    private MyJFrame consoleFrame;
    private final InputPanel inputPanel;
    private final OutputPanel outputPanel;
    private static final String PYTHON_LOGO = "C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\pythonLogo.png";


    public MainFrame() {
        consoleFrame = new MyJFrame("Python Console", new Size(1150, 650), new BorderLayout());
        consoleFrame.addIcon(PYTHON_LOGO);
        outputPanel = new OutputPanel();
        outputPanel.addContainers();
        inputPanel = new InputPanel(outputPanel);
        inputPanel.addContainers();
    }

    @Override
    public void addContainers(){
        consoleFrame.add(outputPanel.get(), BorderLayout.CENTER);
        consoleFrame.add(inputPanel.get(), BorderLayout.SOUTH);
        consoleFrame.setVisible(true);
        consoleFrame.pack();
    }
}
