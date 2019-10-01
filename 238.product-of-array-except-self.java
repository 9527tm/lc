/*
 * @lc app=leetcode id=238 lang=java
 *
 * [238] Product of Array Except Self
 *
 * https://leetcode.com/problems/product-of-array-except-self/description/
 *
 * algorithms
 * Medium (56.76%)
 * Total Accepted:    312.7K
 * Total Submissions: 550.8K
 * Testcase Example:  '[1,2,3,4]'
 *
 * Given an array nums of n integers where n > 1,  return an array output such
 * that output[i] is equal to the product of all the elements of nums except
 * nums[i].
 * 
 * Example:
 * 
 * 
 * Input:  [1,2,3,4]
 * Output: [24,12,8,6]
 * 
 * 
 * Note: Please solve it without division and in O(n).
 * 
 * Follow up:
 * Could you solve it with constant space complexity? (The output array does
 * not count as extra space for the purpose of space complexity analysis.)
 * 
 */
class Solution {
    public int[] productExceptSelf(int[] nums) {
        return sol1(nums); 
    }

    private int[] sol1(int[] nums) {
        int[] res = new int[nums.length];
        int leftProd = 1, rightProd = 1;
        for (int i = 0; i < nums.length; i++) {
            res[i] = leftProd;
            leftProd *= nums[i];
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= rightProd;
            rightProd *= nums[i];
        }
        return res;
    }
        
}
