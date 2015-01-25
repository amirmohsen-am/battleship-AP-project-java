package battleship.frame.panel;

import battleship.Map;
import battleship.graphic.Graphic;
import battleship.graphic.GraphicObject;
import battleship.position.Position;

import javax.swing.*;
import java.awt.*;

/**
 * Created by persianpars on 1/24/15.
 */
public class MapPanel extends JPanel {
    Graphic graphic;

    public void init(Graphic graphic) {
        this.graphic = graphic;

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(Map.getWidth() * Graphic.CELL_WIDTH , Map.getHeight() * Graphic.CELL_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        for (GraphicObject o : graphic.getGraphicObjects()) {
            Position imagePosition = o.getTopLeftGraphicPosition();
            g2d.drawImage(o.getCurrentImage().getImage(), imagePosition.x, imagePosition.y, o.getWidth(), o.getHeight(), null);
        }
//        g2d.scale(1,-1);
    }

}
