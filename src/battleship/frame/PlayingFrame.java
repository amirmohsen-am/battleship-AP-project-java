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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
    public GameInfoPanel gameInfoPanel;

    boolean online;
    int playerNumber;

    PlayerInfoPanel[] playerInfoPanel = new PlayerInfoPanel[2];

    public PlayingFrame(NetworkClient sender[]) {
        this.sender = sender;
    }

    public void init(final GameController controller, final GameEngine engine, boolean online, int playerNumber) {
        this.controller = controller;
        this.engine = engine;
        graphic = controller.getGraphic();
        players = engine.getPlayers();
        this.online = online;
        this.playerNumber = playerNumber;

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

            mapPanel[i].init(players[i].getMap(), graphic[i]);

            playerInfoPanel[i] = new PlayerInfoPanel();

            playerPanel[i] = new PlayerPanel();
            playerPanel[i].add(mapPanel[i]);
            playerPanel[i].add(playerInfoPanel[i]);
        }


        Player1KeyListener player1KeyListener = new Player1KeyListener(this, cursor[1]);
        Player2KeyListener player2KeyListener = new Player2KeyListener(this, cursor[0]);

        if (online) {
            graphic[playerNumber^1].addGraphicObject(cursor[playerNumber^1]);
            if (playerNumber == 0)
                mainPanel.addKeyListener(player1KeyListener);
            else
                mainPanel.addKeyListener(player2KeyListener);
        }
        else {
            for (int i = 0; i < 2; i++)
            graphic[i].addGraphicObject(cursor[i]);
            mainPanel.addKeyListener(player1KeyListener);
            mainPanel.addKeyListener(player2KeyListener);
        }

        mainPanel.add(playerPanel[0]);
        mainPanel.add(new JSeparator(SwingConstants.VERTICAL));
        gameInfoPanel = new GameInfoPanel();
        gameInfoPanel.init(controller, engine);

        mainPanel.add(gameInfoPanel);
        mainPanel.add(new JSeparator(SwingConstants.VERTICAL));
        mainPanel.add(playerPanel[1]);

        pack();
        setVisible(true);

    }

    public class PlayerPanel extends JPanel {
        public PlayerPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//            setPreferredSize(new Dimension(150, 100));

        }
    }

    public class PlayerInfoPanel extends JPanel {
        JRadioButton attackButton;
        JRadioButton radarButton;
        JRadioButton aircraftButton;
        ButtonGroup buttonGroup;

        public PlayerInfoPanel() {
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
    class GameInfoPanel extends JPanel {
        JLabel timeLabel;
        GameController controller;
        GameEngine engine;

        public void init(final GameController controller, GameEngine engine) {
            this.engine = engine;
            this.controller = controller;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//            setPreferredSize(new Dimension(150, 100));

            timeLabel = new JLabel("time: " + engine.getTimer());
//        JButton go = new JButton("Go");
//        go.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    controller.getEngine().update();
//                } catch (GameOverException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
//        go.setFocusable(false);

//        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        go.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(timeLabel);
//        add(go);
        }

//    @Override
//    public void paintComponents(Graphics g) {
//        System.out.println("Salam");
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawString("time: " + engine.getTimer(), 1, 10);
//        timeLabel.setText("time: " + engine.getTimer());
//    }
    }

}
