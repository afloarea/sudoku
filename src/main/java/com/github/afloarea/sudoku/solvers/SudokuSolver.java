package com.github.afloarea.sudoku.solvers;

public interface SudokuSolver {
    int FREE_TILE   = 0;
    int WIDTH       = 9;
    int HEIGHT      = 9;
    int CELL_HEIGHT = 3;
    int CELL_WIDTH  = 3;
    int MIN_VALUE   = 1;
    int MAX_VALUE   = 9;

    boolean solve(int[][] sudoku);
}
