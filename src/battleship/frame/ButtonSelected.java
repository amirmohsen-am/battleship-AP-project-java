package battleship.frame;

import javax.swing.*;
import java.util.Enumeration;

/**
 * Created by persianpars on 1/29/15.
 */
public class ButtonSelected {
    public static String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

}
