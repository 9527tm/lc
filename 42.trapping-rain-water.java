/*
 * @lc app=leetcode id=42 lang=java
 *
 * [42] Trapping Rain Water
 *
 * https://leetcode.com/problems/trapping-rain-water/description/
 *
 * algorithms
 * Hard (44.26%)
 * Total Accepted:    336.7K
 * Total Submissions: 756.5K
 * Testcase Example:  '[0,1,0,2,1,0,1,3,2,1,2,1]'
 *
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it is able to trap after raining.
 * 
 * 
 * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
 * In this case, 6 units of rain water (blue section) are being trapped. Thanks
 * Marcos for contributing this image!
 * 
 * Example:
 * 
 * 
 * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * 
 */
class Solution {
    public int trap(int[] height) {
        return sol1(height);  
        //return sol0_WRONG(height);  
    }

    private int sol1(int[] height) {
        int res = 0, leftMax = 0, rightMax = 0;
        int left = 0, right = height.length - 1;
        while (left <= right) {//H.W: wrong stop condition => while (left < right)
            if (leftMax < rightMax) {       //test case: [1,0,1]    =====^^^ 
                leftMax = Math.max(leftMax, height[left]);
                res += leftMax - height[left++];
            }
            else {
                rightMax = Math.max(rightMax, height[right]); //H.W.: typo => Math.max(rightMax, height[left])
                res += rightMax - height[right--];
            }
        }
        return res;
    }

    private int sol0_WRONG(int[] height) {//H.W.: while (left < right) <= missing the last elem
        if (height.length <= 0) {
            return 0;
        }
        int left = 0, right = height.length - 1;
        int res = 0, leftMax = height[left], rightMax = height[right];
        while (left < right) {//H.W.: left < right STILL WRONG!!!!! 
            if (leftMax < rightMax) {//test case: [1,0,2,1]
                if (leftMax < height[left]) {
                    leftMax = height[left];
                }
                else {
                    res += leftMax - height[left];
                }
                left++;
            }
            else {
                if (rightMax < height[right]) {
                    rightMax = height[right];
                }
                else {
                    res += rightMax - height[right];
                }
                right--;
            }
        }
        return res;
    }
}
