/**
 * CSCI 2113 - Project 2 - Alien Attack
 * 
 * @author Rick Sear
 *
 */
import javax.swing.*;
import javax.swing.Timer;
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
    private Timer gameTimer;

    private Queue<Integer> commandQueue;    //0 - left, 1 - right, else - do nothing
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
        gameFrameSize = 900;
        setTitle("Alien Attack [Alpha]");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set up game engine
        gameTimer = new Timer(cycleTime, new GameEngine());
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

        //Final housekeeping
        gameTimer.start();
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
            cycleTime = Integer.parseInt(configSettings.get(1));
            largeAlienStartSpeed = Integer.parseInt(configSettings.get(2));
            medAlienStartSpeed = Integer.parseInt(configSettings.get(3));
            smallAlienStartSpeed = Integer.parseInt(configSettings.get(4));
            playerSpeed = Integer.parseInt(configSettings.get(5));
            increaseInterval = Integer.parseInt(configSettings.get(6));
            startMaxAliens = Integer.parseInt(configSettings.get(7));
            startMinAliens = Integer.parseInt(configSettings.get(8));
            largeAlienValue = Integer.parseInt(configSettings.get(9));
            medAlienValue = Integer.parseInt(configSettings.get(10));
            smallAlienValue = Integer.parseInt(configSettings.get(11));
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "Could not access config file. Starting game with default values.");
            setDefaultValues();
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Problem reading config file. Starting game with default values.");
            setDefaultValues();
        }
    }

    public void setDefaultValues() {
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

    public class GameEngine implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int currCommand;
            try {
                currCommand = commandQueue.remove();
            } catch(NoSuchElementException i) {
                currCommand = 2;
            }

            if(currCommand==0) {
                System.out.println("MOVE LEFT");
            } else if(currCommand==1) {
                System.out.println("MOVE RIGHT");
            } else {
                System.out.println("DON'T MOVE");
            }
        }
    }

}
