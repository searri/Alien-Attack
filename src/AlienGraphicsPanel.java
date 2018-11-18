/**
 * CSCI 2113 - Project 2 - Alien Attack
 * @author Rick Sear
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AlienGraphicsPanel extends JPanel {
    static final long serialVersionUID = 1L;
    static int LARGE = 90;              //pixel dimensions for each alien size
    static int MEDIUM = 60;
    static int SMALL = 30;
    static int largeAlienSpeed;         //how far each alien size moves in a cycle
    static int medAlienSpeed;
    static int smallAlienSpeed;

    private AlienAttackPlayer player;
    private ArrayList<AlienAttackAlien> aliensOnScreen;
    private int gameScreenSize;

    public AlienGraphicsPanel(int frameSize) {
        setPreferredSize(new Dimension(frameSize, frameSize));
        aliensOnScreen = new ArrayList<AlienAttackAlien>();
        setBackground(Color.BLACK);
        setLayout(null);
        gameScreenSize = frameSize;
        player = new AlienAttackPlayer(LARGE, frameSize);
        add(player);
        addRandomAlien();
    }

    public AlienAttackPlayer getPlayer() {
        return player;
    }

    /**
     * Advances all aliens on game screen
     */
    public void moveAllAliensDown() {
        for(int i=0; i<aliensOnScreen.size(); i++) {
            AlienAttackAlien currAlien = aliensOnScreen.get(i);
            boolean keepAlien;
            int alienSize = currAlien.getAlienSize();
            if(alienSize==LARGE) {
                keepAlien = currAlien.moveAlienDown(largeAlienSpeed);
            } else if(alienSize==MEDIUM) {
                keepAlien = currAlien.moveAlienDown(medAlienSpeed);
            } else if(alienSize==SMALL) {
                keepAlien = currAlien.moveAlienDown(smallAlienSpeed);
            } else {
                System.out.println("ERROR. There was a mismatch between Alien sizes, and the game had to terminate");
                keepAlien = false;  //this must be set to satisfy the Java compiler
                System.exit(0);     //exit immediately, there was some kind of major error
            }
            if(!keepAlien) {
                remove(currAlien);
                aliensOnScreen.remove(i);
            }
        }
    }

    public void addRandomAlien() {
        AlienAttackAlien lolz = new AlienAttackAlien(MEDIUM, 450, gameScreenSize);
        add(lolz);
        aliensOnScreen.add(lolz);
    }

}