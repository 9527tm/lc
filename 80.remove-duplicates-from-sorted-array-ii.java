/*
 * @lc app=leetcode id=80 lang=java
 *
 * [80] Remove Duplicates from Sorted Array II
 *
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/description/
 *
 * algorithms
 * Medium (40.52%)
 * Total Accepted:    205.6K
 * Total Submissions: 506.5K
 * Testcase Example:  '[1,1,1,2,2,3]'
 *
 * Given a sorted array nums, remove the duplicates in-place such that
 * duplicates appeared at most twice and return the new length.
 * 
 * Do not allocate extra space for another array, you must do this by modifying
 * the input array in-place with O(1) extra memory.
 * 
 * Example 1:
 * 
 * 
 * Given nums = [1,1,1,2,2,3],
 * 
 * Your function should return length = 5, with the first five elements of nums
 * being 1, 1, 2, 2 and 3 respectively.
 * 
 * It doesn't matter what you leave beyond the returned length.
 * 
 * Example 2:
 * 
 * 
 * Given nums = [0,0,1,1,1,1,2,3,3],
 * 
 * Your function should return length = 7, with the first seven elements of
 * nums being modified to 0, 0, 1, 1, 2, 3 and 3 respectively.
 * 
 * It doesn't matter what values are set beyond the returned length.
 * 
 * 
 * Clarification:
 * 
 * Confused why the returned value is an integer but your answer is an array?
 * 
 * Note that the input array is passed in by reference, which means
 * modification to the input array will be known to the caller as well.
 * 
 * Internally you can think of this:
 * 
 * 
 * // nums is passed in by reference. (i.e., without making a copy)
 * int len = removeDuplicates(nums);
 * 
 * // any modification to nums in your function would be known by the caller.
 * // using the length returned by your function, it prints the first len
 * elements.
 * for (int i = 0; i < len; i++) {
 * print(nums[i]);
 * }
 * 
 * 
 */
class Solution {
    public int removeDuplicates(int[] nums) {
        return sol1(nums); 
        //return sol2(nums); 
        //return sol2a(nums, 2); 
    }
    
    private int sol1(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (j < 2 || nums[i - 2] != nums[j]) { //H.W.: nums[j - 2] != nums[j]   <= [1,1,1,2,2,3]
                nums[i++] = nums[j];               //      refer lc 26 for more info.
            }
        }
        return i;
    }

    private int sol2(int[] nums) {
        int i = 2;
        for (int j = 2; j < nums.length; j++) {
            if (nums[j] != nums[i - 2]) {//H.W.: see above
                nums[i++] = nums[j];
            }
        }
        return nums.length < 2 ? nums.length : i;
    }

    private int sol2a(int[] nums, int k) {
        int i = k;
        for (int j = k; j < nums.length; j++) {
            if (nums[j] != nums[i - k]) {//H.W.: see above
                nums[i++] = nums[j];
            }
        }
        return nums.length < k ? nums.length : i;
    }
}
