/*
 * @lc app=leetcode id=31 lang=java
 *
 * [31] Next Permutation
 *
 * https://leetcode.com/problems/next-permutation/description/
 *
 * algorithms
 * Medium (30.58%)
 * Total Accepted:    250K
 * Total Submissions: 813.9K
 * Testcase Example:  '[1,2,3]'
 *
 * Implement next permutation, which rearranges numbers into the
 * lexicographically next greater permutation of numbers.
 * 
 * If such arrangement is not possible, it must rearrange it as the lowest
 * possible order (ie, sorted in ascending order).
 * 
 * The replacement must be in-place and use only constant extra memory.
 * 
 * Here are some examples. Inputs are in the left-hand column and its
 * corresponding outputs are in the right-hand column.
 * 
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 * 
 */
class Solution {
    public void nextPermutation(int[] nums) {
        sol1(nums);  
    }
    private void sol1(int[] nums) {//35421 - 45321 - 41235
        int i = nums.length - 1;
        while (i - 1 >= 0 && nums[i - 1] >= nums[i]) {
            i--;
        }
        if (i < 1) {
            reverse(nums, i, nums.length - 1);
            return;
        }
        
        int j = nums.length - 1;
        while (j >= i && nums[j] <= nums[i - 1]) {
            j--;
        }
        swap(nums, i - 1, j);
        reverse(nums, i, nums.length - 1);
    }
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left++, right--);
        }
    }
}
