package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.Size;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyPanel extends JPanel{
    private static final Border greenBorder = BorderFactory.createLineBorder(Color.GRAY, 4);;

    public MyPanel(Size size, LayoutManager layout) {
        this.setPreferredSize(new Dimension(size.getWidth(), size.getHeight()));
        this.setLayout(layout);
        this.setBorder(greenBorder);

    }
}
