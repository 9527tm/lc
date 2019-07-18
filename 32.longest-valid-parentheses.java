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
        //return sol1(s);  //5   DP
        //return sol2(s);  //5   Stack
        //return sol2a(s); //5.5 Stack: Refine sol2 -> sol2a
        return sol3(s);    //6   Two pass scans: O(1) space
    }
    /*
     dp[i]: the length of the longest valid paretheses substring which ends at s[i].
     dp[i] = by two matching cases, dp[i] is changed. 
       case 1:   if s[i] = '('                          => dp[i] = 0
       case 2:   if s[i] = ')'
         case 2.1: if s[i - 1] = '('                    => dp[i] = dp[i - 2] + 2
         case 2.2: if s[i - 1] = ')'
           case 2.2.1 if s[i - 1 - dp[i - 1]] = '('     => dp[i] = dp[i - 1 - dp[i - 1] - 1] + dp[i - 1] + 2
     */
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

    private int sol2(String s) {//grandyang
        Deque<Integer> stack = new ArrayDeque<>();
        int start = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.offerFirst(i);
            }
            else {//if (s.charAt(i) == ')') {
                if (stack.isEmpty()) {
                    start = i + 1;
                }
                else {
                    stack.pollFirst();//matching
                    int num = stack.isEmpty() ? i - start + 1 : i - stack.peekFirst();
                    res = Math.max(res, num);
                }
            }
        }
        return res;
    }

    private int sol2a(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int res = 0;
        stack.offerFirst(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.offerFirst(i);
            }
            else {
                int j = stack.pollFirst();
                if (j >= 0 && s.charAt(j) == '(') {
                    res = Math.max(res, i - stack.peekFirst());
                }
                else {
                    stack.offerFirst(i);
                }
            }
        }
        return res;
    }

    private int sol3(String s) {//H.W.: wrongly inaccurate matching
        int num1 = 0;           //ex: '"())()(()"' should return 2 instead of 2
        int leftNum = 0;
        int rightNum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                leftNum++;
            }
            else {
                rightNum++;
                if (leftNum == rightNum) {//H.W.: wrongly inaccurate matching <= if (leftNum >= rightNum) {
                    num1 = Math.max(num1, rightNum * 2); //'"())()(()"'
                }
                else if (leftNum < rightNum) {//H.W.: missing if (leftNum < rightNum) {
                    leftNum = 0;
                    rightNum = 0;
                }
            }
        }
        //((): left=>right = 0, right=>left = 2
        //()): left=>right = 2, right=>left = 0
        int num2 = 0;
        leftNum = 0;
        rightNum = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ')') {
                rightNum++;
            }
            else {
                leftNum++;
                if (leftNum == rightNum) {//H.W.: wrongly inaccurate matching <= if (leftNum <= rightNum) {
                    num2 = Math.max(num2, leftNum * 2);//'"())()(()"'
                }
                else if (leftNum > rightNum) {//H.W.: missing if (leftNum > rightNum) {
                    rightNum = 0;
                    leftNum = 0;
                }
            }
        }

        return Math.max(num1, num2);
    }
}
