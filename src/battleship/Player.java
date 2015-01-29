package battleship;

import battleship.graphic.Graphic;

import java.io.Serializable;

/** A player of the game
 *
 * @author Ahanchi
 */
public class Player implements Serializable {
    private String name;
    private Map map;
    private int radarUsed = 0;
    private int airCraftUsed = 0;
    private Graphic graphic;



    public Player(String name, Map map) {
        this.name = name;
        this.map = map;
    }

    public void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }

    public Graphic getGraphic() {
        return graphic;
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
