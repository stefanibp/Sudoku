package com.example.sudokugame.model;

import java.util.*;

import java.util.Random;


/**
 *This class represents a Sudoku game board and its logic.
 * @author Stefania Bolaños Perdomo
 * @version 1.0
 */
public class Sudoku {
    /**
     * Internal representation of the Sudoku board using an adapter for a list of lists of integers.
     */
    ListAdapter<IList<Integer>> boardAdapter;
    /**
     * Internal representation of the solved Sudoku board used for verification.
     */
    ListAdapter<IList<Integer>> boardSolveSudoku;
    /**
     * Random number generator used for various functionalities.
     */
    private Random random;

    /**
     * Default constructor that initializes the Sudoku board with empty cells (0)
     * and solves it to store the solution internally.
     */

    public Sudoku() {
        boardAdapter = new ListAdapter<>(new LinkedList<>());
        random = new Random();

        for (int i = 0; i < 6; i++) {
            IList<Integer> row = new LinkedList<>();
            for (int j = 0; j < 6; j++) {
                row.addLast(0);
            }
            boardAdapter.getAdaptedList().addLast(row);
        }

        removeNumbersToCreatePuzzle();
    }

    /**
     * Attempts to solve the current Sudoku board using backtracking.
     *
     * @return True if the board is solved successfully, False otherwise.
     */
    public boolean solveSudoku() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (boardAdapter.getAdaptedList().get(row).get(col) == 0) {

                    List<Integer> numbers = new ArrayList<>();
                    for (int num = 1; num <= 6; num++) {
                        numbers.add(num);
                    }
                    Collections.shuffle(numbers);

                    for (int num : numbers) {
                        if (isValid(row, col, num)) {
                            boardAdapter.getAdaptedList().get(row).set(col, num);


                            if (solveSudoku()) {
                                return true;
                            }

                            boardAdapter.getAdaptedList().get(row).set(col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        boardSolveSudoku = cloneResolvedBoard();

        return true;
    }


    /**
     * Creates a deep copy of the currently solved Sudoku board.
     *
     * @return A new ListAdapter containing a deep copy of the solved board.
     */
    public ListAdapter<IList<Integer>> cloneResolvedBoard() {

        ListAdapter<IList<Integer>> clonedBoard = new ListAdapter<>(new LinkedList<>());

        for (IList<Integer> row : boardAdapter.getAdaptedList()) {
            IList<Integer> clonedRow = new LinkedList<>();
            for (Integer value : row) {
                clonedRow.addLast(Integer.valueOf(value));
            }
            clonedBoard.getAdaptedList().addLast(clonedRow);
        }

        return clonedBoard;
    }

    /**
     * Removes numbers from the solved Sudoku board to create a playable puzzle.
     */
    public void removeNumbersToCreatePuzzle() {

        if (!solveSudoku()) {
            System.out.println("No se pudo generar un tablero solucionado.");
            return;
        }


        Random random = new Random();

        for (int subGridRow = 0; subGridRow < 6; subGridRow += 2) {
            for (int subGridCol = 0; subGridCol < 6; subGridCol += 3) {

                List<int[]> positions = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        positions.add(new int[] {subGridRow + i, subGridCol + j});
                    }
                }

                while (positions.size() > 2) {
                    int index = random.nextInt(positions.size());
                    int[] pos = positions.remove(index);
                    boardAdapter.getAdaptedList().get(pos[0]).set(pos[1], 0);
                }
            }
        }

        System.out.println("___________________");
        for (int i = 0; i <6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(boardSolveSudoku.getAdaptedList().get(i).get(j));

            }
            System.out.println();
        }
        System.out.println("___________________");
        printBoard();
    }

