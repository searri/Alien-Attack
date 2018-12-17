package AlienAttackPackage;
import javax.swing.*;
import java.awt.*;

/**
 * Class for alien enemies
 * @author Rick Sear
 */
public class Alien extends JComponent {
    static final long serialVersionUID = 1L;
    private int gameScreenSize;
    private int alienSize;

    //points to draw Alien ship shape
    static int[] xPoints = { 0,  2, 2, 4,  4,  6, 6, 8,  8, 10, 11, 11, 13, 13, 15, 17, 17, 19, 19, 20, 22, 22, 24, 24, 26, 26, 28, 28, 30, 24, 27, 24, 15,  6,  3,  6};
    static int[] yPoints = {10, 10, 6, 7, 10, 10, 8, 9, 10, 10,  7,  0,  2,  4,  0,  4,  2,  0,  7, 10, 10,  9,  8, 10, 10,  7,  6, 10, 10, 18, 23, 23, 30, 23, 23, 18};
    static int[] triX = {15, 27, 3};
    static int[] triY = {0, 23, 23};

    /**
     * Constructor for Alien
     * @param size the 30, 60, or 90
     * @param startX starting location of the Alien
     * @param screenSize size of the game screen
     */
    public Alien(int size, int startX, int screenSize) {
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
        int[] alienXPoints = new int[xPoints.length];
        int[] alienYPoints = new int[yPoints.length];
        int k = alienSize/30;
        for(int i=0; i<xPoints.length; i++) {
            alienXPoints[i] = xPoints[i]*k;     //scale up the outline of the ship based on Alien size
            alienYPoints[i] = yPoints[i]*k;
        }
        int[] triXPoints = new int[triX.length];
        int[] triYPoints = new int[triY.length];
        for(int i=0; i<triX.length; i++) {
            triXPoints[i] = triX[i]*k;     //scale up the outline of the ship based on Alien size
            triYPoints[i] = triY[i]*k;
        }
        g2d.fillPolygon(alienXPoints, alienYPoints, alienXPoints.length);
        g2d.setColor(new Color(65, 204, 63));
        g2d.fillPolygon(triXPoints, triYPoints, 3);
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