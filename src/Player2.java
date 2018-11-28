import java.awt.*;

/**
 * Implementation of Player class for design 1
 * @author Rick Sear
 */
public class Player2 extends Player {
    static final long serialVersionUID = 1L;

    Color color1 = new Color(0, 179, 179);
    Color color2 = new Color(0, 102, 102);
    int[] bodyGeomX = {3, 3, 5,  5};
    int[] bodyGeomY = {9, 2, 3, 10};
    int[] triX = {};
    int[] triY = {};
    int[] oval1 = {};
    int[] oval2 = {};
    int[] oval3 = {};

    public Player2(int startSize, int screenSize) {
        super(startSize, screenSize);
    }

    // @Override
    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     Graphics2D g2d = (Graphics2D) g;
    //     int k = super.getPlayerSize()/30;
    //     g2d.setColor(color2);
    //     int[] theseWingsX = new int[wingsX.length];
    //     int[] theseWingsY = new int[wingsY.length];
    //     for(int i=0; i<wingsX.length; i++) {
    //         theseWingsX[i] = wingsX[i] * k;
    //         theseWingsY[i] = wingsY[i] * k;
    //     }
    //     g2d.fillPolygon(theseWingsX, theseWingsY, wingsX.length);
    //     g2d.setColor(color1);
    //     int[] thisTriX = new int[triX.length];
    //     int[] thisTriY = new int[triY.length];
    //     for(int i=0; i<triX.length; i++) {
    //         thisTriX[i] = triX[i] * k;
    //         thisTriY[i] = triY[i] * k;
    //     }
    //     g2d.fillPolygon(thisTriX, thisTriY, 3);
    //     int[] thisBody = new int[bodyCircle.length];
    //     for(int i=0; i<bodyCircle.length; i++) {
    //         thisBody[i] = bodyCircle[i] * k;
    //     }
    //     g2d.fillOval(thisBody[0], thisBody[1], thisBody[2], thisBody[2]);
    // }
}