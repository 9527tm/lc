/*
 * @lc app=leetcode id=36 lang=java
 *
 * [36] Valid Sudoku
 *
 * https://leetcode.com/problems/valid-sudoku/description/
 *
 * algorithms
 * Medium (43.36%)
 * Total Accepted:    251.7K
 * Total Submissions: 573.4K
 * Testcase Example:  '[["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]'
 *
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be
 * validated according to the following rules:
 * 
 * 
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without
 * repetition.
 * 
 * 
 * 
 * A partially filled sudoku which is valid.
 * 
 * The Sudoku board could be partially filled, where empty cells are filled
 * with the character '.'.
 * 
 * Example 1:
 * 
 * 
 * Input:
 * [
 * ⁠ ["5","3",".",".","7",".",".",".","."],
 * ⁠ ["6",".",".","1","9","5",".",".","."],
 * ⁠ [".","9","8",".",".",".",".","6","."],
 * ⁠ ["8",".",".",".","6",".",".",".","3"],
 * ⁠ ["4",".",".","8",".","3",".",".","1"],
 * ⁠ ["7",".",".",".","2",".",".",".","6"],
 * ⁠ [".","6",".",".",".",".","2","8","."],
 * ⁠ [".",".",".","4","1","9",".",".","5"],
 * ⁠ [".",".",".",".","8",".",".","7","9"]
 * ]
 * Output: true
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * [
 * ["8","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],
 * ["8",".",".",".","6",".",".",".","3"],
 * ["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],
 * [".","6",".",".",".",".","2","8","."],
 * [".",".",".","4","1","9",".",".","5"],
 * [".",".",".",".","8",".",".","7","9"]
 * ]
 * Output: false
 * Explanation: Same as Example 1, except with the 5 in the top left corner
 * being 
 * ⁠   modified to 8. Since there are two 8's in the top left 3x3 sub-box, it
 * is invalid.
 * 
 * 
 * Note:
 * 
 * 
 * A Sudoku board (partially filled) could be valid but is not necessarily
 * solvable.
 * Only the filled cells need to be validated according to the mentioned
 * rules.
 * The given board contain only digits 1-9 and the character '.'.
 * The given board size is always 9x9.
 * 
 * 
 */
class Solution {
    public boolean isValidSudoku(char[][] board) {
        //return sol1(board);       
        //return sol1a(board);       
        //return sol2(board);       
        return sol3(board);       
    }
    private boolean sol1(char[][] board) {
        Set<Character> set = new HashSet<>();
        int n = board.length, m = n / 3; //n = 9, m = 3
        //1. check rows
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != '.' && !set.add(board[i][j])) {
                    return false;
                }
            }
            set.clear();
        }
        //2. check cols
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                if (board[i][j] != '.' && !set.add(board[i][j])) {
                    return false;
                }
            }
            set.clear();
        }
        //3. check sub-boxes
        for (int i = 0; i < n; i += m) {
            for (int j = 0; j < n; j += m) {
                for (int k = i; k < i + m; k++) {
                    for (int l = j; l < j + m; l++) {
                        if (board[k][l] != '.' && !set.add(board[k][l])) {
                            return false;
                        }
                    }
                }
                set.clear();
            }
        }
        return true;
    }

    private boolean sol1a(char[][] board) {
        boolean res = true;
        int n = board.length, m = n / 3;
        for (int k = 0; res && k < n; k++) {
            res = check(board, 0, k, n, 1) && check(board, k, 0, 1, n);
        }
        for (int i = 0; res && i < n; i += m) {
            for (int j = 0; res && j < n; j += m) {
                res = check(board, i, j, m, m);
            }
        }
        return res;
    }
    private boolean check(char[][] board, int top, int left, int height, int width) {
        boolean res = true;
        boolean[] set = new boolean[height * width];
        for (int i = top; res && i < top + height; i++) {
            for (int j = left; res && j < left + width; j++) {
                if (board[i][j] != '.') {
                    res = !set[board[i][j] - '1']; //H.W.: off by one <= set[board[i][j] - '0']
                    set[board[i][j] - '1'] = true; //H.W.: the same above
                }
            }
        }
        return res;
    }

    private boolean sol2(char[][] board) {///valid-sudoku/discuss/15450/Shared-my-concise-Java-code
        int n = board.length, m = n / 3;
        for (int i = 0; i < n; i++) {
            boolean[] row = new boolean[n], col = new boolean[n], box = new boolean[n];
            for (int j = 0; j < n; j++) {
                if (board[i][j] != '.') {
                    if (row[board[i][j] - '1']) {
                        return false;
                    }
                    row[board[i][j] - '1'] = true;
                }
                if (board[j][i] != '.') {
                    if (col[board[j][i] - '1']) {
                        return false;
                    }
                    col[board[j][i] - '1'] = true;
                }
                int x0 = i / m * m, y0 = i % m * m;
                int x1 = j / m, y1 = j % m;
                int k = x0 + x1, l = y0 + y1;
                if (board[k][l] != '.') {
                    if (box[board[k][l] - '1']) {
                        return false;
                    }
                    box[board[k][l] - '1'] = true;
                }
            }
        }
        return true;
    }

    private boolean sol3(char[][] board) {
        int n = board.length, m = n / 3, p = 10;
        int[] mask = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != '.') {
                    int rowBit = 1 << (board[i][j] - '1');
                    int colBit = 1 << (p + (board[i][j] - '1'));
                    int boxBit = 1 << (2 * p + (board[i][j] - '1'));
                    int k = i / m * m + j / m;
                    if (((mask[i] & rowBit) != 0) || 
                        ((mask[j] & colBit) != 0) ||
                        ((mask[k] & boxBit) != 0)) {
                        return false;
                    }
                    mask[i] |= rowBit;
                    mask[j] |= colBit;
                    mask[k] |= boxBit;
                }
            }
        }
        return true;
    }
}
