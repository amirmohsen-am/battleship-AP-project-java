package battleship;

import battleship.exception.GameOverException;
import battleship.position.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** GameEngine handles events of the game
 *
 * @author Ahanchi
 */
public class GameEngine {
    public final static int ANTIAIRCRAFT_COUNT = 2;
    public final static int MINE_COUNT = 5;
    public final static int RADAR_MAX_USES = 4;
    public final static int AIRCRAFT_MAX_USES = 2;
    public final static int RADAR_RADIUS = 1;

    public static final int[] SHIP_LENGTHS = {4, 3, 3, 2, 2, 1, 1};

    {
        timer = 0;
    }

    public static int equipmentCount = 0;

    private Player[] players;
    private int timer;
    private ArrayList<Queue<String>> events = new ArrayList<Queue<String>>();
    private GameController controller;

    public GameEngine(Player[] players, GameController controller) {
        this.players = players;
        this.controller = controller;
    }

    /** Extends events to the desired length
     *
     * @param size the desired length
     */
    public void resizeEvents(int size) {
        while (events.size() < size)
            events.add(new LinkedList<String>());
    }

    /** Handles all the events in the Time time
     *
     * @throws Exception for wrong format of event input
     */
    public void update() throws GameOverException {
        timer++;
        Pattern attackPattern = Pattern.compile("team (?<name>.+) attack (?<x>\\d+),(?<y>\\d+)");
        Pattern radarPattern = Pattern.compile("team (?<name>.+) radar (?<x>\\d+),(?<y>\\d+)");
        Pattern aircraftPattern = Pattern.compile("team (?<name>.+) aircraft (?<row>\\d+)");

        resizeEvents(timer+1);
        while (!events.get(timer).isEmpty()) {
            String query = events.get(timer).remove();
            Matcher attackMatcher = attackPattern.matcher(query);
            Matcher radarMatcher = radarPattern.matcher(query);
            Matcher aircraftMatcher = aircraftPattern.matcher(query);
            Matcher matcher;
            if (attackMatcher.find()) {
                matcher = attackMatcher;
                Player player = findPlayerByName(matcher.group("name"));
                Position targetPosition = new Position(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                controller.attack(targetPosition, player);
            } else if (radarMatcher.find()) {
                matcher = radarMatcher;
                Player player = findPlayerByName(matcher.group("name"));
                Position targetPosition = new Position(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                controller.radar(targetPosition, player);
            } else if (aircraftMatcher.find()) {
                matcher = aircraftMatcher;
                Player player = findPlayerByName(matcher.group("name"));
                int row = Integer.parseInt(matcher.group("row"));
                controller.aircraft(row, player);
            } else
                throw new RuntimeException("Query has Wrong Format");

        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public GameController getController() {
        return controller;
    }

    /** Adds an event in time seconds from now
     *
     * @param time the time which the event will happen in
     * @param event the input event
     */
    void addEvent(int time, String event) {
        time += timer;
        resizeEvents(time+1);
        events.get(time).add(event);
    }

    public ArrayList<Queue<String>> getEvents() {
        return events;
    }


    /** Returns the opponent of the given player
     *
     * @param thisPlayer the player which we want to know his opponent
     * @return the opponent of given player
     */
    public Player getOpponent(Player thisPlayer) {
        for (Player player : players)
            if (!player.getName().equals(thisPlayer.getName()))
                return player;
        throw new RuntimeException("players are messed up");
    }

    /** Finds a player who has the given name
     *
     * @param name the name of the player whom we're looking for
     * @return the desired player
     */
    public Player findPlayerByName(String name) {
        for (Player player : players)
            if (player.getName().equals(name))
                return player;
        throw new RuntimeException("Wrong Player Name");
    }

    /** Attacks a position of a player
     *
     * @param targetPosition the target position hat will be attacked
     * @param attackingPlayer the player who has attacked
     * @throws Exception if we encounter an exploded equipment
     */
    public void attack(Position targetPosition, Player attackingPlayer) throws GameOverException {
        Player attackedPlayer = getOpponent(attackingPlayer);
        attackedPlayer.getMap().attack(targetPosition);
    }

    /** Runs a radar command
     *
     * @param targetPosition the position which the radar will happen
     * @param attackingPlayer the player who has commanded the radar
     */
    public void radar(Position targetPosition, Player attackingPlayer) {
        Player attackedPlayer = getOpponent(attackingPlayer);
        attackedPlayer.getMap().radar(targetPosition);
    }

    /** Lunches an aircraft on a row of a player
     *
     * @param row the row which will be targeted by aircraft
     * @param attackingPlayer the player who has used aircraft command
     * @throws Exception if we encounter an exploded equipment
     */
    public void aircraft(int row, Player attackingPlayer) throws GameOverException {
        Player attackedPlayer = getOpponent(attackingPlayer);
        attackedPlayer.getMap().aircraft(row);
    }

}
