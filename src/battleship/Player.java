package battleship;

/** A player of the game
 *
 * @author Ahanchi
 */
public class Player {
    private String name;
    private Map map;
    private int radarUsed = 0;
    private int airCraftUsed = 0;

    public Player(String name, Map map) {
        this.name = name;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }

    public int getRadarUsed() {
        return radarUsed;
    }

    public int getAirCraftUsed() {
        return airCraftUsed;
    }

    public void incrementRadarUsed() {
        radarUsed++;
    }

    public void incrementAirCraftUsed() {
        airCraftUsed++;
    }

}
