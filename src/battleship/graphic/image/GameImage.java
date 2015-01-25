package battleship.graphic.image;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class GameImage {
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

    public String getName() {
        return name;
    }

    public ImageIcon[] getImages() {
        return images;
    }

    public GameImage(String name, int width, int height, String folder) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.path = "battleship/resources/images/" + folder;
        File[] listOfFiles = new File[0];

        try {
            listOfFiles = new File(getClass().getClassLoader().getResource(this.path).toURI()).listFiles();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        images = new ImageIcon[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
            images[i] = new ImageIcon(listOfFiles[i].getAbsolutePath());
        }

    }
}
