package battleship.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Network {

    int portNumber = 3109;
    String machineName = "127.0.0.1";
    Scanner scanner;
    Socket userSocket = null;
    ObjectInputStream input = null;
    ObjectOutputStream output=null;

    Network(){}

    void sendMassege(String s) {
        try {
            output.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendChatMassege(String s) {
        sendMassege("CHAT" + s);
    }

    public String nextLine() {
        String ret = null;
        try {
            ret = (String)input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
