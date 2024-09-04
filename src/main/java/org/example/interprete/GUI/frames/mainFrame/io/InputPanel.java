package org.example.interprete.GUI.frames.mainFrame.io;

import org.example.interprete.GUI.MyJPanel;
import org.example.interprete.GUI.Size;
import org.example.interprete.Input;
import org.example.interprete.Output;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class InputPanel implements Input, KeyListener {
    private static final Border GRAY_BORDER = BorderFactory.createLineBorder(Color.GRAY, 4);
    private static Output outputPanel;
    private final ArrayList listOfCommands;
    private JButton runButton;
    private final MyJPanel inputJPanel;
    private JTextField inputTextField;
    private JLabel inputSymbol;
    private String temporaryInput = " ";
    private int currentCommand;

    public InputPanel(Output outputPanel) {
        this.outputPanel = outputPanel;
        inputJPanel = new MyJPanel(new Size(1150,100), new BorderLayout());
        inputTextField = jTextField();
        inputSymbol = jLabel();
        //create object
        listOfCommands = new ArrayList<>();
        currentCommand = 0;
    }

    public MyJPanel get(){
        return inputJPanel;
    }

    public void addContainers() {
        inputJPanel.add(inputSymbol, BorderLayout.WEST);
        inputJPanel.add(inputTextField, BorderLayout.CENTER);
        runButton = runButton();
        inputJPanel.add(runButton, BorderLayout.EAST);
    }


    private JLabel jLabel(){
        JLabel inputSymbol = new JLabel();
        inputSymbol.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputSymbol.setForeground(Color.WHITE);
        inputSymbol.setBackground(new Color(23,59,69));
        //inputSymbol.setBackground(Color.DARK_GRAY);
        inputSymbol.setOpaque(true);
        inputSymbol.setText(" >>> ");
        //inputSymbol.setBorder( BorderFactory.createLineBorder(Color.GRAY, 4));
        inputSymbol.setFocusable(true);
        Thread thread = new Thread(this::symbolChecker);
        thread.start();
        return inputSymbol;
    }

    private JTextField jTextField() {
        JTextField inputTextField = new JTextField();
        inputTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputTextField.setForeground(Color.WHITE);
        inputTextField.setBackground(new Color(23,59,69));
        inputTextField.setCaretColor(Color.WHITE);
        inputTextField.setText("");
        inputTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 0));
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
        runButton.setBackground(new Color(34,123,148));
        runButton.setBorder(BorderFactory.createEtchedBorder());
        runButton.addActionListener(e -> {
            temporaryInput = inputTextField.getText();//.replace(" >>> ", "");
            if(!temporaryInput.isBlank() && !listOfCommands.contains(temporaryInput)){
                listOfCommands.add(temporaryInput);
            }
            currentCommand = listOfCommands.size();
            inputTextField.setText("");
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

    public void symbolChecker(){
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            if(inputTextField.getText().startsWith("\t")){
                inputSymbol.setText(" ... ");
            }else {
                inputSymbol.setText(" >>> ");
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
            inputTextField.setText(currentText+"\t");
        }
        if(e.getKeyCode() == 38){
            if(currentCommand > 0){
                currentCommand--;
                inputTextField.setText(listOfCommands.get(currentCommand).toString());

           }
        }
        if(e.getKeyCode() == 40){
            if(currentCommand < listOfCommands.size()-1){
                currentCommand++;
                inputTextField.setText(listOfCommands.get(currentCommand).toString());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
