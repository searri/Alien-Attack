/**
 * CSCI 2113 - Project 2 - Alien Attack
 * @author Rick Sear
 */

import javax.swing.*;
import java.awt.*;

public class AlienAttackPlayer extends JComponent {
    static final long serialVersionUID = 1L;
    private int playerSize;
    private int gameScreenSize;

    public AlienAttackPlayer(int startSize, int screenSize) {
        playerSize = startSize;
        gameScreenSize = screenSize;
        setSize(playerSize, playerSize);
        setLocation(calcXCoord(), calcYCoord());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.fillOval(0, 0, playerSize, playerSize);
    }

    public int calcXCoord() {
        return (int)((gameScreenSize-playerSize)/2);
    }

    public int calcYCoord() {
        return (int)(gameScreenSize-5-playerSize);
    }
}