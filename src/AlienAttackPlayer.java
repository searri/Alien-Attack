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

    Color color1 = new Color(96, 32, 96);
    Color color2 = new Color(57, 19, 57);
    int[] bodyCircle = {15, 11, 6, 6};
    int[] triX = {6, 24, 15};
    int[] triY = {11, 11, 30};
    int[] wing1X = {0, 2, 2, 4, 4, 9, 9, 4};
    int[] wing1Y = {12, 12, 8, 9, 12, 12, 16, 16};
    int[] wing2X = {10, 10, 12, 12};
    int[] wing2Y = {8, 0, 2, 8};
    int[] wing3X = {18, 18, 20, 20};
    int[] wing3Y = {8, 2, 0, 8};
    int[] wing4X = {21, 26, 26, 28, 28, 30, 26, 21};
    int[] wing4Y = {12, 12, 9, 8, 12, 12, 16, 16};

    public AlienAttackPlayer(int startSize, int screenSize) {
        setPlayerSize(startSize);
        gameScreenSize = screenSize;
        maxXCoord = screenSize-startSize;
        setSize(playerSize, playerSize);
        setLocation(calcXStartCoord(), calcYStartCoord());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color2);
        g2d.fillPolygon(wing1X, wing1Y, 8);
        g2d.fillPolygon(wing2X, wing2Y, 4);
        g2d.fillPolygon(wing3X, wing3Y, 4);
        g2d.fillPolygon(wing4X, wing4Y, 8);
        g2d.setColor(color1);
        g2d.fillPolygon(triX, triY, 3);
        g2d.fillOval(bodyCircle[2], bodyCircle[3], bodyCircle[0], bodyCircle[1]);
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
        int k = newSize/30;
        for(int i=0; i<4; i++) {
            bodyCircle[i]*=k;
        }
        for(int i=0; i<3; i++) {
            triX[i]*=k;
            triY[i]*=k;
        }
        for(int i=0; i<8; i++) {
            wing1X[i]*=k;
            wing1Y[i]*=k;
            wing4X[i]*=k;
            wing4Y[i]*=k;
        }
        for(int i=0; i<4; i++) {
            wing2X[i]*=k;
            wing2Y[i]*=k;
            wing3X[i]*=k;
            wing3Y[i]*=k;
        }
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
            setPlayerSize(playerSize-30);
            maxXCoord = gameScreenSize-playerSize;
            setSize(playerSize, playerSize);
            setLocation(calcXStartCoord(), calcYStartCoord());
            return false;
        }
    }
}