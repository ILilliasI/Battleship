package battleship;

public record ShipPosition(int x, int y) {
    /**
     * Compare ship positions.
     * @param other Position for comparison.
     * @return Are positions cootdinates equal.
     */
    public boolean equals(ShipPosition other) {
        return (this.x == other.x) && (this.y == other.y);
    }
}
