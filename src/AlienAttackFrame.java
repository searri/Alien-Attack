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
        graphicsPanel = new AlienGraphicsPanel();
        getContentPane().setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.gridx = 0;   //(0,0) in grid
        gc.gridy = 0;
        // gc.insets = new Insets(2, 2, 2, 2); //inset of 2 on all sides of grid cell
        gc.fill = GridBagConstraints.BOTH;
        getContentPane().add(graphicsPanel, gc);   
    }

}
