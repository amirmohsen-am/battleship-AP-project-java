package battleship.graphic.image;

import battleship.graphic.Graphic;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by persianpars on 1/25/15.
 */
public class GameImages {
    public static GameImage Ship, Mine, AntiAircraft;
    public static ArrayList<GameImage> gameImageArrayList = new ArrayList<GameImage>();
    static
    {
        Ship = new GameImage("Ship", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT, "Ship");
        Mine = new GameImage("Mine", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT, "Mine");
        AntiAircraft = new GameImage("AntiAircraft", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT, "AntiAircraft");

        gameImageArrayList.add(Ship);
        gameImageArrayList.add(Mine);
        gameImageArrayList.add(AntiAircraft);
    }

    public static GameImage getGameImage(String name) {
        for (GameImage gameImage : gameImageArrayList)
            if (gameImage.name.equals(name))
                return gameImage;
        throw new RuntimeException("GameImage " + name + "not found");
    }
}


