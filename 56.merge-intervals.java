/*
 * @lc app=leetcode id=56 lang=java
 *
 * [56] Merge Intervals
 *
 * https://leetcode.com/problems/merge-intervals/description/
 *
 * algorithms
 * Medium (36.74%)
 * Total Accepted:    415.2K
 * Total Submissions: 1.1M
 * Testcase Example:  '[[1,3],[2,6],[8,10],[15,18]]'
 *
 * Given a collection of intervals, merge all overlapping intervals.
 * 
 * Example 1:
 * 
 * 
 * Input: [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into
 * [1,6].
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * 
 * NOTE: input types have been changed on April 15, 2019. Please reset to
 * default code definition to get new method signature.
 * 
 */
class Solution {
    public int[][] merge(int[][] intervals) {
        return sol1(intervals);        
    }

    private int[][] sol1(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, (itv1, itv2) -> Integer.compare(itv1[0], itv2[0]));
        int i = 0;
        int[] prev = intervals[0];
        for (int j = 1; j < intervals.length; j++) {
            if (prev[1] < intervals[j][0]) {//H.W.: not use prev instead of wrongly using intervals[0] < ...
                intervals[i++] = prev;
                prev = intervals[j];
            }
            else {
                prev[1] = Math.max(prev[1], intervals[j][1]);
            }
        }
        intervals[i++] = prev;
        return Arrays.copyOfRange(intervals, 0, i);
    }
}
