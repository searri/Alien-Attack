/**
 * CSCI 2113 - Project 2 - Alien Attack
 * 
 * @author Rick Sear
 *
 */

import javax.swing.SwingUtilities;

public class AlienAttack {
    private static AlienAttack instance;

    public static AlienAttack Instance() {
        return instance;
    }

    public AlienAttack() {
        
    }

    private void createAndShowGUI() {
        AlienAttackFrame aaFrame = new AlienAttackFrame();
        aaFrame.setVisible(true);
    }

    public static void main(String[] args) {
        instance = new AlienAttack();
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                instance.createAndShowGUI(); 
            }
        });

    }
}
