package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.Size;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyJPanel extends JPanel{
    private static final Border greenBorder = BorderFactory.createLineBorder(Color.GRAY, 4);;

    public MyJPanel(Size size, LayoutManager layout) {
        this.setPreferredSize(new Dimension(size.getWidth(), size.getHeight()));
        this.setLayout(layout);
        this.setBorder(greenBorder);
    }
}
