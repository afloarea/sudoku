package com.github.afloarea.sudoku.solvers.impl;


import com.github.afloarea.sudoku.solvers.SudokuSolver;

import java.util.HashSet;
import java.util.Set;

public final class BacktrackingSudokuSolver implements SudokuSolver {
    private final Set<Integer> visited = new HashSet<>();

    public boolean solve(int[][] sudoku) {
        return fill(sudoku, 0, 0);
    }

    private boolean fill(int[][] sudoku, int row, int column) {
        if (row >= HEIGHT) return true;

        final int nextColumn = (column + 1) % WIDTH;
        final int nextRow = row + (column + 1) / WIDTH;

        if (sudoku[row][column] != FREE_TILE) {
            return fill(sudoku, nextRow, nextColumn);
        }

        boolean isValid = false;
        int value = MIN_VALUE;
        while (!isValid && value <= MAX_VALUE) {
            isValid = validateNewValue(sudoku, row, column, value) && fill(sudoku, nextRow, nextColumn);
            value++;
        }
        if (!isValid) sudoku[row][column] = FREE_TILE;
        return isValid;
    }

    private boolean validateNewValue(int[][] sudoku, int row, int column, int value) {
        sudoku[row][column] = value;

        return validateVerticalConstraint(sudoku, column)
                && validateHorizontalConstraint(sudoku, row)
                && validateCellConstraint(sudoku, row, column);
    }

    private boolean validateHorizontalConstraint(int[][] sudoku, int row) {
        visited.clear();
        for (int columnIndex = 0; columnIndex < WIDTH; columnIndex++) {
            if (findDuplicate(sudoku, row, columnIndex)) return false;
        }

        return true;
    }

    private boolean validateVerticalConstraint(int[][] sudoku, int column) {
        visited.clear();
        for (int rowIndex = 0; rowIndex < HEIGHT; rowIndex++) {
            if (findDuplicate(sudoku, rowIndex, column)) return false;
        }

        return true;
    }

    private boolean validateCellConstraint(int[][] sudoku, int row, int column) {
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

    private boolean findDuplicate(int[][] sudoku, int row, int column) {
        final int tileValue = sudoku[row][column];

        if (tileValue != FREE_TILE) {
            if (visited.contains(tileValue)) return true;
            else visited.add(tileValue);
        }

        return false;
    }

}
