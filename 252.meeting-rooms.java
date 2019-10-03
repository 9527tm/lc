/*
 * @lc app=leetcode id=252 lang=java
 *
 * [252] Meeting Rooms
 *
 * https://leetcode.com/problems/meeting-rooms/description/
 *
 * algorithms
 * Easy (52.95%)
 * Total Accepted:    98.7K
 * Total Submissions: 186.4K
 * Testcase Example:  '[[0,30],[5,10],[15,20]]'
 *
 * Given an array of meeting time intervals consisting of start and end times
 * [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all
 * meetings.
 * 
 * Example 1:
 * 
 * 
 * Input: [[0,30],[5,10],[15,20]]
 * Output: false
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [[7,10],[2,4]]
 * Output: true
 * 
 * 
 * NOTE: input types have been changed on April 15, 2019. Please reset to
 * default code definition to get new method signature.
 * 
 */
class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        return sol0(intervals);
        //return sol1(intervals);       
        //return sol2(intervals);       
    }

    private boolean sol0(int[][] intervals) {
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                // !(f1 <= s2 || f2 <= s1) equals to (f1 > s2 && f2 > s1)
                if (intervals[i][1] > intervals[j][0] && intervals[j][1] > intervals[i][0]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean sol1(int[][] intervals) {
        Arrays.sort(intervals, (itv1, itv2) -> Integer.compare(itv1[0], itv2[0]));
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false;//H.W.: wrongly understand requirement as s[i] <= f[i-1] 
            }
        }
        return true;
    }

    private boolean sol2(int[][] intervals) {
        Arrays.sort(intervals, (itv1, itv2) -> Integer.compare(itv1[0], itv2[0]));
        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i][1] > intervals[i + 1][0]) {
                return false;
            }
        }
        return true;
    }
}
