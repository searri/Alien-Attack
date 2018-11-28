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
    static int LARGE = 90;              //pixel dimensions for each alien size
    static int MEDIUM = 60;
    static int SMALL = 30;

    //CUSTOMIZABLE OPTIONS - read from config file (passed from AlienAttackFrame)
    static int largeAlienSpeed;         //how far each alien size moves in a cycle
    static int medAlienSpeed;
    static int smallAlienSpeed;
    static int maxAliens;               //max number of aliens
    static int minAliens;               //min number of aliens
    static int largeAlienValue;         //point values for each alien size
    static int medAlienValue;
    static int smallAlienValue;

    private Player1 player;
    private ArrayList<AlienAttackAlien> aliensOnScreen;
    private int gameScreenSize;
    private int score;

    public AlienGraphicsPanel(int frameSize) {
        score = 0;
        setPreferredSize(new Dimension(frameSize, frameSize));
        aliensOnScreen = new ArrayList<AlienAttackAlien>();
        setLayout(null);
        gameScreenSize = frameSize;
        player = new Player1(LARGE, frameSize);
        add(player);
    }

    public Player1 getPlayer() {
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
            if(!keepAlien) {        //if the Alien went offscreen, update the score

                //remove Alien from all records to be garbage collected
                remove(currAlien);
                aliensOnScreen.remove(i);

                //update score depending on Alien size
                if(alienSize==LARGE) {
                    score+=largeAlienValue;
                } else if(alienSize==MEDIUM) {
                    score+=medAlienValue;
                } else if(alienSize==SMALL) {
                    score+=smallAlienValue;
                }
            }
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
            AlienAttackAlien newAlien = new AlienAttackAlien(alienSize*30, location, gameScreenSize);   //create alien
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
        //get the left, right, and top border coords of the player
        double playerLeftX = player.getLocation().getX();
        double playerRightX = player.getLocation().getX() + player.getPlayerSize();
        double playerY = player.getLocation().getY();
        for(int i=0; i<aliensOnScreen.size(); i++) {
            //get the right, left, and bottom border coords of the alien
            int currAlienSize = aliensOnScreen.get(i).getAlienSize();
            double currAlienRightX = aliensOnScreen.get(i).getLocation().getX() + currAlienSize;
            double currAlienLeftX = aliensOnScreen.get(i).getLocation().getX();
            double currAlienY = aliensOnScreen.get(i).getLocation().getY() + currAlienSize;

            //logic for a hit on the player's right side
            boolean rightHit = (playerLeftX > currAlienLeftX && playerLeftX < currAlienRightX && playerY < currAlienY);
            //logic for a hit on the player's left side
            boolean leftHit = (playerRightX > currAlienLeftX && playerRightX < currAlienRightX && playerY < currAlienY);
            //logic for a hit on top of the player, alien is bigger than player
            boolean topBigHit = (playerRightX < currAlienRightX && playerLeftX > currAlienLeftX && playerY < currAlienY);
            //logic for a hit on top of the player, alien is smaller than player
            boolean topSmallHit = (playerRightX > currAlienRightX && playerLeftX < currAlienLeftX && playerY < currAlienY);
            
            //return true if any kind of hit was registered
            if(rightHit || leftHit || topBigHit || topSmallHit) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes any alien too close to the player to make respawning fair
     */
    public void clearRespawnArea() {
        for(int i=0; i<aliensOnScreen.size(); i++) {
            AlienAttackAlien currAlien = aliensOnScreen.get(i);
            int currAlienSize = currAlien.getAlienSize();
            double currAlienY = (currAlien.getLocation().getY() + currAlienSize);
            if(currAlienY > (gameScreenSize-(LARGE*3))) {
                remove(currAlien);
                aliensOnScreen.remove(i);
            }
        }
    }

    /**
     * Clears game screen, empties aliensOnScreen ArrayList
     */
    public void clearScreen() {
        for(int i=0; i<aliensOnScreen.size(); i++) {
            remove(aliensOnScreen.get(i));
        }
        aliensOnScreen.clear();
    }

}