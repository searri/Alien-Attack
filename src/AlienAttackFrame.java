/**
 * CSCI 2113 - Project 2 - Alien Attack
 * 
 * @author Rick Sear
 *
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AlienAttackFrame extends JFrame {
    static final long serialVersionUID = 1L;
    private int gameFrameSize;
    private AlienGraphicsPanel graphicsPanel;
    private JButton leftButton;
    private JButton rightButton;
    private JPanel controlPanel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton endButton;
    private JPanel optionPanel;
    
    public AlienAttackFrame() {
        readConfigFile();

        //Controls - initialize and add
        leftButton = new JButton("< LEFT");
        rightButton = new JButton("RIGHT >");
        ControlListener controlsListener = new ControlListener();
        controlPanel = new JPanel();
        leftButton.addActionListener(controlsListener);
        rightButton.addActionListener(controlsListener);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        controlPanel.add(leftButton);
        controlPanel.add(rightButton);

        //Options - initialize and add
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        endButton = new JButton("End");
        optionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        optionPanel.add(startButton);
        optionPanel.add(pauseButton);
        optionPanel.add(endButton);

        //Initialize graphics panel
        graphicsPanel = new AlienGraphicsPanel(gameFrameSize);

        //Add panels to main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(graphicsPanel, BorderLayout.CENTER);   
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        getContentPane().add(optionPanel, BorderLayout.NORTH);

        //Housekeeping
        setTitle("Alien Attack [Alpha]");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public void readConfigFile() {

        gameFrameSize = 900;

    }

    public class ControlListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if(obj == leftButton) {
                System.out.println("LEFT");
            } else if(obj == rightButton) {
                System.out.println("RIGHT");
            } else {
                System.out.println("ERROR");
            }
        }

    }

}
