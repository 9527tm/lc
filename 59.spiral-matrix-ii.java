/*
 * @lc app=leetcode id=59 lang=java
 *
 * [59] Spiral Matrix II
 *
 * https://leetcode.com/problems/spiral-matrix-ii/description/
 *
 * algorithms
 * Medium (48.73%)
 * Total Accepted:    153.5K
 * Total Submissions: 314.2K
 * Testcase Example:  '3'
 *
 * Given a positive integer n, generate a square matrix filled with elements
 * from 1 to n^2 in spiral order.
 * 
 * Example:
 * 
 * 
 * Input: 3
 * Output:
 * [
 * ⁠[ 1, 2, 3 ],
 * ⁠[ 8, 9, 4 ],
 * ⁠[ 7, 6, 5 ]
 * ]
 * 
 * 
 */
class Solution {
    public int[][] generateMatrix(int n) {
        return sol1(n, n);        
    }

    private int[][] sol1(int m, int n) {//m >= 0, n >= 0
        int[][] matrix = new int[m][n];
        int left = 0, right = n - 1, top = 0, bottom = m - 1;
        int i = 1;
        while (left <= right && top <= bottom) {//while (i < m * n) { 
            for (int j = left; j <= right && top <= bottom; j++) {
                matrix[top][j] = i++;
            }
            top++;
            for (int j = top; j <= bottom && left <= right; j++) {
                matrix[j][right] = i++;
            }
            right--;
            for (int j = right; j >= left && top <= bottom; j--) {
                matrix[bottom][j] = i++;
            }
            bottom--;
            for (int j = bottom; j >= top && left <= right; j--) {
                matrix[j][left] = i++;
            }
            left++;
        }
        return matrix;
    }
}
