/*
 * @lc app=leetcode id=542 lang=java
 *
 * [542] 01 Matrix
 *
 * https://leetcode.com/problems/01-matrix/description/
 *
 * algorithms
 * Medium (36.55%)
 * Total Accepted:    51.8K
 * Total Submissions: 141.3K
 * Testcase Example:  '[[0,0,0],[0,1,0],[0,0,0]]'
 *
 * Given a matrix consists of 0 and 1, find the distance of the nearest 0 for
 * each cell.
 * 
 * The distance between two adjacent cells is 1.
 * 
 * 
 * 
 * Example 1: 
 * 
 * 
 * Input:
 * [[0,0,0],
 * ⁠[0,1,0],
 * ⁠[0,0,0]]
 * 
 * Output:
 * [[0,0,0],
 * [0,1,0],
 * [0,0,0]]
 * 
 * 
 * Example 2: 
 * 
 * 
 * Input:
 * [[0,0,0],
 * ⁠[0,1,0],
 * ⁠[1,1,1]]
 * 
 * Output:
 * [[0,0,0],
 * ⁠[0,1,0],
 * ⁠[1,2,1]]
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * The number of elements of the given matrix will not exceed 10,000.
 * There are at least one 0 in the given matrix.
 * The cells are adjacent in only four directions: up, down, left and right.
 * 
 * 
 */
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        //return sol1(matrix);
        //return sol2(matrix);
        return sol3(matrix);
    }

    private int[][] sol1(int[][] matrix) {// O(m * n * (m * n)) / O(m * n) ===> TLE
        int[][] res = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                res[i][j] = search(matrix, i, j);
            }
        }
        return res; 
    }

    private int search(int[][] matrix, int srcX, int srcY) { //BFS I
        if (matrix[srcX][srcY] == 0) {
            return 0; //trivial finding
        }
    
        boolean[][] isVisited = new boolean[matrix.length][matrix[0].length];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{srcX, srcY});
        isVisited[srcX][srcY] = true;
        int dist = 1;

        while (!queue.isEmpty()) {
            int num = queue.size();
            for (int k = 0; k < num; k++) {
                int[] location = queue.poll();
                int x = location[0], y = location[1];
                int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                for (int[] dir : dirs) {
                    int x2 = x + dir[0], y2 = y + dir[1];
                    if (x2 >= 0 && x2 < matrix.length &&
                        y2 >= 0 && y2 < matrix[0].length &&
                        !isVisited[x2][y2]) {
                        if (matrix[x2][y2] == 0) {
                            return dist; //target found
                        }
                        isVisited[x2][y2] = true;
                        queue.offer(new int[]{x2, y2});
                    }
                }
            }
            dist++;
        }

        return Integer.MAX_VALUE; //not found
    }

    private int[][] sol2(int[][] matrix) { //BFS I: O(m * n) / O(m + n)
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) { //H.W.: typo 1: for (int j = 0; ...; i++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        int dist = 1;
        int[][] res = new int[matrix.length][matrix[0].length];
        while (!queue.isEmpty()) {
            int num = queue.size();
            for (int k = 0; k < num; k++) {
                int[] location = queue.poll();
                int i = location[0], j = location[1];
                int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //H.W.: typo 3: {{-1, 0}, {0, 1}, {0, -1}, {0, 1}}
                for (int[] dir : dirs) {
                    int i2 = i + dir[0], j2 = j + dir[1];
                    if (i2 >= 0 && i2 < matrix.length &&
                        j2 >= 0 && j2 < matrix[0].length &&
                        matrix[i2][j2] != 0) {
                        queue.offer(new int[]{i2, j2});
                        matrix[i2][j2] = 0;
                        res[i2][j2] = dist;  //H.W.: typo 2: res[i][j] = dist
                    }
                }
            }
            dist++;
        }
        return res;
    }

    private int[][] sol3(int[][] matrix) {
        final int maxDist = Integer.MAX_VALUE - 1;
        int[][] res = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    int upDist = i > 0 ? res[i - 1][j] : maxDist;
                    int leftDist =  j > 0 ? res[i][j - 1] : maxDist;
                    res[i][j] = Math.min(maxDist, Math.min(upDist, leftDist) + 1);
                }
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j >= 0; j--) {
                if (matrix[i][j] != 0) {
                    int lowDist = i < matrix.length - 1 ? res[i + 1][j] : maxDist;
                    int rightDist = j < matrix[0].length - 1 ? res[i][j + 1] : maxDist;
                    res[i][j] = Math.min(res[i][j], Math.min(lowDist, rightDist) + 1);
                }
            }
        }
        return res;
    }
}
