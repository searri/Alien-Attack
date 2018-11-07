/**
 * CSCI 2113 - Project 2 - Alien Attack
 * 
 * @author Rick Sear
 *
 */
import javax.swing.*;
import java.awt.*;

public class AlienAttackFrame extends JFrame {
    static final long serialVersionUID = 1L;
    private AlienGraphicsPanel graphicsPanel;
    private GridBagConstraints gc;
    
    public AlienAttackFrame() {
        setTitle("Alien Attack [Alpha]");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 900);
        // pack();
        graphicsPanel = new AlienGraphicsPanel();   
    }

}
