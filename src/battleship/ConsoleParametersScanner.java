package battleship;

import java.util.Scanner;

public class ConsoleParametersScanner extends GameParametersScanner {
    private final String[] shipNames = {"carriers",
            "battleships",
            "cruisers",
            "destroyers",
            "submarines"};

    /**
     *
     * @param minValue Minimum allowed value for a number.
     * @param maxValue Maximum allowed value for a number.
     * @return Integer value.
     */
    protected static int inputIntegerValue(int minValue, int maxValue) {
        Scanner in = new Scanner(System.in);
        int value = 0;
        boolean correctValueScanned = false;

        while (!correctValueScanned) {
            if (in.hasNextBigInteger()) {
                value = in.nextInt();
                if ((value < minValue) || (value > maxValue)) {
                    System.out.printf("Incorrect value. Input a number from %d to %d: ",
                            minValue, maxValue);
                    in.nextLine();
                } else {
                    correctValueScanned = true;
                }
            } else {
                System.out.printf("Incorrect value. Input a number from %d to %d: ",
                        minValue, maxValue);
                in.nextLine();
            }
        }

        return value;
    }

    /**
     *
     * @return Boolean value (0 - false, 1 - true).
     */
    protected static int inputBooleanValue() {
        Scanner in = new Scanner(System.in);
        int value = 0;
        boolean correctValueScanned = false;

        while (!correctValueScanned) {
            if (in.hasNextBigInteger()) {
                value = in.nextInt();
                if ((value < 0) || (value > 1)) {
                    System.out.print("Incorrect value. Input 0 or 1: ");
                    in.nextLine();
                } else {
                    correctValueScanned = true;
                }
            } else {
                System.out.print("Incorrect value. Input 0 or 1: ");
                in.nextLine();
            }
        }

        return value;
    }


    /**
     *
     * @param shipName Ship name.
     * @param minValue Minimum allowed value for a number.
     * @param maxValue Maximum allowed value for a number.
     */
    private void printShipsInputMessage(String shipName, int minValue, int maxValue) {
        System.out.printf("Input %s number (from %d to %d): ", shipName, minValue, maxValue);
    }

    /**
     * Check inputted table parameters correctness.
     * @return Are parameters correct.
     */
    @Override
    protected boolean areTableParametersCorrect() {
        System.out.print("Welcome to the Battleship Game!\n");
        printShipsInputMessage("rows", 1,100);
        rowsCount = inputIntegerValue(1, 100);
        printShipsInputMessage("columns", 1,100);
        columnsCount = inputIntegerValue(1, 100);
        maximumCellsOccupiedByShipsCount = (int) ((double)(rowsCount * columnsCount) / 4);

        return true;
    }

    /**
     * Check inputted ships parameters correctness.
     * @return Are parameters correct.
     */
    @Override
    protected boolean areShipsParametersCorrect() {
        System.out.print("\nInput number for each type of ship.");
        System.out.printf("\nSum of all cells occupied by ships numbers must be <= 1/4 of game" +
                " table size(<= %d).\n", maximumCellsOccupiedByShipsCount);

        for (int shipIndex = 0; shipIndex < 5; ++shipIndex) {
            printShipsInputMessage(shipNames[shipIndex],
                    0, (int)((double)maximumCellsOccupiedByShipsCount / (5 - shipIndex)));
            shipsCount[shipIndex] = inputIntegerValue(0,
                    (int)((double)maximumCellsOccupiedByShipsCount / (5 - shipIndex)));

            totalShipsCount += shipsCount[shipIndex];
            maximumCellsOccupiedByShipsCount -= shipsCount[shipIndex] * (5 - shipIndex);
        }
        if (totalShipsCount == 0) {
            System.out.print("Total ships count must > 0.");
            return false;
        }

        return true;

    }

    /**
     * Check inputted game mode parameters correctness.
     * @return Are parameters correct.
     */
    @Override
    protected boolean areModeParametersCorrect() {
        System.out.print("Enable torpedo firing mode? (1 - enable, 0 - don't enable): ");
        torpedoMode = (inputBooleanValue() == 1);
        System.out.print("Enable ship recovery mode? (1 - enable, 0 - don't enable): ");
        recoveryMode = (inputBooleanValue() == 1);
        if (torpedoMode) {
            System.out.print("Input torpedoes number (max - ships number): ");
            torpedoesCount = inputIntegerValue(0, totalShipsCount);
            torpedoMode = (torpedoesCount != 0);
        }

        return true;
    }
}
