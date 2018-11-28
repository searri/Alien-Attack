/**
 * CSCI 2113 - Project 2 - Alien Attack
 */

import javax.swing.*;
import java.awt.*;

/**
 * Class for the player
 * @author Rick Sear
 */
public class AlienAttackPlayer extends JComponent {
    static final long serialVersionUID = 1L;
    private int playerSize;
    private int gameScreenSize;
    private int maxXCoord;

    Color color1 = new Color(96, 32, 96);
    Color color2 = new Color(57, 19, 57);
    int[] bodyCircle = {9, 5, 12};
    int[] triX = {6, 24, 15};
    int[] triY = {11, 11, 30};
    int[] wingsX = { 4,  0,  2, 2, 4,  4, 10, 10, 12, 12, 18, 18, 20, 20, 26, 26, 28, 28, 30, 26};
    int[] wingsY = {16, 12, 12, 8, 9, 12, 12,  0,  2, 12, 12,  3,  0, 12, 12,  9,  8, 12, 12, 16};

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
        int k = playerSize/30;
        g2d.setColor(color2);
        int[] theseWingsX = new int[wingsX.length];
        int[] theseWingsY = new int[wingsY.length];
        for(int i=0; i<wingsX.length; i++) {
            theseWingsX[i] = wingsX[i] * k;
            theseWingsY[i] = wingsY[i] * k;
        }
        g2d.fillPolygon(theseWingsX, theseWingsY, wingsX.length);
        g2d.setColor(color1);
        int[] thisTriX = new int[triX.length];
        int[] thisTriY = new int[triY.length];
        for(int i=0; i<triX.length; i++) {
            thisTriX[i] = triX[i] * k;
            thisTriY[i] = triY[i] * k;
        }
        g2d.fillPolygon(thisTriX, thisTriY, 3);
        int[] thisBody = new int[bodyCircle.length];
        for(int i=0; i<bodyCircle.length; i++) {
            thisBody[i] = bodyCircle[i] * k;
        }
        g2d.fillOval(thisBody[0], thisBody[1], thisBody[2], thisBody[2]);
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
            maxXCoord = gameScreenSize-playerSize;
            setLocation(calcXStartCoord(), calcYStartCoord());
            return false;
        }
    }
}