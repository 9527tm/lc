/*
 * @lc app=leetcode id=33 lang=java
 *
 * [33] Search in Rotated Sorted Array
 *
 * https://leetcode.com/problems/search-in-rotated-sorted-array/description/
 *
 * algorithms
 * Medium (32.95%)
 * Total Accepted:    440.5K
 * Total Submissions: 1.3M
 * Testcase Example:  '[4,5,6,7,0,1,2]\n0'
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown
 * to you beforehand.
 * 
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 * 
 * You are given a target value to search. If found in the array return its
 * index, otherwise return -1.
 * 
 * You may assume no duplicate exists in the array.
 * 
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * 
 * Example 1:
 * 
 * 
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 * 
 */
class Solution {
    public int search(int[] nums, int target) {
        //return sol1(nums, target); //searching on 2 halves
        //return sol2(nums, target);   //min index + 2 normal binary searches
        //return sol3(nums, target); //min index + 1 mapped binary search
        return sol4(nums, target); //+00 and -00 are used as sentinels
    }
    private int sol1(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            else if (nums[left] <= nums[mid]) {//H.W.: tricky case [1, 0], 0 <= when left = mid = 0
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                }
                else {
                    left = mid + 1;
                }
            }
            else {//nums[left] > nums[mid]
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                }
                else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    private int sol2(int[] nums, int target) {
        int start = findIndexOfMinimum(nums);
        int res1 = binarySearch(nums, 0, start - 1, 0, target);
        int res2 = binarySearch(nums, start, nums.length - 1, 0, target);
        return res1 >= 0 ? res1 : res2;
    }

    private int sol3(int[] nums, int target) {
        int start = findIndexOfMinimum(nums);
        return binarySearch(nums, 0, nums.length - 1, start, target);
    }

    private int findIndexOfMinimum(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }
            else if (nums[mid] < nums[left]) {
                right = mid;
            }
            else {//nums[left] < nums[mid] < nums[right]
                break;
            }
        }
        return left; //even when nums.length = 0, we still return left = 0.
    }

    private int binarySearch(int[] nums, int left, int right, int rotateStep, int target) {
        left += rotateStep;
        right += rotateStep;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = nums[mid % nums.length];
            if (midValue == target) {
                return mid % nums.length; //H.W.: return mid;
            }                             //      <= forgot to unmap the index before return:
            else if (midValue < target) {
                left = mid + 1;
            }
            else {//(midValue > target)
                right = mid - 1;
            }
        }
        return -1;
    }
    
    private int sol4(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = (nums[0] <= nums[mid]) == (nums[0] <= target) ? nums[mid] :
                         nums[0] <= nums[mid] ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            if (midVal == target) {
                return mid;
            }
            else if (midVal < target) {
                left = mid + 1;
            }
            else {//midVal > target
                right = mid - 1;
            }
        }
        return -1;
    }
}
