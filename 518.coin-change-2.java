/*
 * @lc app=leetcode id=518 lang=java
 *
 * [518] Coin Change 2
 *
 * https://leetcode.com/problems/coin-change-2/description/
 *
 * algorithms
 * Medium (44.33%)
 * Total Accepted:    59K
 * Total Submissions: 132.6K
 * Testcase Example:  '5\n[1,2,5]'
 *
 * You are given coins of different denominations and a total amount of money.
 * Write a function to compute the number of combinations that make up that
 * amount. You may assume that you have infinite number of each kind of
 * coin.
 * 
 * 
 * 
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are four ways to make up the amount:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: amount = 3, coins = [2]
 * Output: 0
 * Explanation: the amount of 3 cannot be made up just with coins of 2.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: amount = 10, coins = [10] 
 * Output: 1
 * 
 * 
 * 
 * 
 * Note:
 * 
 * You can assume that
 * 
 * 
 * 0 <= amount <= 5000
 * 1 <= coin <= 5000
 * the number of coins is less than 500
 * the answer is guaranteed to fit into signed 32-bit integer
 * 
 * 
 */
class Solution {
    public int change(int amount, int[] coins) {
        return sol1(amount, coins);             //combination => coins loop outside
        //return sol2(amount, coins);           //permutation => amount loop outside
    }
    //https://leetcode.com/problems/coin-change-2/discuss/99212
    //https://leetcode.com/problems/coin-change-2/discuss/99222

    //https://youtu.be/DJ4a7cmjZY0
    /*dp[i][j]: the number of combinations of i types of coins that amounts to j value.
      dp[0][0] = 1, dp[i][0] = 1, dp[0][j] = 0
      dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]]   (j >= coins[i - 1])
                (amount j from w/o i-th type coin) + (amount j - coins[i - 1] from w/ i-th type coins)
      or,
      dp[i][j] = dp[i - 1][j] (j < coins[i - 1])
                (amount j from w/o i-th type coin only)
    */
    private int sol1(int amount, int[] coins) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        dp[0][0] = 1; //H.W.: [0, []]
        for (int i = 1; i <= coins.length; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += (coins[i - 1] <= j) ? dp[i][j - coins[i - 1]] : 0;
            }
        }
        return dp[coins.length][amount];
    }


    //leetcode.com/problems/coin-change-2/discuss/99222/Video-explaining-how-dynamic-programming-works-with-the-Coin-Change-problem/192340
    /*
    Why the outer loop is the coins, not the amount? --
    The reason behind that is that as you mentioned, the problem is to find the total number of combinations, not the permutations. If the outer loop is the amount, then the same combination will be counted multiple times because they can come in in different orders. By letting the coins to be the outer loops, one assures that for any valid combination, the order of each coin will always be the same as their order in coins, so there can be no duplicates.
    */
    //https://youtu.be/jaNZ83Q3QGc
    private int sol2(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int weight : coins) {
            for (int j = 1; j <= amount; j++) {
                if (j >= weight) {
                    dp[j] += dp[j - weight];
                }
            }
        }
        return dp[amount];
    }
}

