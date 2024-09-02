package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.MyJPanel;
import org.example.interprete.GUI.Size;
import org.example.interprete.io.Output;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OutputPanel implements Output {
    private static final Border greenBorder = BorderFactory.createLineBorder(new Color(34,123,148), 4);
    private final MyJPanel outputJPanel;
    private JTextArea textArea;

    public OutputPanel() {
        outputJPanel = new MyJPanel(new Size(1150, 600),new BorderLayout());
    }

    public MyJPanel get(){
        return outputJPanel;
    }

    public void addContainers() {
        outputJPanel.add(jScrollPane());
    }

    public JScrollPane jScrollPane(){
        textArea = jTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setBackground(new Color(34,123,148));
        scrollPane.setBorder(greenBorder);
        return scrollPane;
    }

    private JTextArea jTextArea() {
        JTextArea outputTextArea = new JTextArea();
        outputTextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
        outputTextArea.setForeground(Color.WHITE);
        outputTextArea.setBackground(new Color(23,59,69));
        outputTextArea.setEditable(false);
        return outputTextArea;
    }

    @Override
    public void toConsole(String result) {
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.append(result);
    }
}
