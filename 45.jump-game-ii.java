/*
 * @lc app=leetcode id=45 lang=java
 *
 * [45] Jump Game II
 *
 * https://leetcode.com/problems/jump-game-ii/description/
 *
 * algorithms
 * Hard (28.89%)
 * Likes:    1547
 * Dislikes: 87
 * Total Accepted:    200.9K
 * Total Submissions: 691.1K
 * Testcase Example:  '[2,3,1,1,4]'
 *
 * Given an array of non-negative integers, you are initially positioned at the
 * first index of the array.
 * 
 * Each element in the array represents your maximum jump length at that
 * position.
 * 
 * Your goal is to reach the last index in the minimum number of jumps.
 * 
 * Example:
 * 
 * 
 * Input: [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2.
 * ⁠   Jump 1 step from index 0 to 1, then 3 steps to the last index.
 * 
 * Note:
 * 
 * You can assume that you can always reach the last index.
 * 
 */

// @lc code=start
class Solution {
    public int jump(int[] nums) {
        //return sol1(nums); 
        return sol2(nums); 
    }

    //DP: O(n * n) / O(n) -- TLE
    private int sol1(int[] nums) {
        //dp[i]: the minimum number of steps 
        //       by which we depart from 0 position and reach i position 
        int[] dp = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (j + nums[j] >= i) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }

        }
        return dp[nums.length - 1];
    }

    private int sol2(int[] nums) {
        //dp[i]: the farrest accessible position
        //       after we visited i poisition
        int[] dp = new int[nums.length];

        dp[0] = nums[0];
        for (int i = 1; i < dp.length; i++) {
            if (dp[i - 1] < i) {
                return Integer.MAX_VALUE;
            }
            dp[i] = Math.max(dp[i - 1], i + nums[i]);
        }

        int count = 0; 
        for (int i = 0; i < nums.length - 1; i = dp[i]) {
            count++;
        }
        return count;
    }

}
// @lc code=end
