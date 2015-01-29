package battleship.Network;

import java.io.IOException;

/**
 * Created by Lidia on 1/29/2015.
 */
public class MainNetwork {
    public static void main(String[] args) {
        Server server = new Server();

        final NetworkClient client1 = new NetworkClient();
        final NetworkClient client2 = new NetworkClient();
        //   NetworkClient client3 = new NetworkClient();

        client2.send("ASDS");
        client2.send("q234");


        String s = "";
        //s = (String)client2.input.readObject();
        //System.out.println(s);
        s = client1.nextLine();
        System.out.println(s);
        s = client1.nextLine();
        System.out.println("but not there");
        System.out.println(s);
    }
}
