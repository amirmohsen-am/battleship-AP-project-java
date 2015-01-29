package battleship.frame;

import battleship.ConsoleOutput;
import battleship.graphic.GraphicObject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by persianpars on 1/29/15.
 */
public class Player2KeyListener implements KeyListener{
    PlayingFrame frame;
    GraphicObject cursor;
    int x;

    public Player2KeyListener(PlayingFrame frame, GraphicObject cursor) {
        this.frame = frame;
        this.cursor = cursor;
        x = 1;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_I:
                cursor.goUpOneRow();
                break;
            case KeyEvent.VK_K:
                cursor.goDownOneRow();
                break;
            case KeyEvent.VK_J:
                cursor.goLeftOneColumn();
                break;
            case KeyEvent.VK_L:
                cursor.goRightOneColumn();
                break;
            case KeyEvent.VK_O:
                frame.playerInfoPanel[x].attackButton.setSelected(true);
                break;
            case KeyEvent.VK_P:
                frame.playerInfoPanel[x].radarButton.setSelected(true);
                break;
            case KeyEvent.VK_OPEN_BRACKET:
                frame.playerInfoPanel[x].aircraftButton.setSelected(true);
                break;
            case KeyEvent.VK_SEMICOLON:
                String text = ButtonSelected.getSelectedButtonText(frame.playerInfoPanel[x].buttonGroup);
                switch (text) {
                    case "Attack":
                        frame.sender[x].send(frame.engine.getTimer() + " " + ConsoleOutput.attack(frame.players[x], cursor.getMapPosition()));
                        break;
                    case "Radar":
                        frame.sender[x].send(frame.engine.getTimer() + " " + ConsoleOutput.radar(frame.players[x], cursor.getMapPosition()));
                        break;
                    case "Aircraft":
                        frame.sender[x].send(frame.engine.getTimer() + " " + ConsoleOutput.aircraft(frame.players[x], cursor.getMapPosition().y));
                        break;
                }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
