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
    Map map;
    int WIDTH, HEIGHT;

    public void init(Map map, Graphic graphic) {
        this.graphic = graphic;
        this.map = map;

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        WIDTH = Map.getWidth() * Graphic.CELL_WIDTH + 1;
        HEIGHT = Map.getHeight() * Graphic.CELL_HEIGHT + 1;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //g2d.fillRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i <= 10; i++)
            for (GraphicObject o : graphic.getGraphicObjects())
                if (o.getPriority() == i) {
                    Position imagePosition = o.getTopLeftGraphicPosition();
                    if (i == 9) {
                        if (map.isVisible(o.getMapPosition()))
                            continue;
                    }
                    g2d.drawImage(o.getCurrentImage().getImage(), imagePosition.x, imagePosition.y, o.getWidth(), o.getHeight(), null);
                }
        paintGrid(g2d);
//        g2d.scale(1,-1);
    }

    private void paintGrid(Graphics2D g2d) {
        for (int i = 0; i <= Map.getHeight(); i++)
            g2d.drawLine(0, i * Graphic.CELL_HEIGHT, Map.getWidth()*Graphic.CELL_WIDTH, i*Graphic.CELL_HEIGHT);
        for (int j = 0; j <= Map.getWidth(); j++)
            g2d.drawLine(j * Graphic.CELL_WIDTH, 0, j*Graphic.CELL_WIDTH, Map.getHeight()*Graphic.CELL_HEIGHT);
    }

}
