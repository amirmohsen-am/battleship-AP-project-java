package battleship.frame;

import battleship.graphic.GraphicObject;
import battleship.graphic.image.GameImages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by persianpars on 1/25/15.
 */
public class ButtonsActionListener implements ActionListener {
    GraphicObject cursor;

    public ButtonsActionListener(GraphicObject cursor) {
        this.cursor = cursor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cursor.setGameImage(GameImages.getGameImage(e.getActionCommand()));
    }
}
