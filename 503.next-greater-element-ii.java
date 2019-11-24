/*
 * @lc app=leetcode id=503 lang=java
 *
 * [503] Next Greater Element II
 *
 * https://leetcode.com/problems/next-greater-element-ii/description/
 *
 * algorithms
 * Medium (52.20%)
 * Likes:    896
 * Dislikes: 53
 * Total Accepted:    65.2K
 * Total Submissions: 123.3K
 * Testcase Example:  '[1,2,1]'
 *
 * 
 * Given a circular array (the next element of the last element is the first
 * element of the array), print the Next Greater Number for every element. The
 * Next Greater Number of a number x is the first greater number to its
 * traversing-order next in the array, which means you could search circularly
 * to find its next greater number. If it doesn't exist, output -1 for this
 * number.
 * 
 * 
 * Example 1:
 * 
 * Input: [1,2,1]
 * Output: [2,-1,2]
 * Explanation: The first 1's next greater number is 2; The number 2 can't find
 * next greater number; The second 1's next greater number needs to search
 * circularly, which is also 2.
 * 
 * 
 * 
 * Note:
 * The length of given array won't exceed 10000.
 * 
 */

// @lc code=start
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        return sol1(nums); 
    }

    private int[] sol1(int[] nums) {
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int j = i + 1;
            while (j < i + nums.length && nums[i] >= nums[j % nums.length]) {
                j++;
            }
            res[i] = j < i + nums.length ? nums[j % nums.length] : -1;
        }
        return res;
    }
}
// @lc code=end
