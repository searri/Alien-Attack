/**
 * CSCI 2113 - Project 2 - Alien Attack
 * @author Rick Sear
 */

import javax.swing.*;
import java.awt.*;

public class AlienGraphicsPanel extends JPanel {
    static final long serialVersionUID = 1L;
    private AlienAttackPlayer player;

    public AlienGraphicsPanel(int frameSize) {
        setPreferredSize(new Dimension(frameSize, frameSize));
        setBackground(Color.BLACK);
        setLayout(null);
        player = new AlienAttackPlayer(90, frameSize);
        add(player);
        add(new AlienAttackAlien(90));
    }

}