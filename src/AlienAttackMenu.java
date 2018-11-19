import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AlienAttackMenu extends JPanel {
    static final long serialVersionUID = 1L;
    private int gameScreenSize;
    private GridBagConstraints gc;
    private JTextField gameTitle;
    private JButton startButton;
    private AlienAttackFrame parentFrame;

    public AlienAttackMenu(int screenSize, AlienAttackFrame parent) {

        //Initialization housekeeping
        gameScreenSize = screenSize;
        setPreferredSize(new Dimension(gameScreenSize, gameScreenSize));
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        parentFrame = parent;

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
        this.add(gameTitle, gc);

        //Add start button to panel
        gc.gridy = 2;
        this.add(startButton, gc);

    }

    public class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.closeMainMenu();
        }
    }
}