package battleship;

import battleship.Network.NetworkClient;
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
    private NetworkClient gameInput;
//    private Scanner gameInput;
    Log log;

    int startWidth = 0, endWidth; // baz baste boodan dar JavaDoc
    int startHeight = 0, endHeight;



    public ConsoleInput(NetworkClient gameInput, GameController controller, Log log) {
//    public ConsoleInput(Scanner gameInput, GameController controller, Log log) {
        this.gameInput = gameInput;
        this.controller = controller;
        this.log = log;
    }

    /** Reads the next command from input
     *
     * @throws Exception if the game is not over, but there's no more input
     */
    public void next() throws NoMoreInputException, IOException, GameOverException {
//        if (!gameInput.hasNext())
//            throw new NoMoreInputException();
        String input = gameInput.nextLine();
        double time = Double.parseDouble(input.substring(0, input.indexOf(' ') - 1));
        input = input.substring(input.indexOf(' ')+1);
        input = input.toLowerCase();
        if (input.contains("go")) {
            int number = Integer.parseInt(input.substring(3));
            for (int i = 0; i < number; i++)
                controller.getEngine().update();
        }
        else if (input.contains("attack"))
            controller.getEngine().addEvent(time, input);
        else if (input.contains("aircraft") || input.contains("radar"))
            controller.getEngine().addEvent(time, input);
        else
            throw new IOException("Wrong Input Format during Game");
    }

    /** Reads the names and map of players
     *
     * @param mapInput the scanner to read from
     * @return the read players
     */
    public Player[] getPlayers(Scanner mapInput) throws IOException {
        Player[] players = new Player[2];
        endHeight = mapInput.nextInt()-1;
        endWidth = mapInput.nextInt()-1;
        mapInput.nextLine();

        for (int i = 0; i < 2; i++) {
//            String input = mapInput.nextLine();
//            Matcher matcher = Pattern.compile("Player number (\\d+), please build your map").matcher(input);
            log.println("Player number " + (i+1) + ", please build your map");
            String name = "" + ((char)(((int)'a')+i));
            players[i] = new Player(name, getMap(mapInput));
        }
        return players;
    }
    
    /** Reads the map of a player
     *
     * @param mapInput the scanner to read from
     * @return the read map
     */
    Map getMap(Scanner mapInput) throws IOException {
        ArrayList<Equipment> equipments = new ArrayList<Equipment>();
        getShips(mapInput, equipments);
//        System.err.println("Ships reading done");
        boolean done = false;
        while (mapInput.hasNext()) {
            String input = mapInput.nextLine();
            input = input.toLowerCase();
            if (input.equals("anti aircraft")) {
                getAntiAircraft(mapInput, equipments);
            } else if (input.equals("mine")) {
                getMine(mapInput, equipments);
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
     * @param mapInput the scanner to read from
     * @param equipments list of equipments to append the read antiAircraft
     */
    void getAntiAircraft(Scanner mapInput, ArrayList<Equipment> equipments) {
        int count = 0;
        while (mapInput.hasNextInt()) {
            int row = mapInput.nextInt();
            mapInput.nextLine();
            if (count >= GameEngine.ANTIAIRCRAFT_COUNT)
                return;
            equipments.add(new AntiAircraft(row));
            count++;
        }
    }

    /** Reads a mine
     *
     * @param mapInput the scanner to read from
     * @param equipments list of equipments to append the read mine
     */
    void getMine(Scanner mapInput, ArrayList<Equipment> equipments) throws IOException {
        int count = 0;
        Pattern minePattern = Pattern.compile("(?<x>\\d+),(?<y>\\d+)");
        while (mapInput.hasNext(minePattern)) {
            String input  = mapInput.nextLine();
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
     * @param mapInput the scanner to read from
     * @param equipments list of equipments to append the read ships
     */
    void getShips(Scanner mapInput, ArrayList<Equipment> equipments) throws IOException {
        int[] shipLengths = GameEngine.SHIP_LENGTHS;
        for (int shipLength : shipLengths) {
            String input = mapInput.nextLine();
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
