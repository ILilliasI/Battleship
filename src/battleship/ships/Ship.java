package battleship.ships;

import battleship.ShipPosition;

import java.util.Arrays;
import java.util.Vector;

public abstract class Ship {
    public static int xPositionsCount;
    public static int yPositionsCount;

    public abstract int length();

    protected Vector<ShipPosition> availableForPlacementPositions;
    protected ShipPosition[] shipPlacement = new ShipPosition[length()];

    private boolean placedVertically;
    // 0 - bottom, 1 - right.
    private Vector<Integer> availableDirections = new Vector<>(Arrays.asList(0, 1));
    protected int currentDirection = -1;

    /**
     * Check if ship overlaps with other ship.
     * @param ship Other ship.
     * @return Do ships overlap.
     */
    public boolean overlapsWith(Ship ship) {
        int maxShipPositionX = ship.shipPlacement[0].x() +
                (ship.placedVertically ? 2 : ship.length() + 1);
        int maxShipPositionY = ship.shipPlacement[0].y() +
                (ship.placedVertically ? ship.length() + 1 : 2);

        for (int xPosition = ship.shipPlacement[0].x() - 1;
             xPosition < maxShipPositionX;
             ++xPosition) {
            for (int yPosition = ship.shipPlacement[0].y() - 1;
                 yPosition < maxShipPositionY;
                 ++yPosition) {
                var occupiedPosition = new ShipPosition(xPosition, yPosition);

                if (Arrays.stream(shipPlacement).anyMatch(position ->
                        position.equals(occupiedPosition))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fill available positions array.
     */
    public void fillAvailablePositionsArray() {
        availableForPlacementPositions = new Vector<>(Ship.xPositionsCount * Ship.yPositionsCount);
        availableDirections = new Vector<>(Arrays.asList(0, 1));

        for (int yCoordinate = 0; yCoordinate < Ship.yPositionsCount; ++yCoordinate) {
            for (int xCoordinate = 0; xCoordinate < Ship.xPositionsCount; ++xCoordinate) {
                availableForPlacementPositions.add(new ShipPosition(xCoordinate, yCoordinate));
            }
        }
    }

    /**
     * Sets new position for ship.
     * @return Position set successfully.
     */
    public boolean setNewPosition() {
        if (changeDirection()) {
            return true;
        }
        if (!isPossibleToFindNewPosition()) {
            return false;
        }
        generatePositionCoordinates();

        while (!changeDirection()) {
            if (availableForPlacementPositions.size() == 0) {
                return false;
            }
            deletePosition();
            generatePositionCoordinates();
        }
        return true;
    }

    /**
     * Generates position coordinates.
     */
    private void generatePositionCoordinates() {
        int shipPosition = (int)(Math.random() * availableForPlacementPositions.size());

        shipPlacement[0] = availableForPlacementPositions.elementAt(shipPosition);
        deletePosition();
    }

    /**
     * Changes ship placement direction.
     * @return Is it possible to change ship placement direction.
     */
    protected boolean changeDirection() {
        if (shipPlacement[0] == null) {
            return false;
        }
        if (!generateDirection()) {
            return false;
        }

        if (placedVertically && (shipPlacement[0].y() + length() > yPositionsCount)) {
            if (availableDirections.size() <= 1) {
                return false;
            } else {
                return changeDirection();
            }
        }
        if (!placedVertically && (shipPlacement[0].x() + length() > xPositionsCount))
            if (availableDirections.size() <= 1) {
                return false;
            } else {
                return changeDirection();
            }
        fillShipPlacementArray();

        return true;
    }

    /**
     * Fills ship placement array.
     */
    private void fillShipPlacementArray() {
        int xShift = placedVertically ? 0 : 1;
        int yShift = placedVertically ? 1 : 0;

        for (int i = 1; i < length(); ++i) {
            shipPlacement[i] = new ShipPosition(shipPlacement[i - 1].x() + xShift,
                    shipPlacement[i - 1].y() + yShift);
        }
    }

    /**
     * Generates ship placement direction.
     * @return Direction generated successfully.
     */
    private boolean generateDirection() {
        if (availableDirections.size() == 0) {
            return false;
        }
        currentDirection = availableDirections.elementAt((int)(Math.random() *
                availableDirections.size()));
        availableDirections.removeElement(currentDirection);
        placedVertically = (currentDirection == 0);

        return true;
    }

    /**
     * Deletes ships current position from available positions for this ship.
     */
    private void deletePosition() {
        if (shipPlacement[0] != null) {
            availableForPlacementPositions.removeElement(availableForPlacementPositions.
                    removeIf(position -> position.equals(shipPlacement[0])));
        }
        availableDirections = new Vector<>(Arrays.asList(0, 1));
    }

    /**
     * Check if it is possible to change ship position.
     * @return Is it possible to change ship position.
     */
    public boolean isPossibleToFindNewPosition() {
        return (availableForPlacementPositions.size() > 0) || (availableDirections.size() > 0);
    }

    /**
     * Get ship placement.
     * @return Ship placement.
     */
    public ShipPosition[] getShipPlacement() {
        return shipPlacement;
    }
}
