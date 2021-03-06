/*
 * @lc app=leetcode id=560 lang=java
 *
 * [560] Subarray Sum Equals K
 *
 * https://leetcode.com/problems/subarray-sum-equals-k/description/
 *
 * algorithms
 * Medium (42.89%)
 * Total Accepted:    140.6K
 * Total Submissions: 327.8K
 * Testcase Example:  '[1,1,1]\n2'
 *
 * Given an array of integers and an integer k, you need to find the total
 * number of continuous subarrays whose sum equals to k.
 * 
 * Example 1:
 * 
 * Input:nums = [1,1,1], k = 2
 * Output: 2
 * 
 * 
 * 
 * Note:
 * 
 * The length of the array is in range [1, 20,000].
 * The range of numbers in the array is [-1000, 1000] and the range of the
 * integer k is [-1e7, 1e7].
 * 
 * 
 * 
 */
class Solution {
    public int subarraySum(int[] nums, int k) {
        //return sol1(nums, k);        
        return sol2(nums, k);        
        //return sol2F(nums, k);        
    }

    private int sol0(int[] nums, int k) {
        int sum = 0, res = 0;
        Map<Integer, Integer> preSumMap = new HashMap<>();
        preSumMap.put(0, 1);

        for (int n : nums) {
            int key = sum - (k - n);
            int value = preSumMap.getOrDefault(key, 0);
            res += value;
            sum += n;
            int count = preSumMap.getOrDefault(sum, 0);
            preSumMap.put(sum, count + 1);
        }

        return res;
    }

    private int sol2(int[] nums, int k) {
        int sum = 0, res = 0;
        Map<Integer, Integer> preSumMap = new HashMap<>();
        preSumMap.put(0, 1);

        for (int n : nums) {
            sum += n;
            res += preSumMap.getOrDefault(sum - k, 0);
            preSumMap.put(sum, preSumMap.getOrDefault(sum, 0) + 1);
        }

        return res;
    }

    private int sol2F(int[] nums, int k) {
        int sum = 0;
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<Integer>> preSumMap = new HashMap<>();
        preSumMap.computeIfAbsent(0, key -> new ArrayList<>()).add(-1);

        List<Integer> emptyList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            List<Integer> startList = preSumMap.getOrDefault(sum - k, emptyList);
            for (Integer j : startList) {
                res.add(Arrays.asList(j + 1, i));
            }
            preSumMap.computeIfAbsent(sum, key -> new ArrayList<>()).add(i);
        }

        //System.out.println(res);
        return res.size();
    }
}
