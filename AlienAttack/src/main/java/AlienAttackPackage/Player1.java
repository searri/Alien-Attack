package AlienAttackPackage;
import java.awt.*;

/**
 * Implementation of Player class for design 1
 * @author Rick Sear
 */
public class Player1 extends Player {
    static final long serialVersionUID = 1L;

    Color color1 = new Color(204, 0, 255);
    Color color2 = new Color(153, 0, 204);
    int[] bodyCircle = {9, 5, 12};
    int[] triX = {6, 24, 15};
    int[] triY = {11, 11, 30};
    int[] wingsX = { 4,  0,  2, 2, 4,  4, 10, 10, 12, 12, 18, 18, 20, 20, 26, 26, 28, 28, 30, 26};
    int[] wingsY = {16, 12, 12, 8, 9, 12, 12,  0,  2, 12, 12,  3,  0, 12, 12,  9,  8, 12, 12, 16};

    public Player1(int startSize, int screenSize) {
        super(startSize, screenSize);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int k = super.getPlayerSize()/30;
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
}