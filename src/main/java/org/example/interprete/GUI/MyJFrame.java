package org.example.interprete.GUI;

import org.example.interprete.GUI.Size;

import javax.swing.*;
import java.awt.*;

public class MyJFrame extends JFrame {

    public MyJFrame(String title, Size size, LayoutManager layout){
        this.setTitle(title);
        this.setSize(size.getWidth(), size.getHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(layout);
    }

    public void addIcon(String path){
        ImageIcon imageIcon = new ImageIcon(path);
        this.setIconImage(imageIcon.getImage());
    }
}
