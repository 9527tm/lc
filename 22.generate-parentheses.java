/*
 * @lc app=leetcode id=22 lang=java
 *
 * [22] Generate Parentheses
 *
 * https://leetcode.com/problems/generate-parentheses/description/
 *
 * algorithms
 * Medium (55.32%)
 * Total Accepted:    348.9K
 * Total Submissions: 630.6K
 * Testcase Example:  '3'
 *
 * 
 * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed parentheses.
 * 
 * 
 * 
 * For example, given n = 3, a solution set is:
 * 
 * 
 * [
 * ⁠ "((()))",
 * ⁠ "(()())",
 * ⁠ "(())()",
 * ⁠ "()(())",
 * ⁠ "()()()"
 * ]
 * 
 */
class Solution {
    public List<String> generateParenthesis(int n) {
        //return sol1(n);
        return sol2(n);  
    }

    private List<String> sol1(int n) {
        List<String> res = new ArrayList<>();
        sol1(0, 0, new char[n * 2], res);
        return res;
    }
    private void sol1(int left, int right, char[] tmp, List<String> res) {
        int n = tmp.length / 2;
        if (left >= n && right >= n) {
            res.add(new String(tmp));
            return;
        }

        if(left < n) {
            tmp[left + right] = '(';
            sol1(left + 1, right, tmp, res);
        }
        if (right < left) {
            tmp[left + right] = ')';
            sol1(left, right + 1, tmp, res);
        }
    }

    static class Slot {
        private char[] buffer;
        private int left;
        private int right;
        public Slot(int n) {
            buffer = new char[n * 2];
        }
        public Slot copy() {
            Slot c = new Slot(this.buffer.length / 2);
            c.left = this.left;
            c.right = this.right;
            c.buffer = Arrays.copyOf(this.buffer, this.buffer.length);
            return c;
        }
    }
    private List<String> sol2(int n) {
        List<String> res = new ArrayList<>();
        Queue<Slot> queue = new ArrayDeque<>();
        queue.offer(new Slot(n));
        while (!queue.isEmpty()) {
            Slot slot = queue.poll();
            if (slot.left + slot.right == slot.buffer.length) {
                res.add(new String(slot.buffer));
            }
            else {
                if (slot.left < slot.buffer.length / 2) {
                    Slot copy = slot.copy();
                    copy.buffer[copy.left + copy.right] = '(';
                    copy.left++;
                    queue.offer(copy);
                }
                if (slot.right < slot.left) {
                    slot.buffer[slot.left + slot.right] = ')';
                    slot.right++;
                    queue.offer(slot);
                }
            }
        }
        return res;
    }
}
