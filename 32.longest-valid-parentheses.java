/*
 * @lc app=leetcode id=32 lang=java
 *
 * [32] Longest Valid Parentheses
 *
 * https://leetcode.com/problems/longest-valid-parentheses/description/
 *
 * algorithms
 * Hard (25.64%)
 * Total Accepted:    199.8K
 * Total Submissions: 773.8K
 * Testcase Example:  '"(()"'
 *
 * Given a string containing just the characters '(' and ')', find the length
 * of the longest valid (well-formed) parentheses substring.
 * 
 * Example 1:
 * 
 * 
 * Input: "(()"
 * Output: 2
 * Explanation: The longest valid parentheses substring is "()"
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: ")()())"
 * Output: 4
 * Explanation: The longest valid parentheses substring is "()()"
 * 
 * 
 */
class Solution {
    public int longestValidParentheses(String s) {
        return sol1(s); 
    }
    private int sol1(String s) {
        int[] dp = new int[s.length()];
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')' && i > 0) {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = i > 1 ? dp[i - 2] + 2 : 2;
                }
                else {// s.charAt(i - 1) == ')'
                    int j = i - 1 - dp[i - 1];
                    if (j >= 0 && s.charAt(j) == '(') {
                        dp[i] = j > 1 ? dp[j - 1] + dp[i - 1] + 2 : dp[i - 1] + 2;
                    }
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

}
