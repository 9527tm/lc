/*
 * @lc app=leetcode id=253 lang=java
 *
 * [253] Meeting Rooms II
 *
 * https://leetcode.com/problems/meeting-rooms-ii/description/
 *
 * algorithms
 * Medium (43.68%)
 * Total Accepted:    189K
 * Total Submissions: 432.6K
 * Testcase Example:  '[[0,30],[5,10],[15,20]]'
 *
 * Given an array of meeting time intervals consisting of start and end times
 * [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms
 * required.
 * 
 * Example 1:
 * 
 * 
 * Input: [[0, 30],[5, 10],[15, 20]]
 * Output: 2
 * 
 * Example 2:
 * 
 * 
 * Input: [[7,10],[2,4]]
 * Output: 1
 * 
 * NOTE: input types have been changed on April 15, 2019. Please reset to
 * default code definition to get new method signature.
 * 
 */
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        //return sol1(intervals);        
        //return sol2(intervals);        
        //return sol3(intervals);        
        //return sol1a(intervals);        
        //return sol2a(intervals);        
        return sol3a(intervals);
    }

    private int sol1(int[][] intervals) {
        Arrays.sort(intervals, (itv1, itv2) -> Integer.compare(itv1[0], itv2[0]));
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((itv1, itv2) -> Integer.compare(itv1[1], itv2[1]));
        for (int[] itv : intervals) {
            if (!minHeap.isEmpty() && minHeap.peek()[1] <= itv[0]) {
                minHeap.poll();
            }
            minHeap.offer(itv);
        }
        return minHeap.size();
    }

    private int sol2(int[][] intervals) {
        int[] starts = new int[intervals.length];
        int[] finishes = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            finishes[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(finishes);
        int res = 0, num = 0;
        int i = 0, j = 0;
        while (i < intervals.length) {
            if (starts[i] < finishes[j]) {
                num++;
                res = Math.max(res, num);
                i++;
            }
            else {
                num--;
                j++;
            }
        }
        return res;
    }

    private int sol3(int[][] intervals) {
        int[] starts = new int[intervals.length];
        int[] finishes = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            finishes[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(finishes);
        int num = 0;
        for (int i = 0, j = 0; i < intervals.length; i++) {
            if (starts[i] < finishes[j]) {
                num++;
            }
            else {
                j++;
            }
        }
        return num;
    }

    private int sol1a(int[][] intervals) {
        Arrays.sort(intervals, (itv1, itv2) -> Integer.compare(itv1[0], itv2[0]));
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((itv1, itv2) -> Integer.compare(itv1[1], itv2[1]));
        List<List<int[]>> res = new ArrayList<>();
        Map<int[], List<int[]>> map = new HashMap<>();
        for (int[] itv : intervals) {
            List<int[]> list = null;
            if (!minHeap.isEmpty() && minHeap.peek()[1] <= itv[0]) {
                list = map.remove(minHeap.poll());
            }
            if (list == null) {
                list = new ArrayList<>();
                res.add(list);
            } 
            minHeap.offer(itv);
            list.add(itv);
            map.put(itv, list);
        }
        return res.size();
    }

    private int sol2a(int[][] intervals) {
        int[][] starts = new int[intervals.length][2];
        int[][] finishes = new int[intervals.length][2];
        for (int i = 0; i < intervals.length; i++) {
            starts[i][0] = intervals[i][0];
            finishes[i][0] = intervals[i][1];
            starts[i][1] = i;
            finishes[i][1] = i;
        }
        Arrays.sort(starts, (s1, s2) -> Integer.compare(s1[0], s2[0]));
        Arrays.sort(finishes, (f1, f2) -> Integer.compare(f1[0], f2[0]));
        List<List<int[]>> res = new ArrayList<>();
        Map<Integer, List<int[]>> map = new HashMap<>();
        Queue<List<int[]>> queue = new LinkedList<>();
        int i = 0, j = 0;
        while (i < intervals.length) {
            if (starts[i][0] < finishes[j][0]) {
                if (queue.isEmpty()) {
                    List<int[]> tmp = new ArrayList<>();
                    res.add(tmp);
                    queue.offer(tmp);
                }
                List<int[]> list = queue.poll();
                list.add(intervals[starts[i][1]]);
                map.put(starts[i][1], list);
                i++;
            }
            else {
                queue.offer(map.remove(finishes[j][1]));
                j++;
            }
        }
        return res.size();
    }

    private int sol3a(int[][] intervals) {
        int[][] starts = new int[intervals.length][2];
        int[][] finishes = new int[intervals.length][2];
        for (int i = 0; i < intervals.length; i++) {
            starts[i][0] = intervals[i][0];
            finishes[i][0] = intervals[i][1];
            starts[i][1] = i;
            finishes[i][1] = i;
        }
        Arrays.sort(starts, (s1, s2) -> Integer.compare(s1[0], s2[0]));
        Arrays.sort(finishes, (f1, f2) -> Integer.compare(f1[0], f2[0]));
        List<List<int[]>> res = new ArrayList<>();
        Map<Integer, List<int[]>> map = new HashMap<>();
        Queue<List<int[]>> queue = new LinkedList<>();
        for (int i = 0, j = 0; i < intervals.length; i++) {
            if (starts[i][0] < finishes[j][0]) {
                List<int[]> tmp = new ArrayList<>();
                res.add(tmp);
                queue.offer(tmp);
            }
            else {
                queue.offer(map.remove(finishes[j][1]));
                j++;
            }
            List<int[]> list = queue.poll();
            list.add(intervals[starts[i][1]]);
            map.put(starts[i][1], list);
        }
        return res.size();
    }
}
