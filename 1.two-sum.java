/*
 * @lc app=leetcode id=1 lang=java
 *
 * [1] Two Sum
 *
 * https://leetcode.com/problems/two-sum/description/
 *
 * algorithms
 * Easy (44.54%)
 * Total Accepted:    2.1M
 * Total Submissions: 4.8M
 * Testcase Example:  '[2,7,11,15]\n9'
 *
 * Given an array of integers, return indices of the two numbers such that they
 * add up to a specific target.
 * 
 * You may assume that each input would have exactly one solution, and you may
 * not use the same element twice.
 * 
 * Example:
 * 
 * 
 * Given nums = [2, 7, 11, 15], target = 9,
 * 
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 * 
 * 
 */
class Solution {
    public int[] twoSum(int[] nums, int target) {
        return sol1(nums, target);	 
        //return sol2(nums, target);	 
        //return sol2a(nums, target);	 
    }

    private int[] sol1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer j = map.get(target - nums[i]);
            if (j != null) {
                return new int[]{j, i};
            }
            map.put(nums[i], i);
        }
        return null; //Never be there!
    }

    private int[] sol2(int[] nums, int target) {
        int[][] array = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            array[i][0] = nums[i];
            array[i][1] = i;
        }
        Arrays.sort(array, (pair1, pair2) -> Integer.compare(pair1[0], pair2[0])); //H.W.: typo pair2 => pair1
        int left = 0, right = array.length - 1;
        while (left < right) {
            int sum = array[left][0] + array[right][0];
            if (sum == target) {
                int i = array[left][1], j = array[right][1];
                return i < j ? new int[]{i, j} : new int[]{j, i};
            }
            else if (sum < target) {
                left++;
            }
            else {
                right--;
            }
        }
        return null; //Never be there!
    }

    private int[] sol2a(int[] nums, int target) {
        Integer[] array = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            array[i] = i;
        }
        Arrays.sort(array, (i1, i2) -> Integer.compare(nums[i1], nums[i2]));
        int left = 0, right = array.length - 1;
        while (left < right) {
            int i = array[left], j = array[right];
            int sum = nums[i] + nums[j];
            if (sum == target) {
                return i < j ? new int[]{i, j} : new int[]{j, i};
            }
            else if (sum < target) {
                left++;
            }
            else {
                right--;
            }
        }
        return null; //Never be there!
    }
}
