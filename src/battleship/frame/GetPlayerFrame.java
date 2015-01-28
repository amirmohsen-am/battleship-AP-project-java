package battleship.frame;

import battleship.ConsoleInput;
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

    static int tmp = 1;

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
        cursor = new GraphicObject(new Position(0, 0), GameImages.Ship[0]);
        graphic.addGraphicObject(cursor);
        mapPanel.init(graphic);

        map = new Map(true);
        player = new Player(name, map);
        player.setGraphic(graphic);
        map.setOwner(player);

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
                        horizontal = Boolean.logicalXor(horizontal, true);
                        // TODO : rotation
                        break;
                    case KeyEvent.VK_ENTER:
                        for (int i = 0; i < informationPanel.shipButton.length; i++)
                            if (informationPanel.shipButton[i].isSelected())
                                map.addEquipment(new Ship(ConsoleInput.getShipPositions(cursor.getMapPosition(), (i+1), horizontal)), cursor.getGameImage());
                        if (informationPanel.mineButton.isSelected())
                            map.addEquipment(new Mine(cursor.getMapPosition()), cursor.getGameImage());
                        if (informationPanel.antiAircraftButton.isSelected())
                            map.addEquipment(new AntiAircraft(cursor.getMapPosition()), cursor.getGameImage());
                        break;
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
    private class InformationPanel extends JPanel {
        JRadioButton[] shipButton = new JRadioButton[GameImages.Ship.length];
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
                shipButton[i] = new TypeButton("Ship" + (i+1), GameImages.Ship[i]);
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