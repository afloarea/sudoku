package com.github.afloarea.sudoku.model;

import java.util.Arrays;

/**
 * Model for representing a Sudoku table.
 */
public final class SudokuTable {
    public static final int WIDTH       = 9;
    public static final int HEIGHT      = 9;

    private static final int FREE_TILE  = 0;

    public static final int CELL_HEIGHT = 3;
    public static final int CELL_WIDTH  = 3;
    public static final int MIN_VALUE   = 1;
    public static final int MAX_VALUE   = 9;


    private final int[][] table;

    public SudokuTable() {
        table = new int[HEIGHT][WIDTH];
    }

    public void clear() {
        for (int[] row : table) Arrays.fill(row, FREE_TILE);
    }

    public boolean isTileFree(int row, int column) {
        return table[row][column] == FREE_TILE;
    }

    public void freeTile(int row, int column) {
        table[row][column] = FREE_TILE;
    }

    public void setTileValue(int row, int column, int value) {
        table[row][column] = value;
    }

    public int getTileValue(int row, int column) {
        return table[row][column];
    }

    @Override
    public String toString() {
        return Arrays.deepToString(table);
    }
}
