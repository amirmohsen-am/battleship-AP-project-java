package battleship;

import battleship.equipment.AntiAircraft;
import battleship.equipment.Equipment;
import battleship.equipment.Mine;
import battleship.equipment.Ship;
import battleship.exception.GameOverException;
import battleship.exception.NoMoreInputException;
import battleship.position.EquipmentPosition;
import battleship.position.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Reads Input
 *
 * @author Ahanchi
 */
public class ConsoleInput {
    private GameController controller;
    private Scanner scanner;
    Log log;

    int startWidth = 0, endWidth; // baz baste boodan dar JavaDoc
    int startHeight = 0, endHeight;



    public ConsoleInput(Scanner scanner, GameController controller, Log log) {
        this.scanner = scanner;
        this.controller = controller;
        this.log = log;
    }

    /** Reads the next command from input
     *
     * @throws Exception if the game is not over, but there's no more input
     */
    public void next() throws NoMoreInputException, IOException, GameOverException {
        if (!scanner.hasNext())
            throw new NoMoreInputException();
        String input = scanner.nextLine();
        input = input.toLowerCase();
        if (input.contains("go")) {
            int number = Integer.parseInt(input.substring(3));
            for (int i = 0; i < number; i++)
                controller.getEngine().update();
        }
        else if (input.contains("attack"))
            controller.getEngine().addEvent(1, input);
        else if (input.contains("aircraft") || input.contains("radar"))
            controller.getEngine().addEvent(2, input);
        else
            throw new IOException("Wrong Input Format during Game");
    }

    /** Reads the names and map of players
     *
     * @param console the scanner to read from
     * @return the read players
     */
    public Player[] getPlayers(Scanner console) throws IOException {
        Player[] players = new Player[2];
        endHeight = console.nextInt()-1;
        endWidth = console.nextInt()-1;
        console.nextLine();

        for (int i = 0; i < 2; i++) {
//            String input = console.nextLine();
//            Matcher matcher = Pattern.compile("Player number (\\d+), please build your map").matcher(input);
            log.println("Player number " + (i+1) + ", please build your map");
            String name = "" + ((char)(((int)'a')+i));
            players[i] = new Player(name, getMap(console));
        }
        return players;
    }
    
    /** Reads the map of a player
     *
     * @param console the scanner to read from
     * @return the read map
     */
    Map getMap(Scanner console) throws IOException {
        ArrayList<Equipment> equipments = new ArrayList<Equipment>();
        getShips(console, equipments);
//        System.err.println("Ships reading done");
        boolean done = false;
        while (console.hasNext()) {
            String input = console.nextLine();
            input = input.toLowerCase();
            if (input.equals("anti aircraft")) {
                getAntiAircraft(console, equipments);
            } else if (input.equals("mine")) {
                getMine(console, equipments);
            } else if (input.equals("done")) {
                done = true;
                break;
            }
        }
        if (!done)
            throw new IOException("done was not written");
        Map.startHeight = startHeight;
        Map.startWidth = startWidth;
        Map.endWidth = endWidth;
        Map.endHeight = endHeight;
        Map map = new Map(false, controller, equipments);
        for (Equipment equipment : map.getEquipments())
            for (Position position : equipment.getPositions())
                if (!(startWidth <= position.x && position.x <= endWidth && startHeight <= position.y && position.y <= endHeight))
                    throw new IOException("Position Out of Bound: " + position.toString() + ", equipment: " + equipment.toString());
        return map;
    }

    /** Reads an antiAircraft
     *
     * @param console the scanner to read from
     * @param equipments list of equipments to append the read antiAircraft
     */
    void getAntiAircraft(Scanner console, ArrayList<Equipment> equipments) {
        int count = 0;
        while (console.hasNextInt()) {
            int row = console.nextInt();
            console.nextLine();
            if (count >= GameEngine.ANTIAIRCRAFT_COUNT)
                return;
            equipments.add(new AntiAircraft(row));
            count++;
        }
    }

    /** Reads a mine
     *
     * @param console the scanner to read from
     * @param equipments list of equipments to append the read mine
     */
    void getMine(Scanner console, ArrayList<Equipment> equipments) throws IOException {
        int count = 0;
        Pattern minePattern = Pattern.compile("(?<x>\\d+),(?<y>\\d+)");
        while (console.hasNext(minePattern)) {
            String input  = console.nextLine();
            input = input.toLowerCase();
            Matcher matcher = minePattern.matcher(input);
            int x, y;
            if (matcher.find()) {
                x = Integer.parseInt(matcher.group("x"));
                y = Integer.parseInt(matcher.group("y"));
            }
            else
                throw new IOException("Wrong Mine Input Format");
            if (count >= GameEngine.MINE_COUNT)
                continue;
            equipments.add(new Mine(new EquipmentPosition(x, y)));
            count++;
        }
    }

    /** Reads all the ships of a player
     *
     * @param console the scanner to read from
     * @param equipments list of equipments to append the read ships
     */
    void getShips(Scanner console, ArrayList<Equipment> equipments) throws IOException {
        int[] shipLengths = GameEngine.SHIP_LENGTHS;
        for (int shipLength : shipLengths) {
            String input = console.nextLine();
            input = input.toLowerCase();
            Matcher matcher = Pattern.compile("(?<x>\\d+),(?<y>\\d+) (?<direction>h|v)").matcher(input);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group("x")), y = Integer.parseInt(matcher.group("y"));
                ArrayList<EquipmentPosition> positions = getShipPositions(x, y, shipLength, matcher.group("direction").equals("h"));
                equipments.add(new Ship(positions));

            }
            else
                throw new IOException("Wrong Ship Input Format");

        }
    }
    public static ArrayList<EquipmentPosition> getShipPositions(int x, int y, int shipLength, boolean horizontal) {
        int dx[] = {1, 0};
        int dy[] = {0, 1};
        ArrayList<EquipmentPosition> positions = new ArrayList<EquipmentPosition>();
        int direction = horizontal ? 0 : 1;
        for (int i = 0; i < shipLength; i++)
            positions.add(new EquipmentPosition(x + i*dx[direction], y + i*dy[direction]));
        return positions;
    }

    public static ArrayList<EquipmentPosition> getShipPositions(Position startPosition, int shipLength, boolean horizontal) {
        return getShipPositions(startPosition.x, startPosition.y, shipLength, horizontal);
    }
}
