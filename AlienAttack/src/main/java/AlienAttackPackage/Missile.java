package AlienAttackPackage;
import javax.swing.*;
import java.awt.*;

/**
 * Class for Missile objects
 */
public class Missile extends JComponent {
    static final long serialVersionUID = 1L;
    private Color flame = new Color(255, 170, 0);
    private Color bodyColor = new Color(226, 226, 226);
    private int[] finsX = {4, 12, 16, 0};
    private int[] finsY = {16, 16, 24, 24};
    private int[] flameX = {4, 12, 8};
    private int[] flameY = {28, 28, 40};

    /**
     * Constructor creates the missile at the specified point
     * @param startPoint upper left corner of the missile hitbox
     */
    public Missile(Point startPoint) {
        setSize(16, 40);
        setLocation(startPoint);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillOval(4, 0, 8, 8);
        g2d.fillPolygon(finsX, finsY, finsX.length);
        g2d.setColor(bodyColor);
        g2d.fillRect(4, 4, 8, 16);
        g2d.setColor(flame);
        g2d.fillArc(4, 24, 8, 8, 0, 180);
        g2d.fillPolygon(flameX, flameY, flameX.length);
    }
}