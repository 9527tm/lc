/*
 * @lc app=leetcode id=91 lang=java
 *
 * [91] Decode Ways
 *
 * https://leetcode.com/problems/decode-ways/description/
 *
 * algorithms
 * Medium (22.95%)
 * Total Accepted:    300.5K
 * Total Submissions: 1.3M
 * Testcase Example:  '"12"'
 *
 * A message containing letters from A-Z is being encoded to numbers using the
 * following mapping:
 * 
 * 
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * 
 * 
 * Given a non-empty string containing only digits, determine the total number
 * of ways to decode it.
 * 
 * Example 1:
 * 
 * 
 * Input: "12"
 * Output: 2
 * Explanation: It could be decoded as "AB" (1 2) or "L" (12).
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: "226"
 * Output: 3
 * Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2
 * 6).
 * 
 */
class Solution {
    public int numDecodings(String s) {
        return sol1(s);
    }

    private int sol1(String s) {
        if (s.length() <= 0 || s.charAt(0) == '0') {
            return 0;
        }
        int res = 1;
        int oldRes = res; //tricky initialization //H.W.: oldRes = 0: "10"
        for (int i = 1; i < s.length(); i++) {
            int oneDigit = s.charAt(i) - '0';
            int twoDigits = (s.charAt(i - 1) - '0') * 10 + oneDigit;
            int temp = res;

            res = oneDigit > 0 ? res : 0;
            res += (twoDigits >= 10 && twoDigits <= 26) ? oldRes : 0; //H.W: twoNums should range from [10, 26]: "101"
            oldRes = temp;
        } 
        return res;
    }
}
