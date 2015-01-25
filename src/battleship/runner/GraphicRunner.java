package battleship.runner;

import battleship.Map;
import battleship.frame.GetMapFrame;

/**
 * Created by persianpars on 1/25/15.
 */
public class GraphicRunner {
    public static void main(String[] args) {
        Map.startHeight = Map.startWidth = 0;
        Map.endWidth = Map.endHeight = 10;
        GetMapFrame getMapFrame = new GetMapFrame();
        getMapFrame.init();
    }
}
