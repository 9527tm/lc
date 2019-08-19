/*
 * @lc app=leetcode id=538 lang=java
 *
 * [538] Convert BST to Greater Tree
 *
 * https://leetcode.com/problems/convert-bst-to-greater-tree/description/
 *
 * algorithms
 * Easy (51.18%)
 * Total Accepted:    89.4K
 * Total Submissions: 171.9K
 * Testcase Example:  '[5,2,13]'
 *
 * Given a Binary Search Tree (BST), convert it to a Greater Tree such that
 * every key of the original BST is changed to the original key plus sum of all
 * keys greater than the original key in BST.
 * 
 * 
 * Example:
 * 
 * Input: The root of a Binary Search Tree like this:
 * ⁠             5
 * ⁠           /   \
 * ⁠          2     13
 * 
 * Output: The root of a Greater Tree like this:
 * ⁠            18
 * ⁠           /   \
 * ⁠         20     13
 * 
 * 
 */
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
    public TreeNode convertBST(TreeNode root) {
        //return sol1(root); 
        return sol2(root);
    }

    private TreeNode sol1(TreeNode root) {
        sol1(root, new int[1]);
        return root;
    }
    private void sol1(TreeNode root, int[] sum) {
        if (root == null) {
            return;
        }
        sol1(root.right, sum);
        sum[0] += root.val;
        root.val = sum[0];
        sol1(root.left, sum);
    }

    private TreeNode sol2(TreeNode root) {
        return sol2(root, new int[1]);
    }
    private TreeNode sol2(TreeNode root, int[] sum) {
        if (root == null) {
            return null;
        }
        TreeNode root2 = new TreeNode(0); //default value intialization
        root2.right = sol2(root.right, sum);
        sum[0] += root.val;
        root2.val = sum[0];
        root2.left = sol2(root.left, sum);
        return root2;
    }

}
