package battleship.frame;

import battleship.ConsoleInput;
import battleship.GameEngine;
import battleship.Map;
import battleship.Player;
import battleship.equipment.AntiAircraft;
import battleship.equipment.Mine;
import battleship.equipment.Ship;
import battleship.frame.button.TypeButton;
import battleship.graphic.Graphic;
import battleship.graphic.GraphicObject;
import battleship.graphic.image.GameImages;
import battleship.frame.panel.MapPanel;
import battleship.position.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Created by persianpars on 1/24/15.
 */
public class GetPlayerFrame extends JFrame {
    Player player;
    Map map;
    Graphic graphic = new Graphic();
    GraphicObject cursor;
    InformationPanel informationPanel;
    JPanel mainPanel;
    public static final Random rand = new Random();

    static int tmp = 1;

    public int shipCount[] = new int[4], mineCount = 0, antiCount = 0;

    boolean horizontal = true;

    boolean done = false;
    public Player init(int playerNumber) {
//        String name = JOptionPane.showInputDialog("Player " + playerNumber + " Please enter your name");
        String name = "" + tmp++;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        requestFocus();


        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setFocusable(true);
        mainPanel.requestFocus();

        getContentPane().add(mainPanel);

        MapPanel mapPanel = new MapPanel();
        cursor = new GraphicObject(new Position(0, 0), GameImages.ShipH[0], 0, 10);
        graphic.addGraphicObject(cursor);

        for (int i = 0; i < Map.getHeight(); i++)
            for (int j = 0; j < Map.getWidth(); j++) {
                GraphicObject graphicObject = new GraphicObject(new Position(j, i), GameImages.Cloud, GameImages.CloudSpeed, true, 9);
                graphicObject.setImageIndex(rand.nextInt(graphicObject.getGameImage().getImages().length));
                graphic.addGraphicObject(graphicObject);

            }
        for (int i = 0; i < Map.getHeight(); i++)
            for (int j = 0; j < Map.getWidth(); j++) {
                GraphicObject graphicObject = new GraphicObject(new Position(j, i), GameImages.Sea, GameImages.SeaSpeed, true, 0);
                graphicObject.setImageIndex(rand.nextInt(graphicObject.getGameImage().getImages().length));
                graphic.addGraphicObject(graphicObject);
            }


        map = new Map(true);
        player = new Player(name, map);
        player.setGraphic(graphic);
        map.setOwner(player);

        mapPanel.init(map, graphic);


        KeyListener keyListener = new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        cursor.goLeftOneColumn();
                        break;
                    case KeyEvent.VK_RIGHT:
                        cursor.goRightOneColumn();
                        break;
                    case KeyEvent.VK_DOWN:
                        cursor.goDownOneRow();
                        break;
                    case KeyEvent.VK_UP:
                        cursor.goUpOneRow();
                        break;
                    case KeyEvent.VK_SPACE:
                        cursor.rotate();
                        for (int i = 0; i < informationPanel.shipButton.length; i++)
                            if (informationPanel.shipButton[i].isSelected()) {
                                if (cursor.isHorizontal())
                                    cursor.setGameImage(GameImages.ShipH[i]);
                                else
                                    cursor.setGameImage(GameImages.ShipV[i]);
                            }
                        cursor.setMiddleGraphicPosition(cursor.recalcMiddleGraphPosition());
                        break;
                    case KeyEvent.VK_ENTER:
                        for (int i = 0; i < informationPanel.shipButton.length; i++)
                            if (informationPanel.shipButton[i].isSelected() && shipCount[i]+1 <= GameEngine.SHIP_MAX[i]) {
                                shipCount[i]++;
                                if (cursor.isHorizontal())
                                    map.addEquipment(new Ship(ConsoleInput.getShipPositions(cursor.getMapPosition(), (i + 1), cursor.isHorizontal())), GameImages.ShipH[i]);
                                else
                                    map.addEquipment(new Ship(ConsoleInput.getShipPositions(cursor.getMapPosition(), (i + 1), cursor.isHorizontal())), GameImages.ShipV[i]);
                            }
                        if (informationPanel.mineButton.isSelected() && mineCount <= GameEngine.MINE_MAX) {
                            mineCount++;
                            map.addEquipment(new Mine(cursor.getMapPosition()), GameImages.Mine);
                        }
                        if (informationPanel.antiAircraftButton.isSelected() && antiCount <= GameEngine.ANTIAIRCRAFT_MAX) {
                            antiCount++;
                            map.addEquipment(new AntiAircraft(cursor.getMapPosition()), GameImages.AntiAircraft);
                        }
                        break;
                }
                if (informationPanel.antiAircraftButton.isSelected()) {
                    for (int j = 0; j < Map.getWidth(); j++)
                        cursor.goLeftOneColumn();
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

        mainPanel.add(mapPanel, BorderLayout.WEST);
        informationPanel = new InformationPanel();
        informationPanel.init();

        mainPanel.add(informationPanel, BorderLayout.EAST);


        pack();
        setVisible(true);



        while (!done) {
            repaint();
            try {
                Thread.sleep(1000 / Graphic.FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        graphic.getGraphicObjects().remove(cursor);

        dispose();
        return player;


    }
    public class InformationPanel extends JPanel {
        JRadioButton[] shipButton = new JRadioButton[GameImages.ShipH.length];
        JRadioButton mineButton;
        JRadioButton antiAircraftButton;
        ButtonGroup buttonGroup;

        void init() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setPreferredSize(new Dimension(150, 100));

//            setFocusable(false);

            buttonGroup = new ButtonGroup();

            for (int i = 0; i < shipButton.length; i++)
                shipButton[i] = new TypeButton("Ship" + (i+1), GameImages.ShipH[i]);
            shipButton[0].setSelected(true);
            mineButton = new TypeButton("Mine", GameImages.Mine);
            antiAircraftButton = new TypeButton("AntiAircraft", GameImages.AntiAircraft);

            ButtonsActionListener actionListener = new ButtonsActionListener(GetPlayerFrame.this, cursor);
            for (int i = 0; i < shipButton.length; i++)
                shipButton[i].addActionListener(actionListener);
            mineButton.addActionListener(actionListener);
            antiAircraftButton.addActionListener(actionListener);

            for (int i = 0; i < shipButton.length; i++)
                shipButton[i].setFocusable(false);
            mineButton.setFocusable(false);
            antiAircraftButton.setFocusable(false);

            for (int i = 0; i < shipButton.length; i++)
                buttonGroup.add(shipButton[i]);
            buttonGroup.add(mineButton);
            buttonGroup.add(antiAircraftButton);

            for (int i = 0; i < shipButton.length; i++)
                add(shipButton[i]);
            add(mineButton);
            add(antiAircraftButton);


            JButton done = new JButton("Done");

            done.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GetPlayerFrame.this.done = true;
                }
            });
            add(done);
        }
    }
}