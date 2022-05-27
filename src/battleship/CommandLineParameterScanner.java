package battleship;

public class CommandLineParameterScanner extends GameParametersScanner {

    private int cellsOccupiedByShipsCount = 0;
    private final String[] gameArgs;
    private final String errorMessage = "Incorrect number of game parameters in command line:\n" +
            "Waited:\n" +
            "rowsNumber(from 1 to 100) columnsNumber(from 1 to 100) " +
            "carriersNumber battleshipsNumber cruisersNumber " +
            "destroyersNumber submarinesNumber " +
            "enableTorpedoMode(1 - enable, 0 - don't enable) " +
            "enableRecoveryMode(1 - enable, 0 - don't enable) " +
            "torpedoesNumber(max=shipsNumber)\n";

    /**
     *
     * @param args Game arguments from command line.
     */
    public CommandLineParameterScanner(String[] args) {
        gameArgs = args;
    }

    /**
     * Check entered table parameters correctness.
     * @return Are parameters correct.
     */
    @Override
    protected boolean areTableParametersCorrect() {
        if (gameArgs.length != 10) {
            System.out.print(errorMessage);
            return false;
        }
        if (!isInteger(gameArgs[0]) ||
                (Integer.parseInt(gameArgs[0]) < 1) ||
                (Integer.parseInt(gameArgs[0]) > 100)) {
            System.out.print("Incorrect rows number. Input a number from 1 to 100.\n");
            return false;
        } else if (!isInteger(gameArgs[1]) ||
                (Integer.parseInt(gameArgs[1]) < 1) ||
                (Integer.parseInt(gameArgs[1]) > 100)) {
            System.out.print("Incorrect columns number. Input a number from 1 to 100.\n");
            return false;
        }
        rowsCount = Integer.parseInt(gameArgs[0]);
        columnsCount = Integer.parseInt(gameArgs[1]);
        maximumCellsOccupiedByShipsCount = (int) ((double)(rowsCount * columnsCount) / 4);

        return true;
    }

    /**
     * Check entered ships parameters correctness.
     * @return Are parameters correct.
     */
    @Override
    protected boolean areShipsParametersCorrect() {
        for (int i = 2; i <= 6; ++i) {
            if (!isInteger(gameArgs[i])) {
                System.out.print("Incorrect ships number. Input an integer >= 0.\n");
                return false;
            }
            int shipNumber = Integer.parseInt(gameArgs[i]);
            shipsCount[i - 2] = shipNumber;
            totalShipsCount += shipNumber;
            cellsOccupiedByShipsCount += (5 - (i - 2)) * shipNumber;
        }
        if (cellsOccupiedByShipsCount > maximumCellsOccupiedByShipsCount) {
            System.out.printf("Incorrect total ships number. Sum of all cells occupied by must " +
                    "be <= 1/4 of game table size (<= %d).\n", maximumCellsOccupiedByShipsCount);
            return false;
        }
        return true;
    }

    /**
     * Check entered game mode parameters correctness.
     * @return Are parameters correct.
     */
    @Override
    protected boolean areModeParametersCorrect() {
        if (!isBoolean(gameArgs[7])) {
            System.out.print("Incorrect torpedo mode value. Input 0 or 1.\n");
            return false;
        } else if (!isBoolean(gameArgs[8])) {
            System.out.print("Incorrect recovery mode value. Input 0 or 1.\n");
            return false;
        } else if (!isInteger(gameArgs[9]) ||
                (Integer.parseInt(gameArgs[9]) < 0) ||
                (Integer.parseInt(gameArgs[9]) > totalShipsCount)) {
            System.out.printf("Incorrect torpedoes number value. " +
                    "Input a number from 0 to %d\n", totalShipsCount);
            return false;
        }
        recoveryMode = (Integer.parseInt(gameArgs[8]) == 1);
        torpedoesCount = Integer.parseInt(gameArgs[9]);
        torpedoMode = torpedoMode && (torpedoesCount > 0);

        return true;
    }
}
