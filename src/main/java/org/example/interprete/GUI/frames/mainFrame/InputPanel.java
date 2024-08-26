package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.Size;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InputPanel implements Containters{
    private static final Border greenBorder = BorderFactory.createLineBorder(Color.GRAY, 4);;
    private static OutputPanel outputPanel;

    private MyJPanel inputPanel;

    public InputPanel(OutputPanel outputPanel) {
        this.outputPanel = outputPanel;
        inputPanel = new MyJPanel(new Size(1150,100), new BorderLayout());
    }

    public MyJPanel get(){
        return inputPanel;
    }

    @Override
    public void addContainers() {
        JTextField inputTextField = jTextField();

        inputPanel.add(inputTextField, BorderLayout.CENTER);
        inputPanel.add(runButton(inputTextField), BorderLayout.EAST);
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
        //creates a button
        JButton runButton = new JButton();
        /* set the size and position of the button -> not used at the moment
           because the container that holds this panel (inputPanel) its using a flowLayout,
           otherwise it would be useful
        */
        //used only if the container has setted the layout to null
        //runButton.setBounds(950,50,150,100);
        //set the text of the button
        runButton.setText("Run");
        //remove the border from text within the button
        runButton.setFocusable(false);
        //add an icon to the button
        runButton.setIcon(imageIcon);
        //perform any action by clicking the button
        runButton.addActionListener(e-> {
            System.out.println("Action here");
            String input = inputTextField.getText();
            inputTextField.setText(" >>> ");
            input = input.replace(">>> ", "");
            //System.out.println("this is the text from JTextField: "+ inputTextField.getText();
            //outputTextArea.setText(input);
            outputPanel.displayToConsole(input);

        });
        //set the position of the text inside the button -> X axes
        runButton.setHorizontalTextPosition(JButton.CENTER);
        //set the position of the text inside the button -> Y ayes
        runButton.setVerticalTextPosition(JButton.BOTTOM);
        //set the font of the button (font, bold/italic../ sizeOfFont)
        runButton.setFont(new Font("Comic Sans", Font.BOLD, 18));
        //set the color of the text
        runButton.setForeground(Color.BLACK);
        //set the color of the button
        runButton.setBackground(Color.gray);
        //set border for button
        runButton.setBorder(BorderFactory.createEtchedBorder());
        return runButton;
    }


}
