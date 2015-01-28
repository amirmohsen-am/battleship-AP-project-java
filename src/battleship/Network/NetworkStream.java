package battleship.Network;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Lidia on 1/28/2015.
 */
public abstract class NetworkStream {
    Server server;
    Socket clientSocket;

    NetworkStream(){}

    public NetworkStream(Server server) {
        this.server = server;
    }
}
