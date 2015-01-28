package battleship.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    int portNumber;
    String machineName;
    ServerSocket myService;
    ArrayList<ObjectOutputStream> outputsNOS = new ArrayList<ObjectOutputStream>();
    Socket ret;

    public Server() {
        this("127.0.0.1",3109);
    }

    public Server(String machineName, int portNumber) {
        this.machineName = machineName;
        this.portNumber = portNumber;
        try {
            myService = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getMachineName() {
        return machineName;
    }

    private Socket Connect(NetworkStream client) {
        ret = null;
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ret = myService.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            client.clientSocket = new Socket(machineName, portNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    void add(NetworkOutputStream outputStream) {
        Socket socket = Connect(outputStream);
        try {
            outputsNOS.add(new ObjectOutputStream(socket.getOutputStream()));
            outputStream.input = new ObjectInputStream(outputStream.clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void add(NetworkInputStream inputStream) {
        Socket socket = Connect(inputStream);
        try {
            final ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            inputStream.output = new ObjectOutputStream(inputStream.clientSocket.getOutputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            String s = (String) input.readObject();
                            for (ObjectOutputStream output : outputsNOS)
                                output.writeObject(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
