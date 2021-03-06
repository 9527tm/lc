/*
 * @lc app=leetcode id=57 lang=java
 *
 * [57] Insert Interval
 *
 * https://leetcode.com/problems/insert-interval/description/
 *
 * algorithms
 * Hard (31.86%)
 * Likes:    1088
 * Dislikes: 136
 * Total Accepted:    203.2K
 * Total Submissions: 634.4K
 * Testcase Example:  '[[1,3],[6,9]]\n[2,5]'
 *
 * Given a set of non-overlapping intervals, insert a new interval into the
 * intervals (merge if necessary).
 * 
 * You may assume that the intervals were initially sorted according to their
 * start times.
 * 
 * Example 1:
 * 
 * 
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with
 * [3,5],[6,7],[8,10].
 * 
 * NOTE: input types have been changed on April 15, 2019. Please reset to
 * default code definition to get new method signature.
 * 
 */

// @lc code=start
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        //return sol1(intervals, newInterval); 
        //return sol2(intervals, newInterval); 
        return sol3(intervals, newInterval); 
    }

    //O(n) / O(n)
    private int[][] sol1(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        boolean hasInserted = false;
        for (int[] interval : intervals) {
            if (hasInserted) {
                res.add(interval);
                continue;
            }
            
            if (interval[1] < newInterval[0]) {
                res.add(interval);
            }
            else if (newInterval[1] < interval[0]) {
                res.add(newInterval);
                res.add(interval);
                hasInserted = true;
            }
            else {
                newInterval[0] = Math.min(newInterval[0], interval[0]);
                newInterval[1] = Math.max(newInterval[1], interval[1]);
            }
        }
        
        if (!hasInserted) {
            res.add(newInterval);
        }
        return res.toArray(new int[res.size()][]);
    } 

    //O(n) / O(n)
    private int[][] sol2(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        int[][] newIntervals = {newInterval};
        int i = 0, j = 0;
        while (i < intervals.length && j < newIntervals.length) {
            if (intervals[i][1] < newIntervals[j][0]) {
                res.add(intervals[i++]);
            }
            else if (newIntervals[j][1] < intervals[i][0]) {
                res.add(newIntervals[j++]);
            }
            else {
                if (intervals[i][1] < newIntervals[j][1]) {
                    newIntervals[j][0] = Math.min(newIntervals[j][0], intervals[i++][0]);
                }
                else {
                    intervals[i][0] = Math.min(intervals[i][0], newIntervals[j++][0]);
                }
            }
        }

        while (i < intervals.length || j < newIntervals.length) {
            int[] curr = i < intervals.length ? intervals[i++] : newIntervals[j++];
            res.add(curr);
        }
        return res.toArray(new int[res.size()][]);
    }


    //O(nlgn) / O(n)
    private int[][] sol3(int[][] intervals, int[] newInterval) {
        TreeSet<int[]> treeSet = new TreeSet<>((interval1, interval2) -> {
                        return  interval1[1] < interval2[0] ? -1 :
                                interval2[1] < interval1[0] ? 1 : 0;});
        treeSet.add(newInterval);
        for (int[] interval : intervals) {
            while (treeSet.contains(interval)) {
                int[] overlappingInterval = treeSet.floor(interval);
                interval[0] = Math.min(interval[0], overlappingInterval[0]);
                interval[1] = Math.max(interval[1], overlappingInterval[1]);
                treeSet.remove(overlappingInterval);
            }
            treeSet.add(interval);
        }
        return treeSet.toArray(new int[treeSet.size()][]);
    }
}
// @lc code=end
