import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AlienAttackMenu extends JFrame {
    static final long serialVersionUID = 1L;
    private int gameScreenSize;
    private GridBagConstraints gc;
    private JTextField gameTitle;
    private JButton startButton;
    private JPanel menuPanel;

    public AlienAttackMenu() {

        //Initialization housekeeping
        gameScreenSize = 900;
        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setPreferredSize(new Dimension(gameScreenSize, gameScreenSize));
        gc = new GridBagConstraints();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Alien Attack Menu [Alpha]");

        //Initialize title field
        gameTitle = new JTextField("Alien Attack!");
        gameTitle.setEditable(false);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 32));

        //Initialize start button
        startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        StartListener startListener = new StartListener();
        startButton.addActionListener(startListener);

        //Add title field to panel
        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        menuPanel.add(gameTitle, gc);

        //Add start button to panel
        gc.gridy = 2;
        menuPanel.add(startButton, gc);

        //final housekeeping
        getContentPane().add(menuPanel);
        pack();
    }

    public class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AlienAttackFrame attackFrame = new AlienAttackFrame(AlienAttackMenu.this);
            attackFrame.setVisible(true);
            AlienAttackMenu.this.setVisible(false);
        }
    }
}