package battleship.graphic;

import battleship.Map;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by persianpars on 1/24/15.
 */
public class Graphic implements Serializable{

    // has to be odd
    public static final int CELL_WIDTH = 51;
    public static final int CELL_HEIGHT = 51;

    public static final int FPS = 30;

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



    private ArrayList<GraphicObject> graphicObjects = new ArrayList<GraphicObject>();

    public ArrayList<GraphicObject> getGraphicObjects() {
        return graphicObjects;
    }


    public void addGraphicObject(final GraphicObject graphicObject) {
        graphicObjects.add(graphicObject);
        if (graphicObject.speedMS != 0) {
            final Thread newThread = new Thread(graphicObject);
            newThread.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        newThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    graphicObjects.remove(graphicObject);
                }
            }).start();
        }
    }



}
