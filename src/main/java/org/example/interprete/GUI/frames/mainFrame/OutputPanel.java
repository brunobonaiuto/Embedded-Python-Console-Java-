package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.Size;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OutputPanel implements Containters{
    private static final Border greenBorder = BorderFactory.createLineBorder(Color.GRAY, 4);;
    private MyJPanel outputPanel;
    private JTextArea textArea;

    public OutputPanel() {
        outputPanel = new MyJPanel(new Size(1150, 600),new BorderLayout());

    }

    public MyJPanel get(){
        return outputPanel;
    }

    @Override
    public void addContainers() {
        outputPanel.add(jScrollPane());
    }

    public JScrollPane jScrollPane(){
        textArea = jTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setBackground(Color.GRAY);
        scrollPane.setBorder(greenBorder);
        return scrollPane;
    }

    private JTextArea jTextArea() {
        JTextArea outputTextArea = new JTextArea();
        outputTextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
        //set color of the font
        outputTextArea.setForeground(Color.WHITE);
        //set the color of the textField
        outputTextArea.setBackground(Color.DARK_GRAY);
        //set the color of the InputWaiting line
        //outputTextArea.setCaretColor(Color.WHITE);
        outputTextArea.setText(" Welcome to Python \n +---------------------------------------+ \n");
        outputTextArea.setEditable(false);
        return outputTextArea;
    }

    public void displayToConsole(String result){
        textArea.append(">>> " + result + "\n");
    }
}
