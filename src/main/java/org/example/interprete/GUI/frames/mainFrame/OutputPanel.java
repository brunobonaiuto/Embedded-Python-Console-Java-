package org.example.interprete.GUI.frames.mainFrame;

import org.example.interprete.GUI.Size;

import java.awt.*;

public class OutputPanel {
    public MyPanel create(){
        return new MyPanel(new Size(1150, 600),new BorderLayout());
    }
}
