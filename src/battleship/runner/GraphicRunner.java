package battleship.runner;

import battleship.*;
import battleship.frame.GetPlayerFrame;
import battleship.frame.PlayingFrame;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

/**
 * Created by persianpars on 1/25/15.
 */
public class GraphicRunner {
    public static void main(String[] args) throws IOException {
        Map.startHeight = Map.startWidth = 0;
        Map.endWidth = Map.endHeight = 10;
//        GetMapFrame getMapFrame = new GetMapFrame();
//        getMapFrame.init();
        Log log;
        log = new Log(true, "out.txt");

        DataOutputStream writer = null;

        GameController controller = new GameController();
        GameEngine engine = new GameEngine(getPlayers(), controller);
        ConsoleInput consoleInput = new ConsoleInput(new Scanner(System.in), controller, log);

        PlayingFrame playingFrame = new PlayingFrame(writer);
//        playingFrame.init(controller, engine);
        controller.init(engine, log, consoleInput, playingFrame);
        controller.start();
    }

    public static Player[] getPlayers() {
        Player[] players = new Player[2];
//        GetPlayerFrame[] getPlayerFrames = new GetPlayerFrame[2];
        players[0] = new GetPlayerFrame().init(1);
        System.out.println("dadga");
        players[1] = new GetPlayerFrame().init(2);
        return players;
    }

}
