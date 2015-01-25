package battleship.frame;

import battleship.ConsoleInput;
import battleship.Map;
import battleship.equipment.AntiAircraft;
import battleship.equipment.Mine;
import battleship.equipment.Ship;
import battleship.graphic.Graphic;
import battleship.graphic.GraphicObject;
import battleship.graphic.image.GameImage;
import battleship.graphic.image.GameImages;
import battleship.frame.panel.MapPanel;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by persianpars on 1/24/15.
 */
public class GetMapFrame extends JFrame {
    Map map;
    Graphic graphic = new Graphic();
    GraphicObject cursor;
    InformationPanel informationPanel;
    JPanel mainPanel;

    public void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        MapPanel mapPanel = new MapPanel();
        cursor = new GraphicObject(new Position(0, 0), Graphic.getMiddleGraphicPosition(0, 0), GameImages.Ship);
        graphic.addGraphicObject(cursor);
        mapPanel.init(graphic);

        map = new Map(true);
        map.setGraphic(graphic);

        mapPanel.addKeyListener(new KeyListener() {

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
                    case KeyEvent.VK_ENTER:
                        if (informationPanel.shipButton.isSelected())
                            map.addEquipment(new Ship(ConsoleInput.getShipPositions(cursor.getMapPosition(), 1, true)), cursor.getGameImage());
                        else if (informationPanel.mineButton.isSelected())
                            map.addEquipment(new Mine(cursor.getMapPosition()), cursor.getGameImage());
                        else
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
        });

        mainPanel.add(mapPanel, BorderLayout.WEST);
        informationPanel = new InformationPanel();
        informationPanel.init();

        mainPanel.add(informationPanel, BorderLayout.EAST);

        getContentPane().add(mainPanel);

        pack();
        setVisible(true);

        while (true) {
            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        dispose();

    }
    public class InformationPanel extends JPanel {
        JRadioButton shipButton;
        JRadioButton mineButton;
        JRadioButton antiAircraftButton;
        void init() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setPreferredSize(new Dimension(150, 100));

            setFocusable(false);

            ButtonGroup buttonGroup = new ButtonGroup();

            shipButton = new typeButton("Ship", GameImages.Ship);
            shipButton.setSelected(true);
            mineButton = new typeButton("Mine", GameImages.Mine);
            antiAircraftButton = new typeButton("AntiAircraft", GameImages.AntiAircraft);

            ButtonsActionListener actionListener = new ButtonsActionListener(cursor);

            shipButton.addActionListener(actionListener);
            mineButton.addActionListener(actionListener);
            antiAircraftButton.addActionListener(actionListener);

            shipButton.setFocusable(false);
            mineButton.setFocusable(false);
            antiAircraftButton.setFocusable(false);

            buttonGroup.add(shipButton);
            buttonGroup.add(mineButton);
            buttonGroup.add(antiAircraftButton);

            add(shipButton);
            add(mineButton);
            add(antiAircraftButton);
        }
    }
    public class typeButton extends JRadioButton {
        GameImage gameImage;

        public typeButton(String text, GameImage gameImage) {
//            super(text, gameImage.getImages()[0]);
            super(text);
            this.gameImage = gameImage;
            this.setActionCommand(text);
        }
    }
}