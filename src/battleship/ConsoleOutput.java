package battleship;

import battleship.position.Position;

/**
 * Created by persianpars on 1/29/15.
 */
public class ConsoleOutput {
    public static String attack(Player attackingPlayer, Position position) {
        return "team " + attackingPlayer.getName() + " attack " + position.getString();
    }

    public static String radar(Player attackingPlayer, Position position) {
        return "team " + attackingPlayer.getName() + " radar " + position.getString();
    }

    public static String aircraft(Player attackingPlayer, int row) {
        return "team " + attackingPlayer.getName() + " aircraft " + row;
    }

}
