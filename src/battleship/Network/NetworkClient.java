package battleship.Network;

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

    public NetworkClient(){
        this("127.0.0.1", 3109);
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
            return (String)input.readObject();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
