package battleship.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lidia on 1/29/2015.
 */
public class Server {

    int portNumber;
    String machineName;
    ServerSocket myService;
    ArrayList<ObjectOutputStream> outputsNOS = new ArrayList<ObjectOutputStream>();

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
        run();
    }

    public void run() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {;
                    try {
                        Thread.sleep(20);
                        Socket socket = myService.accept();
                        System.out.println("new client connected");
                        outputsNOS.add(new ObjectOutputStream(socket.getOutputStream()));
                        final ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        Thread thread1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    try {
                                        try {
                                            Thread.sleep(20);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        String s = (String) input.readObject();
                                        for (ObjectOutputStream output : outputsNOS) {
                                            output.writeObject(s);
                                            output.flush();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        thread1.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
