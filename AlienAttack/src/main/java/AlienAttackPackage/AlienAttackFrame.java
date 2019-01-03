package AlienAttackPackage;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Class for the overarching active game frame; this contains all the game elements
 * @author Rick Sear
 */
public class AlienAttackFrame extends JFrame {
    static final long serialVersionUID = 1L;
    private AlienGraphicsPanel graphicsPanel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton endButton;
    private JPanel optionPanel;
    private Timer gameTimer;
    private Queue<Integer> commandQueue;    //0 - left, 1 - right, 2 - fire, else - do nothing
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

    public AlienAttackFrame(AlienAttackMenu menu, int playerType, Rectangle frameSize) {

        //Housekeeping
        readConfigFile();
        setTitle("Alien Attack!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainMenu = menu;
        menu.setVisible(false);

        //set up game engine to trigger on timer pulse
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
        graphicsPanel = new AlienGraphicsPanel(new Dimension(frameSize.width-32, frameSize.height-164), playerType);

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
     * Initializes member variables
     */
    public void readConfigFile() {
        cycleTime = 50;
        AlienGraphicsPanel.largeAlienSpeed = 5;
        AlienGraphicsPanel.medAlienSpeed = 8;
        AlienGraphicsPanel.smallAlienSpeed = 10;
        playerSpeed = 30;
        increaseInterval = 100;
        increaseSize = 1;
        AlienGraphicsPanel.maxAliens = 2;
        AlienGraphicsPanel.minAliens = 0;
        AlienGraphicsPanel.largeAlienValue = 50;
        AlienGraphicsPanel.medAlienValue = 25;
        AlienGraphicsPanel.smallAlienValue = 10;
        spawnInterval = 40;
        AlienGraphicsPanel.missileSpeed = 10;
    }

    /**
     * Hide current game window and show menu screen, passing in the game's score.
     * When a new game starts, there will no longer be an active reference to this game frame, so garbage collection will take care of it
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
            } else if(c==32) {      //spacebar: fire missile
                graphicsPanel.launchMissile();
            } else if(c==27) {      //escape: Pause
                pauseButton.doClick();
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

            //if START was selected...
            if(obj == startButton) {
                gameTimer.start();

                //disable START and enable everything else
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);

                AlienAttackFrame.this.getContentPane().requestFocus();

            //if PAUSE was selected
            } else if(obj == pauseButton) {
                gameTimer.stop();

                //enable START and disable everything else
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);

            //if END was selected
            } else if(obj == endButton) {
                gameTimer.stop();
                openMainMenu();
            } else {
                System.out.println("ERROR");
            }
        }
    }

    
    /**
     * Inner class to handle modifying game elements when triggered by clock
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
                currCommand = 3;
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

            //MISSILE MOVEMENT - move all missiles up
            graphicsPanel.advanceMissiles();

            //MISSILE HITS - Check for missile hits
            graphicsPanel.aliensHit();

            //GAME DIFFICULTY - check if it's time to increase game difficulty
            if(cyclesElapsed%increaseInterval == 0) {
                graphicsPanel.increaseMaxAliens(increaseSize);
                graphicsPanel.increaseMinAliens(increaseSize);
            }

            //SCORE - update score display
            int currScore = graphicsPanel.getScore();
            scoreBoard.setText(Integer.toString(currScore));


            //PLAYER HIT - test to see if player has been hit
            if(graphicsPanel.playerIsHit()) {
                //if so, flash the screen red and clear aliens from the screen (more playable than just removing one alien)
                graphicsPanel.setBackground(Color.RED);
                graphicsPanel.clearScreen();

                //if player is dead, show score and elapsed time
                if(graphicsPanel.getPlayer().shrinkPlayerSize()) {
                    gameTimer.stop();
                    JOptionPane.showMessageDialog(
                        AlienAttackFrame.this, 
                        "Your score: "+graphicsPanel.getScore()+"\n"
                        +"Elapsed time: "+(cyclesElapsed/20)+"s",
                        "You died!",
                        JOptionPane.PLAIN_MESSAGE
                    );

                    //trigger the END button event
                    endButton.doClick();
                }
            }

            //Refresh game screen
            AlienAttackFrame.this.repaint();
        }
    }

}
