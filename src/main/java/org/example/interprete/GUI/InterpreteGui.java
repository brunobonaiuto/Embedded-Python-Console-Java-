package org.example.interprete.GUI;

import org.example.interprete.GUI.frames.mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InterpreteGui {
    private static final Border greenBorder = BorderFactory.createLineBorder(Color.GRAY, 4);;
    private static final String PYTHON_LOGO = "C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\pythonLogo.png";


    public static void initializeWindowComponents(){
        MainFrame mainFrame = new MainFrame();
        mainFrame.createJFrameWithContainers();

        
//        //---------------------------------------------------------------
//        //JFrame
//        MyFrame consoleFrame = new MyFrame("Python Console", new Size(1150, 650), new BorderLayout());
//        consoleFrame.addIcon(PYTHON_LOGO);

        //---------------------------------------------------------------
        //JPanel -> by default they are flow layout
        JPanel outputPanel = createJPanel(new Size(1150, 600));
        JPanel inputPanel = createJPanel(new Size(1150,100));

        //---------------------------------------------------------------
        //JTextField = A GUI textbox component that can be used to add, set or get text
        JTextField inputTextField = createTextField();

        //---------------------------------------------------------------
        //JTextArea
        JTextArea outputTextArea = getjTextArea();

        //JScrollPane for text area
        JScrollPane scrollPane = new JScrollPane(outputTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setBackground(Color.GRAY);
        scrollPane.setBorder(greenBorder);
        //TODO/FixMe/change the color of the buttom up and down



        //---------------------------------------------------------------
        //JButton
        JButton runButton = createRunButton(inputTextField, outputTextArea);

        //---------------------------------------------------------------
        //Adds
        //when frame uses Layout = new BorderLayout, set the container in this way
        consoleFrame.add(outputPanel, BorderLayout.CENTER);
        consoleFrame.add(inputPanel,BorderLayout.SOUTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        //outputPanel.add(outputTextArea, BorderLayout.NORTH);
        inputPanel.add(inputTextField, BorderLayout.CENTER);
        inputPanel.add(runButton, BorderLayout.EAST);

        //inputPanel.add(runButton);

        //---------------------------------------------------------------
        consoleFrame.setVisible(true);
        consoleFrame.pack();
    }

    private static JTextArea getjTextArea() {
        JTextArea outputTextArea = new JTextArea();
        outputTextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
        //set color of the font
        outputTextArea.setForeground(Color.WHITE);
        //set the color of the textField
        outputTextArea.setBackground(Color.DARK_GRAY);
        //set the color of the InputWaiting line
        //outputTextArea.setCaretColor(Color.WHITE);
        outputTextArea.setText(" Welcome to Python \n +---------------------------------------+ \n");
        outputTextArea.setEditable(false);
        return outputTextArea;
    }

    private static JTextField createTextField() {
        JTextField inputTextField = new JTextField();
        inputTextField.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputTextField.setForeground(Color.WHITE);
        inputTextField.setBackground(Color.DARK_GRAY);
        inputTextField.setCaretColor(Color.WHITE);
        inputTextField.setText(" >>> ");
        inputTextField.setBorder(greenBorder);
        return inputTextField;
    }

    private static void JlayeredPaneExample() {
        JLayeredPane layeredPane = new JLayeredPane();
        //make sure that the layout in which we gonna put the layeredPane is null
        //ex -> frame.setLayout(null)
        //set size and position
        layeredPane.setBounds(0,0,250,250);
        //add the layeredPane to a frame
        //frame.add(layeredPane);
        //now nothing will be display, so Add something to this stack of panels ie. some panel or some labels
        //layeredPane.add(new JPanel(), JLayerPaned.DEFAULT_LAYER);
        // or
        //layeredPane.add(new JLabel(), Integer.valueOf(0);
    }

    private static JPanel createJPanel(Size size) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(size.getWidth(), size.getHeight()));
        panel.setLayout(new BorderLayout());
        panel.setBorder(greenBorder);
        return panel;
    }

    private static JButton createRunButton(JTextField inputTextField, JTextArea outputTextArea) {
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\playButton.png");
        //creates a button
        JButton runButton = new JButton();
        /* set the size and position of the button -> not used at the moment
           because the container that holds this panel (inputPanel) its using a flowLayout,
           otherwise it would be useful
        */
        //used only if the container has setted the layout to null
        //runButton.setBounds(950,50,150,100);
        //set the text of the button
        runButton.setText("Run");
        //remove the border from text within the button
        runButton.setFocusable(false);
        //add an icon to the button
        runButton.setIcon(imageIcon);
        //perform any action by clicking the button
        runButton.addActionListener(e-> {
            System.out.println("Action here");
            String input = inputTextField.getText();
            inputTextField.setText(" >>> ");
            input = input.replace(">>> ", "");
            //System.out.println("this is the text from JTextField: "+ inputTextField.getText();
            //outputTextArea.setText(input);
            outputTextArea.append(">>> "+input +"\n");

        });
        //set the position of the text inside the button -> X axes
        runButton.setHorizontalTextPosition(JButton.CENTER);
        //set the position of the text inside the button -> Y ayes
        runButton.setVerticalTextPosition(JButton.BOTTOM);
        //set the font of the button (font, bold/italic../ sizeOfFont)
        runButton.setFont(new Font("Comic Sans", Font.BOLD, 18));
        //set the color of the text
        runButton.setForeground(Color.BLACK);
        //set the color of the button
        runButton.setBackground(Color.gray);
        //set border for button
        runButton.setBorder(BorderFactory.createEtchedBorder());
        return runButton;
    }
}
