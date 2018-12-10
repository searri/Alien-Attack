/**
 * CSCI 2113 - Project 2 - Alien Attack
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the actual graphics window in the game frame; handles all the repainting of elements and contains player and aliens
 * @author Rick Sear
 */
public class AlienGraphicsPanel extends JPanel {
    static final long serialVersionUID = 1L;
    static int LARGE = 90;              //pixel dimensions for each alien size (constants)
    static int MEDIUM = 60;
    static int SMALL = 30;

    //CUSTOMIZABLE OPTIONS - read from config file (set by AlienAttackFrame)
    static int largeAlienSpeed;         //how far each alien size moves in a cycle
    static int medAlienSpeed;
    static int smallAlienSpeed;
    static int maxAliens;               //max number of aliens
    static int minAliens;               //min number of aliens
    static int largeAlienValue;         //point values for each alien size
    static int medAlienValue;
    static int smallAlienValue;
    static int missileSpeed;            //how far a missile moves in a cycle

    private Player player;              //player object
    private ArrayList<Alien> aliensOnScreen;     //list of on-screen aliens
    private ArrayList<Missile> missilesOnScreen;            //list of on-screen missiles
    private int gameScreenSize;         //screen size
    private int score;                  //score

    public AlienGraphicsPanel(int frameSize, int playerType) {
        score = 0;
        setPreferredSize(new Dimension(frameSize, frameSize));
        aliensOnScreen = new ArrayList<Alien>();
        missilesOnScreen = new ArrayList<Missile>();
        setLayout(null);
        gameScreenSize = frameSize;

        //instantiate abstract Player object as one of the three types, depending on the choice passed to the constructor
        if(playerType==1) {
            player = new Player1(LARGE, frameSize);
        } else if(playerType==2) {
            player = new Player2(LARGE, frameSize);
        } else {
            player = new Player3(LARGE, frameSize);
        }
        add(player);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Increase the maximum number of aliens that can be spawned
     * @param increaseAmt Amount by which to increase
     */
    public void increaseMaxAliens(int increaseAmt) {
        maxAliens+=increaseAmt;
    }

    /**
     * Increase the minumum number of aliens that can be spawned
     * @param increaseAmt Amount by which to increase
     */
    public void increaseMinAliens(int increaseAmt) {
        minAliens+=increaseAmt;
    }

    /**
     * Advances all aliens on game screen
     */
    public void moveAllAliensDown() {
        setBackground(Color.BLACK);
        for(int i=0; i<aliensOnScreen.size(); i++) {
            Alien currAlien = aliensOnScreen.get(i);
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
            if(!keepAlien) {        //if the Alien went offscreen, update the score

                //remove Alien from all records to be garbage collected
                remove(currAlien);
                aliensOnScreen.remove(i);

                //update score
                updateScore(currAlien);
            }
        }
    }

    /**
     * Updates score when an alien either leaves the screen or gets missile'd
     * @param alien Alien that just got killed
     */
    public void updateScore(Alien alien) {
        int alienSize = alien.getAlienSize();
        if(alienSize==LARGE) {
            score+=largeAlienValue;
        } else if(alienSize==MEDIUM) {
            score+=medAlienValue;
        } else if(alienSize==SMALL) {
            score+=smallAlienValue;
        }
    }

    /**
     * Add a random number (between maxAliens and minAliens) of randomly defined aliens to the game screen
     */
    public void addRandomAliens() {
        int aliensToGenerate = randomNumInRange(minAliens, maxAliens);  //how many aliens to generate this cycle
        for(int i=0; i<aliensToGenerate; i++) {
            int alienSize = randomNumInRange(1, 3);     //how big is the new alien
            int location = randomNumInRange(0, gameScreenSize);     //where is it
            Alien newAlien = new Alien(alienSize*30, location, gameScreenSize);   //create alien
            add(newAlien);                      //add to screen
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

    public int getScore() {
        return score;
    }

    /**
     * Tests all aliens in current state against player; if hitboxes overlap, then player is hit
     * @return true if player has been hit
     */
    public boolean playerIsHit() {
        Rectangle playerBorder = player.getBounds();
        for(int i=0; i<aliensOnScreen.size(); i++) {
            Alien currAlien = aliensOnScreen.get(i);
            Rectangle alienBorder = currAlien.getBounds();
            if(playerBorder.intersects(alienBorder)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears game screen, empties ArrayLists of elements
     */
    public void clearScreen() {
        for(int i=0; i<aliensOnScreen.size(); i++) {
            remove(aliensOnScreen.get(i));
        }
        aliensOnScreen.clear();
        for(int i=0; i<missilesOnScreen.size(); i++) {
            remove(missilesOnScreen.get(i));
        }
        missilesOnScreen.clear();
    }

    /**
     * Advances all missiles on screen
     */
    public void advanceMissiles() {
        for(int i=0; i<missilesOnScreen.size(); i++) {
            Missile currMissile = missilesOnScreen.get(i);

            //Move missile up
            int missileY = currMissile.getLocation().y;
            currMissile.setLocation(currMissile.getLocation().x, missileY-missileSpeed);

            //See if it's still on screen
            Point missileLoc = currMissile.getLocation();
            if(missileLoc.y+10<=0) {
                //it's off screen
                remove(currMissile);
                missilesOnScreen.remove(currMissile);
            }
        }
    }

    /**
     * Tests all Aliens with all missiles to see if it's been hit
     */
    public void aliensHit() {
        for(int i=0; i<missilesOnScreen.size(); i++) {
            Missile currMissile = missilesOnScreen.get(i);
            for(int j=0; j<aliensOnScreen.size(); j++) {
                Alien currAlien = aliensOnScreen.get(j);
                Rectangle alienBorder = currAlien.getBounds();
                Rectangle missileBorder = currMissile.getBounds();
                if(missileBorder.intersects(alienBorder)) {
                    //Collision found: remove both alien and missile
                    remove(currMissile);
                    remove(currAlien);
                    missilesOnScreen.remove(currMissile);
                    aliensOnScreen.remove(currAlien);
                    updateScore(currAlien);
                }
            }
        }
    }

    /**
     * Spawns a missile from within the player
     */
    public void launchMissile() {
        int missileSpawnX = (player.getLocation().x)+(player.getPlayerSize()/2)-2;
        int missileSpawnY = (player.getLocation().y)+4;
        Missile newMissile = new Missile(new Point(missileSpawnX, missileSpawnY));
        add(newMissile);
        missilesOnScreen.add(newMissile);
    }

}