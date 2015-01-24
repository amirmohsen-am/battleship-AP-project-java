package battleship.position;

/**
 * Created by persianpars on 12/25/14.
 */

/** A kind of position which is included in an equipment
 *
 * @author Ahanchi
 */
public class EquipmentPosition extends Position {
    private boolean blown;

    {
        blown = false;
    }

    public EquipmentPosition(int x, int y) {
        super(x, y);
    }

    public boolean isBlown() {
        return blown;
    }

    public void setBlown(boolean blown) {
        this.blown = blown;
    }

    @Override
    public String toString() {
        return "EquipmentPosition{" +
                "blown=" + blown +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
