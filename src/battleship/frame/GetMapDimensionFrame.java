package battleship.frame;

import battleship.position.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by persianpars on 1/29/15.
 */
public class GetMapDimensionFrame extends JFrame {
    private JTextField widthField, heightField;
    boolean exit = false;
    public Position init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        requestFocus();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setFocusable(true);
        mainPanel.requestFocus();

        getContentPane().add(mainPanel);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Width : "));
        widthField = new JTextField("10", 20);
        panel.add(widthField);
        mainPanel.add(panel);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Height : "));
        heightField = new JTextField("10", 20);
        panel.add(heightField);

        mainPanel.add(panel);

        JButton button = new JButton("OK");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit = true;
            }
        });

        pack();
        setVisible(true);

        while (!exit) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Position(Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()));
    }

}
