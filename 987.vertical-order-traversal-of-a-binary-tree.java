/*
 * @lc app=leetcode id=987 lang=java
 *
 * [987] Vertical Order Traversal of a Binary Tree
 *
 * https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/description/
 *
 * algorithms
 * Medium (32.56%)
 * Likes:    218
 * Dislikes: 527
 * Total Accepted:    20.8K
 * Total Submissions: 62.8K
 * Testcase Example:  '[3,9,20,null,null,15,7]'
 *
 * Given a binary tree, return the vertical order traversal of its nodes
 * values.
 * 
 * For each node at position (X, Y), its left and right children respectively
 * will be at positions (X-1, Y-1) and (X+1, Y-1).
 * 
 * Running a vertical line from X = -infinity to X = +infinity, whenever the
 * vertical line touches some nodes, we report the values of the nodes in order
 * from top to bottom (decreasing Y coordinates).
 * 
 * If two nodes have the same position, then the value of the node that is
 * reported first is the value that is smaller.
 * 
 * Return an list of non-empty reports in order of X coordinate.  Every report
 * will have a list of values of nodes.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * 
 * 
 * 
 * Input: [3,9,20,null,null,15,7]
 * Output: [[9],[3,15],[20],[7]]
 * Explanation: 
 * Without loss of generality, we can assume the root node is at position (0,
 * 0):
 * Then, the node with value 9 occurs at position (-1, -1);
 * The nodes with values 3 and 15 occur at positions (0, 0) and (0, -2);
 * The node with value 20 occurs at position (1, -1);
 * The node with value 7 occurs at position (2, -2).
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * 
 * 
 * Input: [1,2,3,4,5,6,7]
 * Output: [[4],[2],[1,5,6],[3],[7]]
 * Explanation: 
 * The node with value 5 and the node with value 6 have the same position
 * according to the given scheme.
 * However, in the report "[1,5,6]", the node value of 5 comes first since 5 is
 * smaller than 6.
 * 
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * The tree will have between 1 and 1000 nodes.
 * Each node's value will be between 0 and 1000.
 * 
 * 
 * 
 * 
 * 
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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        return sol1(root); 
    }

    static class State {
        TreeNode node; //curr node
        int x; //column number, range: [-infinity, infinity]
        int y; //row number,    range: [0 -> -infinity]
        public State(TreeNode node, int x, int y) {
            this.node = node;
            this.x = x;
            this.y = y;
        }
    }
    private List<List<Integer>> sol1(TreeNode root) {
        List<State> stateList = new ArrayList<>();
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(root, 0, 0));
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            stateList.add(curr);
            if (curr.node.left != null) {
                queue.offer(new State(curr.node.left, curr.x - 1, curr.y - 1));
            }
            if (curr.node.right != null) {
                queue.offer(new State(curr.node.right, curr.x + 1,curr.y - 1));
            }
        }

        Collections.sort(stateList, (s1, s2) -> {
            return s1.x != s2.x ? Integer.compare(s1.x, s2.x) :
                   s1.y != s2.y ? Integer.compare(s2.y, s1.y) :
                   Integer.compare(s1.node.val, s2.node.val);
        });

        int preCol = Integer.MIN_VALUE;
        List<List<Integer>> res = new ArrayList<>();
        for (State curr : stateList) {
            if (preCol != curr.x) {
                res.add(new ArrayList<>());
                preCol = curr.x;
            }
            res.get(res.size() - 1).add(curr.node.val);
        }

        return res;
    }
}
// @lc code=end
