package org.example.interprete.GUI;

import javax.swing.*;
import java.awt.*;

public class InterpreteGui {

    public static void pythonConsole(){
        //---------------------------------------------------------------
        MyFrame consoleFrame = createFrame();
        //---------------------------------------------------------------
        //JButton
        JButton runButton = createRunButton();
        //---------------------------------------------------------------
        //JPanel -> by default they are flow layout
        JPanel outputPanel = createJPanel(Color.black, 400);
        JPanel inputPanel = createJPanel(Color.GRAY, 250);
        //------------------------------------
        //layout Manager = defines the natural (defaults) layout for components within a container
        /*
        set layout null -> requires to the components to specify where do they want to be place,
        in this case, the runButton needs to setBounds when layout is null
         */
        //inputPanel.setLayout(null);

        /*
        set layout new BorderLayout() -> does not require to the component the desired location,
        instead is the panel that will decide where is going to be placed
        */
        //inputPanel.setLayout(new BorderLayout());
        //inputPanel.add(runButton, BorderLayout.EAST);

        /*
        FlowLayout = places components in a row, sized at their preferred size,
                    if the horizontal space in the container is too small,
                    the flowLayout class uses the next available row
         */
        //flow layout for inputPanel will automatically move the components inside the panel
        inputPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 20,50)); //stick the components to the right
        //and then just add the button in this way
        //inputPanel.add(runButton);

        /*
        GridLayout = places components in a grid of cells,
                    each component takes all the available spaces inside the cell
                    and each cell is the same size
        */
        //---------------------------------------------------------------
        /*
        JLayeredPane = is used to stack panels on top of the others, like in a 3Dimension
                       is a Swing container that provides a third dimension for positioning components
        */
        //JlayeredPaneExample();

        //---------------------------------------------------------------
        //Adds
        //when frame uses Layout = new BorderLayout, set the container in this way
        consoleFrame.add(outputPanel, BorderLayout.CENTER);
        consoleFrame.add(inputPanel,BorderLayout.SOUTH);

        inputPanel.add(runButton);

        //---------------------------------------------------------------
        consoleFrame.setVisible(true);
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

    private static MyFrame createFrame() {
        //Main JFrame
        MyFrame consoleFrame = new MyFrame("Python Console", Color.WHITE);
        //set the size of a frame
        consoleFrame.setSize(1150, 650);
        //configure the layout of the frame, BY DEFAULT IS BORDER LAYOUT
        //consoleFrame.setLayout(new FlowLayout());
        consoleFrame.setLayout(new BorderLayout(10,10)); //componets inside the frame will have a gap of 10,10 between each others
        //consoleFrame.setLayout(new GridLayout(2,1,0,5));
        //set title by default is true
        //consoleFrame.setTitle(true);
        //personal method
        consoleFrame.addIcon("C:\\Users\\bbbolivar\\Documents\\MEGA\\MEGAsync\\IdeaProjects\\PythonConsole2\\src\\main\\java\\org\\example\\interprete\\GUI\\pythonLogo.png");
        return consoleFrame;
    }

    private static JPanel createJPanel(Color black, int height) {
        //create a panel
        JPanel outputPanel = new JPanel();
        //set the color of the panel
        outputPanel.setBackground(black);
        //set the size of the panel
        outputPanel.setPreferredSize(new Dimension(1150, height));
        return outputPanel;
    }

    private static JButton createRunButton() {
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
        runButton.addActionListener(e-> System.out.println("Action here"));
        //set the position of the text inside the button -> X axes
        runButton.setHorizontalTextPosition(JButton.CENTER);
        //set the position of the text inside the button -> Y ayes
        runButton.setVerticalTextPosition(JButton.BOTTOM);
        //set the font of the button (font, bold/italic../ sizeOfFont)
        runButton.setFont(new Font("Comic Sans", Font.BOLD, 18));
        //set the color of the text
        runButton.setForeground(Color.BLACK);
        //set the color of the button
        runButton.setBackground(Color.WHITE);
        //set border for button
        runButton.setBorder(BorderFactory.createEtchedBorder());
        return runButton;
    }
}
