package battleship.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class NetworkOutputStream extends NetworkStream {
    ObjectOutputStream output;

    NetworkOutputStream(){};

    public NetworkOutputStream(Server server) {
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
