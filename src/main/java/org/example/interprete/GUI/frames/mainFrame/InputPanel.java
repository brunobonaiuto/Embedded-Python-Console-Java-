package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.MyJPanel;
import org.example.interprete.GUI.Size;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InputPanel {
    private static final Border greenBorder = BorderFactory.createLineBorder(Color.GRAY, 4);;
    private static OutputPanel outputPanel;

    private final MyJPanel inputJPanel;

    public InputPanel(OutputPanel outputPanel) {
        this.outputPanel = outputPanel;
        inputJPanel = new MyJPanel(new Size(1150,100), new BorderLayout());
    }

    public MyJPanel get(){
        return inputJPanel;
    }

    public void addContainers() {
        JTextField inputTextField = jTextField();
        inputJPanel.add(inputTextField, BorderLayout.CENTER);
        inputJPanel.add(runButton(inputTextField), BorderLayout.EAST);
    }

    private static JTextField jTextField() {
        JTextField inputTextField = new JTextField();
        inputTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputTextField.setForeground(Color.WHITE);
        inputTextField.setBackground(Color.DARK_GRAY);
        inputTextField.setCaretColor(Color.WHITE);
        inputTextField.setText(" >>> ");
        inputTextField.setBorder(greenBorder);
        return inputTextField;
    }

    private static JButton runButton(JTextField inputTextField) {
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\playButton.png");
        JButton runButton = new JButton();
        runButton.setText("Run");
        runButton.setFocusable(false);
        runButton.setIcon(imageIcon);
        runButton.addActionListener(e-> {
            String input = inputTextField.getText();
            inputTextField.setText(" >>> ");
            input = input.replace(">>> ", "");
            outputPanel.displayToConsole(input);
        });
        runButton.setHorizontalTextPosition(JButton.CENTER);
        runButton.setVerticalTextPosition(JButton.BOTTOM);
        runButton.setFont(new Font("Comic Sans", Font.BOLD, 18));
        runButton.setForeground(Color.BLACK);
        runButton.setBackground(Color.gray);
        runButton.setBorder(BorderFactory.createEtchedBorder());
        return runButton;
    }
}