    /**
     * Prints the current state of the Sudoku board to the console.
     */
    public void printBoard() {
        for (int i = 0; i < 6; i++) {
            IList<Integer> row = boardAdapter.getAdaptedList().get(i);
            for (int j = 0; j < 6; j++) {
                System.out.print(row.get(j) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Sets the value of the cell at the specified row and column.
     * This method modifies the board in place.
     *
     * @param row The row index of the cell (0-based).
     * @param col The column index of the cell (0-based).
     * @param value The value to set in the cell (1-6), or 0 to clear the cell.
     */
    public void setValue(int row, int col, int value) {
        if (row < 0 || row >= 6 || col < 0 || col >= 6 || value < 1 || value > 6) {
            throw new IllegalArgumentException("Posición o valor fuera de rango");
        }
        boardAdapter.getAdaptedList().get(row).set(col, value);
    }

    /**
     * Clears the value of the cell at the specified row and column by setting it to 0.
     *
     * @param row The row index of the cell (0-based).
     * @param col The column index of the cell (0-based).
     */
    public void deletedValue(int row, int col) {
        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            throw new IllegalArgumentException("Posición o valor fuera de rango");
        }
        boardAdapter.getAdaptedList().get(row).set(col, 0);
    }

    /**
     * Gets the value of the cell at the specified row and column.
     *
     * @param row The row index of the cell (0-based).
     * @param col The column index of the cell (0-based).
     * @return The value at the specified cell, or 0 if the cell is empty.
     */
    public int getValue(int row, int col) {
        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            throw new IllegalArgumentException("Posición o valor fuera de rango");
        }

        return boardAdapter.getAdaptedList().get(row).get(col);
    }


    public boolean isValid(int row, int col, int value) {

        for (int j = 0; j < 6; j++) {
            if (boardAdapter.getAdaptedList().get(row).get(j) == value) {
                return false;
            }
        }

        for (int i = 0; i < 6; i++) {
            if (boardAdapter.getAdaptedList().get(i).get(col) == value) {
                return false;
            }
        }

        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (boardAdapter.getAdaptedList().get(i).get(j) == value) {
                    return false;
                }
            }
        }

        return true;
    }


    public boolean isCompleteAndValid() {

        for (int row = 0; row < 6; row++) {
            Set<Integer> seen = new HashSet<>();
            for (int col = 0; col < 6; col++) {
                int value = boardAdapter.getAdaptedList().get(row).get(col);
                if (value != 0) {
                    if (seen.contains(value)) {
                        return false;
                    }
                    seen.add(value);
                }
            }
        }

        for (int col = 0; col < 6; col++) {
            Set<Integer> seen = new HashSet<>();
            for (int row = 0; row < 6; row++) {
                int value = boardAdapter.getAdaptedList().get(row).get(col);
                if (value != 0) {
                    if (seen.contains(value)) {
                        return false;
                    }
                    seen.add(value);
                }
            }
        }
        return true;
    }

    public boolean isValidInSubGrids() {
        for (int startRow = 0; startRow < 6; startRow += 2) {
            for (int startCol = 0; startCol < 6; startCol += 3) {
                Set<Integer> seen = new HashSet<>();

                for (int i = startRow; i < startRow + 2; i++) {
                    for (int j = startCol; j < startCol + 3; j++) {
                        int value = boardAdapter.getAdaptedList().get(i).get(j);
                        if (value != 0) {
                            if (seen.contains(value)) {
                                return false;
                            }
                            seen.add(value);
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isCompleteAndValidWithSubGrids() {
        System.out.println("isCompleteAndValid: " + isCompleteAndValid() + " -  isValidInSubGrids(): " + isValidInSubGrids());
        return isCompleteAndValid() && isValidInSubGrids();
    }

    public void clearBoard() {
        for (int i = 0; i < 6; i++) {
            IList<Integer> row = boardAdapter.getAdaptedList().get(i);
            for (int j = 0; j < 6; j++) {
                row.set(j, 0);
            }
        }
    }

    public int[] fillRandomCell() {
        List<int[]> emptyCells = new ArrayList<>();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (boardAdapter.getAdaptedList().get(row).get(col) == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            return null;
        }

        int[] randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
        int row = randomCell[0];
        int col = randomCell[1];

        int solvedValue = boardSolveSudoku.getAdaptedList().get(row).get(col);

        boardAdapter.getAdaptedList().get(row).set(col, solvedValue);

        return new int[]{row, col};
    }

    /**
     * Checks if the Sudoku puzzle has been solved completely and correctly by verifying
     * that the board is full and all Sudoku rules are met (rows, columns, and sub-grids).
     *
     * @return true if the Sudoku puzzle is solved correctly, false otherwise.
     */
    public boolean checkWin() {

        if (boardAdapter.getAdaptedList().size() != boardSolveSudoku.getAdaptedList().size()) {
            return false;
        }

        for (int row = 0; row < boardAdapter.getAdaptedList().size(); row++) {
            IList<Integer> currentRow = boardAdapter.getAdaptedList().get(row);
            IList<Integer> solutionRow = boardSolveSudoku.getAdaptedList().get(row);

            if (currentRow.size() != solutionRow.size()) {
                return false;
            }

            for (int col = 0; col < currentRow.size(); col++) {
                if (!currentRow.get(col).equals(solutionRow.get(col))) {
                    return false;
                }
            }
        }

        return true;
    }

}


