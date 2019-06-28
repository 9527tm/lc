/*
 * @lc app=leetcode id=75 lang=java
 *
 * [75] Sort Colors
 *
 * https://leetcode.com/problems/sort-colors/description/
 *
 * algorithms
 * Medium (42.37%)
 * Total Accepted:    326.8K
 * Total Submissions: 770K
 * Testcase Example:  '[2,0,2,1,1,0]'
 *
 * Given an array with n objects colored red, white or blue, sort them in-place
 * so that objects of the same color are adjacent, with the colors in the order
 * red, white and blue.
 * 
 * Here, we will use the integers 0, 1, and 2 to represent the color red,
 * white, and blue respectively.
 * 
 * Note: You are not suppose to use the library's sort function for this
 * problem.
 * 
 * Example:
 * 
 * 
 * Input: [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 * 
 * Follow up:
 * 
 * 
 * A rather straight forward solution is a two-pass algorithm using counting
 * sort.
 * First, iterate the array counting number of 0's, 1's, and 2's, then
 * overwrite array with total number of 0's, then 1's and followed by 2's.
 * Could you come up with a one-pass algorithm using only constant space?
 * 
 * 
 */
class Solution {
    public void sortColors(int[] nums) {
        //sol0(nums, 3);
        //sol1(nums);
        sol2(nums);
    }

    private void sol0(int[] nums, int k) {
        int[] counts = new int[k];
        for (int n : nums) {
            counts[n]++;
        }
        int i = 0;
        for (int j = 0; j < counts.length; j++) {
            while (counts[j]-- > 0) {
                nums[i++] = j;
            }
        }
    }

    private void sol1(int[] nums) {
        int i = 0, j = 0, k = nums.length - 1;
        while (j <= k) {
            if (nums[j] == 0) {
                swap(nums, i++, j++);
            }
            else if (nums[j] == 1) {
                j++;
            }
            else {
                swap(nums, j, k--);
            }
        }
    }

    private void sol2(int[] nums) {
        int i = 0, j = 0, k = 0;
        for (int n : nums) {
            if (n == 0) {
                swap(nums, j, k++);
                swap(nums, i++, j++);
            }
            else if (n == 1) {
                swap(nums, j++, k++);
            }
            else {
                k++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
