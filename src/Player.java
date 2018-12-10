import javax.swing.*;

/**
 * Absract class for the player
 * @author Rick Sear
 */
abstract class Player extends JComponent {
    static final long serialVersionUID = 1L;
    private int playerSize;
    private int gameScreenSize;
    private int maxXCoord;

    public Player(int startSize, int screenSize) {
        playerSize = startSize;
        gameScreenSize = screenSize;
        maxXCoord = screenSize-startSize;
        setSize(playerSize, playerSize);
        setLocation(calcXStartCoord(), calcYStartCoord());
    }

    public int calcXStartCoord() {
        return (int)((gameScreenSize-playerSize)/2);
    }

    public int calcYStartCoord() {
        return (int)(gameScreenSize-5-playerSize);
    }

    public int getPlayerSize() {
        return playerSize;
    }

    /**
     * Move the player left, but within the game frame
     * @param howManyPixels number of pixels to move left
     */
    public void movePlayerLeft(int howManyPixels) {
        double currX = getLocation().getX();
        currX-=howManyPixels;
        if(currX<0) {
            setLocation(0, (int)getLocation().getY());
        } else {
            setLocation((int)currX, (int)getLocation().getY());
        }
    }

    /**
     * Move player right, but within the game frame
     * @param howManyPixels: number of pixels to move right
     */
    public void movePlayerRight(int howManyPixels) {
        double currX = getLocation().getX();
        currX+=howManyPixels;
        if(currX>maxXCoord) {
            setLocation(maxXCoord, (int)getLocation().getY());
        } else {
            setLocation((int)currX, (int)getLocation().getY());
        }
    }

    /**
     * Shrinks player down one size
     * @return true if player is dead
     */
    public boolean shrinkPlayerSize() {
        if(playerSize == 30) {
            return true;
        } else {
            playerSize-=30;
            setSize(playerSize, playerSize);
            maxXCoord = gameScreenSize-playerSize;
            setLocation(calcXStartCoord(), calcYStartCoord());
            return false;
        }
    }
}