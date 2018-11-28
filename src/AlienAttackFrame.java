/**
 * CSCI 2113 - Project 2 - Alien Attack
 */
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Class for the overarching game frame; this contains all the game elements
 * @author Rick Sear
 */
public class AlienAttackFrame extends JFrame {
    static final long serialVersionUID = 1L;
    private int gameFrameSize;
    private AlienGraphicsPanel graphicsPanel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton endButton;
    private JPanel optionPanel;
    private Timer gameTimer;
    private Queue<Integer> commandQueue;    //0 - left, 1 - right, else - do nothing
    private JTextField scoreBoard;
    private JTextField scoreLabel;
    private JPanel upperFields;
    private AlienAttackMenu mainMenu;

    //CUSTOMIZABLE VALUES (read in via config file)
    private int cycleTime;                  //how often the game clock cycles (milliseconds)
    private int playerSpeed;                //how far the player moves per cycle
    private int increaseInterval;           //how many cycles until difficulty increases
    private int increaseSize;               //how many more aliens spawn when difficulty increases
    private int spawnInterval;              //how many cycles until new aliens are spawned

    public AlienAttackFrame(AlienAttackMenu menu) {

        //Housekeeping
        readConfigFile();
        gameFrameSize = 900;
        setTitle("Alien Attack!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainMenu = menu;
        menu.setVisible(false);

        //set up game engine
        gameTimer = new Timer(cycleTime, new GameEngine());
        commandQueue = new LinkedList<Integer>();

        //Options - initialize and add
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        endButton = new JButton("End");
        OptionsListener optionListener = new OptionsListener();
        startButton.addActionListener(optionListener);
        pauseButton.addActionListener(optionListener);
        endButton.addActionListener(optionListener);
        optionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        optionPanel.add(startButton);
        optionPanel.add(pauseButton);
        optionPanel.add(endButton);

        //Scoreboard - initialize
        scoreBoard = new JTextField("");
        scoreLabel = new JTextField("- SCORE -");
        scoreBoard.setHorizontalAlignment(JTextField.CENTER);
        scoreLabel.setHorizontalAlignment(JTextField.CENTER);
        scoreBoard.setEditable(false);
        scoreLabel.setEditable(false);

        //Initialize graphics panel
        graphicsPanel = new AlienGraphicsPanel(gameFrameSize);

        //Initialize upper info panel
        upperFields = new JPanel(new BorderLayout());
        upperFields.add(optionPanel, BorderLayout.SOUTH);
        upperFields.add(scoreBoard, BorderLayout.CENTER);
        upperFields.add(scoreLabel, BorderLayout.NORTH);

        //Add panels to main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(graphicsPanel, BorderLayout.CENTER);
        getContentPane().add(upperFields, BorderLayout.NORTH);

        //Final housekeeping
        startButton.doClick();
        pack();
        getContentPane().addKeyListener(new ControlsListener());
        getContentPane().requestFocus();
    }

    /**
     * Reads a text config file to set options for the game
     */
    public void readConfigFile() {

        try {
            //temporary ArrayList to store settings
            ArrayList <String> configSettings = new ArrayList<String>();
            
            //setup Scanner to read in config file from resources directory
            Scanner fileReader = new Scanner(new File("project-2-searri/src/resources/AlienAttack.properties"));
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] data = line.split(" ");
                configSettings.add(data[1]);    //only add the numerical values to the ArrayList
            }
            fileReader.close();

            //initialize member variables to the values read in from config file
            cycleTime = Integer.parseInt(configSettings.get(1));
            AlienGraphicsPanel.largeAlienSpeed = Integer.parseInt(configSettings.get(2));
            AlienGraphicsPanel.medAlienSpeed = Integer.parseInt(configSettings.get(3));
            AlienGraphicsPanel.smallAlienSpeed = Integer.parseInt(configSettings.get(4));
            playerSpeed = Integer.parseInt(configSettings.get(5));
            increaseInterval = Integer.parseInt(configSettings.get(6));
            increaseSize = Integer.parseInt(configSettings.get(7));
            AlienGraphicsPanel.maxAliens = Integer.parseInt(configSettings.get(8));
            AlienGraphicsPanel.minAliens = Integer.parseInt(configSettings.get(9));
            AlienGraphicsPanel.largeAlienValue = Integer.parseInt(configSettings.get(10));
            AlienGraphicsPanel.medAlienValue = Integer.parseInt(configSettings.get(11));
            AlienGraphicsPanel.smallAlienValue = Integer.parseInt(configSettings.get(12));
            spawnInterval = Integer.parseInt(configSettings.get(13));

        //catch various exceptions that could occur, inform the user, and close the game
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "Could not access config file. Exiting game.");
            System.exit(0);
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Problem reading config file. Exiting game.");
            System.exit(0);
        }
    }

    /**
     * Hide current game window and show menu screen, passing in the game's score
     */
    public void openMainMenu() {
        this.setVisible(false);
        mainMenu.examineScore(graphicsPanel.getScore());
        mainMenu.setVisible(true);
    }

    /**
     * Inner class to listen for keyboard input and then add the appropriate movement command to the command queue
     * @see java.awt.event.KeyListener
     */
    public class ControlsListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent key) {
            int c = key.getKeyCode();
            if(c == 37) {   //left arrow key
                commandQueue.add(0);    //0: LEFT
            } else if(c == 39) {    //right arrow key
                commandQueue.add(1);    //1: RIGHT
            } else {
                System.out.println("UNACCEPTABLE INPUT");
            }
        }
    
        @Override
        public void keyReleased(KeyEvent e) {}
    
        @Override
        public void keyTyped(KeyEvent key) {}
    }

    /**
     * Inner class to start and stop the game clock when prompted by the user
     * @see java.awt.event.ActionListener
     */
    public class OptionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if(obj == startButton) {
                gameTimer.start();

                //disable START and enable everything else
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);

            } else if(obj == pauseButton) {
                gameTimer.stop();

                //enable START and disable everything else
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);

            } else if(obj == endButton) {
                gameTimer.stop();
                openMainMenu();
            } else {
                System.out.println("ERROR");
            }
        }
    }

    
    /**
     * Inner class to handle moving game elements when triggered by clock
     * @see java.awt.event.ActionListener
     */
    public class GameEngine implements ActionListener {
        private int cyclesElapsed = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            cyclesElapsed++;    //increment cycles elapsed

            //PLAYER MOVEMENT - take the next commandQueue item (if it's empty, don't move the player)
            int currCommand;
            try {
                currCommand = commandQueue.remove();
            } catch(NoSuchElementException i) { //.remove() throws this exception if Queue is empty
                currCommand = 2;
            }

            if(currCommand==0) {            //O: LEFT
                graphicsPanel.getPlayer().movePlayerLeft(playerSpeed);
            } else if(currCommand==1) {     //1: RIGHT
                graphicsPanel.getPlayer().movePlayerRight(playerSpeed);
            }

            //ALIEN MOVEMENT - move all aliens down, generate more aliens
            graphicsPanel.moveAllAliensDown();
            if((cyclesElapsed % spawnInterval) == 0) {
                graphicsPanel.addRandomAliens();
            }

            //GAME DIFFICULTY - check if it's time to increase game difficulty
            if(cyclesElapsed%increaseInterval == 0) {
                System.out.println(cyclesElapsed+": INCREASE DIFFICULTY");
                graphicsPanel.increaseMaxAliens(increaseSize);
                graphicsPanel.increaseMinAliens(increaseSize);
            }

            //SCORE - update score display
            int currScore = graphicsPanel.getScore();
            scoreBoard.setText(Integer.toString(currScore));

            //PLAYER HIT - test to see if player has been hit
            if(graphicsPanel.playerIsHit()) {
                graphicsPanel.setBackground(Color.RED);
                graphicsPanel.clearScreen();

                //if player is dead
                if(graphicsPanel.getPlayer().shrinkPlayerSize()) {
                    gameTimer.stop();
                    JOptionPane.showMessageDialog(
                        AlienAttackFrame.this, 
                        "Your score: "+graphicsPanel.getScore()+"\n"
                        +"Elapsed time: "+(cyclesElapsed/20)+"s",
                        "You died!",
                        JOptionPane.PLAIN_MESSAGE
                    );
                    endButton.doClick();
                }
            }

            //Refresh game screen
            AlienAttackFrame.this.repaint();
        }
    }

}
