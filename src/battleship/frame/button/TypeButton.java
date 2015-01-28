package battleship.frame.button;

import battleship.graphic.image.GameImage;

import javax.swing.*;

/**
* Created by persianpars on 1/28/15.
*/
public class TypeButton extends JRadioButton {
    GameImage gameImage;

    public TypeButton(String text, GameImage gameImage) {
//            super(text, gameImage.getImages()[0]);
        super(text);
        this.gameImage = gameImage;
        this.setActionCommand(text);
    }
}
