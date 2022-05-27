package battleship;

public abstract class GameParametersScanner {
    protected boolean torpedoMode = false,
            recoveryMode = true;
    protected int rowsCount,
            columnsCount;
    protected int[] shipsCount = new int[5];
    protected int totalShipsCount = 0;
    protected int torpedoesCount = 0;
    protected int maximumCellsOccupiedByShipsCount = 0;

    abstract boolean areTableParametersCorrect();
    abstract boolean areShipsParametersCorrect();
    abstract boolean areModeParametersCorrect();

    /**
     * Check if a value is integer.
     * @param inputString Inputted string.
     * @return Is a value integer.
     */
    protected static boolean isInteger(String inputString) {
        if (inputString == null) {
            return false;
        }

        try {
            Integer.parseInt(inputString);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Check if a value is boolean.
     * @param inputString Inputted value.
     * @return Is a value boolean.
     */
    protected static boolean isBoolean(String inputString) {
        int value;

        if (inputString == null) {
            return false;
        }

        try {
            value = Integer.parseInt(inputString);
        } catch (NumberFormatException ex) {
            return false;
        }

        if ((value == 0) || (value == 1)) {
            return true;
        }
        return false;
    }

    /**
     * Check if game parameters are correct.
     * @return Are game parameters correct.
     */
    boolean areGameParametersCorrect() {
        if (areTableParametersCorrect() &&
                areShipsParametersCorrect() &&
                areModeParametersCorrect()) {
            return true;
        }
        return false;
    }
}
