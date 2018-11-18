/**
 * CSCI 2113 - Project 2 - Alien Attack
 * @author Rick Sear
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AlienGraphicsPanel extends JPanel {
    static final long serialVersionUID = 1L;
    static int LARGE = 90;              //pixel dimensions for each alien size
    static int MEDIUM = 60;
    static int SMALL = 30;
    static int largeAlienSpeed;         //how far each alien size moves in a cycle
    static int medAlienSpeed;
    static int smallAlienSpeed;
    static int maxAliens;               //starting max number of aliens
    static int minAliens;               //starting min number of aliens

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

    /**
     * Add a random number of randomly defined aliens to the game screen
     */
    public void addRandomAliens() {
        int aliensToGenerate = randomNumInRange(minAliens, maxAliens);  //how many aliens to generate this cycle
        for(int i=0; i<aliensToGenerate; i++) {
            int alienSize = randomNumInRange(1, 3);     //how big is the new alien
            int location = randomNumInRange(0, gameScreenSize);     //where is it
            AlienAttackAlien newAlien = new AlienAttackAlien(alienSize*30, location, gameScreenSize);   //create alien
            add(newAlien);  //add to screen
            aliensOnScreen.add(newAlien);       //add to list
        }
    }

    /**
     * Simple method to create a random integer within a set range (inclusive)
     * @param min Minimum number to create
     * @param max Maximum number to create
     * @return random number between min and max
     */
    public int randomNumInRange(int min, int max) {
		Random rando = new Random();
		return rando.nextInt((max - min) + 1) + min;
    }
}