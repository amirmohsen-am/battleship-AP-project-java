package battleship.graphic.image;

import battleship.graphic.Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

public class GameImage implements Serializable {
    String name;
    String path;
    ImageIcon[] images;
    int width, height;


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameImage(String name, String folder, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.path = "battleship/resources/images/" + folder;
        File[] listOfFiles = null;

        try {
            URL url = getClass().getClassLoader().getResource(this.path);
            if (url != null)
                listOfFiles = new File(url.toURI()).listFiles();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (listOfFiles == null) {
            images = new ImageIcon[1];
            images[0] = new ImageIcon();
            System.err.println("GameImage " + name + " with folder: " + folder  + " has no images");
        }
        else {
            Arrays.sort(listOfFiles, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
                }
            });
            images = new ImageIcon[listOfFiles.length];
            for (int i = 0; i < listOfFiles.length; i++) {
                images[i] = new ImageIcon(listOfFiles[i].getAbsolutePath());
            }
        }
    }


    public String getName() {
        return name;
    }

    public ImageIcon[] getImages() {
        return images;
    }

    public GameImage(String name, String path) {
        this(name, path, Graphic.CELL_HEIGHT, Graphic.CELL_WIDTH);
    }

    public GameImage clone() {
        return new GameImage(name, path, width, height);
    }
}
