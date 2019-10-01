/*
 * @lc app=leetcode id=53 lang=java
 *
 * [53] Maximum Subarray
 *
 * https://leetcode.com/problems/maximum-subarray/description/
 *
 * algorithms
 * Easy (44.68%)
 * Total Accepted:    643K
 * Total Submissions: 1.4M
 * Testcase Example:  '[-2,1,-3,4,-1,2,1,-5,4]'
 *
 * Given an integer array nums, find the contiguous subarray (containing at
 * least one number) which has the largest sum and return its sum.
 * 
 * Example:
 * 
 * 
 * Input: [-2,1,-3,4,-1,2,1,-5,4],
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 * 
 * 
 * Follow up:
 * 
 * If you have figured out the O(n) solution, try coding another solution using
 * the divide and conquer approach, which is more subtle.
 * 
 */
class Solution {
    public int maxSubArray(int[] nums) {
        //return sol1(nums);        
        return sol2(nums);        
    }

    private int sol1(int[] nums) {
        int sum = nums[0], res = sum;
        for (int i = 1; i < nums.length; i++) {
            sum = Math.max(sum + nums[i], nums[i]);
            res = Math.max(res, sum);
        }
        return res;
    }

    private int sol2(int[] nums) {
        return helperSol2(nums, 0, nums.length - 1);
    }

    private int helperSol2(int[] nums, int left, int right) {
        if (left >= right) {
            return nums[left];
        }
        int mid = left + (right -  left) / 2;
        int leftRes = helperSol2(nums, left, mid);
        int rightRes = helperSol2(nums, mid + 1, right);
        int midRes = crossSum(nums, left, mid, right);
        return Math.max(midRes, Math.max(leftRes, rightRes));
    }

    private int crossSum(int[] nums, int left, int mid, int right) {
        //int leftSum = 0, leftMax = Integer.MIN_VALUE;  //H.W.: wrongly initialize left = 0, leftMax = 0;
        //for (int i = mid; i >= left; i--) {
        int leftSum = nums[mid], leftMax = leftSum;//both initialiaztions are OK!
        for (int i = mid - 1; i >= left; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax, leftSum);
        }
        int rightSum = 0, rightMax = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            rightSum += nums[i];
            rightMax = Math.max(rightMax, rightSum);
        }
        return leftMax + rightMax;
    }
}
