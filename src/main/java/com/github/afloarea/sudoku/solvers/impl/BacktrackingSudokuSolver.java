package com.github.afloarea.sudoku.solvers.impl;


import com.github.afloarea.sudoku.model.SudokuTable;
import com.github.afloarea.sudoku.solvers.SudokuSolver;

import java.util.HashSet;
import java.util.Set;

import static com.github.afloarea.sudoku.model.SudokuTable.*;

/**
 * Backtracking implementation of a sudoku solver.
 * Can solve one Sudoku game at a time.
 */
public final class BacktrackingSudokuSolver implements SudokuSolver {
    private final Set<Integer> visited = new HashSet<>();

    public boolean solve(SudokuTable sudoku) {
        if (!isValid(sudoku)) {
            return false;
        }
        return fill(sudoku, 0, 0);
    }

    private boolean fill(SudokuTable sudoku, int row, int column) {
        if (row >= HEIGHT) return true;

        final int nextColumn = (column + 1) % WIDTH;
        final int nextRow = row + (column + 1) / WIDTH;

        if (!sudoku.isTileFree(row, column)) {
            return fill(sudoku, nextRow, nextColumn);
        }

        boolean isValid = false;
        int value = MIN_VALUE;
        while (!isValid && value <= MAX_VALUE) {
            sudoku.setTileValue(row, column, value);
            isValid = isValid(sudoku, row, column) && fill(sudoku, nextRow, nextColumn);
            value++;
        }
        if (!isValid) sudoku.freeTile(row, column);
        return isValid;
    }

    private boolean isValid(SudokuTable sudoku) {
        for (int row = 0; row < HEIGHT; row++) {
            for (int column = 0; column < WIDTH; column++) {

                if (!sudoku.isTileFree(row, column) && !isValid(sudoku, row, column)) {
                    return false;
                }

            }
        }

        return true;
    }

    private boolean isValid(SudokuTable sudoku, int row, int column) {
        return validateVerticalConstraint(sudoku, column)
                && validateHorizontalConstraint(sudoku, row)
                && validateCellConstraint(sudoku, row, column);
    }

    private boolean validateHorizontalConstraint(SudokuTable sudoku, int row) {
        visited.clear();
        for (int columnIndex = 0; columnIndex < WIDTH; columnIndex++) {
            if (findDuplicate(sudoku, row, columnIndex)) return false;
        }

        return true;
    }

    private boolean validateVerticalConstraint(SudokuTable sudoku, int column) {
        visited.clear();
        for (int rowIndex = 0; rowIndex < HEIGHT; rowIndex++) {
            if (findDuplicate(sudoku, rowIndex, column)) return false;
        }

        return true;
    }

    private boolean validateCellConstraint(SudokuTable sudoku, int row, int column) {
        visited.clear();
        final int startRow      = row / CELL_HEIGHT * CELL_HEIGHT;
        final int endRow        = startRow + CELL_HEIGHT;
        final int startColumn   = column / CELL_WIDTH * CELL_WIDTH;
        final int endColumn     = startColumn + CELL_WIDTH;

        for (int rowIndex = startRow; rowIndex < endRow; rowIndex++) {
            for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++) {
                if (findDuplicate(sudoku, rowIndex, columnIndex)) return false;
            }
        }

        return true;
    }

    private boolean findDuplicate(SudokuTable sudoku, int row, int column) {
        if (sudoku.isTileFree(row, column)) {
            return false;
        }

        final int tileValue = sudoku.getTileValue(row, column);
        if (!visited.contains(tileValue)) {
            visited.add(tileValue);
            return false;
        }

        return true;
    }

}
