package battleship.graphic;

import battleship.Map;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import java.util.ArrayList;

/**
 * Created by persianpars on 1/24/15.
 */
public class Graphic {

    // has to be odd
    public static final int CELL_WIDTH = 61;
    public static final int CELL_HEIGHT = 61;

    /** Returns MIDDLE of cell with given column and row
     *
     * @param column
     * @param row
     * @return
     */
    public static Position getMiddleGraphicPosition(int column, int row) {
        return getMiddleGraphicPosition(new Position(column, row));
    }



    /**Returns MIDDLE of cell with given column and row
     *
     * @param mapPosition
     * @return
     */
    public static Position getMiddleGraphicPosition(Position mapPosition) {
        return new Position(mapPosition.x * CELL_WIDTH + CELL_WIDTH/2, mapPosition.y * CELL_HEIGHT + CELL_HEIGHT/2);
    }

    public static Position getMiddleGraphicPosition(ArrayList<EquipmentPosition> positions) {
        Position result = new Position(0, 0);
        for (Position position : positions) {
            result.add(getMiddleGraphicPosition(position));
        }
        result.x /= positions.size();
        result.y /= positions.size();
        return result;
    }


//    public static Position getStartGraphicPosition(Position mapPosition, int width, int height) {
//        Position result = getMiddleGraphicPosition(mapPosition)
//    }


    public static boolean isValidPosition(Position position) {
        if (position.x < 0 || position.x >= Map.getWidth() * CELL_WIDTH ||
            position.y < 0 || position.y >= Map.getHeight() * CELL_HEIGHT)
            return false;
        return true;
    }

    public Graphic() {
    }

    private ArrayList<GraphicObject> graphicObjects = new ArrayList<GraphicObject>();

    public ArrayList<GraphicObject> getGraphicObjects() {
        return graphicObjects;
    }
    public void addGraphicObject(GraphicObject graphicObject) {
        graphicObjects.add(graphicObject);
    }

}
