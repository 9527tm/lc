/*
 * @lc app=leetcode id=698 lang=java
 *
 * [698] Partition to K Equal Sum Subsets
 *
 * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/description/
 *
 * algorithms
 * Medium (43.73%)
 * Likes:    1145
 * Dislikes: 65
 * Total Accepted:    60.4K
 * Total Submissions: 136.9K
 * Testcase Example:  '[4,3,2,3,5,2,1]\n4'
 *
 * Given an array of integers nums and a positive integer k, find whether it's
 * possible to divide this array into k non-empty subsets whose sums are all
 * equal.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * Output: True
 * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3),
 * (2,3) with equal sums.
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * 1 <= k <= len(nums) <= 16.
 * 0 < nums[i] < 10000.
 * 
 * 
 */

// @lc code=start
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        return sol1(nums, k);
    }

    private boolean sol1(int[] nums, int k) {
        int sum = 0, max = 0;
        for (int n : nums) {
            sum += n;
            max = Math.max(max, n);
        }
        if (sum % k != 0 || max > sum / k) {
            return false;
        }
        Arrays.sort(nums);
        reverse(nums);
        return dfs(nums, 0, 0, sum / k);
    }
    private boolean dfs(int[] nums, int i, int sum, int target) {
        if (i >= nums.length) {
            return true;
        }
        for (int j = i; j < nums.length; j++) {
            swap(nums, i, j);
            if (nums[i] + sum <= target) {
                if (dfs(nums, i + 1, (nums[i] + sum) % target, target)) {
                    return true;
                }
            }
            swap(nums, i, j);
        }
        return false;
    }
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    private void reverse(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            swap(nums, left++, right--);
        }
    }
}
// @lc code=end
