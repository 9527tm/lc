/*
 * @lc app=leetcode id=153 lang=java
 *
 * [153] Find Minimum in Rotated Sorted Array
 *
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/
 *
 * algorithms
 * Medium (43.06%)
 * Total Accepted:    298.7K
 * Total Submissions: 691K
 * Testcase Example:  '[3,4,5,1,2]'
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown
 * to you beforehand.
 * 
 * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
 * 
 * Find the minimum element.
 * 
 * You may assume no duplicate exists in the array.
 * 
 * Example 1:
 * 
 * 
 * Input: [3,4,5,1,2] 
 * Output: 1
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [4,5,6,7,0,1,2]
 * Output: 0
 * 
 * 
 */
class Solution {
    public int findMin(int[] nums) {
        //return sol1(nums);  5   //bs1 
        //return sol2(nums);  5   //bs2
        //return sol3(nums);  5   //bs2 + implict 2 cases
        //return sol4(nums);  5.5 //bs1 + implict 2 cases
        return sol5(nums);  //5.5 //follow up: compatible for asceding / descending rotated array.
    }

    private int sol1(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            if (nums[left] < nums[right]) {
                return nums[left];
            }
            int mid = left + (right - left) / 2;
            if (nums[left] <= nums[mid]) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }
        return nums[left];
    }

    private int sol2(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[left] < nums[right]) {
                return nums[left];
            }
            else if (nums[left] < nums[mid]) {
                left = mid;
            }
            else {
                right = mid;
            }
        }
        return Math.min(nums[left], nums[right]);
    }

    private int sol3(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[right]) {
                right = mid;
            }
            else {
                left = mid;
            }
        }
        return Math.min(nums[left], nums[right]);
    }

    private int sol4(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {//or more efficient: while (left < right && nums[left] > nums[right]) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {//case 2.1
                left = mid + 1;
            }
            else {//case 1 and case 2.2
                right = mid;
            }
        }
        return nums[left];
    }

    private int sol5(int[] nums) {//6 cases: return min when ascending or descending on rotated array.
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[right] < nums[left]) {
                if (nums[mid] > nums[left]) {//34567812: 2 ascending subarrays
                    left = mid + 1;
                }
                else if (nums[mid] < nums[right]) {//78123456: 2 ascending subarrays
                    right = mid;
                }
                else {//nums[right] < nums[mid] < nums[left] //87654321: 1 descending array
                    return nums[right]; //min
                }
            }
            else {//nums[left] < nums[right]
                if (nums[mid] > nums[right]) {//32187654: 2 descending subarrays
                    right = mid - 1;
                }
                else if (nums[mid] < nums[left]) {//65432187: 2 descending subarrays
                    left = mid;
                }
                else {//nums[left] < nums[mid] < nums[right] //12345678: 1 ascending array
                    return nums[left]; //min
                }
            }
        }
        return Math.min(nums[left], nums[right]);
    }
}

