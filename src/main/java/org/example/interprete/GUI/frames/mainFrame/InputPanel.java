package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.MyJPanel;
import org.example.interprete.GUI.Size;
import org.example.interprete.io.Input;
import org.example.interprete.io.Output;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.TableHeaderUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class InputPanel implements Input, KeyListener {
    private static final Border greenBorder = BorderFactory.createLineBorder(Color.GRAY, 4);;
    private static Output outputPanel;
    private JButton runButton;
    private final MyJPanel inputJPanel;
    private JTextField inputTextField;
    private String temporaryInput = " ";

    public InputPanel(Output outputPanel) {
        this.outputPanel = outputPanel;
        inputJPanel = new MyJPanel(new Size(1150,100), new BorderLayout());
        inputTextField = jTextField();
    }

    public MyJPanel get(){
        return inputJPanel;
    }

    public void addContainers() {
        inputJPanel.add(inputTextField, BorderLayout.CENTER);
        runButton = runButton();
        inputJPanel.add(runButton, BorderLayout.EAST);
    }

    private JTextField jTextField() {
        JTextField inputTextField = new JTextField();
        inputTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputTextField.setForeground(Color.WHITE);
        inputTextField.setBackground(Color.DARK_GRAY);
        inputTextField.setCaretColor(Color.WHITE);
        inputTextField.setText(" >>> ");
        inputTextField.setBorder(greenBorder);
        //
        inputTextField.addKeyListener(this);
        inputTextField.setFocusTraversalKeysEnabled(false);
        return inputTextField;
    }

    private JButton runButton() {
        ImageIcon imageIcon = new ImageIcon( "C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\frames\\mainFrame\\icons\\playButton.png");
        JButton runButton = new JButton();
        runButton.setText("Run");
        runButton.setFocusable(false);
        runButton.setIcon(imageIcon);
        runButton.setHorizontalTextPosition(JButton.CENTER);
        runButton.setVerticalTextPosition(JButton.BOTTOM);
        runButton.setFont(new Font("Comic Sans", Font.BOLD, 18));
        runButton.setForeground(Color.BLACK);
        runButton.setBackground(Color.gray);
        runButton.setBorder(BorderFactory.createEtchedBorder());
        runButton.addActionListener(e -> {
            temporaryInput = inputTextField.getText().replace(" >>> ", "");
            inputTextField.setText(" >>> ");
            if(!temporaryInput.equals("")){
                outputPanel.toConsole(temporaryInput+"\n");
            }
            else {
                outputPanel.toConsole("\n");
            }
        });
        return runButton;
    }

    @Override
    public String fromConsole() {
        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if(!temporaryInput.equals(" ")){
                String aux = temporaryInput;
                temporaryInput = " ";
                return aux;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //keyTyped = invoked when a key is typed. Uses .keyChar method as input, outputs a char

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //keyPressed = invoked when a physical key is pressed down. Uses .keyCode as input, outputs a int
        if(e.getKeyCode() == 10){
            runButton.doClick();
        }
        if (e.getKeyCode() == 9){
            String currentText = inputTextField.getText();
            inputTextField.setText(currentText+"    ");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //keyReleased = called whenever a button is released
        System.out.println("enter is char: "+ e.getKeyChar());
        System.out.println("enter is code: "+ e.getKeyCode());
    }
}
