/*
 * @lc app=leetcode id=973 lang=java
 *
 * [973] K Closest Points to Origin
 *
 * https://leetcode.com/problems/k-closest-points-to-origin/description/
 *
 * algorithms
 * Medium (61.47%)
 * Total Accepted:    100.1K
 * Total Submissions: 163K
 * Testcase Example:  '[[1,3],[-2,2]]\n1'
 *
 * We have a list of points on the plane.  Find the K closest points to the
 * origin (0, 0).
 * 
 * (Here, the distance between two points on a plane is the Euclidean
 * distance.)
 * 
 * You may return the answer in any order.  The answer is guaranteed to be
 * unique (except for the order that it is in.)
 * 
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: points = [[1,3],[-2,2]], K = 1
 * Output: [[-2,2]]
 * Explanation: 
 * The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest K = 1 points from the origin, so the answer is just
 * [[-2,2]].
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
 * Output: [[3,3],[-2,4]]
 * (The answer [[-2,4],[3,3]] would also be accepted.)
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * 1 <= K <= points.length <= 10000
 * -10000 < points[i][0] < 10000
 * -10000 < points[i][1] < 10000
 * 
 * 
 * 
 */
class Solution {
    public int[][] kClosest(int[][] points, int K) {
        //return sol1(points, K); 
        return sol2(points, K); 
    }

    private int[][] sol1(int[][] points, int K) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(K, 
                        (p1, p2) -> Integer.compare(p2[0] * p2[0] + p2[1] * p2[1], 
                                                    p1[0] * p1[0] + p1[1] * p1[1]));
     
        for (int[] p : points) {
            if (maxHeap.size() < K) {
                maxHeap.offer(p);
            }
            else {
                int[] max = maxHeap.peek();
                if (p[0] * p[0] + p[1] * p[1] < max[0] * max[0] + max[1] * max[1]) {
                    maxHeap.poll();
                    maxHeap.offer(p);
                }
            }
        }
     
        int[][] res = new int[K][];
        for (int i = 0; i < K; i++) {
            res[K - 1 - i] = maxHeap.poll();
        }
        return res;
    }

    private int[][] sol2(int[][] points, int K) {
        int left = 0, right = points.length - 1;
        while (left <= right) {
            int topN = partition(points, left, right) + 1; //topN includes: [< pivot][pivot]
            if (topN < K) {
                left = topN;
            }
            else if (topN > K) {
                right = topN - 1; //H.W.: wrong corner case => right = topN;
            }
            else {
                break;
            }
        }
        return Arrays.copyOf(points, K);
    }

    private int partition(int[][] points, int left, int right) {
        int pivot = left + (int)((right - left + 1) * Math.random());
        swap(points, pivot, right);
        int i = left;
        for (int j = i; j < right; j++) {
            if (sumOfSqr(points[j][0], points[j][1]) <
                sumOfSqr(points[right][0], points[right][1])) {
                swap(points, i++, j);
            }
        }
        swap(points, i, right);
        return i;
    }
    private void swap(int[][] array, int i, int j) {
        int[] temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    private int sumOfSqr(int a, int b) {
        return a * a + b * b;
    }
}
