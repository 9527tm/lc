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
        //return sol1(s);
        //return sol2(s);
        //return sol3(s);
        return sol4(s);
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

    private int sol2(String s) {
        if (s.length() <= 0 || s.charAt(0) == '0') {
            return 0;
        }
        int sum = 1, oldSum = sum; //H.W.: initialization: VERY IMPORTANT!
        for (int i = 1; i < s.length(); i++) {//H.W.: loop starts from 1
            int d1 = s.charAt(i) - '0';
            int d2 = s.charAt(i - 1) - '0';
            
            int temp = (d1 > 0 && d1 < 10) ? sum : 0;
            temp += (d2 == 1 || (d2 == 2 && d1 < 7)) ? oldSum : 0; //H.W.: d2 cannot be 0
            oldSum = sum;
            sum = temp;
        }
        return sum;
    }

    private int sol3(String s) {
        if (s.length() <= 0 || s.charAt(0) == '0') {
            return 0;
        }
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= s.length(); i++) {
            if (s.charAt(i - 1) > '0') {
                dp[i] += dp[i - 1];
            }
            if (s.charAt(i - 2) == '1' || 
                (s.charAt(i - 2) == '2' && s.charAt(i - 1) < '7')) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[s.length()];
    }

    //https://leetcode.com/submissions/detail/275358384/
    private int sol4(String s) {
        int count1 = 1, count2 = 1;
        for (int i = 0; i < s.length(); i++) {
            int temp = 0;
            temp += (s.charAt(i) >= '1' && s.charAt(i) <= '9') ? count1 : 0;
            temp += (i > 0 && ((s.charAt(i - 1) == '1') ||
                               (s.charAt(i - 1) == '2' && s.charAt(i) <= '6'))) 
                    ? count2 : 0;
            count2 = count1;
            count1 = temp;
        }
        return count1;
    }
}
