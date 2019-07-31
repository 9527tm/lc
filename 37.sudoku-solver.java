/*
 * @lc app=leetcode id=37 lang=java
 *
 * [37] Sudoku Solver
 *
 * https://leetcode.com/problems/sudoku-solver/description/
 *
 * algorithms
 * Hard (37.29%)
 * Total Accepted:    137.4K
 * Total Submissions: 362.1K
 * Testcase Example:  '[["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]'
 *
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * 
 * A sudoku solution must satisfy all of the following rules:
 * 
 * 
 * Each of the digits 1-9 must occur exactly once in each row.
 * Each of the digits 1-9 must occur exactly once in each column.
 * Each of the the digits 1-9 must occur exactly once in each of the 9 3x3
 * sub-boxes of the grid.
 * 
 * 
 * Empty cells are indicated by the character '.'.
 * 
 * 
 * A sudoku puzzle...
 * 
 * 
 * ...and its solution numbers marked in red.
 * 
 * Note:
 * 
 * 
 * The given board contain only digits 1-9 and the character '.'.
 * You may assume that the given Sudoku puzzle will have a single unique
 * solution.
 * The given board size is always 9x9.
 * 
 * 
 */
class Solution {

    final int n = 9, m = n / 3, f = 100;
    public void solveSudoku(char[][] board) {
        sol1(board); 
    }
    private void sol1(char[][] board) {
        int[][] row = new int[n][n], col = new int[n][n], box = new int[n][n];
        init(board, row, col, box);
        sol1(board, row, col, box, 0, 0);
        fill(board, row);
    }
    private void init(char[][] board, int[][] row, int[][] col, int[][] box) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int k = i / m * m + j / m;
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    row[i][num] = 1 + j;
                    col[j][num] = 1 + i;
                    box[k][num] = 1 + i * f + j;
                }
            }
        }
    }
    private void fill(char[][] board, int[][] row) {
        for (int i = 0; i < n; i++) {
            for (int num = 0; num < n; num++) {
                int j = row[i][num] - 1;
                board[i][j] = (char)(num + '1');
            }
        }
    }
    private boolean sol1(char[][] board, int[][] row, int[][] col, int[][] box, int i, int j) {
        if (i >= n) {
            return true;
        }
        if (j >= n) {
            return sol1(board, row, col, box, i + 1, 0);
        }
        if (board[i][j] != '.') {
            return sol1(board, row, col, box, i, j + 1);
        }
        int k = i / m * m + j / m;
        for (int num = 0; num < n; num++) {
            if (row[i][num] == 0 && col[j][num] == 0 && box[k][num] == 0) {
                row[i][num] = 1 + j;
                col[j][num] = 1 + i;
                box[k][num] = 1 + i * f + j;
                if (sol1(board, row, col, box, i, j + 1)) {
                    return true;
                }
                row[i][num] = 0;
                col[j][num] = 0;
                box[k][num] = 0;
            }
        }
        return false;
    }
}
