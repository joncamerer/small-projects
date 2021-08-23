import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard {

    private final List<Integer> DEFAULT_POSSIBILITIES;
    private final int UNASSIGNED = 0;
    private final int[][] grid;

    public static void main(String[] args) {
        SudokuBoard app = new SudokuBoard();

        app.run();
    }

    public SudokuBoard() {
        this.DEFAULT_POSSIBILITIES = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        this.grid = new int[9][9];
    }

    public void run() {
        boolean gridAssigned = false;

        while (!gridAssigned) {
            clearGrid();
            gridAssigned = assignGrid();
        }

        printGrid();
    }

    public void clearGrid() {
        for (int[] row : grid) {
            Arrays.fill(row, 0);
        }
    }

    public void printGrid() {
        for (int r = 0; r < grid.length; r++) {
            if (r > 0 && r % 3 == 0) {
                System.out.println("----------------------");
            }

            printRow(r);
            System.out.println();
        }
    }

    public void printRow(int rowIndex) {
        for (int i = 0; i < grid[rowIndex].length; i++) {
            if (i > 0 && i % 3 == 0) {
                System.out.print("| ");
            }

            System.out.print(grid[rowIndex][i] + " ");
        }
    }

    public boolean assignGrid() {
        for (int r = 0; r < grid.length; r++) {
            for (int i = 0; i < grid[r].length; i++) {
                //chooseNumber will return -1 if there's no possible match
                int proposedNumber = chooseNumber(getRowPossibilities(r), getColumnPossibilities(i),
                        getSquarePossibilities(r, i));

                if (proposedNumber > 0) {
                    grid[r][i] = proposedNumber;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public List<Integer> getRowPossibilities(int rowIndex) {
        List<Integer> rowPossibilities = new ArrayList<>(DEFAULT_POSSIBILITIES);

        for (int i = 0; i < grid[rowIndex].length; i++) {
            int thisNumber = grid[rowIndex][i];

            if (thisNumber == UNASSIGNED) {
                break;
            } else if (rowPossibilities.contains(thisNumber)) {
                rowPossibilities.remove((Integer) thisNumber);
            }
        }

        return rowPossibilities;
    }

    public List<Integer> getColumnPossibilities(int columnIndex) {
        List<Integer> columnPossibilities = new ArrayList<>(DEFAULT_POSSIBILITIES);

        for (int[] row : grid) {
            int thisNumber = row[columnIndex];

            if (thisNumber == UNASSIGNED) {
                break;
            } else if (columnPossibilities.contains(thisNumber)) {
                columnPossibilities.remove((Integer) thisNumber);
            }
        }

        return columnPossibilities;
    }

    public List<Integer> getSquarePossibilities(int rowIndex, int columnIndex) {
        List<Integer> squarePossibilities = new ArrayList<>(DEFAULT_POSSIBILITIES);

        // Set minimum and maximum indexes for the 3 X 3 square containing the provided coordinates
        int rowMin = getMinimumIndex(rowIndex);
        int colMin = getMinimumIndex(columnIndex);
        int rowMax = rowMin + 2;
        int colMax = colMin + 2;

        for (int r = rowMax; r >= rowMin; r--) {
            for (int i = colMax; i >= colMin; i--) {
                int thisNumber = grid[r][i];

                if (thisNumber != UNASSIGNED && squarePossibilities.contains(thisNumber)) {
                    squarePossibilities.remove((Integer) thisNumber);
                }
            }
        }

        return squarePossibilities;
    }

    public int chooseNumber(List<Integer> rowPossibilities, List<Integer> columnPossibilities,
                            List<Integer> squarePossibilities) {

        boolean possibleMatchExists = false;

        for (Integer rowPossibility : rowPossibilities) {
            if (columnPossibilities.contains(rowPossibility) && squarePossibilities.contains(rowPossibility)) {
                possibleMatchExists = true;
                break;
            }
        }

        while (possibleMatchExists) {
            int proposedNumber = (int) (Math.random() * 10);

            if (proposedNumber == 0) {
                proposedNumber += 1;
            }

            if (rowPossibilities.contains(proposedNumber) && columnPossibilities.contains(proposedNumber)
                    && squarePossibilities.contains(proposedNumber)) {
                return proposedNumber;
            }
        }

        return -1;
    }

    public int getMinimumIndex(int currentIndex) {
        if (currentIndex < 3) {
            return 0;
        } else if (currentIndex < 6) {
            return 3;
        } else {
            return 6;
        }

    }
}