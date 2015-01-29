package battleship.runner.startGame;

import battleship.Map;
import battleship.Network.Server;
import battleship.Player;
import battleship.frame.GetMapDimensionFrame;
import battleship.frame.GetPlayerFrame;
import battleship.graphic.Graphic;
import battleship.position.Position;
import battleship.runner.GraphicRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lidia on 1/29/2015.
 */
public class GUI {
    private JFrame mainFrame;
    private JFrame networkFrame;
    private JTextField ipField, portField;
    private int state;


    public void start() {
   /*     javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });*/
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        //Create and set up the window.
        mainFrame = new JFrame("BoxLayoutDemo");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        //Set up the content pane.
        addComponentsToPane(mainFrame.getContentPane());

        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        final ArrayList<JButton> buttons = new ArrayList<JButton>();

        buttons.add(getFirstButton());
        buttons.add(getSecondButton());
        buttons.add(getThirdButton());

        for(JButton button : buttons) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            pane.add(button);
        }
    }

    public JButton getFirstButton() {
        JButton button = new JButton("Play Single Player");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.this.mainFrame.dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GUI.this.startOfflineGame();
                    }
                });
            }
        });
        return button;
    }

    public JButton getSecondButton() {
        JButton button = new JButton("Create a server");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.this.mainFrame.dispose();
                GUI.this.state = 1;
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        networkFrame("Create");
                    }
                });
                /*while(!isNetworkFrameFinished)
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }*/
                System.out.println("ASD");
            }
        });
        return button;
    }

    public JButton getThirdButton() {
        JButton button = new JButton("Join a game");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.this.mainFrame.dispose();
                GUI.this.state = 2;
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        networkFrame("Join");
                    }
                });
            }
        });
        return button;
    }

    boolean buttonNotPressed = false;

    public void networkFrame(String buttonText) {
        networkFrame = new JFrame();
        networkFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        networkFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("IP Adress : "));
        ipField = new JTextField("Enter IP Adress", 20);
        panel.add(ipField);
        mainPanel.add(panel);
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Port Number : "));
        portField = new JTextField("Enter Port Number", 20);
        panel.add(portField);
        mainPanel.add(panel);
        JButton button = new JButton(buttonText);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.this.networkFrame.dispose();
                GUI.this.startOnlineGame();
            }
        });
        mainPanel.add(button);

        networkFrame.add(mainPanel);

        networkFrame.pack();
        networkFrame.setVisible(true);


    }

    public void startOnlineGame() {
        int portNumber = Integer.parseInt(portField.getText());
        String IPAddress = ipField.getText();
        Player[] players = new Player[2];
        if (state == 1) {
            Server server = new Server();
//            Position dimension = new GetMapDimensionFrame().init();
            Position dimension = new Position(10, 10);
            Map.startHeight = Map.startWidth = 0;
            Map.endWidth = dimension.x-1;
            Map.endHeight = dimension.y-1;

            players[0] = new GetPlayerFrame().init(0);
            players[1] = new GetPlayerFrame().init(0);
            try {
                GraphicRunner.main(IPAddress, true, 0, players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {

            players[0] = new GetPlayerFrame().init(0);
            players[1] = new GetPlayerFrame().init(0);
            try {
                GraphicRunner.main(IPAddress, true, 1, players);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void startOfflineGame() {
//        Position dimension = new GetMapDimensionFrame().init();
        Position dimension = new Position(10, 10);
        Map.startHeight = Map.startWidth = 0;
        Map.endWidth = dimension.x-1;
        Map.endHeight = dimension.y-1;
//        Server server = new Server();
        try {
            GraphicRunner.main("127.0.0.1", false, 0, GraphicRunner.getPlayers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

