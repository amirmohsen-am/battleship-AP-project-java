package battleship.runner;

import battleship.*;
import battleship.Network.Client;
import battleship.Network.Network;
import battleship.Network.NetworkOutputStream;
import battleship.Network.NetworkInputStream;
import battleship.Network.Server;
import battleship.frame.GetPlayerFrame;
import battleship.frame.PlayingFrame;

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

        Server server = new Server();
        NetworkInputStream getter = new NetworkInputStream(server);
        NetworkOutputStream[] sender = new NetworkOutputStream[2];
        for (int i = 0; i < 2; i++)
            sender[i] = new NetworkOutputStream(server);

        GameController controller = new GameController();
        GameEngine engine = new GameEngine(getPlayers(), controller);

        ConsoleInput consoleInput = new ConsoleInput(new Scanner(System.in), controller, log);

        PlayingFrame playingFrame = new PlayingFrame(sender);
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
