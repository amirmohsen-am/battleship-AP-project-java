package battleship.position;

/** Specifies a cell of the map
 *
 * @author Ahanchi
 */
public class Position {
    public int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Returns the position's data in form of a string
     *
     * @return string format of the position
     */
    public String getString() {
        return x + "," + y;
    }

    /** Returns the maximum perpendicular distance between two points
     *
      * @param p the second point to which the point should be compared
     * @return the desired distance
     */
    public int getMaxDistance(Position p) {
        return Math.max(Math.abs(p.x - x), Math.abs(p.y - y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
        if (o == null) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        if (y != position.y) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
