package AlienAttackPackage;
import java.awt.*;

/**
 * Implementation of Player class for design 3
 * @author Rick Sear
 */
public class Player3 extends Player {
    static final long serialVersionUID = 1L;

    Color color1 = new Color(0, 51, 204);
    Color color2 = new Color(0, 102, 255);
    int[] bodyGeom1X = {9, 11, 11, 19, 19, 21, 21,  9};
    int[] bodyGeom1Y = {3,  4,  9,  9,  4,  3, 10, 10};
    int[] bodyGeom2X = {4, 6,  6, 24, 24, 26, 26,  4};
    int[] bodyGeom2Y = {6, 7, 12, 12,  7,  6, 13, 13};
    int[] triX = {15, 19, 11};
    int[] triY = { 0,  7,  7};
    int[] oval1 = {6, 6, 18, 18};
    int[] oval2 = {0, 11, 10, 10};
    int[] oval3 = {20, 11, 10, 10};
    int[] arc = {5, 10, 20, 20, 180, 180};

    public Player3(int startSize, Dimension screenSize) {
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
        int[] thisArc = new int[arc.length];
        for(int i=0; i<oval1.length; i++) {
            thisOval1[i] = oval1[i] * k;
            thisOval2[i] = oval2[i] * k;
            thisOval3[i] = oval3[i] * k;
            thisArc[i] = arc[i] * k;
        }
        thisArc[4] = arc[4];
        thisArc[5] = arc[5];
        int[] thisBody1X = new int[bodyGeom1X.length];
        int[] thisBody1Y = new int[bodyGeom1Y.length];
        int[] thisBody2X = new int[bodyGeom2X.length];
        int[] thisBody2Y = new int[bodyGeom2Y.length];
        for(int i=0; i<bodyGeom1X.length; i++) {
            thisBody1X[i] = bodyGeom1X[i] * k;
            thisBody1Y[i] = bodyGeom1Y[i] * k;
            thisBody2X[i] = bodyGeom2X[i] * k;
            thisBody2Y[i] = bodyGeom2Y[i] * k;
        }
        int[] thisTriX = new int[triX.length];
        int[] thisTriY = new int[triY.length];
        for(int i=0; i<triX.length; i++) {
            thisTriX[i] = triX[i] * k;
            thisTriY[i] = triY[i] * k;
        }

        g2d.setColor(color2);
        g2d.fillArc(thisArc[0], thisArc[1], thisArc[2], thisArc[3], thisArc[4], thisArc[5]);
        g2d.fillPolygon(thisBody1X, thisBody1Y, thisBody1X.length);
        g2d.setColor(color1);
        g2d.fillPolygon(thisBody2X, thisBody2Y, thisBody2X.length);
        // g2d.fillPolygon(thisTriX, thisTriY, 3);
        g2d.fillOval(thisOval3[0], thisOval3[1], thisOval3[2], thisOval3[3]);
        g2d.fillOval(thisOval2[0], thisOval2[1], thisOval2[2], thisOval2[3]);
        g2d.setColor(color2);
        g2d.fillOval(thisOval1[0], thisOval1[1], thisOval1[2], thisOval1[3]);
        
    }
}