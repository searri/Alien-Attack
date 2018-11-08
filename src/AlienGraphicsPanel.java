/**
 * CSCI 2113 - Project 2 - Alien Attack
 * @author Rick Sear
 */

import javax.swing.*;
import java.awt.*;

public class AlienGraphicsPanel extends JPanel {
    static final long serialVersionUID = 1L;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);    //space is notoriously black

        g.setColor(Color.GREEN);
        g.fillRect(10, 10, 100, 100);

        g.setColor(Color.BLUE);
        g.fillRect(20, 20, 100, 100);
    }

}