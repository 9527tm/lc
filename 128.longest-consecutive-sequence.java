/*
 * @lc app=leetcode id=128 lang=java
 *
 * [128] Longest Consecutive Sequence
 *
 * https://leetcode.com/problems/longest-consecutive-sequence/description/
 *
 * algorithms
 * Hard (42.44%)
 * Total Accepted:    231.3K
 * Total Submissions: 541.4K
 * Testcase Example:  '[100,4,200,1,3,2]'
 *
 * Given an unsorted array of integers, find the length of the longest
 * consecutive elements sequence.
 * 
 * Your algorithm should run in O(n) complexity.
 * 
 * Example:
 * 
 * 
 * Input: [100, 4, 200, 1, 3, 2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4].
 * Therefore its length is 4.
 * 
 * 
 */
class Solution {
    public int longestConsecutive(int[] nums) {
        return sol1(nums); 
        //return sol2(nums); 
    }

    private int sol1(int[] nums) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (map.containsKey(n)) {
                continue;
            }
            map.put(n, n);
            int start = map.getOrDefault(n - 1, n);
            int end = map.getOrDefault(n + 1, n);
            map.put(start, end);
            map.put(end, start);
            res = Math.max(res, end - start + 1);
        }
        return res;
    }

    private int sol2(int[] nums) {
        return 0;
    }
}
