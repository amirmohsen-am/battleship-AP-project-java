package battleship;

import java.util.LinkedList;

/**
 * Created by persianpars on 1/29/15.
 */
public class Event implements Comparable {
    public Double time;
    public String command;

    public Event(Double time, String command) {
        this.time = time;
        this.command = command;
    }

    @Override
    public int compareTo(Object o) {
        int x = time.compareTo(((Event) o).time);
        if (x != 0)
            return x;
        return command.compareTo(((Event) o).command);
    }
}
