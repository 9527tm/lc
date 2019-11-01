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
        //return sol1(intervals);        
        //return sol1a(intervals);        
        //return sol2(intervals);        
        //return sol3(intervals);
        //return sol4(intervals);
        return sol4a(intervals);
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

    private int[][] sol1a(int[][] intervals) {
        if (intervals.length < 2) {
            return intervals;
        }
        Arrays.sort(intervals, (itv1, itv2) -> Integer.compare(itv1[0], itv2[0]));
        List<int[]> list = new ArrayList<>();
        int[] prev = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (prev[1] < intervals[i][0]) {
                list.add(prev);
                prev = intervals[i];
            }
            else {
                prev[1] = Math.max(prev[1], intervals[i][1]);
            }
        }
        list.add(prev);
        return list.toArray(new int[list.size()][]); //H.W.: don't know list.toArray(...)
    }

    private int[][] sol2(int[][] intervals) {
        int[] starts = new int[intervals.length];
        int[] finishes = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            finishes[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(finishes);
        List<int[]> list = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < intervals.length; i++) {
            if (i == intervals.length - 1 || finishes[i] < starts[i + 1]) {
                list.add(new int[]{starts[j], finishes[i]});
                j = i + 1;
            }
        }
        return list.toArray(new int[list.size()][]);
    }

    //https://leetcode.com/problems/merge-intervals/discuss/21359/Clean-TreeSet-solution-in-Java
    private int[][] sol3(int[][] intervals) {
        TreeSet<int[]> treeSet = new TreeSet<>((itv1, itv2) -> {return itv1[1] < itv2[0] ? -1 : itv2[1] < itv1[0] ? 1 : 0;});
        for (int[] itv : intervals) {
            while (treeSet.contains(itv)) {
                int[] olpItv = treeSet.ceiling(itv);
                itv[0] = Math.min(itv[0], olpItv[0]);
                itv[1] = Math.max(itv[1], olpItv[1]);
                treeSet.remove(olpItv);
            }
            treeSet.add(itv);
        }
        return treeSet.toArray(new int[treeSet.size()][]);
    }

    //H.W.: wrongly merge without copy space!  [[5, 6], [7, 8],  [0, 1], [2, 3], [4, 5]]
    //      [7, 8] is actually overwritten by [2, 3], though [5, 6] was avoided!
    
    //O(nlgn) / O(n): DC without extra copying / with temp spaces
    private int[][] sol4(int[][] intervals) {
        int newRight = sol4(intervals, 0, intervals.length - 1, Arrays.copyOf(intervals, intervals.length));
        return Arrays.copyOfRange(intervals, 0, newRight + 1);
    }

    private int sol4(int[][] intervals, int left, int right, int[][] copys) {
        if (left + 1 > right) {
            return right;//H.W.: wrongly return length instead of right end point
        }
        int mid = left + (right - left) / 2;
        int right1 = sol4(copys, left, mid, intervals);
        int right2 = sol4(copys, mid + 1, right, intervals);
        return merge(copys, left, right1, mid + 1, right2, intervals);
    }

    private int merge(int[][] input, int left1, int right1, int left2, int right2, int[][] output) {
        int k = left1;
        int[] curr = input[left1++];
        while (left1 <= right1 || left2 <= right2) {
            int[] next = (left1 > right1 || (left2 <= right2 && input[left2][0] < input[left1][0])) ?
                    input[left2++] : input[left1++];
            if (curr[1] < next[0]) {
                output[k++] = curr;
                curr = next;
            }
            else if (next[1] < curr[0]) {
                output[k++] = next;
            }
            else {
                curr[0] = Math.min(curr[0], next[0]);
                curr[1] = Math.max(curr[1], next[1]);
            }
        }
        output[k] = curr;
        return k;
    }

    //O(nlgn) / O(nlgn)
    private int[][] sol4a(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        for (int[] interval : intervals) {
            list.add(interval);
        }
        List<int[]> res = sol4a(list);
        return res.toArray(new int[res.size()][]);
    }

    private List<int[]> sol4a(List<int[]> list) {
        int size = list.size();
        if (size <= 1) {
            return list;
        }
        List<int[]> list1 = list.subList(0, size / 2);
        List<int[]> list2 = list.subList(size / 2, size);
        //return merge2(sol4a(list1), sol4a(list2)); //H.W.: wrong merge structure
        return merge(sol4a(list1), sol4a(list2)); //H.W.: forgot to call sol4a recursively
    }                                              //      => return merge(list1, list2);

    private List<int[]> merge(List<int[]> list1, List<int[]> list2) {
        int[] curr = list1.get(0);
        List<int[]> res = new ArrayList<>();
        int i = 1, j = 0;
        while (i < list1.size() || j < list2.size()) {
            int[] next = (i >= list1.size() || 
                          (j < list2.size() && list2.get(j)[0] < list1.get(i)[0])) ?
                         list2.get(j++) : list1.get(i++); //H.W.: typo => list2.get(i++)
            if (curr[1] < next[0]) {
                res.add(curr);
                curr = next;
            }
            else if (next[1] < curr[0]) {
                res.add(next);
            }
            else {
                curr[0] = Math.min(curr[0], next[0]);
                curr[1] = Math.max(curr[1], next[1]);
            }
        }
        res.add(curr); //H.W.: forgot postprocessing curr!
        return res;
    }

    //H.W.: merge without "curr / next" structure
    //https://leetcode.com/submissions/detail/275112699/
    /*private List<int[]> merge2(List<int[]> list1, List<int[]> list2) {
    }*/
}
