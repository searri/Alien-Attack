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
    private int maxXCoord;

    public AlienAttackPlayer(int startSize, int screenSize) {
        playerSize = startSize;
        gameScreenSize = screenSize;
        maxXCoord = screenSize-startSize;
        setSize(playerSize, playerSize);
        setLocation(calcXStartCoord(), calcYStartCoord());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.fillOval(0, 0, playerSize, playerSize);
    }

    public int calcXStartCoord() {
        return (int)((gameScreenSize-playerSize)/2);
    }

    public int calcYStartCoord() {
        return (int)(gameScreenSize-5-playerSize);
    }

    public int getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(int newSize) {
        playerSize = newSize;
    }

    /**
     * Move the player left, but within the game frame
     * @param howManyPixels number of pixels to move left
     */
    public void movePlayerLeft(int howManyPixels) {
        double currX = getLocation().getX();
        currX-=howManyPixels;
        if(currX<0) {
            setLocation(0, (int)getLocation().getY());
        } else {
            setLocation((int)currX, (int)getLocation().getY());
        }
    }

    /**
     * Move player right, but within the game frame
     * @param howManyPixels: number of pixels to move right
     */
    public void movePlayerRight(int howManyPixels) {
        double currX = getLocation().getX();
        currX+=howManyPixels;
        if(currX>maxXCoord) {
            setLocation(maxXCoord, (int)getLocation().getY());
        } else {
            setLocation((int)currX, (int)getLocation().getY());
        }
    }

    /**
     * Shrinks player down one size
     * @return true if player is dead
     */
    public boolean shrinkPlayerSize() {
        if(playerSize == 30) {
            return true;
        } else {
            playerSize-=30;
            setSize(playerSize, playerSize);
            setLocation(calcXStartCoord(), calcYStartCoord());
            return false;
        }
    }
}