/*
 * @lc app=leetcode id=741 lang=java
 *
 * [741] Cherry Pickup
 *
 * https://leetcode.com/problems/cherry-pickup/description/
 *
 * algorithms
 * Hard (31.01%)
 * Likes:    562
 * Dislikes: 43
 * Total Accepted:    13.7K
 * Total Submissions: 43.7K
 * Testcase Example:  '[[0,1,-1],[1,0,-1],[1,1,1]]'
 *
 * In a N x N grid representing a field of cherries, each cell is one of three
 * possible integers.
 * 
 * 
 * 
 * 
 * 0 means the cell is empty, so you can pass through;
 * 1 means the cell contains a cherry, that you can pick up and pass
 * through;
 * -1 means the cell contains a thorn that blocks your way.
 * 
 * 
 * 
 * 
 * Your task is to collect maximum number of cherries possible by following the
 * rules below:
 * 
 * 
 * 
 * 
 * Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or
 * down through valid path cells (cells with value 0 or 1);
 * After reaching (N-1, N-1), returning to (0, 0) by moving left or up through
 * valid path cells;
 * When passing through a path cell containing a cherry, you pick it up and the
 * cell becomes an empty cell (0);
 * If there is no valid path between (0, 0) and (N-1, N-1), then no cherries
 * can be collected.
 * 
 * 
 * 
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: grid =
 * [[0, 1, -1],
 * ⁠[1, 0, -1],
 * ⁠[1, 1,  1]]
 * Output: 5
 * Explanation: 
 * The player started at (0, 0) and went down, down, right right to reach (2,
 * 2).
 * 4 cherries were picked up during this single trip, and the matrix becomes
 * [[0,1,-1],[0,0,-1],[0,0,0]].
 * Then, the player went left, up, up, left to return home, picking up one more
 * cherry.
 * The total number of cherries picked up is 5, and this is the maximum
 * possible.
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * grid is an N by N 2D array, with 1 <= N <= 50.
 * Each grid[i][j] is an integer in the set {-1, 0, 1}.
 * It is guaranteed that grid[0][0] and grid[N-1][N-1] are not
 * -1.
 * 
 * 
 * 
 * 
 * 
 */

// @lc code=start
class Solution {
    public int cherryPickup(int[][] grid) {
        //return sol1(grid); //5.5: DFS
        return sol2(grid);   //6.0: DFS
    }

    //DFS: O(n ^ 4) / O(n ^ 4) + (2n - 2)
    //https://leetcode.com/problems/cherry-pickup/discuss/329945
    private int sol1(int[][] grid) {
        int n = grid.length;
        return Math.max(0, dfs1(grid, 0, 0, 0, 0, new Integer[n][n][n][n]));
    }

    private int dfs1(int[][] grid, int x, int y, int x2, int y2,
                    Integer[][][][] mem) {//H.W.: wrongly pass the sum down to subproblems
        int n = grid.length;              //      then get max result from them
        if (x >= n || y >= n || x2 >= n || y2 >= n || //https://leetcode.com/submissions/detail/271322542/
            grid[x][y] == -1 || grid[x2][y2] == -1) {
            return Integer.MIN_VALUE;
        }
        if (mem[x][y][x2][y2] != null) {
            return mem[x][y][x2][y2];
        }

        int res = 0;
        if (x == n - 1 && y == n - 1) {
            res = grid[x][y];
        }
        /*if (x2 == n - 1 && y == n - 1) {
            res = grid[x2][y2];
        }*/
        else {
            int currValue = (x == x2 && y == y2) ? grid[x][y] : grid[x][y] + grid[x2][y2];
            int nextValue = Math.max(Math.max(dfs1(grid, x + 1, y, x2 + 1, y2, mem),
                                        dfs1(grid, x + 1, y, x2, y2 + 1, mem)),
                               Math.max(dfs1(grid, x, y + 1, x2 + 1, y2, mem),
                                        dfs1(grid, x, y + 1, x2, y2 + 1, mem)));
            res = (nextValue != Integer.MIN_VALUE) ? currValue + nextValue : Integer.MIN_VALUE;
        }

        mem[x][y][x2][y2] = res;
        return res;
    }

    //DFS: O(n ^ 3) / (n ^ 3) + (2n - 2)
    //https://leetcode.com/problems/cherry-pickup/discuss/279967
    private int sol2(int[][] grid) {
        int n = grid.length;
        return Math.max(0, dfs2(grid, 0, 0, 0, new Integer[n][n][n]));
    }

    private int dfs2(int[][] grid, int x, int y, int x2, Integer[][][] mem) {
        int n = grid.length;
        int y2 = x + y - x2;
        if (x >= n || y >= n || x2 >= n || y2 >= n ||
            grid[x][y] == -1 || grid[x2][y2] == -1) {
            return Integer.MIN_VALUE;
        }
        if (mem[x][y][x2] != null) {
            return mem[x][y][x2];
        }
        int res = 0;
        if (x == n - 1 && y == n - 1) {
            res = grid[x][y];
        }
        else {
            int currValue = (x == x2 && y == y2) ? grid[x][y] : grid[x][y] + grid[x2][y2];
            int nextValue = Math.max(Math.max(dfs2(grid, x + 1, y, x2 + 1, mem),
                                              dfs2(grid, x + 1, y, x2, mem)),
                                     Math.max(dfs2(grid, x, y + 1, x2 + 1, mem),
                                              dfs2(grid, x, y + 1, x2, mem)));
            res = (nextValue != Integer.MIN_VALUE) ? currValue + nextValue : Integer.MIN_VALUE;
        }
        mem[x][y][x2] = res;
        return res;
    }
}
// @lc code=end
