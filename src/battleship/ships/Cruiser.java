package battleship.ships;

public class Cruiser extends Ship {
    private final int length = 3;

    /**
     *
     * @return Number of cells occupied by the ship.
     */
    @Override
    public int length() {
        return length;
    }

    /**
     *
     * @return Ship type.
     */
    @Override
    public String toString() {
        return "cruiser";
    }
}
