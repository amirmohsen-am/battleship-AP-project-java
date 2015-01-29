package battleship;

import battleship.equipment.AntiAircraft;
import battleship.equipment.Equipment;
import battleship.equipment.Mine;
import battleship.equipment.Ship;
import battleship.exception.GameOverException;
import battleship.graphic.Graphic;
import battleship.graphic.GraphicObject;
import battleship.graphic.image.GameImage;
import battleship.graphic.image.GameImages;
import battleship.position.EquipmentPosition;
import battleship.position.Position;
import javafx.geometry.Pos;

import java.io.Serializable;
import java.util.ArrayList;

/** The Map of a player
 *
 * @author Ahanchi
 */
public class Map implements Serializable{
    private GameController controller = null;
    /** The Player variable must be set in initialization*/
    private Player owner = null;
    private ArrayList<Equipment> equipments;
    boolean visible[][];
    boolean blown[][];


    /** These variables are in [) (include-excluded) format*/
    public static int startWidth = -1, endWidth;
    /** These variables are in [) (include-excluded) format*/
    public static int startHeight = -1, endHeight;

    public static int getWidth() {
        if (startWidth == -1)
            throw new RuntimeException("width of map was not set");
        return endWidth - startWidth+1;
    }

    public static int getHeight() {
        if (startHeight == -1)
            throw new RuntimeException("height of map was not set");
        return endHeight - startHeight+1;
    }

    public Map(boolean cellsVisible) {
        equipments = new ArrayList<Equipment>();
        if (startWidth == -1 || startHeight == -1)
            throw new RuntimeException("width or height of map was not set");
        visible = new boolean[getWidth()][getHeight()];
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++)
                visible[i][j] = cellsVisible;
        blown = new boolean[getWidth()][getHeight()];
    }

    public Map(boolean cellsVisible, GameController controller, ArrayList<Equipment> equipments) {
        this(cellsVisible);
        this.controller = controller;
        this.equipments = equipments;
    }


    {
        equipments = new ArrayList<Equipment>();
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isVisible(int x, int y) {
        return visible[x][y];
    }
    public boolean isVisible(Position position) {
        return visible[position.x][position.y];
    }

    public boolean isBlown(int x, int y) {
        return blown[x][y];
    }
    public boolean isBlown(Position position) {
        return blown[position.x][position.y];
    }

    public void setVisible(int x, int y, boolean visible) {
        this.visible[x][y] = visible;
    }
    public void setVisible(boolean visible) {
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++)
                this.visible[j][i] = visible;
    }

    public void setBlown(int x, int y, boolean blown) {
        this.blown[x][y] = blown;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    /** Attacks a give position, in the reqiered order
     *
     * @param targetPosition the position which is begin attacked
     * @throws Exception if an attacked equipment has already been attacked
     */
    public void attack(Position targetPosition) throws GameOverException {

        // Ship Explode
        for (Equipment equipment : equipments) {
            if (equipment instanceof Ship && equipment.contains(targetPosition) && !equipment.isBlown(targetPosition)) {
                equipment.explode(targetPosition);
            }
        }

        // Mine Explode
        for (Equipment equipment : equipments) {
            if (equipment instanceof Mine && equipment.contains(targetPosition) && !equipment.isBlown(targetPosition)) {
                equipment.explode(targetPosition);
            }
        }
        // AntiAircraft Explode
        for (Equipment equipment : equipments) {
            if (equipment instanceof AntiAircraft && equipment.contains(targetPosition) && !equipment.isBlown(targetPosition)) {
                equipment.explode(targetPosition);
            }
        }

    }

    /** Runs an aircraft command
     *
     * @param targetRow the row which the aircraft will attack
     * @throws Exception if an attacked equipment has already been attacked
     */
    public void aircraft(int targetRow) throws GameOverException {
        if (owner.getAirCraftUsed() >= GameEngine.AIRCRAFT_MAX_USES)
            return;
        owner.incrementAirCraftUsed();
        Position targetPosition = new Position(1, targetRow);
        for (Equipment equipment : equipments) {
            if (equipment instanceof AntiAircraft && equipment.contains(targetPosition) && !equipment.isBlown(targetPosition)) {
                ((AntiAircraft) equipment).destroy();
                controller.attack(((AntiAircraft) equipment).getPosition(), controller.getEngine().getOpponent(owner));
                try {
                    Thread.sleep(GameImages.AircraftSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        // there is no antiaircraft in this row
        for (int x = startWidth; x <= endWidth; x++)
            controller.attack(new Position(x, targetRow), controller.getEngine().getOpponent(owner));
    }

    /** Uses radar to identify equipments near a given position
     *
      * @param targetPosition the position which will be radar-ed
     */
    public void radar(Position targetPosition) {
        if (owner.getRadarUsed() >= GameEngine.RADAR_MAX_USES)
            return;
        owner.incrementRadarUsed();
        ArrayList<Position> result = new ArrayList<Position>();
        for (Equipment equipment: equipments)
            if (equipment instanceof Ship)
                for (EquipmentPosition position : equipment.getPositions())
                    if (!position.isBlown() && targetPosition.getMaxDistance(position) <= GameEngine.RADAR_RADIUS)
                        result.add(position);
        controller.reportRadar(result, owner, targetPosition);

    }

    /** Checks to see if all ships on the map have been destroyed
     *
     * @return true iff all the ships of the map have been destroyed
     */
    public boolean isAllShipDestroyed() {
        for (Equipment equipment : equipments)
            if (equipment instanceof Ship && !equipment.isDestroyed())
                return false;
        return true;
    }
    public void addEquipment(Equipment equipment, GameImage gameImage) {
        equipments.add(equipment);
        owner.getGraphic().addGraphicObject(new GraphicObject(
                equipment.getPositions().get(0),
                Graphic.getMiddleGraphicPosition(equipment.getPositions()),
                gameImage));
    }
}
