package battleship.frame;

import battleship.ConsoleInput;
import battleship.Map;
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
        this.cursor = cursor;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        if (name.contains("Ship"))
            name = name + (cursor.isHorizontal() ? "H" : "V");
        cursor.setGameImage(GameImages.getGameImage(name));
        cursor.setMiddleGraphicPosition(cursor.recalcMiddleGraphPosition());
        if (name.equals("AntiAircraft")) {
            if (frame.informationPanel.antiAircraftButton.isSelected()) {
                for (int j = 0; j < Map.getWidth(); j++)
                    cursor.goLeftOneColumn();
            }
        }

    }
}
