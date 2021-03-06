/*
 * @lc app=leetcode id=297 lang=java
 *
 * [297] Serialize and Deserialize Binary Tree
 *
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/description/
 *
 * algorithms
 * Hard (42.80%)
 * Total Accepted:    222.5K
 * Total Submissions: 519.1K
 * Testcase Example:  '[1,2,3,null,null,4,5]'
 *
 * Serialization is the process of converting a data structure or object into a
 * sequence of bits so that it can be stored in a file or memory buffer, or
 * transmitted across a network connection link to be reconstructed later in
 * the same or another computer environment.
 * 
 * Design an algorithm to serialize and deserialize a binary tree. There is no
 * restriction on how your serialization/deserialization algorithm should work.
 * You just need to ensure that a binary tree can be serialized to a string and
 * this string can be deserialized to the original tree structure.
 * 
 * Example: 
 * 
 * 
 * You may serialize the following tree:
 * 
 * ⁠   1
 * ⁠  / \
 * ⁠ 2   3
 * ⁠    / \
 * ⁠   4   5
 * 
 * as "[1,2,3,null,null,4,5]"
 * 
 * 
 * Clarification: The above format is the same as how LeetCode serializes a
 * binary tree. You do not necessarily need to follow this format, so please be
 * creative and come up with different approaches yourself.
 * 
 * Note: Do not use class member/global/static variables to store states. Your
 * serialize and deserialize algorithms should be stateless.
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
public class Codec {
    //private CodecInf sol = new Sol1();
    private CodecInf sol = new Sol2();

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return sol.serialize(root); 
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        return sol.deserialize(data); 
    }
}

interface CodecInf {
    public String serialize(TreeNode root);
    public TreeNode deserialize(String data);
}

class Sol1 implements CodecInf {
    private final String SEP = " ", NULL = "N"; //H.W.:     SEP set to "+"

    public String serialize(TreeNode root) {    //          conflicting with regex of split()
        StringBuilder builder = new StringBuilder();//H.W.: SEP set to "-"
        serialize(root, builder);                   //       conflicting with negative numbers
        return builder.toString();   
    }
    private void serialize(TreeNode root, StringBuilder builder) {
        if (root == null) {
            builder.append(NULL).append(SEP);
            return;
        }
        builder.append(root.val).append(SEP);
        serialize(root.left, builder);
        serialize(root.right, builder);
    }

    public TreeNode deserialize(String data) {
        String[] tokens = data.split(SEP);
        return deserialize(tokens, new int[1]);
    }
    private TreeNode deserialize(String[] tokens, int[] id) {
        String value = tokens[id[0]++];
        if (value.equals(NULL)) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(value));
        root.left = deserialize(tokens, id);
        root.right = deserialize(tokens, id);
        return root;
    }
}


class Sol2 implements CodecInf {
    private final String SEP = " ", NULL = "N";

    public String serialize(TreeNode root) {//H.W.: failed to handle left / right in the same level.
        if (root == null) {                 //      no matter either of them is null.
            return "";                      //      https://leetcode.com/submissions/detail/267334352/
        }
        StringBuilder builder = new StringBuilder();
        int lastNumPos = 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                builder.append(NULL).append(SEP);
                continue;
            }

            builder.append(node.val).append(SEP);
            lastNumPos = builder.length();

            queue.offer(node.left);
            queue.offer(node.right);
        }

        builder.setLength(lastNumPos);
        return builder.toString();
    }

    public TreeNode deserialize(String data) {
        if (data.length() <= 0) {
            return null;
        }
        int id = 0;
        String[] tokens = data.split(SEP);
        TreeNode root = new TreeNode(Integer.parseInt(tokens[id++]));

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (id < tokens.length && !tokens[id].equals(NULL)) {
                node.left = new TreeNode(Integer.parseInt(tokens[id]));
                queue.offer(node.left);
            }
            id++;
            if (id < tokens.length && !tokens[id].equals(NULL)) {
                node.right = new TreeNode(Integer.parseInt(tokens[id]));
                queue.offer(node.right);
            }
            id++;
        }

        return root;
    }
}
// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
