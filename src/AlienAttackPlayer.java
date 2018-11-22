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
    int[] bodyCircle = {45, 33, 18, 18};
    int[] triX = {18, 72, 45};
    int[] triY = {33, 33, 90};
    int[] wing1X = {0, 6, 6, 12, 12, 27, 27, 12};
    int[] wing1Y = {36, 36, 24, 27, 36, 36, 48, 48};
    int[] wing2X = {30, 30, 36, 36};
    int[] wing2Y = {24, 0, 6, 24};
    int[] wing3X = {54, 54, 60, 60};
    int[] wing3Y = {24, 6, 0, 24};
    int[] wing4X = {63, 78, 78, 84, 84, 90, 78, 63};
    int[] wing4Y = {36, 36, 27, 24, 36, 36, 48, 48};

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
        g2d.setColor(color2);
        g2d.fillPolygon(wing1X, wing1Y, 8);
        g2d.fillPolygon(wing2X, wing2Y, 4);
        g2d.fillPolygon(wing3X, wing3Y, 4);
        g2d.fillPolygon(wing4X, wing4Y, 8);
        g2d.setColor(color1);
        g2d.fillPolygon(triX, triY, 3);
        g2d.setColor(Color.BLUE);
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
            bodyCircle[i]/=k;
        }
        for(int i=0; i<3; i++) {
            triX[i]/=k;
            triY[i]/=k;
        }
        for(int i=0; i<8; i++) {
            wing1X[i]/=k;
            wing1Y[i]/=k;
            wing4X[i]/=k;
            wing4Y[i]/=k;
        }
        for(int i=0; i<4; i++) {
            wing2X[i]/=k;
            wing2Y[i]/=k;
            wing3X[i]/=k;
            wing3Y[i]/=k;
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