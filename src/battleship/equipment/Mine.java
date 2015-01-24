package battleship.equipment;

import battleship.GameController;
import battleship.Player;
import battleship.exception.GameOverException;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import java.util.ArrayList;
import java.util.Arrays;

/** Mine equipment of the game
 *
 * @author Ahanchi
 */
public class Mine extends Equipment {
    private EquipmentPosition position;

    public Mine(int id, Player owner, GameController controller, EquipmentPosition position) {
        super(id, owner, controller);
        this.position = position;
    }

    public Mine(int id, EquipmentPosition position) {
        super(id);
        this.position = position;
    }

    public EquipmentPosition getPosition() {
        return position;
    }

    @Override
    public boolean isDestroyed() {
        return position.isBlown();
    }

    @Override
    public boolean isBlown(Position targetPosition) {
        if (!contains(targetPosition))
            throw new RuntimeException("Mine " + id + "does not contain" + targetPosition);
        return false;
    }

    @Override
    public boolean contains(Position targetPosition) {
        return targetPosition.equals(position);
    }

    @Override
    public void explode(Position targetPosition) throws GameOverException {
        if (!contains(targetPosition))
            throw new RuntimeException("Mine " + id + "does not contain" + targetPosition);
        if (isBlown(targetPosition))
            throw new RuntimeException("Mine " + id + "is Blown");
        position.setBlown(true);
        getController().reportMineExplode(this);
    }

    @Override
    public ArrayList<EquipmentPosition> getPositions() {
        return new ArrayList<EquipmentPosition>(Arrays.asList(position));
    }
}
