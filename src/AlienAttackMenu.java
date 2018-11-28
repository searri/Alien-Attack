/**
 * CSCI 2113 - Project 2 - Alien Attack
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
    private JTextField gameTitle;
    private JButton startButton;
    private JPanel menuPanel;
    private ArrayList<HiScoreNode> highScores;
    private JPanel leaderBoard;
    private JPanel leaderBoardContent;
    private JTextArea scoreAreaNames;
    private JTextArea scoreAreaScores;
    private JTextField scoreLabel;
    private JButton showHiScores;
    private JButton backToMenu;

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

        //Initialize start button
        startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        StartListener startListener = new StartListener();
        startButton.addActionListener(startListener);

        //Initialize leaderboard button
        showHiScores = new JButton("VIEW HIGH SCORES");
        showHiScores.setFont(new Font("Arial", Font.PLAIN, 14));
        HiScoreListener hiScoreListener = new HiScoreListener();
        showHiScores.addActionListener(hiScoreListener);
        backToMenu = new JButton("Back to Menu");
        backToMenu.addActionListener(hiScoreListener);

        //Initialize leaderboard component
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

        //Add title field to panel
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        menuPanel.add(gameTitle, gc);

        //Add start button to panel
        gc.gridy = 3;
        menuPanel.add(startButton, gc);

        //Add leaderboard button to panel
        gc.gridy = 4;
        menuPanel.add(showHiScores, gc);

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
            Scanner fileReader = new Scanner(new File("project-2-searri/src/resources/hiscores.txt"));
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] data = line.split(" ");
                int score = Integer.parseInt(data[1]);
                String name = data[0];
                HiScoreNode newNode = new HiScoreNode(score, name);
                highScores.add(newNode);
            }
            fileReader.close();
            Collections.sort(highScores);

        //catch various exceptions that could occur and inform the user (these aren't fatal, so no system exit)
        } catch(IOException e) {
            System.out.println("ERROR: Could not load High Scores list.");
        } catch(NumberFormatException e) {
            System.out.println("ERROR: The High Scores list is not in an acceptable format.");
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
            FileWriter writer = new FileWriter("project-2-searri/src/resources/hiscores.txt");
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
            AlienAttackFrame attackFrame = new AlienAttackFrame(AlienAttackMenu.this);
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
}