package battleship;

import battleship.equipment.AntiAircraft;
import battleship.equipment.Equipment;
import battleship.equipment.Mine;
import battleship.equipment.Ship;
import battleship.exception.GameOverException;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import java.util.ArrayList;

/** The Map of a player
 *
 * @author Ahanchi
 */
public class Map {
    private GameController controller;
    /** The Player variable must be set in initialization*/
    private Player owner = null;
    private ArrayList<Equipment> equipments;

    /** These variables are in [) (include-excluded) format*/
    int startWidth, endWidth;
    /** These variables are in [) (include-excluded) format*/
    int startHeight, endHeight;


    public Map(GameController controller, ArrayList<Equipment> equipments, int startWidth, int endWidth, int startHeight, int endHeight) {
        this.controller = controller;
        this.equipments = equipments;
        this.startWidth = startWidth;
        this.endWidth = endWidth;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
    }

    {
        equipments = new ArrayList<Equipment>();
    }

    public void setOwner(Player owner) {
        this.owner = owner;
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
        controller.reportRadar(result, owner);

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
}
