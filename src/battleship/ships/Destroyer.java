package battleship.ships;

public class Destroyer extends Ship {
    private final int length = 2;

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
        return "destroyer";
    }
}
