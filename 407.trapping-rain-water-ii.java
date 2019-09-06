/*
 * @lc app=leetcode id=407 lang=java
 *
 * [407] Trapping Rain Water II
 *
 * https://leetcode.com/problems/trapping-rain-water-ii/description/
 *
 * algorithms
 * Hard (39.75%)
 * Total Accepted:    30.4K
 * Total Submissions: 76.2K
 * Testcase Example:  '[[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]'
 *
 * Given an m x n matrix of positive integers representing the height of each
 * unit cell in a 2D elevation map, compute the volume of water it is able to
 * trap after raining.
 * 
 * 
 * 
 * Note:
 * 
 * Both m and n are less than 110. The height of each unit cell is greater than
 * 0 and is less than 20,000.
 * 
 * 
 * 
 * Example:
 * 
 * 
 * Given the following 3x6 height map:
 * [
 * ⁠ [1,4,3,1,3,2],
 * ⁠ [3,2,1,3,2,4],
 * ⁠ [2,3,3,2,3,1]
 * ]
 * 
 * Return 4.
 * 
 * 
 * 
 * 
 * The above image represents the elevation map
 * [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]] before the rain.
 * 
 * 
 * 
 * 
 * 
 * After the rain, water is trapped between the blocks. The total volume of
 * water trapped is 4.
 * 
 */
class Solution {
    public int trapRainWater(int[][] heightMap) {
        return sol1(heightMap); 
    }

    private int sol1(int[][] heightMap) {
        if (heightMap.length <= 0 || heightMap[0].length <= 0) {
            return 0;
        }
        int m = heightMap.length, n = heightMap[0].length;
        boolean isVisited[][] = new boolean[m][n];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(17, 
                         (c1, c2) -> Integer.compare(c1[2], c2[2]));

        for (int i = 0; i < m; i++) {
            minHeap.offer(new int[]{i, 0, heightMap[i][0]});
            minHeap.offer(new int[]{i, n - 1, heightMap[i][n - 1]});
            isVisited[i][0] = true;
            isVisited[i][n - 1] = true;
        }
        for (int j = 0; j < n; j++) {
            minHeap.offer(new int[]{0, j, heightMap[0][j]});
            minHeap.offer(new int[]{m - 1, j, heightMap[m - 1][j]});
            isVisited[0][j] = true;
            isVisited[m - 1][j] = true;
        }

        int res = 0, level = 0;
        while (!minHeap.isEmpty()) {
            int[] cell = minHeap.poll();
            int x = cell[0], y = cell[1], z = cell[2];
            level = Math.max(level, z);
            res += level - z;

            int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : dirs) {
                int x2 = x + dir[0], y2 = y + dir[1];
                if (x2 >= 0 && x2 < m &&
                    y2 >= 0 && y2 < n &&
                    !isVisited[x2][y2]) {
                    minHeap.offer(new int[]{x2, y2, heightMap[x2][y2]});
                    isVisited[x2][y2] = true;
                }
            }
        }

        return res;
    }
}
