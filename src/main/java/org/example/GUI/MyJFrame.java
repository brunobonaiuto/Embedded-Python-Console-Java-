package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class MyJFrame extends JFrame {

    public MyJFrame(String title, ContainerSize containerSize, LayoutManager layout){
        this.setTitle(title);
        this.setSize(containerSize.getWidth(), containerSize.getHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(layout);
    }

    public void addIcon(String path){
        ImageIcon imageIcon = new ImageIcon(path);
        this.setIconImage(imageIcon.getImage());
    }
}
