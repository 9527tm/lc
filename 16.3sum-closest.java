/*
 * @lc app=leetcode id=16 lang=java
 *
 * [16] 3Sum Closest
 *
 * https://leetcode.com/problems/3sum-closest/description/
 *
 * algorithms
 * Medium (45.76%)
 * Total Accepted:    353.6K
 * Total Submissions: 772.7K
 * Testcase Example:  '[-1,2,1,-4]\n1'
 *
 * Given an array nums of n integers and an integer target, find three integers
 * in nums such that the sum is closest to target. Return the sum of the three
 * integers. You may assume that each input would have exactly one solution.
 * 
 * Example:
 * 
 * 
 * Given array nums = [-1, 2, 1, -4], and target = 1.
 * 
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 * 
 * 
 */
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        return sol1(nums, target); 
    }

    private int sol1(int[] nums, int target) {
        Arrays.sort(nums);
        long res = nums[0] + nums[1] + nums[2]; //<= assume exactly one solution
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                if (left > i + 1 && nums[left] == nums[left - 1]) {
                    left++;
                }
                else {
                    long sum = (long)nums[i] + (long)nums[left] + (long)nums[right];
                    if (Math.abs(sum - target) < Math.abs(res - target)) {
                        res = sum;
                    }
                    if (sum < target) {
                        left++;
                    }
                    else if (sum > target) {
                        right--;
                    }
                    else {
                        return target;
                    }
                }
            }
        }
        return (int)res;


    }
}
