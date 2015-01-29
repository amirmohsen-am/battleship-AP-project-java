package battleship.runner;

import battleship.Map;
import battleship.Network.Server;
import battleship.runner.startGame.GUI;

import java.io.IOException;

/**
 * Created by persianpars on 1/29/15.
 */
public class StartGameRunner {
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.start();
//        Map.startHeight = Map.startWidth = 0;
//        Map.endWidth = Map.endHeight = 10;
//
//        Server server = new Server();
//
//        try {
//            GraphicRunner.main("127.0.0.1", false, 0, GraphicRunner.getPlayers());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
