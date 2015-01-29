package battleship.equipment;

//import battleship.Player;

import battleship.GameController;
import battleship.GameEngine;
import battleship.Player;
import battleship.exception.GameOverException;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import java.io.Serializable;
import java.util.ArrayList;

/** Equipments used in the game
 *
 * @author Ahanchi
 */
abstract public class Equipment implements Serializable{
    protected int id;

    private Player owner = null;
    private GameController controller = null;
    // they have to be set before game is started

    public Equipment(Player owner, GameController controller) {
        id = GameEngine.equipmentCount++;
        this.owner = owner;
        this.controller = controller;
    }

    public Equipment() {
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    /** Checks to see if the whole parts of equipments are exploded
     *
     * @return true iff the equipment is completely destroyed
     */
    public abstract boolean isDestroyed();

    /** Checks to see if the given position of the equipment is exploded
     *
     * @param targetPosition the required position to be checked
     * @return true iff the given position is blown
     * @throws Exception if the equipment does not contain the given position
     */
    public abstract boolean isBlown(Position targetPosition);

    /** Checks too see if the equipment contains the given position
     *
     * @param targetPosition the position which we want to see if the equipment contains it or nor
     * @return true iff equipment contains the given position
     */
    public abstract boolean contains(Position targetPosition);

    /** Explodes the given position of the Equipment
     *
     * @param targetPosition the position which will be exploded
     * @throws Exception if the equipment does not contain the given position or that part of the equipment is already exploded
     */
    public abstract void explode(Position targetPosition) throws GameOverException;

    public abstract ArrayList<EquipmentPosition> getPositions();


    public int getId() {
        return id;
    }

    public Player getOwner() {
        return owner;
    }

    public GameController getController() {
        return controller;
    }

}