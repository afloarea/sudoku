package com.github.afloarea.sudoku.solvers;

import com.github.afloarea.sudoku.model.SudokuTable;

/**
 * A SudokuSolver is capable of solving a Sudoku game.
 */
public interface SudokuSolver {

    /**
     * Attempt to solve given Sudoku.
     * @param sudoku the Sudoku to solve
     * @return whether the Sudoku was solved successfully or not
     */
    boolean solve(SudokuTable sudoku);

}
