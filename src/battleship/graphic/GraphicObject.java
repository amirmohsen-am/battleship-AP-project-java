package battleship.graphic;

import battleship.graphic.image.GameImage;
import battleship.position.Position;

import javax.swing.*;

/**
 * Created by persianpars on 1/24/15.
 */
public class GraphicObject implements Runnable {
    Position mapPosition, middleGraphicPosition;
    GameImage gameImage;

    int imageIndex = 0;
    int speedMS = 0;
    boolean loop = false;

//    public GraphicObject(Position middleGraphicPosition, GameImage gameImage) {
//        this.middleGraphicPosition = middleGraphicPosition;
//        this.gameImage = gameImage;
//    }

    public GraphicObject(Position mapPosition, Position middleGraphicPosition, GameImage gameImage, int speedMS, boolean loop) {
        this.middleGraphicPosition = middleGraphicPosition;
        this.mapPosition = mapPosition;
        this.gameImage = gameImage;
        this.speedMS = speedMS;
        this.loop = loop;
    }

    public GraphicObject(Position mapPosition, Position middleGraphicPosition, GameImage gameImage, int speedMS) {
        this(mapPosition, middleGraphicPosition, gameImage, speedMS, false);
    }


    public GraphicObject(Position mapPosition, Position middleGraphicPosition, GameImage gameImage) {
        this(mapPosition, middleGraphicPosition, gameImage, 0);
    }


    public GraphicObject(Position mapPosition, GameImage gameImage, int speedMS) {
        this(mapPosition, Graphic.getMiddleGraphicPosition(mapPosition), gameImage, speedMS);
    }

    public GraphicObject(Position mapPosition, GameImage gameImage, int speedMS, boolean loop) {
        this(mapPosition, Graphic.getMiddleGraphicPosition(mapPosition), gameImage, speedMS, loop);
    }


    public GraphicObject(Position mapPosition, GameImage gameImage) {
        this(mapPosition, gameImage, 0);
    }



    public void setGameImage(GameImage gameImage) {
        this.gameImage = gameImage;
        imageIndex = 0;
    }

    public Position getMapPosition() {
        return mapPosition;
    }

    public Position getMiddleGraphicPosition() {
        return middleGraphicPosition;
    }

    public int getWidth() { return gameImage.getWidth(); }
    public int getHeight() { return gameImage.getHeight(); }

    public GameImage getGameImage() {
        return gameImage;
    }

    public ImageIcon getCurrentImage() {
        return gameImage.getImages()[imageIndex];
    }

    public Position getTopLeftGraphicPosition() {
        return new Position(middleGraphicPosition.x - gameImage.getWidth()/2, middleGraphicPosition.y - gameImage.getHeight()/2);
    }

    public void setMiddleGraphicPosition(Position middleGraphicPosition) {
        this.middleGraphicPosition = middleGraphicPosition;
    }

    public void goUpOneRow() {
        addDimension(0, -1);
    }

    public void goDownOneRow() {
        addDimension(0, 1);
    }

    public void goLeftOneColumn() {
        addDimension(-1, 0);
    }

    public void goRightOneColumn() {
        addDimension(1, 0);
    }

    public boolean addDimension(int column, int row) {
        middleGraphicPosition.x += Graphic.CELL_WIDTH * column;
        middleGraphicPosition.y += Graphic.CELL_HEIGHT * row;
        if (!Graphic.isValidPosition(middleGraphicPosition)) {
            middleGraphicPosition.x -= Graphic.CELL_WIDTH * column;
            middleGraphicPosition.y -= Graphic.CELL_HEIGHT * row;
            return false;
        }
        mapPosition.x += column;
        mapPosition.y += row;
        return true;

    }

    @Override
    public void run() {
        while (speedMS != 0) {
            imageIndex++;
            if (imageIndex >= gameImage.getImages().length) {
                if (loop)
                    imageIndex = 0;
                else
                    break;
            }
            try {
                Thread.sleep(speedMS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
