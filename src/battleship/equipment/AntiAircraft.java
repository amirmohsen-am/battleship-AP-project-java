package battleship.equipment;

import battleship.GameController;
import battleship.Player;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import java.util.ArrayList;
import java.util.Arrays;

/** AntiAircraft equipment of the game
 *
 * @author Ahanchi
 */
public class AntiAircraft extends Equipment {
    private EquipmentPosition position;

    public AntiAircraft(Player owner, GameController controller, EquipmentPosition position) {
        super(owner, controller);
        this.position = position;
    }

    public AntiAircraft(int row) {
        super();
        position = new EquipmentPosition(1, row);
    }

    public AntiAircraft(Position position) {
        super();
        this.position = new EquipmentPosition(position.x, position.y);
    }

    public EquipmentPosition getPosition() {
        return position;
    }

    public int getRow() {
        return position.y;
    }

    @Override
    public boolean isDestroyed() {
        return position.isBlown();
    }

    @Override
    public boolean isBlown(Position targetPosition) {
        return position.isBlown();
    }

    @Override
    public boolean contains(Position targetPosition) {
        return targetPosition.equals(position);
    }

    @Override
    public void explode(Position targetPosition){
        if (!contains(targetPosition))
            throw new RuntimeException("AntiAircraft " + id + "does not contain" + targetPosition.y);
        if (isBlown(targetPosition))
            throw new RuntimeException("AntiAircraft " + id + "is Blown");
        position.setBlown(true);
        getController().reportAntiAircraftHitDirectly(this);
    }

    @Override
    public ArrayList<EquipmentPosition> getPositions() {
        return new ArrayList<EquipmentPosition>(Arrays.asList(position));
    }

    /** Destroys the antiAircraft after it defends against an aircraft
     *
     * @throws Exception if it has benn already blown
     */
    public void destroy() { // if hit using antiaircraft (should be put in javadoc)
        if (isDestroyed())
            throw new RuntimeException("AntiAircraft " + id + "is Blown");
        position.setBlown(true);
        getController().reportAntiAircraftHit(this);

    }
}
