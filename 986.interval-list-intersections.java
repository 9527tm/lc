/*
 * @lc app=leetcode id=986 lang=java
 *
 * [986] Interval List Intersections
 *
 * https://leetcode.com/problems/interval-list-intersections/description/
 *
 * algorithms
 * Medium (63.97%)
 * Likes:    536
 * Dislikes: 24
 * Total Accepted:    39.5K
 * Total Submissions: 61.3K
 * Testcase Example:  '[[0,2],[5,10],[13,23],[24,25]]\n[[1,5],[8,12],[15,24],[25,26]]'
 *
 * Given two lists of closed intervals, each list of intervals is pairwise
 * disjoint and in sorted order.
 * 
 * Return the intersection of these two interval lists.
 * 
 * (Formally, a closed interval [a, b] (with a <= b) denotes the set of real
 * numbers x with a <= x <= b.  The intersection of two closed intervals is a
 * set of real numbers that is either empty, or can be represented as a closed
 * interval.  For example, the intersection of [1, 3] and [2, 4] is [2,
 * 3].)
 * 
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * 
 * 
 * Input: A = [[0,2],[5,10],[13,23],[24,25]], B =
 * [[1,5],[8,12],[15,24],[25,26]]
 * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
 * Reminder: The inputs and the desired output are lists of Interval objects,
 * and not arrays or lists.
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * 0 <= A.length < 1000
 * 0 <= B.length < 1000
 * 0 <= A[i].start, A[i].end, B[i].start, B[i].end < 10^9
 * 
 * 
 * NOTE: input types have been changed on April 15, 2019. Please reset to
 * default code definition to get new method signature.
 * 
 * 
 */

// @lc code=start
class Solution {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        return sol1(A, B); 
    }

    //O(n) / O(n)
    private int[][] sol1(int[][] A, int[][] B) {
        List<int[]> res = new ArrayList<>();
        int i = 0, j = 0;
        while (i < A.length && j < B.length) {
            if (A[i][1] < B[j][1]) {
                if (B[j][0] <= A[i][1]) {
                    res.add(new int[]{Math.max(A[i][0], B[j][0]), A[i][1]});
                }
                i++;
            }
            else {
                if (A[i][0] <= B[j][1]) {
                    res.add(new int[]{Math.max(A[i][0], B[j][0]), B[j][1]});
                }
                j++;
            }
        }
        return res.toArray(new int[res.size()][]); //H.W.: forgot to toArray()
    }

}
// @lc code=end
