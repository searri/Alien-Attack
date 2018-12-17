package AlienAttackPackage;

/**
 * This is a node to be held in the leaderboard ArrayList of the AlienAttackMenu
 * It holds two pieces of data: a score and a name
 * @author Rick Sear
 */
public class HiScoreNode implements Comparable<HiScoreNode>{
    private int score;
    private String name;

    public HiScoreNode(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public String toString() {
        return name+" "+score;
    }

    public int compareTo(HiScoreNode otherNode) {
        int scoresCompare = Integer.compare(score, otherNode.getScore());
        if(scoresCompare==0) {
            return name.compareTo(otherNode.getName());
        } else {
            return -(scoresCompare);
        }
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}