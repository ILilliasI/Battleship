package battleship;

import battleship.ships.*;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private GameTablePrinter gameTablePrinter;
    // 0 - not-fired, 1 - ship, 2 - miss, 3 - hit, 4 - sunk.
    private int[][] gameTable;
    private boolean torpedoMode,
            recoveryMode;
    private boolean usedTorpedo = false;
    private int torpedoesCount;
    private int rowsCount;
    private int columnsCount;
    private int[] shipsCount;

    private int totalShipsCount;
    private int totalShotsCount = 0;
    private Ship[] ships;
    private Ship previousShotDamagedShip;

    /**
     * Starts the battleship game.
     * @param gameArgs Game arguments from command line.
     */
    public void start(String[] gameArgs) {

        if (!checkGameParametersCorrectness(gameArgs)) {
            return;
        }
        generateShips();
        if (!(new ShipPlacementGenerator()).generateRandomShipsPlacement(ships)) {
            System.out.print("Impossible to place ships.");
            return;

        }
        fillInitialGameTable();
        gameTablePrinter = new GameTablePrinter();

        while (totalShipsCount > 0) {
            makeMove();
        }
        printEndMessage();
    }

    /**
     * Actions of a separate move.
     */
    private void makeMove() {
        usedTorpedo = false;
        gameTablePrinter.printGameTable(gameTable);
        int [] shotCoordinates = enterShotCoordinates();

        if ((gameTable[shotCoordinates[1]][shotCoordinates[0]] != 1) &&
                recoveryMode && (previousShotDamagedShip != null)) {
            recoverShip(previousShotDamagedShip);
        }
        switch (gameTable[shotCoordinates[1]][shotCoordinates[0]]) {
            case(0):
                System.out.print("Miss!\n");
                gameTable[shotCoordinates[1]][shotCoordinates[0]] = 2;
                previousShotDamagedShip = null;
                break;
            case(1):
                System.out.print("Hit!\n");
                gameTable[shotCoordinates[1]][shotCoordinates[0]] = 3;
                checkIfShipWasSunk(shotCoordinates);
                break;
            default:
                System.out.print("You have already shot at this cell.\n");
                previousShotDamagedShip = null;
                break;
        }
        ++totalShotsCount;
    }

    /**
     * Checks if hit ship was sunk.
     * @param shotCoordinates X and Y coordinates.
     */
    private void checkIfShipWasSunk(int [] shotCoordinates) {
        var shotPosition = new ShipPosition(shotCoordinates[0], shotCoordinates[1]);
        Ship damagedShip = findDamagedShip(shotPosition);

        if (recoveryMode &&
                (previousShotDamagedShip != null) &&
                (!previousShotDamagedShip.equals(damagedShip))) {
            recoverShip(previousShotDamagedShip);
        }

        if (!usedTorpedo) {
            for (var shipPosition : damagedShip.getShipPlacement()) {
                if (gameTable[shipPosition.y()][shipPosition.x()] == 1) {
                    previousShotDamagedShip = damagedShip;
                    return;
                }
            }
        }
        drownShip(damagedShip);
        System.out.printf("You just have sunk a %s.\n", damagedShip);
        --totalShipsCount;
        previousShotDamagedShip = null;
    }

    /**
     * Drowns ship.
     * @param ship Ship to drown.
     */
    private void drownShip(Ship ship) {
        for (var shipPosition : ship.getShipPlacement()) {
            gameTable[shipPosition.y()][shipPosition.x()] = 4;
        }
    }

    /**
     * Finds damaged ship in fleet ships array.
     * @param shotPosition Coordinates of the shot.
     * @return Damaged ship.
     */
    private Ship findDamagedShip(ShipPosition shotPosition) {
        for (var ship : ships) {
            if (Arrays.stream(ship.getShipPlacement()).anyMatch(position ->
                    position.equals(shotPosition))) {
                return ship;
            }
        }
        return null;
    }

    /**
     * Recovers damaged ship if recovery mode is enabled.
     * @param shipToRecover The ship damaged on the last move.
     */
    private void recoverShip(Ship shipToRecover) {
        for (var shipPosition : shipToRecover.getShipPlacement()) {
            gameTable[shipPosition.y()][shipPosition.x()] = 1;
        }
    }

    /**
     * Scans shot coordinates.
     * @return X and Y coordinates.
     */
    private int[] enterShotCoordinates() {
        System.out.printf("Enter x coordinate to shoot(from 1 to %d): ", columnsCount);
        int xCoordinate = ConsoleParametersScanner.inputIntegerValue(1, columnsCount) - 1;

        System.out.printf("Enter y coordinate to shoot(from 1 to %d): ", rowsCount);
        int yCoordinate = ConsoleParametersScanner.inputIntegerValue(1, rowsCount) - 1;
        if (torpedoMode) {
            System.out.print("If you want to use torpedo, print T (otherwise press Enter): ");

            if ((new Scanner(System.in)).nextLine().trim().equals("T")) {
                if (torpedoesCount <= 0) {
                    System.out.print("No torpedoes available.\n");
                } else {
                    usedTorpedo = true;
                    --torpedoesCount;
                }
            }
        }

        return new int[] {xCoordinate, yCoordinate};
    }

    /**
     * Checks if entered game parameters are correct.
     * @param gameArgs Arguments from command line.
     * @return Are entered parameters correct.
     */
    private boolean checkGameParametersCorrectness(String[] gameArgs) {
        GameParametersScanner scanner;

        if (gameArgs.length > 0) {
            scanner = new CommandLineParameterScanner(gameArgs);
        } else {
            scanner = new ConsoleParametersScanner();
        }
        boolean areGameParametersCorrect = scanner.areGameParametersCorrect();

        if (areGameParametersCorrect) {
            initializeGameParameters(scanner);
        }

        return areGameParametersCorrect;
    }

    /**
     * Initializes game parameters.
     * @param scanner Parameters scanner.
     */
    private void initializeGameParameters(GameParametersScanner scanner) {
        shipsCount = scanner.shipsCount;
        totalShipsCount = scanner.totalShipsCount;
        recoveryMode = scanner.recoveryMode;
        torpedoMode = scanner.torpedoMode;
        torpedoesCount = scanner.torpedoesCount;
        rowsCount = scanner.rowsCount;
        columnsCount = scanner.columnsCount;
        Ship.xPositionsCount = columnsCount;
        Ship.yPositionsCount = rowsCount;
        gameTable = new int[rowsCount][columnsCount];
        System.out.printf("Columns: %d\n", columnsCount);
        System.out.printf("Rows: %d\n", rowsCount);
        System.out.printf("Recovery mode: %b\n", recoveryMode);
        System.out.printf("Torpedo mode: %b\n", torpedoMode);
        System.out.printf("Torpedo count: %d\n", torpedoesCount);
    }

    /**
     * Generates ships according to the entered parameters.
     */
    private void generateShips() {
        ships = new Ship[totalShipsCount];
        int generatedShipsCount = 0;

        for (int shipTypeNumber = 0; shipTypeNumber < shipsCount.length; ++shipTypeNumber) {
            for (int j = 0; j < shipsCount[shipTypeNumber]; ++j) {
                switch (shipTypeNumber) {
                    case(0):
                        ships[generatedShipsCount++] = new Carrier();
                        break;
                    case(1):
                        ships[generatedShipsCount++] = new Battleship();
                        break;
                    case(2):
                        ships[generatedShipsCount++] = new Cruiser();
                        break;
                    case(3):
                        ships[generatedShipsCount++] = new Destroyer();
                        break;
                    case(4):
                        ships[generatedShipsCount++] = new Submarine();
                        break;
                    default:
                }
            }
        }
    }

    /**
     * Arranges the ships on the game table.
     */
    private void fillInitialGameTable() {
        for (var ship: ships) {
            for (int i = 0; i < ship.length(); ++i) {
                gameTable[ship.getShipPlacement()[i].y()][ship.getShipPlacement()[i].x()] = 1;
            }
        }
    }

    /**
     * Prints end message.
     */
    private void printEndMessage() {
        System.out.printf("The game is over!\nTotal number of shots: %d", totalShotsCount);
    }
}
