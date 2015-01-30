package battleship.runner;

import battleship.Map;
import battleship.Network.NetworkClient;
import battleship.Network.Server;
import battleship.Network.ServerPlayer;
import battleship.Player;
import battleship.frame.GetMapDimensionFrame;
import battleship.frame.GetPlayerFrame;
import battleship.position.Position;
import battleship.runner.startGame.GUI;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by persianpars on 1/29/15.
 */
public class StartGameRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        GUI gui = new GUI();
//        gui.start();

        String string = scanner.next();
        if (Objects.equals(string, "Offline")) {
            Position dimension = new GetMapDimensionFrame().init();
//            Position dimension = new Position(10, 10);
            Map.startHeight = Map.startWidth = 0;
            Map.endWidth = dimension.x-1;
            Map.endHeight = dimension.y-1;
            Server server = new Server();
            try {
                GraphicRunner.main("127.0.0.1", false, 0, GraphicRunner.getPlayers());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {

            Player[] players = new Player[2];
            Position dimension = new GetMapDimensionFrame().init();
//            Position dimension = new Position(10, 10);
            Map.startHeight = Map.startWidth = 0;
            Map.endWidth = dimension.x-1;
            Map.endHeight = dimension.y-1;
            if (string.equals("Create")) {



                Server server = new Server();
                ServerPlayer serverPlayer = new ServerPlayer();

                NetworkClient client = new NetworkClient(3121);
                while(serverPlayer.numberOfPlayers < 2) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("here I am");
                players[0] = new GetPlayerFrame().init(0);
                System.out.println("asdasd");

                client.sendPlayer(players[0]);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                players[1] = new GetPlayerFrame().init(0);
                players[0] = client.readPlayer();
                players[1] = client.readPlayer();

                System.out.println("POOOOOOL");
                System.exit(0);

                try {
                    GraphicRunner.main("127.0.0.1", true, 0, players);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                String IPAddress = scanner.next();
  //              players[0] = new GetPlayerFrame().init(0);

                players[1] = new GetPlayerFrame().init(0);

                NetworkClient client = new NetworkClient(IPAddress, 3121);

                client.sendPlayer(players[1]);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                players[0] = client.readPlayer();
                players[1] = client.readPlayer();

                System.exit(0);


                try {
                    GraphicRunner.main(IPAddress, true, 1, players);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

//        Map.startHeight = Map.startWidth = 0;
//        Map.endWidth = Map.endHeight = 10;
//
//        Server server = new Server();
//        try {
//            GraphicRunner.main("127.0.0.1", false, 0, GraphicRunner.getPlayers());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
