package battleship.console;

import battleship.ConsoleInput;
import battleship.GameController;
import battleship.GameEngine;
import battleship.Log;
import battleship.exception.GameOverException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Scanner;

// TODO: Change Exceptions to RuntimeException

public class ConsoleRunner {
    public static void main(String[] args) throws IOException, URISyntaxException {
        GameController controller = new GameController();
        Scanner console = new Scanner(System.in);

        Log log;
        log = new Log(true, "out.txt");
//        File file = new File(ConsoleRunner.class.getResource("input20.txt").toURI());

//        Scanner inputFromFile = new Scanner(file);
        ConsoleInput consoleInput = new ConsoleInput(console, controller, log);
        GameEngine engine = new GameEngine(consoleInput.getPlayers(console), controller);
        controller.init(engine, log, consoleInput);
        controller.start();
        log.close();
//            log.close(); //TODO

    }
}