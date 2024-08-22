package org.example.interprete.GUI;

import javax.swing.*;
import java.awt.*;

public class InterpreteGui {


    public static void pythonConsole(){
        // Create a JFrame (the main window for the application).
        JFrame frame = new JFrame("Python Console");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JLayeredPane layeredPane = new JLayeredPane();
        frame.add(layeredPane);

        JPanel outputLine = createJpanel(Color.BLACK, 0, 0, 400, 250);
        JPanel inputLine = createJpanel(Color.GRAY, 0, 250, 400, 150);

        JButton runLine = new JButton("Run");
        runLine.setBounds(100,100,100,100);
        inputLine.add(runLine, BorderLayout.EAST);


        layeredPane.add(outputLine, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(inputLine, JLayeredPane.DEFAULT_LAYER);

        frame.setVisible(true);
    }

    private static JPanel createJpanel(Color color, int x, int y, int width, int height) {
        JPanel panel1 = new JPanel();
        panel1.setBackground(color);
        panel1.setBounds(x,y,width, height);
        return panel1;
    }
}
