package battleship.frame;

import battleship.ConsoleOutput;
import battleship.GameController;
import battleship.GameEngine;
import battleship.Network.NetworkInputStream;
import battleship.Network.NetworkOutputStream;
import battleship.Player;
import battleship.exception.GameOverException;
import battleship.frame.button.TypeButton;
import battleship.frame.panel.MapPanel;
import battleship.graphic.Graphic;
import battleship.graphic.GraphicObject;
import battleship.graphic.image.GameImages;
import battleship.position.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by persianpars on 1/28/15.
 */
public class PlayingFrame extends JFrame {
    GameController controller;
    GameEngine engine;

    Player[] players;

    NetworkOutputStream[] sender;

    Graphic[] graphic;
    GraphicObject[] cursor = new GraphicObject[2];

    JPanel mainPanel;
    PlayerPanel[] playerPanel = new PlayerPanel[2];
    MapPanel[] mapPanel = new MapPanel[2];

    InformationPanel[] informationPanel = new InformationPanel[2];

    public PlayingFrame(NetworkOutputStream sender[]) {
        this.sender = sender;
    }

    public void init(final GameController controller, final GameEngine engine) {
        this.controller = controller;
        this.engine = engine;
        graphic = controller.getGraphic();
        players = engine.getPlayers();
//        graphic = new Graphic[2];
//        for (int i = 0; i < 2; i++) {
//            graphic[i] = new Graphic();
//        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setFocusable(true);
        mainPanel.requestFocus();


        getContentPane().add(mainPanel);

        for (int i = 0; i < 2; i++) {
            mapPanel[i] = new MapPanel();
            cursor[i] = new GraphicObject(new Position(0, 0), GameImages.CursorAttack);
            graphic[i].addGraphicObject(cursor[i]);
            mapPanel[i].init(players[i].getMap(), graphic[i]);

            informationPanel[i] = new InformationPanel();

            playerPanel[i] = new PlayerPanel();
            playerPanel[i].add(mapPanel[i]);
            playerPanel[i].add(informationPanel[i]);
        }
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        cursor[1].goUpOneRow();
                        break;
                    case KeyEvent.VK_S:
                        cursor[1].goDownOneRow();
                        break;
                    case KeyEvent.VK_A:
                        cursor[1].goLeftOneColumn();
                        break;
                    case KeyEvent.VK_D:
                        cursor[1].goRightOneColumn();
                        break;
                    case KeyEvent.VK_I:
                        cursor[0].goUpOneRow();
                        break;
                    case KeyEvent.VK_K:
                        cursor[0].goDownOneRow();
                        break;
                    case KeyEvent.VK_J:
                        cursor[0].goLeftOneColumn();
                        break;
                    case KeyEvent.VK_L:
                        cursor[0].goRightOneColumn();
                        break;
                    case KeyEvent.VK_F:
                        String text = ButtonSelected.getSelectedButtonText(informationPanel[0].buttonGroup);
                        switch (text) {
                            case "Attack":
                                sender[0].send(engine.getTimer() + " " + ConsoleOutput.attack(players[0], cursor[0].getMapPosition()));
                                break;
                            case "Radar":
                                sender[0].send(engine.getTimer() + " " + ConsoleOutput.radar(players[0], cursor[0].getMapPosition()));
                                break;
                            case "Aircraft":
                                sender[0].send(engine.getTimer() + " " + ConsoleOutput.aircraft(players[0], cursor[0].getMapPosition().y));
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
        };
        mainPanel.addKeyListener(keyListener);


        mainPanel.add(playerPanel[0]);
        mainPanel.add(new JSeparator(SwingConstants.VERTICAL));
        JButton go = new JButton("Go");
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.getEngine().update();
                } catch (GameOverException e1) {
                    e1.printStackTrace();
                }
            }
        });
        mainPanel.add(go);
        mainPanel.add(new JSeparator(SwingConstants.VERTICAL));
        mainPanel.add(playerPanel[1]);

        pack();
        setVisible(true);

    }

    private class PlayerPanel extends JPanel {
        public PlayerPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//            setPreferredSize(new Dimension(150, 100));

        }
    }

    private class InformationPanel extends JPanel {
        JRadioButton attackButton;
        JRadioButton radarButton;
        JRadioButton aircraftButton;
        ButtonGroup buttonGroup;

        public InformationPanel() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setPreferredSize(new Dimension(150, 100));

            setFocusable(false);

            buttonGroup = new ButtonGroup();

            attackButton = new TypeButton("Attack", GameImages.CursorAttack);
            radarButton = new TypeButton("Radar", GameImages.CursorRadar);
            aircraftButton = new TypeButton("Aircraft", GameImages.CursorAircraft);

            attackButton.setSelected(true);

            attackButton.setFocusable(false);
            radarButton.setFocusable(false);
            aircraftButton.setFocusable(false);

            buttonGroup.add(attackButton);
            buttonGroup.add(radarButton);
            buttonGroup.add(aircraftButton);

            add(attackButton);
            add(radarButton);
            add(aircraftButton);
        }

    }
}
