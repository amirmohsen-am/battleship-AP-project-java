package battleship.startGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Lidia on 1/29/2015.
 */
public class GUI {
    private JFrame mainFrame;
    private JFrame networkFrame;
    private JTextField ipField, portField;
    private int state;


    void start() {
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
                GUI.this.startSingleplayerGame();
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
                GUI.this.startMultiplayerGame();
            }
        });
        mainPanel.add(button);

        networkFrame.add(mainPanel);

        networkFrame.pack();
        networkFrame.setVisible(true);
    }

    public void startMultiplayerGame() {
        int portNumber = Integer.parseInt(portField.getText());
        String IPAdress = ipField.getText();
        Network network;
        if(state==1) {
            network = new Server(IPAdress, portNumber);
        } else {
            network = new Client(IPAdress, portNumber);
        }
    }

    public void startSingleplayerGame() {
    }
}

