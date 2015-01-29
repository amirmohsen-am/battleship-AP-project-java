package battleship.frame;

import battleship.GameController;
import battleship.GameEngine;
import battleship.Network.NetworkClient;
import battleship.Player;
import battleship.exception.GameOverException;
import battleship.frame.button.TypeButton;
import battleship.frame.panel.MapPanel;
import battleship.graphic.Graphic;
import battleship.graphic.GraphicObject;
import battleship.graphic.image.GameImages;
import battleship.position.Position;
import sun.nio.ch.Net;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by persianpars on 1/28/15.
 */
public class PlayingFrame extends JFrame {
    GameController controller;
    GameEngine engine;

    Player[] players;

    NetworkClient[] sender;

    Graphic[] graphic;
    GraphicObject[] cursor = new GraphicObject[2];

    JPanel mainPanel;
    PlayerPanel[] playerPanel = new PlayerPanel[2];
    MapPanel[] mapPanel = new MapPanel[2];

    InformationPanel[] informationPanel = new InformationPanel[2];

    public PlayingFrame(NetworkClient sender[]) {
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
            cursor[i] = new GraphicObject(new Position(0, 0), GameImages.CursorAttack, 0, 10);
            graphic[i].addGraphicObject(cursor[i]);
            mapPanel[i].init(players[i].getMap(), graphic[i]);

            informationPanel[i] = new InformationPanel();

            playerPanel[i] = new PlayerPanel();
            playerPanel[i].add(mapPanel[i]);
            playerPanel[i].add(informationPanel[i]);
        }
        Player1KeyListener player1KeyListener = new Player1KeyListener(this, cursor[1]);
        Player2KeyListener player2KeyListener = new Player2KeyListener(this, cursor[0]);

        mainPanel.addKeyListener(player1KeyListener);
        mainPanel.addKeyListener(player2KeyListener);


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

    protected class InformationPanel extends JPanel {
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
