package battleship.frame;

import battleship.ConsoleOutput;
import battleship.graphic.GraphicObject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by persianpars on 1/29/15.
 */
public class Player1KeyListener implements KeyListener {
    PlayingFrame frame;
    GraphicObject cursor;
    int x;

    public Player1KeyListener(PlayingFrame frame, GraphicObject cursor) {
        this.frame = frame;
        this.cursor = cursor;
        x = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                cursor.goUpOneRow();
                break;
            case KeyEvent.VK_S:
                cursor.goDownOneRow();
                break;
            case KeyEvent.VK_A:
                cursor.goLeftOneColumn();
                break;
            case KeyEvent.VK_D:
                cursor.goRightOneColumn();
                break;
            case KeyEvent.VK_F:
                String text = ButtonSelected.getSelectedButtonText(frame.informationPanel[x].buttonGroup);
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
