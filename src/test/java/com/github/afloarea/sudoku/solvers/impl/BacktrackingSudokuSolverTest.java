package com.github.afloarea.sudoku.solvers.impl;


import com.github.afloarea.sudoku.model.SudokuTable;
import com.github.afloarea.sudoku.solvers.SudokuSolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BacktrackingSudokuSolverTest {

    private SudokuSolver solver = new BacktrackingSudokuSolver();

    @Test
    @DisplayName("Test that the hardest sudoku is solved")
    void testHardestSudoku() {

        final int[][] hardestSudoku = {
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };

        final SudokuTable table = new SudokuTable();

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                table.setTileValue(row, column, hardestSudoku[row][column]);
            }
        }

        Assertions.assertTrue(solver.solve(table));
    }

}
