/*
 * @lc app=leetcode id=35 lang=java
 *
 * [35] Search Insert Position
 *
 * https://leetcode.com/problems/search-insert-position/description/
 *
 * algorithms
 * Easy (40.91%)
 * Total Accepted:    428.3K
 * Total Submissions: 1M
 * Testcase Example:  '[1,3,5,6]\n5'
 *
 * Given a sorted array and a target value, return the index if the target is
 * found. If not, return the index where it would be if it were inserted in
 * order.
 * 
 * You may assume no duplicates in the array.
 * 
 * Example 1:
 * 
 * 
 * Input: [1,3,5,6], 5
 * Output: 2
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [1,3,5,6], 2
 * Output: 1
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: [1,3,5,6], 7
 * Output: 4
 * 
 * 
 * Example 4:
 * 
 * 
 * Input: [1,3,5,6], 0
 * Output: 0
 * 
 * 
 */
class Solution {
    public int searchInsert(int[] nums, int target) {
        //return sol1(nums, target); 
        //return sol2(nums, target); 
        return sol3(nums, target); 
    }
    private int sol1(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            else if (nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return left;
    }

    private int sol2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }
        return (left < nums.length && nums[left] < target) ? left + 1 : left;
    }

    private int sol3(int[] nums, int target) {//new variant for smallest larger or equal 
        int left = 0, right = nums.length - 1;//       also for first occurence
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1; //why does this work?
            }                    //if nums[mid] = target: the range is shrinked to [left, mid - 1]
        }                        //                       all the elem in it are smaller than target
        return left;             //                       binary search ends at: mid = left > right = mid - 1
    }                            //if nums[mid] > target: the range is shrinked to [left, mid - 1]
}                                //   if there is no one elem in it larger or equal target:
                                 //                       binary search ends at: mid = left > right = mid - 1
                                 //   if there is at least one elem in it larget or equal target:
                                 //                       we assume that elem is nums[mid - 1] or nums[mid - k - 1] (k >= 0)
                                 //                       certainly the range [left, mid - 1] covers the answer.
                                 //in one word, we return "left" which is equal to "right + 1" when binary search ends.
                                 //similar tricks:
                                 //1. /find-first-and-last-position-of-element-in-sorted-array/discuss/14910/The-insert-position-trick
                                 //2. /search-insert-position/discuss/15080/My-8-line-Java-solution
