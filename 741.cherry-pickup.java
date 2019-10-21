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
        //return sol2(grid);   //6.0: DFS
        return sol3(grid);   //6.0: DP
        //return sol4(grid);   //6.0: DP
        //return sol5(grid);   //6.5: DP
        //return sol5(grid);     //6.5: DP

        /*TUITION:
          1. Basement:     a round trip (src <=> dst) equals two single trips (src => dst).
                           -- Don't change the grid.
          2. Time Optim.:  each single trip has the same pace and distance as the other trip.
                           -- O(n ^ 4) => O(n ^ 3).
          3. Space Optim.: coordinate pair (x, y) can be converted to pair (steps, x).
                           -- dp matrix can be reused and updated backwards 
                              since dp elements depend others forwards. 
          4. Followup:
             Q1. will one single trip repeat any part trail of the other?
                 if so, except for the intersection points, the will be some points to be wrongly collected twice.
                 -- fortunately, the answer is NO. why?
                    at time t1, trip A goes to point P1 (x1, y1) and trip B goes to point P2 (x2, y2).
                    we assume at time t2 (t2 > t1), A goes to point P2. and we have:
                    i.   since A and B starts from (0, 0) and at the same pace => x1 + y1 = x2 + y2
                    ii.  during time t1 to t2, A has dx and dy steps           => x1 + dx = x2, y1 + dy = y2
                    iii. A and B move down and right (never up nor left)       => dx >= 0, dy >= 0
                    from i, ii and iii   x1 + y1 = x2 + y2 = x1 + dx + y1 + dy => dx + dy = 0 
                                                                               => dx = 0 and dy = 0
                    Contradiciton!!!
             Q2. will invalid point pairs [P1(x1, y1) and P2(x2, y2), x1 + y1 # x2 + y2] 
                 spoil DP recursion?
                 -- fortunately, the answer is NO also. why?
                    (x1, y1) = (0, 0) + (dx1, dy1), dx1 = x1, dy1 = y1
                    (x2, y2) = (0, 0) + (dx2, dy2), dx2 = x2, dy2 = y2
                    
                    each step: (xi, yi) = (xi - 1, yi) + (1, 0) or (xi, yi - 1) + (0, 1)
                    A starts from (0, 0) and arrives at (x1, y1) by x1 + y1 steps;
                    B arrives at (x2, y2) when A goes to (x1, y1), 
                    that's, when A started from (0, 0) and B started from (x2_n, y2_n) and x2_n + y2_n # 0
                    
                    dp(x1, y1, x2, y2) = dp(x1_1, y1_1, x2_1, y2_1) + 
                                         grid[x1_1][y1_1] + (or grid[x2_1][y2_1])
                    dp(x1_1, y1_1, x2_1, y2_1) = dp(x1_2, y1_2, x2_2, y2_2) +
                                                 grid[x1_2][y1_2] + (or grid[x2_2][y2_2])
                    ... after x1 + y1 steps,
                    dp(x1_n-1, y1_n-1, x2_n-1, y2_n-1) = dp(x1_n, y1_n, x2_n, y2_n) + 
                                                 grid[x1_n][y1_n] + (or grid[x2_n][y2_n])
                    
                    dp(0, 0, x2_n, y2_n) = dp(x1_n, y1_n, x2_n, y2_n) # dp(0, 0, 0, 0)
                    dp(0, 0, x2_n, y2_n) = dp(-1, 0, x2_n+1, y2_n) + 
                                           grid[-1][0] + (or grid[x2_n+1][y2_n+1])
                                         or,
                                         = dp(0, -1, x2_n+1, y2_n) + 
                                           grid[0][-1] + (or grid[x2_n+1][y2_n+1])
                    
                    so we know: initially dp(0, 0, x2_n, y2_n) = -1,
                                all the dp(x1_i, y1_i, x2_i, y2_i) and finally dp(x1, y1, x2, y2) are -1.
                    
         */
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
    //leetcode.com/problems/cherry-pickup/discuss/329945/Very-easy-to-follow-:-step-by-step-recursive-backtracking-with-memoization-N4./365157
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

    //DP: O(n ^ 4) / O(n ^ 4)
    //https://leetcode.com/problems/cherry-pickup/discuss/109911
    private int sol3(int[][] grid) {
        int n = grid.length;
        int[][][][] dp = new int[n + 1][n + 1][n + 1][n + 1];
        for (int x = 0; x <= n; x++) {
            for (int y = 0; y <= n; y++) {
                for (int x2 = 0; x2 <= n; x2++) {
                    for (int y2 = 0; y2 <= n; y2++) {
                        if (x == 0 || y == 0 || x2 == 0 || y2 == 0) {
                            dp[x][y][x2][y2] = -1;
                        }
                        else if (x == 1 && y == 1 && x2 == 1 && y2 == 1) {
                            dp[x][y][x2][y2] = grid[0][0];
                        }
                        else {
                            if (grid[x - 1][y - 1] < 0 || grid[x2 - 1][y2 - 1] < 0) {
                                dp[x][y][x2][y2] = -1;
                            }
                            else {
                                int currValue = (x == x2 && y == y2) ? 
                                    grid[x - 1][y - 1] : grid[x - 1][y - 1] + grid[x2 - 1][y2 - 1];
                                int prevValue = Math.max(Math.max(dp[x - 1][y][x2 - 1][y2], 
                                                                  dp[x - 1][y][x2][y2 - 1]),
                                                         Math.max(dp[x][y - 1][x2 - 1][y2],
                                                                  dp[x][y - 1][x2][y2 - 1]));
                                dp[x][y][x2][y2] = prevValue >= 0 ? prevValue + currValue : -1;
                            }
                        }
                        
                        /*if (x * y * x2 * y2 != 0) {
                            System.out.printf("%d\t%d\t%d\t%d\t%d\n", x, y, x2, y2, dp[x][y][x2][y2]);
                        }*/
                    }
                }
            }
        }
        return Math.max(0, dp[n][n][n][n]); //H.W.: forgot to return 0 when no solution.
    }

    //DP: O(n ^ 3) / O(n ^ 3)
    //https://leetcode.com/problems/cherry-pickup/discuss/109911
    //https://leetcode.com/problems/cherry-pickup/discuss/165218
    private int sol4(int[][] grid) {
        int n = grid.length;
        int[][][] dp = new int[n + 1][n + 1][n + 1];
        for (int x = 0; x <= n; x++) {
            for (int y = 0; y <= n; y++) {
                for (int x2 = 0; x2 <= n; x2++) { 
                    int y2 = x + y - x2; //TICKY: how can we judge if a y2 is valid!!! (y2: 1 <= y2 <= n)
                    if (y2 < 0 || y2 > n) {//H.W.: forgot to validate y2 (y2 == n is ok!)
                        dp[x][y][x2] = -1;
                    }
                    else if (x == 0 || y == 0 || x2 == 0 || y2 == 0) {//H.W.: forgot to check y2 == 0
                        dp[x][y][x2] = -1;
                    }
                    else if (x == 1 && y == 1 && x2 == 1 && y2 == 1) {
                        dp[x][y][x2] = grid[0][0];
                    }
                    else {
                        if (grid[x - 1][y - 1] < 0 || grid[x2 - 1][y2 - 1] < 0) {
                            dp[x][y][x2] = -1;
                        }
                        else {
                            int currValue = (x == x2 && y == y2) ? 
                                grid[x - 1][y - 1] : grid[x - 1][y - 1] + grid[x2 - 1][y2 - 1];
                            int prevValue = Math.max(Math.max(dp[x - 1][y][x2 - 1], 
                                                              dp[x - 1][y][x2]),
                                                     Math.max(dp[x][y - 1][x2 - 1],
                                                              dp[x][y - 1][x2]));
                            dp[x][y][x2] = prevValue >= 0 ? prevValue + currValue : -1;
                        }
                    }
                }
            }
        }
        return Math.max(0, dp[n][n][n]); 
    }

    //DP: O(n ^ 3) / O(n ^ 2)
    //https://leetcode.com/problems/cherry-pickup/discuss/109903
    //Why x: n - 1 => 0
    //       x2: n - 1 >= 0 
    //   Why we reuse dp[][] and updates it backwards?
    //   Because: dp[x][x2] depends on dp[x - 1][x2], dp[x][x2 - 1], dp[x - 1][x2 - 1],
    //            we update dp[x][x2] before its sources.
    //            in this way, we avoid early updating error.
    private int sol5(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int steps = 0; steps < 2 * n - 1; steps++) {
            for (int x = n - 1; x >= 0; x--) {
                for (int x2 = n - 1; x2 >= 0; x2--) {
                    int y = steps - x, y2 = steps - x2;
                    if (y < 0 || y >= n || y2 < 0 || y2 >= n) {//Tricky: check y and y2
                        dp[x][x2] = -1;
                    }
                    else if (x == 0 && y == 0 && x2 == 0 && y2 == 0) {
                        dp[x][x2] = grid[0][0];
                    }
                    else {
                        if (grid[x][y] == -1 || grid[x2][y2] == -1) {
                            dp[x][x2] = -1;                          
                        }
                        else {
                            if (x >= 1) {
                                dp[x][x2] = Math.max(dp[x][x2], dp[x - 1][x2]);
                            }
                            if (x2 >= 1) {
                                dp[x][x2] = Math.max(dp[x][x2], dp[x][x2 - 1]);
                            }
                            if (x >= 1 && x2 >= 1) {
                                dp[x][x2] = Math.max(dp[x][x2], dp[x - 1][x2 - 1]);
                            }
                            
                            if (dp[x][x2] >= 0) {
                                dp[x][x2] += (x == x2 && y == y2) ? 
                                    grid[x][y] : grid[x][y] + grid[x2][y2];
                            }
                        }
                    }
                }
            }
        }

        return Math.max(0, dp[n - 1][n - 1]);
    }

    //DP: O(n ^ 3) / O(n ^ 2)
    //https://leetcode.com/problems/cherry-pickup/discuss/109903
    private int sol5a(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n + 1][n + 1];
        for (int steps = 0; steps <= 2 * n; steps++) {
            for (int x = n; x >= 0; x--) {
                for (int x2 = n; x2 >= 0; x2--) {
                    int y = steps - x, y2 = steps - x2;
                    if (x == 0 || x2 == 0 || y <= 0 || y > n || y2 <= 0 || y2 > n) {//Tricky: check y and y2
                        dp[x][x2] = -1;
                    }
                    else if (x == 1 && y == 1 && x2 == 1 && y2 == 1) {
                        dp[x][x2] = grid[0][0];
                    }
                    else {
                        if (grid[x - 1][y - 1] == -1 || grid[x2 - 1][y2 - 1] == -1) {//H.W.: forgot index = len - 1
                            dp[x][x2] = -1;                                          //      => grid[x][y]
                        }
                        else {
                            int currValue = (x == x2) ? grid[x - 1][y - 1] : 
                                    grid[x - 1][y - 1] + grid[x2 - 1][y2 - 1];
                            int prevValue = Math.max(Math.max(dp[x][x2], dp[x - 1][x2]),
                                                    Math.max(dp[x][x2 - 1], dp[x - 1][x2 - 1]));
                            dp[x][x2] = (prevValue != -1) ? prevValue + currValue : -1;
                        }
                    }
                }
            }
        }

        return Math.max(0, dp[n][n]);
    }
}
// @lc code=end
