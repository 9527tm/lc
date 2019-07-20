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
        //return sol1(nums);  //5   simplify the problem into 153
        //return sol2(nums);  //5   more concise than sol1
        //return sol3(nums);  //5.5 logical thinking  and comprehensive categories.
        return sol3a(nums);   //6.0 refine sol3
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

    private int sol2(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {//case 3
            int mid = left + (right - left) / 2;
            if (nums[mid] == nums[right]) {
                right--;
            }
            else if (nums[mid] > nums[right]) {//case 2.1
                left = mid + 1;
            }
            else {//case 1 and 2.2
                right = mid;
            }
        }
        return nums[left];
    }
    /*
     Before Shifting:
        case 0: [1,1,1,1,1,1,1] nums[left] = nums[right]
        case 1: [0,1,1,1,1,1,2] nums[left] < nums[right]
     
     After Shifting:
        case 1: [0,1,1,1,1,1,2] nums[left] < nums[right] (rotate n times of array length)
                                (nums[mid]  < nums[right]) 
                                ACTION: return nums[left]

        case 2: [2,0,1,1,1,1,1] nums[left] > nums[right] (1 = nums[bp1] < nums[bp2] = 2)
                                ACTION: if (nums[mid] > nums[right]): left <= mid + 1;
                                        else                        : right <= mid;
                                                   
        case 3: [1,1,1,2,0,1,1] nums[left] = nums[right] (1 = nums[bp1] = nums[bp2] = 1) 
                                ACTION: if (nums[mid] > nums[right]): left <= mid + 1

        case 4: [1,1,2,0,1,1,1] nums[left] = nums[right] (1 = nums[bp1] = nums[bp2] = 1) 
                                ACTION: if (nums[mid] < nums[right]): right <= mid
                                
        case 5: [1,1,1,1,2,0,1] nums[left] = nums[right] (1 = nums[bp1] = nums[bp2] = 1) 
                                ACTION: if (nums[mid] == nums[right]): right--
                                DILEMMA: solution(0) is right to mid.

        case 6: [1,2,0,1,1,1,1] nums[left] = nums[right] (1 = nums[bp1] = nums[bp2] = 1)
                                ACTION: if (nums[mid] == nums[right]): right--
                                DILEMMA: solution(0) is left to mid.

        case 7: [1,1,1,1,1,1,1] nums[left] = nums[right] (1 = nums[bp1] = nums[bp2] = 1)
                                ACTION: if (nums[mid] == nums[right]): right--
                                DILEMMA: solution(1) is left at mid.
     */
    private int sol3(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[left] < nums[right]) {//0123345
                break;
            }
            else if (nums[left] > nums[right]) {
                if (nums[mid] > nums[right]) {//2334501
                    left = mid + 1;
                }
                else {//4501233, 4501111
                    right = mid;
                }
            }
            else {//nums[left] == nums[right]
                if (nums[mid] > nums[right]) {//2333012
                    left = mid + 1;
                }
                else if (nums[mid] < nums[right]) {//23012
                    right = mid;
                }
                else {//222222012, 2012222222, 2222222222
                    right--;
                }
            }
        }
        return nums[left];
    }

    private int sol3a(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            if (nums[left] < nums[right]) {//01111222
                break;
            }
            else {//nums[left] >= nums[right]
                int mid = left + (right - left) / 2;
                if (nums[mid] > nums[right]) {//234501, 1234501
                    left = mid + 1;
                }
                else if (nums[mid] < nums[right]) {//501234, 4501234
                    right = mid;
                }
                else {//nums[mid] == nums[right]
                    if (nums[left] > nums[right]) {//501222222
                        right = mid;
                    }
                    else {//nums[left] == nums[right] == nums[mid] //201222222, 2222222012, 2222222222
                        right--;
                    }
                }
            }
        }
        return nums[left];
    }

}
