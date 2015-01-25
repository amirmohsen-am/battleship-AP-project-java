package battleship.equipment;

import battleship.GameController;
import battleship.Player;
import battleship.exception.GameOverException;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import java.util.ArrayList;

/** Ship equipment of the game
 *
 * @author Ahanchi
 */
public class Ship extends Equipment {
    private ArrayList<EquipmentPosition> positions;

    public Ship(Player owner, GameController controller, ArrayList<EquipmentPosition> positions) {
        super(owner, controller);
        this.positions = positions;
    }

    public Ship(ArrayList<EquipmentPosition> positions) {
        super();
        this.positions = positions;
    }


    @Override
    public boolean isBlown(Position targetPosition) {
        if (!contains(targetPosition))
                throw new RuntimeException("Ship " + id + "does not contain" + targetPosition);
        for (EquipmentPosition position : positions)
            if (targetPosition.equals(position) && !position.isBlown())
                return false;
        return true;
    }

    @Override
    public boolean contains(Position targetPosition) {
        for (Position position : positions)
            if (position.equals(targetPosition))
                return true;
        return false;
    }

    @Override
    public void explode(Position targetPosition) throws GameOverException {
        if (!contains(targetPosition))
            throw new RuntimeException("Ship " + id + "does not contain" + targetPosition);
        if (isBlown(targetPosition))
            throw new RuntimeException("Ship " + id + "is Blown");
        for (EquipmentPosition position : positions) {
            if (!position.isBlown() && position.equals(targetPosition)) {
                position.setBlown(true);
                getController().reportShipCellExplode(targetPosition, getOwner());
            }
        }
        if (isDestroyed())
            getController().reportShipDestroyed(this);
    }

    @Override
    public boolean isDestroyed() {
        for (EquipmentPosition position : positions)
            if (!position.isBlown())
                return false;
        return true;
    }

    @Override
    public ArrayList<EquipmentPosition> getPositions() {
        return positions;
    }
}
