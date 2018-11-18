/**
 * CSCI 2113 - Project 2 - Alien Attack
 * @author Rick Sear
 */

import javax.swing.*;
import java.awt.*;

public class AlienAttackAlien extends JComponent {
    static final long serialVersionUID = 1L;
    private int gameScreenSize;
    private int alienSize;

    public AlienAttackAlien(int size, int startX, int screenSize) {
        alienSize = size;
        gameScreenSize = screenSize;
        setSize(alienSize, alienSize);
        setLocation(startX, 0);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        int[] xPoints = {0, alienSize, alienSize/2};
        int[] yPoints = {0, 0, alienSize};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    /**
     * Moves alien downwards on game screen by specified number of pixels
     * @param howManyPixels number of pixels to move the alien down by
     * @return whether or not the alien is still visible
     */
    public boolean moveAlienDown(int howManyPixels) {
        double currY = getLocation().getY();
        currY+=howManyPixels;
        if(currY>=gameScreenSize) {     //if this is true, then the alien is off-screen
            return false;               //signal parent to remove element
        } else {
            setLocation((int)getLocation().getX(), (int)currY);
            return true;                //signal parent to keep element
        }
    }

    public int getAlienSize() {
        return alienSize;
    }

}