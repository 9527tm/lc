/*
 * @lc app=leetcode id=15 lang=java
 *
 * [15] 3Sum
 *
 * https://leetcode.com/problems/3sum/description/
 *
 * algorithms
 * Medium (24.15%)
 * Total Accepted:    567.4K
 * Total Submissions: 2.3M
 * Testcase Example:  '[-1,0,1,2,-1,-4]'
 *
 * Given an array nums of n integers, are there elements a, b, c in nums such
 * that a + b + c = 0? Find all unique triplets in the array which gives the
 * sum of zero.
 * 
 * Note:
 * 
 * The solution set must not contain duplicate triplets.
 * 
 * Example:
 * 
 * 
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 * 
 * A solution set is:
 * [
 * ⁠ [-1, 0, 1],
 * ⁠ [-1, -1, 2]
 * ]
 * 
 * 
 */
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        //return sol1(nums);
        return sol2(nums);
    }
    
    private List<List<Integer>> sol1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
               continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                if (left > i + 1 && nums[left] == nums[left - 1]) {
                    left++;
                }
                else {
                    int sum = nums[i] + nums[left] + nums[right];
                    if (sum < 0) {
                        left++;
                    }
                    else if (sum > 0) {
                        right--;
                    }
                    else {
                        res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                        left++;
                        right--;
                    }
                }
            }
        }
        return res;
    }
    
    private List<List<Integer>> sol2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < 0) {
                    left++;
                }
                else if (sum > 0) {
                    right--;
                }
                else {
                    res.add(Arrays.asList(nums[i], nums[left++], nums[right--]));
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                    /*while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }*/
                }
            }
        }
        return res;
    }
}
