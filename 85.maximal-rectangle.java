/*
 * @lc app=leetcode id=85 lang=java
 *
 * [85] Maximal Rectangle
 *
 * https://leetcode.com/problems/maximal-rectangle/description/
 *
 * algorithms
 * Hard (34.69%)
 * Likes:    1806
 * Dislikes: 54
 * Total Accepted:    139.6K
 * Total Submissions: 399.9K
 * Testcase Example:  '[["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]'
 *
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle
 * containing only 1's and return its area.
 * 
 * Example:
 * 
 * 
 * Input:
 * [
 * ⁠ ["1","0","1","0","0"],
 * ⁠ ["1","0","1","1","1"],
 * ⁠ ["1","1","1","1","1"],
 * ⁠ ["1","0","0","1","0"]
 * ]
 * Output: 6
 * 
 * 
 */

// @lc code=start
class Solution {
    public int maximalRectangle(char[][] matrix) {
        //return sol1(matrix);  //rowly progressive max histogram      
        return sol2(matrix);    //rowly progressive drag knife (拖刀计)   
    }

    private int sol1(char[][] matrix) {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            return 0;
        }
        int res = 0;
        int[] heights = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            promptLatitude(matrix[i], heights);
            res = Math.max(res, calculateMaximumRectangle(heights));
        }
        return res;
    }
    private void promptLatitude(char[] row, int[] heights) {
        for (int i = 0; i < row.length; i++) {
            heights[i] = (row[i] == '1') ? heights[i] + 1 : 0;
        }
    }
    private int calculateMaximumRectangle(int[] heights) {
        Deque<Integer> stack = new LinkedList<>();
        int res = 0;
        for (int i = 0; i <= heights.length; i++) {
            int newHeight = (i < heights.length) ? heights[i] : 0;
            while (!stack.isEmpty() && heights[stack.peekFirst()] >= newHeight) {
                int height = heights[stack.pollFirst()];
                int right = i - 1; //H.W.: right = stack.pollFirst(); ex: heights = [0,1,2,3]
                int left = !stack.isEmpty() ? stack.peekFirst() : -1;
                int width = right - left; //[right is inclusive, left is exclusice]
                res = Math.max(res, height * width);
            }
            stack.offerFirst(i); //H.W.: forgot to input ops to global variables.
        }
        return res;
    }

    private int sol2(char[][] matrix) {
        if (matrix.length <= 0 || matrix[0].length <= 0) {
            return 0;
        }
        int m = matrix.length, n = matrix[0].length;
        int[] heights = new int[n], leftWidths = new int[n], rightWidths = new int[n];
        Arrays.fill(leftWidths, Integer.MAX_VALUE);
        Arrays.fill(rightWidths, Integer.MAX_VALUE);
        int res = 0;
        for (int i = 0; i < m; i++) {
            //forward scan
            int leftLen = 0;
            for (int j = 0; j < n; j++) {
                leftLen = (matrix[i][j] == '1') ? leftLen + 1 : 0; 
                leftWidths[j] = (matrix[i][j] == '1') ? Math.min(leftWidths[j], leftLen) : Integer.MAX_VALUE;
            }                   //H.W.: forgot to judge matrix[i][j] when update leftWidth
            //backward scan
            int rightLen = 0;
            for (int j = n - 1; j >= 0; j--) {
                rightLen = (matrix[i][j] == '1') ? rightLen + 1 : 0;
                rightWidths[j] = (matrix[i][j] == '1') ? Math.min(rightWidths[j], rightLen) : Integer.MAX_VALUE;
            }
            //collect result candidate
            for (int j = 0; j < n; j++) {
                heights[j] = (matrix[i][j] == '1') ? heights[j] + 1 : 0;
                res = Math.max(res, heights[j] * (leftWidths[j] + rightWidths[j] - 1));
            }   //we are safe when heights[j] is 0 although leftWidths[j] and rightWidths[j] are Integer.MAX_VALUE
        }
        return res;
    }

}
// @lc code=end
