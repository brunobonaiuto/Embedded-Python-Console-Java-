package org.example.interprete.GUI;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    MyFrame(String title, Color color){
        //set the title of a Frame
        this.setTitle(title);
        //set the color of a frame
        this.setBackground(color);
        //set the action of closing frame, since by default it just hide the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addIcon(String path){
        //create a icon from a given path
        ImageIcon imageIcon = new ImageIcon(path);
        //set the icon of the frame (converts the icon to an image)
        this.setIconImage(imageIcon.getImage()); //requires an image, not an icon
    }
}
