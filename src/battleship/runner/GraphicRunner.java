package battleship.runner;

import battleship.*;
import battleship.Network.NetworkClient;
import battleship.Network.Server;
import battleship.frame.GetPlayerFrame;
import battleship.frame.PlayingFrame;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Created by persianpars on 1/25/15.
 */
public class GraphicRunner {
    public static void main(String ipAddress, boolean online, int playerNumber, Player[] players) throws IOException {
//        Map.startHeight = Map.startWidth = 0;
//        Map.endWidth = Map.endHeight = 10;
//        GetMapFrame getMapFrame = new GetMapFrame();
//        getMapFrame.init();
        Log log;
        log = new Log(true, "out.txt");

        NetworkClient getter;
        getter = new NetworkClient(ipAddress, 3109);
        NetworkClient[] sender;
        sender = new NetworkClient[2];
        for (int i = 0; i < 2; i++)
            sender[i] = new NetworkClient(ipAddress, 3109);

//        Scanner getter = new Scanner(System.in);

        GameController controller = new GameController();
        GameEngine engine = new GameEngine(players, controller);

        ConsoleInput consoleInput = new ConsoleInput(getter, controller, log);


        PlayingFrame playingFrame = new PlayingFrame(sender);
//        playingFrame.init(controller, engine);
        controller.init(engine, log, consoleInput, playingFrame, online, playerNumber);
        controller.start();
    }

    public static Player[] getPlayers() {
        Player[] players = new Player[2];
//        GetPlayerFrame[] getPlayerFrames = new GetPlayerFrame[2];
        System.out.println("Waiting for Player 1 Info");
        players[0] = new GetPlayerFrame().init(1);
        System.out.println("Got Player 1 Info");
        System.out.println("Waiting for Player 2 Info");
        players[1] = new GetPlayerFrame().init(2);
        System.out.println("Got Player 2 Info");

        return players;
    }

}
