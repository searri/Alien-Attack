package AlienAttackPackage;
import java.awt.*;

/**
 * Implementation of Player class for design 2
 * @author Rick Sear
 */
public class Player2 extends Player {
    static final long serialVersionUID = 1L;

    Color color1 = new Color(0, 255, 255);
    Color color2 = new Color(51, 204, 204);
    int[] bodyGeomX = {3, 3, 5,  5, 25, 25, 27, 27, 20, 23,  7, 10};
    int[] bodyGeomY = {9, 2, 3, 10, 10,  3,  2,  9, 17, 23, 23, 17};
    int[] triX = {0, 30, 15};
    int[] triY = {9,  9, 24};
    int[] oval1 = {10, 19, 10, 8};
    int[] oval2 = {7, 4, 16, 10};
    int[] oval3 = {11, 0, 8, 8};

    public Player2(int startSize, int screenSize) {
        super(startSize, screenSize);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int k = super.getPlayerSize()/30;
        int[] thisOval1 = new int[oval1.length];
        int[] thisOval2 = new int[oval2.length];
        int[] thisOval3 = new int[oval3.length];
        for(int i=0; i<oval1.length; i++) {
            thisOval1[i] = oval1[i] * k;
            thisOval2[i] = oval2[i] * k;
            thisOval3[i] = oval3[i] * k;
        }
        int[] thisBodyX = new int[bodyGeomX.length];
        int[] thisBodyY = new int[bodyGeomY.length];
        for(int i=0; i<bodyGeomX.length; i++) {
            thisBodyX[i] = bodyGeomX[i] * k;
            thisBodyY[i] = bodyGeomY[i] * k;
        }
        int[] thisTriX = new int[triX.length];
        int[] thisTriY = new int[triY.length];
        for(int i=0; i<triX.length; i++) {
            thisTriX[i] = triX[i] * k;
            thisTriY[i] = triY[i] * k;
        }

        g2d.setColor(color1);
        g2d.fillOval(thisOval1[0], thisOval1[1], thisOval1[2], thisOval1[3]);
        g2d.setColor(color2);
        g2d.fillPolygon(thisBodyX, thisBodyY, thisBodyX.length);
        g2d.fillOval(thisOval3[0], thisOval3[1], thisOval3[2], thisOval3[3]);
        g2d.setColor(color1);
        g2d.fillOval(thisOval2[0], thisOval2[1], thisOval2[2], thisOval2[3]);
        g2d.fillPolygon(thisTriX, thisTriY, 3);
    }
}