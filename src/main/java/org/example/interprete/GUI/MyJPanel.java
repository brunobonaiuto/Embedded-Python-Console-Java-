package org.example.interprete.GUI;

import org.example.interprete.GUI.Size;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyJPanel extends JPanel{
    private static final Border greenBorder = BorderFactory.createLineBorder(new Color(34,123,148), 4);;

    public MyJPanel(Size size, LayoutManager layout) {
        this.setPreferredSize(new Dimension(size.getWidth(), size.getHeight()));
        this.setLayout(layout);
        this.setBorder(greenBorder);
    }
}
