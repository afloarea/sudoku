package com.github.afloarea.sudoku.view;

import com.github.afloarea.sudoku.model.SudokuTable;
import com.github.afloarea.sudoku.solvers.SudokuSolver;
import com.github.afloarea.sudoku.solvers.impl.BacktrackingSudokuSolver;
import processing.core.PApplet;

import java.awt.Color;

public final class ProcessingView extends PApplet {
    private static final int VIEW_WIDTH     = 600;
    private static final int VIEW_HEIGHT    = 600;

    private static final int TEXT_SIZE      = 32;
    private static final int CELL_SIZE      = 60;

    private static final int BUTTON_WIDTH   = 180;
    private static final int BUTTON_HEIGHT  = 40;

    private static final int BACKGROUND_COLOR   = new Color(0, 132, 132).getRGB();
    private static final int HIGHLIGHT_COLOR    = new Color(204, 102, 0).getRGB();
    private static final int WHITE              = Color.WHITE.getRGB();
    private static final int BLACK              = Color.BLACK.getRGB();
    private static final int LIGHT_GREY         = Color.LIGHT_GRAY.getRGB();
    private static final int MEDIUM_GREY        = Color.GRAY.getRGB();

    private static final SudokuSolver solver = new BacktrackingSudokuSolver();

    private final SudokuTable sudoku = new SudokuTable();

    private int selectedRow = -1;
    private int selectedColumn = -1;

    @Override
    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    @Override
    public void setup() {
        textSize(TEXT_SIZE);
    }

    @Override
    public void draw() {
        background(BACKGROUND_COLOR);

        highlight();

        stroke(0);
        drawGrid();

        drawValues();

        drawButtons();
    }

    @Override
    public void mouseClicked() {
        // check solve button pressed
        if (mouseX >= 100 && mouseX <= 280 && mouseY >= 550 && mouseY <= 590) {
            solver.solve(sudoku);
            return;
        }

        // check clear button pressed
        if (mouseX >= 300 && mouseX <= 480 && mouseY >=550 && mouseY < 590) {
            sudoku.clear();
            return;
        }

        //check cell selected
        final int column = floor((mouseX - 30f) / CELL_SIZE);
        final int row = (mouseY - 1) / CELL_SIZE;
        if (row < 0 || row >= 9 || column < 0 || column >= 9) {
            selectedRow = -1;
            selectedColumn = -1;
        } else {
            selectedRow = row;
            selectedColumn = column;
        }
    }

    @Override
    public void keyPressed() {
        if (selectedRow < 0 || selectedColumn < 0) return;

        if (Character.isDigit(key)) {
            sudoku.setTileValue(selectedRow, selectedColumn, Character.getNumericValue(key));
        }
    }

    private void highlight() {
        if (selectedRow < 0 || selectedColumn < 0) return;
        fill(HIGHLIGHT_COLOR);
        rect(30 + selectedColumn * CELL_SIZE, 1 + selectedRow * 60, CELL_SIZE, CELL_SIZE);
        fill(WHITE);
    }

    private void drawValues() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {

                if (sudoku.isTileFree(row, column)) {
                    continue;
                }
                text(sudoku.getTileValue(row, column), 50 + column * CELL_SIZE, 40 + row * CELL_SIZE);
            }
        }
    }

    private void drawGrid() {
        for (int row = 0; row <= 9; row++) {
            setStroke(row);
            line(30, 1 + row * CELL_SIZE, 570, 1 + row * CELL_SIZE);
        }
        for (int column = 0; column <= 9; column++) {
            setStroke(column);
            line(30 + column * CELL_SIZE, 1, 30 + column * CELL_SIZE, VIEW_HEIGHT - CELL_SIZE);
        }
    }

    private void setStroke(int index) {
        if (index % 3 == 0) stroke(BLACK);
        else stroke(MEDIUM_GREY);
    }

    private void drawButtons() {
        fill(LIGHT_GREY);
        rect(100, 550, BUTTON_WIDTH, BUTTON_HEIGHT);
        fill(BLACK);
        text("Solve", 150, 580);
        fill(WHITE);

        fill(LIGHT_GREY);
        rect(300, 550, BUTTON_WIDTH, BUTTON_HEIGHT);
        fill(BLACK);
        text("Clear", 350, 580);
        fill(WHITE);
    }

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{ "ProcessingView" }, new ProcessingView());
    }
}
