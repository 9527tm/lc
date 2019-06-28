/*
 * @lc app=leetcode id=283 lang=java
 *
 * [283] Move Zeroes
 *
 * https://leetcode.com/problems/move-zeroes/description/
 *
 * algorithms
 * Easy (54.48%)
 * Total Accepted:    479.6K
 * Total Submissions: 879.6K
 * Testcase Example:  '[0,1,0,3,12]'
 *
 * Given an array nums, write a function to move all 0's to the end of it while
 * maintaining the relative order of the non-zero elements.
 * 
 * Example:
 * 
 * 
 * Input: [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * 
 * Note:
 * 
 * 
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 * 
 */
class Solution {
    public void moveZeroes(int[] nums) {
        //sol1(nums); 
        //sol1a(nums);
        //sol2(nums); 
        sol2a(nums); 
        //sol3(nums); //relative order fails
        //sol4(nums); //relative order fails
    }
    
    private void sol1(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                nums[i++] = nums[j];
            }
        }
        for (int j = i; j < nums.length; j++) {
            nums[j] = 0;
        }
    }

    private void sol1a(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                if (j != i) {
                    nums[i] = nums[j];
                }
                i++;
            }
        }
        for (int j = i; j < nums.length; j++) {
            if (nums[j] != 0) {
                nums[j] = 0;
            }
        }
    }

    private void sol2(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                swap(nums, i++, j);
            }
        }
    }

    private void sol2a(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                if (j != i) {
                    nums[i] = nums[j];
                    nums[j] = 0;
                }
                i++;
            }
        }
    }

    private void sol3(int[] nums) {//relative order cannot be held.
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            if (nums[left] != 0) {
                left++;
            }
            else if (nums[right] == 0) {
                right--;
            }
            else {
                swap(nums, left++, right--);
            }
        }
    }

    private void sol4(int[] nums) {//relative order cannot be held.
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            if (nums[left] != 0) {
                left++;
            }
            else {
                swap(nums, left, right--);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
