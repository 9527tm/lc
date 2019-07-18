/*
 * @lc app=leetcode id=154 lang=java
 *
 * [154] Find Minimum in Rotated Sorted Array II
 *
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/description/
 *
 * algorithms
 * Hard (39.40%)
 * Total Accepted:    135.7K
 * Total Submissions: 343.2K
 * Testcase Example:  '[1,3,5]'
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown
 * to you beforehand.
 * 
 * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
 * 
 * Find the minimum element.
 * 
 * The array may contain duplicates.
 * 
 * Example 1:
 * 
 * 
 * Input: [1,3,5]
 * Output: 1
 * 
 * Example 2:
 * 
 * 
 * Input: [2,2,2,0,1]
 * Output: 0
 * 
 * Note:
 * 
 * 
 * This is a follow up problem to Find Minimum in Rotated Sorted Array.
 * Would allow duplicates affect the run-time complexity? How and why?
 * 
 * 
 */
class Solution {
    public int findMin(int[] nums) {
        return sol1(nums); 
    }
    //why this works?
    /*1. when nums[left] == nums[right],      /        /
         there will be a confusion: -----|---/  - or -/  ---|-------
                                              /         /
         the minimum may be left or right of mid.
         so we shrink left by one and no loss of searching space happens 
         because of nums[right] holds the same value as nums[left].

      2. if nums[left] =/= nums[right], there will be two cases: "<" or ">".
         2.1.: if "<", the nums is non-decreasing from left to right (no shifting).
               obviously, nums[left] is one of minimums.
               our algorithm always works and right moves close to left until meet at left (minimum).

         2.2.: if ">", there is a break point by shifting, 
               so, nums[left, bp] is a non-decreasing segment and nums[bp + 1, right] is another.
               nums[bp] is one of maximums while nums[bp + 1] is one of minimums.

               2.2.1.: if the mid falls into [left, bp], i.e., [left, mid, bp], 
                       we throws away the segment [left, mid];
               2.2.2.: if the mid flas into [bp + 1, right], i.e., [bp + 1, mid, bp], 
                       we throws away the segment [bp + 1, mid, bp].

               our algorithm takes effect by judging nums[mid] <= nums[right]: 
               true  is for 2.2.2.: [mid, right] is in second non-decreasing segment.
                                    and all elements in it are no less than nums[mid].
               and 
               false is for 2.2.1.: [left, mid] is in first non-decreasing segment.
                                    and all elements in it are no less than nums[mid].
    */
    private int sol1(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            if (nums[left] == nums[right]) {
                left++;
                continue;
            }
            int mid = left + (right - left) / 2;
            if (nums[mid] <= nums[right]) {
                right = mid;
            }
            else {
                left = mid;
            }
        }
        return Math.min(nums[left], nums[right]);
    }
}
