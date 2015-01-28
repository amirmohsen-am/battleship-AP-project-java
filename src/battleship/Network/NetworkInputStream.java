package battleship.Network;

import java.io.IOException;
import java.io.ObjectInputStream;

public class NetworkInputStream extends NetworkStream {
    ObjectInputStream input;

    NetworkInputStream(){}

    public NetworkInputStream(Server server) {
        super(server);
        server.add(this);
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
}
