package org.example.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyJPanel extends JPanel{
    private static final Border greenBorder = BorderFactory.createLineBorder(new Color(34,123,148), 4);;

    public MyJPanel(ContainerSize containerSize, LayoutManager layout) {
        this.setPreferredSize(new Dimension(containerSize.getWidth(), containerSize.getHeight()));
        this.setLayout(layout);
        this.setBorder(greenBorder);
    }
}
