/*
 * @lc app=leetcode id=314 lang=java
 *
 * [314] Binary Tree Vertical Order Traversal
 *
 * https://leetcode.com/problems/binary-tree-vertical-order-traversal/description/
 *
 * algorithms
 * Medium (42.04%)
 * Likes:    687
 * Dislikes: 133
 * Total Accepted:    87.5K
 * Total Submissions: 206.6K
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * Given a binary tree, return the vertical order traversal of its nodes'
 * values. (ie, from top to bottom, column by column).
 * 
 * If two nodes are in the same row and column, the order should be from left
 * to right.
 * 
 * Examples 1:
 * 
 * 
 * Input: [3,9,20,null,null,15,7]
 * 
 * ⁠  3
 * ⁠ /\
 * ⁠/  \
 * ⁠9  20
 * ⁠   /\
 * ⁠  /  \
 * ⁠ 15   7 
 * 
 * Output:
 * 
 * [
 * ⁠ [9],
 * ⁠ [3,15],
 * ⁠ [20],
 * ⁠ [7]
 * ]
 * 
 * 
 * Examples 2:
 * 
 * 
 * Input: [3,9,8,4,0,1,7]
 * 
 * ⁠    3
 * ⁠   /\
 * ⁠  /  \
 * ⁠  9   8
 * ⁠ /\  /\
 * ⁠/  \/  \
 * ⁠4  01   7 
 * 
 * Output:
 * 
 * [
 * ⁠ [4],
 * ⁠ [9],
 * ⁠ [3,0,1],
 * ⁠ [8],
 * ⁠ [7]
 * ]
 * 
 * 
 * Examples 3:
 * 
 * 
 * Input: [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left
 * child is 5)
 * 
 * ⁠    3
 * ⁠   /\
 * ⁠  /  \
 * ⁠  9   8
 * ⁠ /\  /\
 * ⁠/  \/  \
 * ⁠4  01   7
 * ⁠   /\
 * ⁠  /  \
 * ⁠  5   2
 * 
 * Output:
 * 
 * [
 * ⁠ [4],
 * ⁠ [9,5],
 * ⁠ [3,0,1],
 * ⁠ [8,2],
 * ⁠ [7]
 * ]
 * 
 * 
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        return sol1(root); 
    }

    static class State {
        TreeNode node;
        int col;
        public State(TreeNode node, int col) {
            this.node = node;
            this.col = col;
        }
    }
    private List<List<Integer>> sol1(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        int minCol = Integer.MAX_VALUE;
        int maxCol = Integer.MIN_VALUE;
        Map<Integer, List<Integer>> map = new HashMap<>();
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(root, 0));
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            List<Integer> colList = map.get(curr.col);
            if (colList == null) {
                colList = new ArrayList<>();
                map.put(curr.col, colList);
            }
            colList.add(curr.node.val);
            minCol = Math.min(minCol, curr.col);
            maxCol = Math.max(maxCol, curr.col);
            if (curr.node.left != null) {
                queue.offer(new State(curr.node.left, curr.col - 1));
            }
            if (curr.node.right != null) {
                queue.offer(new State(curr.node.right, curr.col + 1));
            }
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int col = minCol; col <= maxCol; col++) {
            res.add(map.get(col));
        }
        return res;
    }
}
// @lc code=end
