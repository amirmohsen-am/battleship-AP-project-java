package battleship.Network;

import java.io.IOException;
import java.io.ObjectInputStream;

public class NetworkOutputStream extends NetworkStream {
    ObjectInputStream input;

    NetworkOutputStream(){}

    public NetworkOutputStream(Server server) {
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
