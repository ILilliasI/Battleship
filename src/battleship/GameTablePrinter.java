package battleship;

import java.util.HashMap;
import java.util.Map;
import static java.lang.Math.log10;

public class GameTablePrinter {
    private int[][] gameTable;
    private final Map<Integer, Character> tableStates = new HashMap<>() {{
        put(0, ' ');
        put(1, ' ');
        put(2, 'M');
        put(3, 'H');
        put(4, 'S');
    }};

    /**
     * Print first row of the game table.
     * @param maxColDigitsCount Maximum number of digits in column number.
     */
    private void printFirstRowOfGameTable(int maxColDigitsCount) {
        StringBuilder firstPartOfRow = new StringBuilder((gameTable[0].length + 1) * 6);
        StringBuilder secondPartOfRow = new StringBuilder((gameTable[0].length + 1) * 6);
        firstPartOfRow.append(" ".repeat(maxColDigitsCount + 2));
        secondPartOfRow.append(" ".repeat(maxColDigitsCount + 1));
        secondPartOfRow.append("+");

        for (int colIndex = 0; colIndex < gameTable[0].length; ++colIndex) {
            int digitsCount = (int)log10(colIndex + 1) + 1;
            firstPartOfRow.append(colIndex + 1);
            firstPartOfRow.append(" ".repeat(6 - digitsCount));
            secondPartOfRow.append("-----+");
        }
        System.out.print(firstPartOfRow);
        System.out.print("\n");
        System.out.print(secondPartOfRow);
        System.out.print("\n");

    }

    /**
     * Print first part of current row.
     * @param rowIndex Index of current row.
     * @param maxColDigitsCount Maximum number of digits in column number.
     */
    private void printFirstPartOfRow(int rowIndex, int maxColDigitsCount) {
        StringBuilder firstPartOfRow = new StringBuilder((gameTable[rowIndex].length + 1) * 6);

        for (int colIndex = 0; colIndex < gameTable[rowIndex].length; ++colIndex) {
            if (colIndex == 0) {
                int digitsCount = (int)log10(rowIndex + 1) + 1;
                firstPartOfRow.append((rowIndex + 1));
                firstPartOfRow.append(" ".repeat(maxColDigitsCount + 1 - digitsCount));
                firstPartOfRow.append("+");
            }
            firstPartOfRow.append("  ");
            firstPartOfRow.append(tableStates.get(gameTable[rowIndex][colIndex]));
            firstPartOfRow.append("  +");
        }
        System.out.print(firstPartOfRow);
        System.out.print("\n");
    }

    /**
     * Print second part of current row.
     * @param rowIndex Current row index.
     * @param maxColDigitsCount Maximum number of digits in column number.
     */
    private void printSecondPartOfRow(int rowIndex, int maxColDigitsCount) {
        StringBuilder secondPartOfRow = new StringBuilder((gameTable[rowIndex].length + 1) * 6);

        for (int colIndex = 0; colIndex < gameTable[rowIndex].length; ++colIndex) {
            if (colIndex == 0) {
                secondPartOfRow.append(" ".repeat(maxColDigitsCount + 1));
                secondPartOfRow.append("+");
            }
            secondPartOfRow.append("-----+");
        }
        System.out.print(secondPartOfRow);
        System.out.print("\n");

    }

    /**
     * Print game table in console.
     * @param gameTable Current game table.
     */
    void printGameTable(int[][] gameTable) {
        this.gameTable = gameTable;
        // The maximum number of digits in a number from the first column of the table.
        int maxColDigitsCount = (int)log10(gameTable.length) + 1;

        for (int rowIndex = 0; rowIndex < gameTable.length; ++rowIndex) {
            if (rowIndex == 0) {
                printFirstRowOfGameTable(maxColDigitsCount);
            }
            printFirstPartOfRow(rowIndex, maxColDigitsCount);
            printSecondPartOfRow(rowIndex, maxColDigitsCount);
        }
    }
}
