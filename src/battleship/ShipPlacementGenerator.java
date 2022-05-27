package battleship;

import battleship.ships.Ship;

public class ShipPlacementGenerator {
    private int currentShipIndex = 0;
    private int previousShipIndex = 0;
    private boolean overlaps;

    /**
     * Generate random ships placement.
     * @param ships Array of fleet ships.
     * @return Placement for ships has been successfully generated.
     */
    boolean generateRandomShipsPlacement(Ship[] ships) {
        ships[0].fillAvailablePositionsArray();

        while (currentShipIndex < ships.length) {
            if (currentShipIndex > 40) {
                //  System.out.print(ships[currentShipIndex].availableForPlacementPositions.size());
                // System.out.print("\n");
            }
            if (!ships[currentShipIndex].setNewPosition()) {
                if (--currentShipIndex == -1) {
                    return false;
                } else {
                    continue;
                }
            }
            overlaps = false;
            previousShipIndex = 0;

            if (shipOverlapsWithOthers(ships)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if current ship overlaps with other ships.
     * @param ships Array of fleet ships.
     * @return Current ship overlaps with other ships.
     */
    private boolean shipOverlapsWithOthers(Ship[] ships) {
        while (previousShipIndex < currentShipIndex) {
            if (ships[currentShipIndex].overlapsWith(ships[previousShipIndex])) {
                overlaps = true;
                if (ships[currentShipIndex].isPossibleToFindNewPosition()) {
                    break;
                } else {
                    if (--currentShipIndex != -1) {
                        break;
                    } else {
                        return true;
                    }
                }
            }
            else {
                ++previousShipIndex;
            }
        }
        if (!overlaps) {
            if (++currentShipIndex < ships.length) {
                ships[currentShipIndex].fillAvailablePositionsArray();
            }
        }
        return false;
    }
}
