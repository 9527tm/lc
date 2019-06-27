/*
 * @lc app=leetcode id=26 lang=java
 *
 * [26] Remove Duplicates from Sorted Array
 *
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/
 *
 * algorithms
 * Easy (40.93%)
 * Total Accepted:    614.9K
 * Total Submissions: 1.5M
 * Testcase Example:  '[1,1,2]'
 *
 * Given a sorted array nums, remove the duplicates in-place such that each
 * element appear only once and return the new length.
 * 
 * Do not allocate extra space for another array, you must do this by modifying
 * the input array in-place with O(1) extra memory.
 * 
 * Example 1:
 * 
 * 
 * Given nums = [1,1,2],
 * 
 * Your function should return length = 2, with the first two elements of nums
 * being 1 and 2 respectively.
 * 
 * It doesn't matter what you leave beyond the returned length.
 * 
 * Example 2:
 * 
 * 
 * Given nums = [0,0,1,1,1,2,2,3,3,4],
 * 
 * Your function should return length = 5, with the first five elements of nums
 * being modified to 0, 1, 2, 3, and 4 respectively.
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
 */
class Solution {
    public int removeDuplicates(int[] nums) {
        //return sol1(nums); 
        //return sol2(nums); 
        return sol2a(nums, 1);
    }
    
    private int sol1(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (j == 0 || nums[j] != nums[j - 1]) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }
    
    private int sol2(int[] nums) {
        int i = 1; //k
        for (int j = 1; j < nums.length; j++) {// int j = k;
            if (nums[j] != nums[i - 1]) {      // nums[j] != nums[i - k]
                nums[i++] = nums[j];
            }
        }
        return nums.length > 0 ? i : 0;        // nums.length >= k ? i : nums.length
    }

    private int sol2a(int[] nums, int k) {
        int i = k;
        for (int j = k; j < nums.length; j++) {
            if (nums[j] != nums[i - k]) {//H.W.: Yiran Li's mistake if (nums[j] != nums[j - 1)
                nums[i++] = nums[j];     //      ex: [1,1,1,2,2,3], 3
            }                            //why? since the array is shrinked after i = 2,
        }                                //     the nums[2] is changed from 1 to 2 and nums[4] gets lost soon.
        return nums.length <= k ? nums.length : i;
    }
}
