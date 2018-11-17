/**
 * CSCI 2113 - Project 2 - Alien Attack
 * @author Rick Sear
 */

import javax.swing.*;
import java.awt.*;

public class AlienAttackAlien extends JComponent {
    static final long serialVersionUID = 1L;
    private int alienSize;

    public AlienAttackAlien(int startSize, int startX, int startY) {
        alienSize = startSize;
        setSize(alienSize, alienSize);
        setLocation(startX, startY);
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

}