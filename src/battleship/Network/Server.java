package battleship.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Scanner;

import java.io.*;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server extends Network{
    ServerSocket myService = null;

    Server(){};

    Server(String machineName, int portNumber) {
        return;
        /*this.machineName = machineName;
        this.portNumber = portNumber;*/
    }

    public void start() {
        System.out.println("server is here");
        try {
            scanner = new Scanner(System.in);
            myService = new ServerSocket(portNumber);
            userSocket = myService.accept();
            output = new ObjectOutputStream(userSocket.getOutputStream());
            input = new ObjectInputStream(userSocket.getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }

        chat();
    }

    private void chat() {
        System.out.println("server you are connected");

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String s = null;
                    try {
                        s = (String)input.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println("client: " + s);
                }
            }
        });
        myThread.start();

        Thread myThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String s = scanner.nextLine();
                    System.out.println("server : " + s);
                    try {
                        output.writeObject(s);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        });
        myThread2.start();
    }
}
