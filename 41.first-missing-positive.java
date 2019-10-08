/*
 * @lc app=leetcode id=41 lang=java
 *
 * [41] First Missing Positive
 *
 * https://leetcode.com/problems/first-missing-positive/description/
 *
 * algorithms
 * Hard (29.83%)
 * Total Accepted:    248.4K
 * Total Submissions: 830.5K
 * Testcase Example:  '[1,2,0]'
 *
 * Given an unsorted integer array, find the smallest missing positive
 * integer.
 * 
 * Example 1:
 * 
 * 
 * Input: [1,2,0]
 * Output: 3
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [3,4,-1,1]
 * Output: 2
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: [7,8,9,11,12]
 * Output: 1
 * 
 * 
 * Note:
 * 
 * Your algorithm should run in O(n) time and uses constant extra space.
 * 
 */
class Solution {
    public int firstMissingPositive(int[] nums) {
        //return sol1(nums); 
        return sol2(nums); 
    }

    private int sol1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] >= 1 && nums[i] <= nums.length &&  //H.W.: forgot 2 checks (nums[i] != i + 1 && nums[i] != nums[j])
                   nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {//j = nums[i] - k
                int j = nums[i] - 1;                                  //0 <= j < nums.length
                swap(nums, i, j);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return nums.length + 1;
    }    
    
    private int sol2(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int j = nums[i] - 1; //j = nums[i] - k;
            while (j >= 0 && j < nums.length && i != j && nums[i] != nums[j]) {
                swap(nums, i, j);
                j = nums[i] - 1; //j = nums[i] - k
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (i != nums[i] - 1) {// - k
                return i + 1; // + k
            }
        }
        return nums.length + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
