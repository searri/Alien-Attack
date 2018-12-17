package AlienAttackPackage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

/**
 * Class for game menu screen; handles the leaderboard and calls instances of AlienAttackFrame
 * @author Rick Sear
 */
public class AlienAttackMenu extends JFrame {
    static final long serialVersionUID = 1L;
    private int gameScreenSize;
    private GridBagConstraints gc;
    private ArrayList<HiScoreNode> highScores;
    private int currPlayer;

    //main menu components
    private JTextField gameTitle;
    private JButton startButton;
    private JPanel menuPanel;
    private JButton showHiScores;
    private JTextField tutorial1;
    private JTextField tutorial2;

    //leaderboard components
    private JPanel leaderBoard;
    private JPanel leaderBoardContent;
    private JTextArea scoreAreaNames;
    private JTextArea scoreAreaScores;
    private JTextField scoreLabel;
    private JButton backToMenu;

    //player sample components
    private JTextField selectPlayer;
    private JPanel playerSamples;
    private Player1 p1Sample;
    private Player2 p2Sample;
    private Player3 p3Sample;

    public AlienAttackMenu() {

        //Initialization housekeeping
        gameScreenSize = 900;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setPreferredSize(new Dimension(gameScreenSize, gameScreenSize));
        gc = new GridBagConstraints();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Alien Attack Menu");
        highScores = new ArrayList<HiScoreNode>();

        //Initialize title field
        gameTitle = new JTextField("Alien Attack!");
        gameTitle.setEditable(false);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 32));
        gameTitle.setHorizontalAlignment(JTextField.CENTER);

        //Initializae tutorial field
        tutorial1 = new JTextField("Use the LEFT/RIGHT arrow keys to move");
        tutorial1.setEditable(false);
        tutorial1.setFont(new Font("Arial", Font.PLAIN, 16));
        tutorial1.setHorizontalAlignment(JTextField.CENTER);
        tutorial2 = new JTextField("Use SPACEBAR to shoot");
        tutorial2.setEditable(false);
        tutorial2.setFont(new Font("Arial", Font.PLAIN, 16));
        tutorial2.setHorizontalAlignment(JTextField.CENTER);

        //Initialize start button
        startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        StartListener startListener = new StartListener();
        startButton.addActionListener(startListener);
        startButton.setEnabled(false);

        //Initialize leaderboard button
        showHiScores = new JButton("VIEW HIGH SCORES");
        showHiScores.setFont(new Font("Arial", Font.PLAIN, 14));
        HiScoreListener hiScoreListener = new HiScoreListener();
        showHiScores.addActionListener(hiScoreListener);
        backToMenu = new JButton("Back to Menu");
        backToMenu.addActionListener(hiScoreListener);

        //Initialize leaderboard component and hide it
        scoreAreaScores = new JTextArea();
        scoreAreaNames = new JTextArea();
        scoreLabel = new JTextField("LEADERBOARD");
        scoreLabel.setHorizontalAlignment(JTextField.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        leaderBoard = new JPanel(new BorderLayout());
        leaderBoardContent = new JPanel();
        leaderBoardContent.add(scoreAreaNames);
        leaderBoardContent.add(scoreAreaScores);
        leaderBoard.add(scoreLabel, BorderLayout.NORTH);
        leaderBoard.add(backToMenu, BorderLayout.SOUTH);
        leaderBoard.add(leaderBoardContent, BorderLayout.CENTER);
        leaderBoard.setPreferredSize(new Dimension(gameScreenSize, gameScreenSize));
        leaderBoard.setBackground(Color.WHITE);
        leaderBoardContent.setBackground(Color.WHITE);
        leaderBoard.setVisible(false);

        //Initialize player appearance samples
        currPlayer = 0;
        PlayerSelector mouseSelection = new PlayerSelector();
        p1Sample = new Player1(90, 900);
        p1Sample.setLocation(new Point(10, 10));
        p1Sample.addMouseListener(mouseSelection);
        p2Sample = new Player2(90, 900);
        p2Sample.setLocation(new Point(110, 10));
        p2Sample.addMouseListener(mouseSelection);
        p3Sample = new Player3(90, 900);
        p3Sample.setLocation(new Point(210, 10));
        p3Sample.addMouseListener(mouseSelection);
        selectPlayer = new JTextField("Select your player:");
        selectPlayer.setEditable(false);
        selectPlayer.setFont(new Font("Arial", Font.PLAIN, 24));
        selectPlayer.setHorizontalAlignment(JTextField.CENTER);

        //Add player samples to the sample panel
        playerSamples = new JPanel();
        playerSamples.setLayout(null);
        playerSamples.add(p1Sample);
        playerSamples.add(p2Sample);
        playerSamples.add(p3Sample);
        playerSamples.setPreferredSize(new Dimension(320, 110));

        //Add title field to panel
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        menuPanel.add(gameTitle, gc);

        //Add player samples panel to menu panel
        gc.gridy = 2;
        menuPanel.add(selectPlayer, gc);
        gc.gridy = 3;
        menuPanel.add(playerSamples, gc);

        //Add start button to panel
        gc.gridy = 4;
        menuPanel.add(startButton, gc);

        //Add leaderboard button to panel
        gc.gridy = 5;
        menuPanel.add(showHiScores, gc);

        //Add tutorial to panel
        gc.gridy = 7;
        menuPanel.add(tutorial1, gc);
        gc.gridy = 8;
        menuPanel.add(tutorial2, gc);

        //final housekeeping
        getContentPane().add(leaderBoard);
        getContentPane().add(menuPanel);
        pack();
    }

    /**
     * Reads in high scores from 'hiscores.txt'. Scores must be formatted as 'Name(String) Score(Int)'
     */
    public void readHighScores() {
        try {
            //setup Scanner to read in config file from resources directory
            Scanner fileReader = new Scanner(new File("config/hiscores.txt"));

            //read in scores/names from resources document and save them to an ArrayList
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] data = line.split(" ");
                int score = Integer.parseInt(data[1]);
                String name = data[0];
                HiScoreNode newNode = new HiScoreNode(score, name);
                highScores.add(newNode);
            }
            fileReader.close();

            //sort ArrayList
            Collections.sort(highScores);

        //catch various exceptions that could occur and inform the user (these aren't fatal, so no system exit)
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "ERROR: Could not load High Scores list.");
            System.exit(0);
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: The High Scores list is not in an acceptable format.");
            System.exit(0);
        }
    }

    /**
     * Takes a score from an AlienAttackFrame and compares it to the current scores. If it's in the top 10, takes a username
     */
    public void examineScore(int score){
        readHighScores();
        int lowestScore = highScores.get(9).getScore();

        //if in the top 10:
        if(score>lowestScore) {
            String userName = (String)JOptionPane.showInputDialog(
                this, 
                "Congrats on your top 10 score! Enter your name:", 
                "HIGH SCORE!", 
                JOptionPane.PLAIN_MESSAGE
            );
            if(userName==null || userName=="" || userName==" ") {
                //handle empty input without crashing
                userName = "NoName";
            } else {
                //remove spaces from username to prevent errors reading file
                userName = userName.replaceAll(" ", "");
            }

            //impose limit on length of username (45 characters)
            if(userName.length() > 45) {
                userName = userName.substring(0, 46);
            }

            //add score to leaderboard list and re-sort it
            HiScoreNode newNode = new HiScoreNode(score, userName);
            highScores.add(newNode);
            Collections.sort(highScores);

        //user didn't get a top 10 score...
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Sorry, looks like you didn't make the leaderboard!",
                "Better luck next time...",
                JOptionPane.PLAIN_MESSAGE
            );
        }

        //save scores to text file and clear the ArrayList
        printHighScores();
        highScores.clear();
    }

    /**
     * Prints the first 10 entries of the leaderboard Arraylist to 'hiscores.txt'
     */
    public void printHighScores() {
        try {
            FileWriter writer = new FileWriter("config/hiscores.txt");
            for(int i=0; i<10; i++) {
                writer.write(highScores.get(i).toString());
                writer.write("\n");
            }
            writer.close();
        } catch(IOException e) {
            //not a fatal error, no system exit
            System.out.println("ERROR: Error writing high scores file.");
        }
    }

    /**
     * Inner class to initialize a new AlienAttackFrame if start button is pressed
     */
    public class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AlienAttackFrame attackFrame = new AlienAttackFrame(AlienAttackMenu.this, currPlayer);
            attackFrame.setVisible(true);
            AlienAttackMenu.this.setVisible(false);
        }
    }

    /**
     * Inner class to switch between the leaderboard panel and menu panel (changes visibility of panels)
     */
    public class HiScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if(src==showHiScores) {
                //switching to leaderboard, read in top 10 scores from file and add to text area
                readHighScores();
                String leaderboardNameContent = "\n";
                String leaderboardScoreContent = "\n";
                for(int i=0; i<10; i++) {
                    leaderboardNameContent+=highScores.get(i).getName();
                    leaderboardNameContent+="\n";
                    leaderboardScoreContent+=highScores.get(i).getScore();
                    leaderboardScoreContent+="\n";
                }
                scoreAreaNames.setText(leaderboardNameContent);
                scoreAreaScores.setText(leaderboardScoreContent);
                menuPanel.setVisible(false);
                leaderBoard.setVisible(true);
                pack();

                //save high scores to text file and clean out leaderboard Arraylist
                printHighScores();
                highScores.clear();
            } else if(src==backToMenu) {
                //switching to menu, hide the leaderboard and redraw the menu
                leaderBoard.setVisible(false);
                menuPanel.setVisible(true);
                pack();
            }
        }
    }

    /**
     * Inner class to listen for mouse clicks on the player selection field and update the game player as necessary
     */
    public class PlayerSelector implements MouseListener {
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseClicked(MouseEvent e) {
            Object obj = e.getSource();

            //add a border around the selected player
            Border redLine = BorderFactory.createLineBorder(Color.RED);
            if(obj==p1Sample) {
                p1Sample.setBorder(redLine);
                p2Sample.setBorder(null);
                p3Sample.setBorder(null);
                startButton.setEnabled(true);
                currPlayer = 1;
            } else if(obj==p2Sample) {
                p1Sample.setBorder(null);
                p2Sample.setBorder(redLine);
                p3Sample.setBorder(null);
                startButton.setEnabled(true);
                currPlayer = 2;
            } else if(obj==p3Sample) {
                p1Sample.setBorder(null);
                p2Sample.setBorder(null);
                p3Sample.setBorder(redLine);
                startButton.setEnabled(true);
                currPlayer = 3;
            } else {
                //should never reach this point
                System.out.println("Unknown object.");
            }
            AlienAttackMenu.this.repaint();
        }
        
    }
}