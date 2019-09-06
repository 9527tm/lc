/*
 * @lc app=leetcode id=5 lang=java
 *
 * [5] Longest Palindromic Substring
 *
 * https://leetcode.com/problems/longest-palindromic-substring/description/
 *
 * algorithms
 * Medium (27.77%)
 * Total Accepted:    643.6K
 * Total Submissions: 2.3M
 * Testcase Example:  '"babad"'
 *
 * Given a string s, find the longest palindromic substring in s. You may
 * assume that the maximum length of s is 1000.
 * 
 * Example 1:
 * 
 * 
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: "cbbd"
 * Output: "bb"
 * 
 * 
 */
class Solution {
    public String longestPalindrome(String s) {
        return sol1(s); 
    }

    private String sol1(String s) {
        int[] res = {0, -1};
        for (int i = 0; i < s.length(); i++) {
            expand(s, i, i, res);
            expand(s, i, i + 1, res);
        }
        return s.substring(res[0], res[1] + 1);
    }

    private void expand(String s, int left, int right, int[] res) {
        while (left >= 0 && right < s.length() &&
               s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        if (right - left - 2 > res[1] - res[0]) {
            res[0] = left + 1; //H.W.: forgot to wrap up back by 1 => res[0] = left;
            res[1] = right - 1;//                                  => res[1] = right;
        }
    }
}
