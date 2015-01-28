package battleship.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class NetworkInputStream extends NetworkStream {
    ObjectOutputStream output;

    NetworkInputStream(){};

    public NetworkInputStream(Server server) {
        super(server);
        server.add(this);
    }

    public void send(String s) {
        try {
            output.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
