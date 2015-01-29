package battleship.Network;

import battleship.Map;
import battleship.Player;
import sun.nio.ch.Net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Lidia on 1/29/2015.
 */
public class NetworkClient {
    int portNumber;
    String machineName;
    Socket server;
    ObjectInputStream input;
    ObjectOutputStream output;

    public NetworkClient() {
        this(3109);
    }

    public NetworkClient(int portNumber) {
        this("127.0.0.1", portNumber);
    }

    public NetworkClient(String machineName, int portNumber) {
        this.machineName = machineName;
        this.portNumber = portNumber;
        try {
            server = new Socket(machineName, portNumber);
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String nextLine() {
        try {
            System.out.println("waiting for network input");
            String result = (String)input.readObject();
            System.out.println("got input");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void send(String s) {
        try {
            output.writeObject(s);
            output.flush();
            System.out.println("Send: " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPlayer(Player player) {
        try {
            output.writeObject(player);
            output.flush();
            //output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player readPlayer() {
        try {
            Player player = (Player)input.readObject();
            return player;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
