/*
 * @lc app=leetcode id=34 lang=java
 *
 * [34] Find First and Last Position of Element in Sorted Array
 *
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/
 *
 * algorithms
 * Medium (33.68%)
 * Total Accepted:    323.5K
 * Total Submissions: 955.1K
 * Testcase Example:  '[5,7,7,8,8,10]\n8'
 *
 * Given an array of integers nums sorted in ascending order, find the starting
 * and ending position of a given target value.
 * 
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * 
 * If the target is not found in the array, return [-1, -1].
 * 
 * Example 1:
 * 
 * 
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 * 
 * Example 2:
 * 
 * 
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 * 
 */
class Solution {
    public int[] searchRange(int[] nums, int target) {
        //return sol1(nums, target); //5   two binary search: first / last occurrence
        //return sol2(nums, target); //5   one binary search but twice uses: smallest larger or equal
        //return sol3(nums, target); //5.5 one divide and conquer: tricky proof of O(lgN)
        return sol3a(nums, target);  //5.5 optimized merging from two sections: sol3 => sol3a
    }

    private int[] sol1(int[] nums, int target) {
        int firstIndex = findFirstOccurrence(nums, target);
        int lastIndex = findLastOccurrence(nums, target);
        return new int[] {firstIndex, lastIndex};
    }
    private int findFirstOccurrence(int[] nums, int target) {
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
        return nums.length <= 0 ? -1 : 
               nums[left] == target ? left : -1;//H.W.: wrongly think left is the answer and forget to judge. 
    }
    private int findLastOccurrence(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            }
            else {
                left = mid;
            }
        }
        return nums.length <= 0 ? -1 :  
               nums[right] == target ? right : //H.W.: wrongly choose left before right for last index
               nums[left] == target ? left : -1;
    }

    private int[] sol2(int[] nums, int target) {
        int index1 = findSmallestNoLessThan(nums, target);
        if (index1 >= nums.length || nums[index1] != target) {
            return new int[] {-1, -1};
        }
        if (target >= Integer.MAX_VALUE) {
            return new int[] {index1, nums.length - 1};
        }
        int index2 = findSmallestNoLessThan(nums, target + 1);
        return new int[] {index1, index2 - 1};
    }
    private int findSmallestNoLessThan(int[] nums, int target) {
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
        if (left < nums.length && nums[left] < target) {
            left += 1;//H.W.: wrong index2 <= forgot to handle the last one elem
        }
        return left;
    }

    private int[] sol3(int[] nums, int target) {///discuss/14707/9-11-lines-O(log-n)
        return findRange(nums, target, 0, nums.length - 1);//Why O(lgN) instead of O(N)?
    }                                                      //worst case://[0,1,1,...,1,1,2], 1
    private int[] findRange(int[] nums, int target, int left, int right) {
        if (left > right || nums[left] > target || nums[right] < target) {
            return new int[] {-1, -1};
        }//now: nums[left] <= target <= nums[right]
        if (nums[left] == nums[right]) {// both ends == target
            return new int[] {left, right};
        }//now: nums[left] < nums[right] (target is between both ends
        int mid = left + (right - left) / 2;
        int[] resMid = (nums[mid] == target) ? new int[] {mid, mid} : new int[] {-1, -1};
        int[] resLeft = findRange(nums, target, left, mid - 1);
        int[] resRight = findRange(nums, target, mid + 1, right);
        int start = resLeft[0] != -1 ? resLeft[0] : resMid[0] != -1 ? resMid[0] : resRight[0];
        int end = resRight[1] != -1 ? resRight[1] : resMid[1] != -1 ? resMid[1] :resLeft[1];
        return new int[] {start, end};
    }

    private int[] sol3a(int[] nums, int target) {
        return findRange3a(nums, target, 0, nums.length - 1);
    }
    private int[] findRange3a(int[] nums, int target, int left, int right) {
        if (left > right || nums[left] > target || nums[right] < target) {
            return new int[] {-1, -1};
        }
        if (nums[left] == nums[right]) {//nums[left] == target == nums[right]
            return new int[] {left, right};
        }
        //nums[left] < nums[right], and target between both ends
        int mid = left + (right - left) / 2;
        int[] resLeft = findRange3a(nums, target, left, mid);//Tricky here: otherwise [left, mid - 1] 
        int[] resRight = findRange3a(nums, target, mid + 1, right);//and [mid, right] will be endless recursion.
        int start = resLeft[0] != -1 ? resLeft[0] : resRight[0];  //test case: [0,1],1
        int end = resRight[1] != -1 ? resRight[1] : resLeft[1];
        return new int[] {start, end};
    }



}
