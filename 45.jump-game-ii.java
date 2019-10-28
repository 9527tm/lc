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
        //return sol1(nums); //DP:          5.0
        //return sol2(nums); //DP + Greedy: 5.5
        //return sol3(nums); //Greedy:      5.0
        //return sol4(nums); //DFS:         6.5
        return sol4a(nums);  //DFS:         7.0
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

    //DP + Greedy: O(n) / O(n)
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

    //Greedy: O(n) / O(1)
    private int sol3(int[] nums) {
        int count = 0;
        int max = 0, nextMax = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (i > max) {
                return Integer.MAX_VALUE;
            }

            nextMax = Math.max(nextMax, i + nums[i]);
            if (i == max) {
                count++;
                max = nextMax;
            }
        }
        return count;
    }

    //BFS: O(n) / O(1)
    private int sol4(int[] nums) {
        int left = 0, right = 0, depth = 0;//left/right: first/last node of a layer
        while (left <= right) {            //nodes of a layer are in the queue
            int nextRight = 0;             
            for (int i = left; i <= right; i++) {//queue is not empty
                if (i >= nums.length - 1) {//expand
                    return depth;
                }
                nextRight = Math.max(nextRight, i + nums[i]);//generate
            }
            left = right + 1; //deque current layer nodes
            right = nextRight;//enque next layer nodes
            depth++;          //go deeper by one layer
        }
        return Integer.MAX_VALUE; //not found
    }

    //BFS: O(n) / O(1)
    private int sol4a(int[] nums) {
        int head = 0, tail = 0, depth = 0; //H.W.: [0]
        while (head <= tail) {                //queue is not empty
            int size = tail - head + 1;       //queue size
            for (int i = 0; i < size; i++) {  //process nodes of one layer
                int curr = head++;            //deque
                if (curr >= nums.length - 1) {//expand   H.W.: [0] => check goal when expanding
                    return depth;             //         instead of generating
                }
                int nexts = curr + nums[curr];//generate
                tail = Math.max(tail, nexts); //enqueue
            }
            depth++;                          //go deeper one layer
        }
        return Integer.MAX_VALUE;             //goal is not found
    }
}
// @lc code=end
