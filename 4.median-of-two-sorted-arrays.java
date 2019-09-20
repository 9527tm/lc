/*
 * @lc app=leetcode id=4 lang=java
 *
 * [4] Median of Two Sorted Arrays
 *
 * https://leetcode.com/problems/median-of-two-sorted-arrays/description/
 *
 * algorithms
 * Hard (27.07%)
 * Total Accepted:    507.1K
 * Total Submissions: 1.9M
 * Testcase Example:  '[1,3]\n[2]'
 *
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * 
 * Find the median of the two sorted arrays. The overall run time complexity
 * should be O(log (m+n)).
 * 
 * You may assume nums1 and nums2 cannot be both empty.
 * 
 * Example 1:
 * 
 * 
 * nums1 = [1, 3]
 * nums2 = [2]
 * 
 * The median is 2.0
 * 
 * 
 * Example 2:
 * 
 * 
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * 
 * The median is (2 + 3)/2 = 2.5
 * 
 * 
 */
class Solution {
    //https://leetcode.com/submissions/detail/164236917/
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return sol1(nums1, nums2); 
    }

    private double sol1(int[] nums1, int[] nums2) {
        int med1 = (nums1.length + nums2.length + 0) / 2;
        int med2 = (nums1.length + nums2.length - 1) / 2;
        return med1 == med2 ? sol1Kth(nums1, nums2, med1) :
               (sol1Kth(nums1, nums2, med1) + sol1Kth(nums1, nums2, med2)) / 2.0;
    }
    private int sol1Kth(int[] nums1, int[] nums2, int k) {//k = 0...nums1.length + nums2.length -1
        int left1 = 0, right1 = nums1.length - 1;
        int left2 = 0, right2 = nums2.length - 1;
        while (left1 <= right1 && left2 <= right2) {
            int mid1 = left1 + (right1 - left1) / 2;
            int mid2 = left2 + (right2 - left2) / 2;
            if (mid1 + mid2 < k) {//<=> if (mid1 + mid2 + 1 <= k) {
                if (nums1[mid1] < nums2[mid2]) {
                    left1 = mid1 + 1;
                }
                else {
                    left2 = mid2 + 1;
                }
            }
            else {
                if (nums1[mid1] > nums2[mid2]) {
                    right1 = mid1 - 1;
                }
                else {
                    right2 = mid2 - 1;
                }
            }
        }
        return left1 > right1 ? nums2[k - left1] : nums1[k - left2];
    }

}
