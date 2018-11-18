/**
 * CSCI 2113 - Project 2 - Alien Attack
 * 
 * @author Rick Sear
 *
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

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
    private Queue<Integer> commandQueue;    //0 - left, 1 - right
    private int cycleTime;                  //how often the game clock cycles (milliseconds)
    private int largeAlienStartSpeed;       //how far Large aliens move per cycle
    private int medAlienStartSpeed;         //how far Medium aliens move per cycle
    private int smallAlienStartSpeed;       //how far Small aliens move per cycle
    private int playerSpeed;                //how far the player moves per cycle
    private int increaseInterval;           //how many cycles until difficulty increases
    private int increaseSize;               //how many more aliens spawn when difficulty increases
    private int startMaxAliens;             //starting max number of aliens
    private int startMinAliens;             //starting min number of aliens
    private int largeAlienValue;            //point values for each alien class
    private int medAlienValue;
    private int smallAlienValue;

    public AlienAttackFrame() {
        
        //Housekeeping
        readConfigFile();
        setTitle("Alien Attack [Alpha]");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        commandQueue = new LinkedList<Integer>();

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

        pack();
    }

    public void readConfigFile() {

        try {
            ArrayList <String> configSettings = new ArrayList<String>();
            Scanner fileReader = new Scanner(new File("project-2-searri/src/resources/AlienAttack.properties"));
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] data = line.split(" ");
                configSettings.add(data[1]);
            }
            fileReader.close();
            System.out.println(configSettings);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "Could not access config file. Starting game with default values.");
            cycleTime = 200;
            largeAlienStartSpeed = 20;
            medAlienStartSpeed = 30;
            smallAlienStartSpeed = 40;
            playerSpeed = 40;
            increaseInterval = 20;
            increaseSize = 2;
            startMaxAliens = 4;
            startMinAliens = 1;
            largeAlienValue = 50;
            medAlienValue = 25;
            smallAlienValue = 10;
        }
    }

    public class ControlListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if(obj == leftButton) {
                commandQueue.add(0);
            } else if(obj == rightButton) {
                commandQueue.add(1);
            } else {
                System.out.println("ERROR");
            }
        }

    }

}
