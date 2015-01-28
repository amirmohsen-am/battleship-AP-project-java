package battleship.frame;

import battleship.ConsoleInput;
import battleship.graphic.Graphic;
import battleship.graphic.GraphicObject;
import battleship.graphic.image.GameImages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by persianpars on 1/25/15.
 */
public class ButtonsActionListener implements ActionListener {
    GraphicObject cursor;
    GetPlayerFrame frame;

    public ButtonsActionListener(GetPlayerFrame frame, GraphicObject cursor) {
        this.frame = frame;
        this.cursor = cursor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cursor.setGameImage(GameImages.getGameImage(e.getActionCommand()));
        cursor.setMiddleGraphicPosition(Graphic.getMiddleGraphicPosition(ConsoleInput.getShipPositions(
                cursor.getMapPosition(), Math.max(cursor.getHeight()/Graphic.CELL_HEIGHT, cursor.getWidth()/Graphic.CELL_WIDTH), frame.horizontal)));
    }
}
