package battleship.graphic.image;

import battleship.GameEngine;
import battleship.graphic.Graphic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by persianpars on 1/25/15.
 */
public class GameImages implements Serializable {
    public static GameImage[] ShipH = new GameImage[4];
    public static GameImage[] ShipV = new GameImage[4];
    public static GameImage Mine;
    public static GameImage AntiAircraft;
    public static GameImage CursorAttack;
    public static GameImage CursorRadar;
    public static GameImage CursorAircraft;
    public static GameImage ExplodeAnimation;
    public static GameImage VisibleCell;
    public static GameImage Cloud;
    public static GameImage DestroyedCell;

    public static GameImage RadarAnimation;
    public static GameImage Fire;
    public static GameImage Sea;

    public static int ExplodeAnimationSpeed = 100;
    public static int CloudSpeed = 300;
    public static int FireSpeed = 140;
    public static int RadarSpeed = 140;
    public static int SeaSpeed = 100;
    public static int AircraftSpeed = 50;

    public static ArrayList<GameImage> gameImageArrayList = new ArrayList<GameImage>();

    static
    {
        for (int i = 0; i < ShipH.length; i++) {
            ShipH[i] = new GameImage("Ship" + (i + 1) + "H", "Ship" + (i + 1) + "H", Graphic.CELL_WIDTH * (i + 1), Graphic.CELL_HEIGHT);
            ShipV[i] = new GameImage("Ship" + (i + 1) + "V", "Ship" + (i + 1) + "V", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT  * (i + 1));
//            gameImageArrayList.add(Ship[i]);
        }
        Mine = new GameImage("Mine", "Mine", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);
        AntiAircraft = new GameImage("AntiAircraft", "AntiAircraft", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);

        CursorAttack = new GameImage("CursorAttack", "CursorAttack", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);
        CursorRadar = new GameImage("CursorRadar", "CursorRadar", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);
        CursorAircraft = new GameImage("CursorAircraft", "CursorAircraft", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);

        ExplodeAnimation = new GameImage("Explosion", "Explosion", Graphic.CELL_WIDTH+10, Graphic.CELL_HEIGHT+10);
        RadarAnimation = new GameImage("Radar", "Radar", Graphic.CELL_WIDTH * 3, Graphic.CELL_HEIGHT * 3);
        Fire = new GameImage("Fire", "Fire", Graphic.CELL_WIDTH + 10, Graphic.CELL_HEIGHT + 10);

        VisibleCell = new GameImage("VisibleCell", "VisibleCell");
        Cloud = new GameImage("Cloud", "Cloud", Graphic.CELL_WIDTH*3/2, Graphic.CELL_HEIGHT*3/2);

        DestroyedCell = new GameImage("DestroyedCell", "DestroyedCell", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);

        Sea = new GameImage("Sea", "Sea", Graphic.CELL_WIDTH, Graphic.CELL_HEIGHT);


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
            else if (field.getType() == GameImage[].class) {
                try {
                    for (GameImage gameImage : (GameImage[])field.get(o)) {
                        gameImageArrayList.add(gameImage);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    public static GameImage getGameImage(String name) {
        for (GameImage gameImage : gameImageArrayList)
            if (gameImage.name.equals(name)) {
                System.out.println(gameImage.name);
                return gameImage;
            }
        throw new RuntimeException("GameImage " + name + " not found");
    }
}


