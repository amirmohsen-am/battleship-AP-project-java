package battleship.graphic.image;

import battleship.graphic.Graphic;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by persianpars on 1/25/15.
 */
public class GameImages {
    public static GameImage[] Ship = new GameImage[4];
    public static GameImage Mine;
    public static GameImage AntiAircraft;
    public static GameImage CursorAttack;
    public static GameImage CursorRadar;
    public static GameImage CursorAircraft;
    public static GameImage ExplodeAnimation;

    public static int ExplodeAnimationSpeed = 100;

    public static ArrayList<GameImage> gameImageArrayList = new ArrayList<GameImage>();

    static
    {
        for (int i = 0; i < Ship.length; i++) {
            Ship[i] = new GameImage("Ship" + (i + 1), "Ship" + (i + 1), Graphic.CELL_WIDTH * (i + 1), Graphic.CELL_HEIGHT);
//            gameImageArrayList.add(Ship[i]);
        }
        Mine = new GameImage("Mine", "Mine", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);
        AntiAircraft = new GameImage("AntiAircraft", "AntiAircraft", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);

        CursorAttack = new GameImage("CursorAttack", "CursorAttack", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);
        CursorRadar = new GameImage("CursorRadar", "CursorRadar", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);
        CursorAircraft = new GameImage("CursorAircraft", "CursorAircraft", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);

        ExplodeAnimation = new GameImage("Explosion", "Explosion", Graphic.CELL_WIDTH+10, Graphic.CELL_HEIGHT+10);

//        gameImageArrayList.add(Mine);
//        gameImageArrayList.add(AntiAircraft);

        GameImages o = new GameImages();
        for (Field field : GameImages.class.getDeclaredFields()) {
            if (field.getType() == GameImage.class)
                try {
                    gameImageArrayList.add((GameImage) field.get(o));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }

    }

    public static GameImage getGameImage(String name) {
        for (GameImage gameImage : gameImageArrayList)
            if (gameImage.name.equals(name))
                return gameImage;
        throw new RuntimeException("GameImage " + name + "not found");
    }
}


