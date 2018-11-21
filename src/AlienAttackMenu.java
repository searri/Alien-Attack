import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class AlienAttackMenu extends JFrame {
    static final long serialVersionUID = 1L;
    private int gameScreenSize;
    private GridBagConstraints gc;
    private JTextField gameTitle;
    private JButton startButton;
    private JPanel menuPanel;
    private ArrayList<HiScoreNode> highScores;
    private JPanel leaderBoard;
    private JTextArea scoreArea;
    private JTextField scoreLabel;
    private JButton showHiScores;

    public AlienAttackMenu() {

        //Initialization housekeeping
        gameScreenSize = 900;
        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setPreferredSize(new Dimension(gameScreenSize, gameScreenSize));
        gc = new GridBagConstraints();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Alien Attack Menu [Alpha]");
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

        //Initialize leaderboard component
        scoreArea = new JTextArea();
        scoreLabel = new JTextField("LEADERBOARD");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        leaderBoard = new JPanel(new BorderLayout());
        leaderBoard.add(scoreArea, BorderLayout.CENTER);
        leaderBoard.add(scoreLabel, BorderLayout.NORTH);
        leaderBoard.setPreferredSize(new Dimension(gameScreenSize, gameScreenSize));
        leaderBoard.setVisible(false);

        //Initialize leaderboard button
        showHiScores = new JButton("VIEW HIGH SCORES");
        showHiScores.setFont(new Font("Arial", Font.PLAIN, 14));
        HiScoreListener hiScoreListener = new HiScoreListener();
        showHiScores.addActionListener(hiScoreListener);

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

        //catch various exceptions that could occur and inform the user
        } catch(IOException e) {
            System.out.println("ERROR: Could not load High Scores list.");
        } catch(NumberFormatException e) {
            System.out.println("ERROR: The High Scores list is not in an acceptable format.");
        }

    }

    public void examineScore(int score){
        int lowestScore = highScores.get(9).getScore();
        if(score>lowestScore) {
            String userName = (String)JOptionPane.showInputDialog(
                this, 
                "Congrats on your top-10 score! Enter your name:", 
                "HIGH SCORE!", 
                JOptionPane.PLAIN_MESSAGE
            );
            HiScoreNode newNode = new HiScoreNode(score, userName);
            highScores.add(newNode);
            Collections.sort(highScores);

        } else {
            JOptionPane.showMessageDialog(
                this,
                "Sorry, looks like you didn't make the leaderboard!",
                "Better luck next time...",
                JOptionPane.PLAIN_MESSAGE
            );
        }
    }

    public void printHighScores() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("project-2-searri/src/resources/hiscores.txt"));
            for(HiScoreNode i : highScores) {
                writer.write(i.toString());
                writer.write("\n");
            }
            writer.close();
        } catch(IOException e) {
            System.out.println("ERROR: Error writing high scores file.");
        }
    }

    public class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AlienAttackFrame attackFrame = new AlienAttackFrame(AlienAttackMenu.this);
            attackFrame.setVisible(true);
            AlienAttackMenu.this.setVisible(false);
        }
    }

    public class HiScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            readHighScores();
            String leaderboardContent = "\n";
            for(HiScoreNode i : highScores) {
                leaderboardContent+=i.getName();
                leaderboardContent+="\t";
                leaderboardContent+=i.getScore();
                leaderboardContent+="\n";
            }
            scoreArea.setText(leaderboardContent);
            menuPanel.setVisible(false);
            leaderBoard.setVisible(true);
            pack();
            System.out.print(leaderboardContent);
        }
    }
}