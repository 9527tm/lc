/*
 * @lc app=leetcode id=3 lang=java
 *
 * [3] Longest Substring Without Repeating Characters
 *
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
 *
 * algorithms
 * Medium (28.90%)
 * Total Accepted:    1.1M
 * Total Submissions: 3.9M
 * Testcase Example:  '"abcabcbb"'
 *
 * Given a string, find the length of the longest substring without repeating
 * characters.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: "abcabcbb"
 * Output: 3 
 * Explanation: The answer is "abc", with the length of 3. 
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * 
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3. 
 * ⁠            Note that the answer must be a substring, "pwke" is a
 * subsequence and not a substring.
 * 
 * 
 * 
 * 
 * 
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        return sol1(s);        
    }

    private int sol1(String s) {
        boolean[] set = new boolean[256]; //H.W.: assume only lettle case letter => [26]
        int i = 0, j = 0, res = 0;
        while (j < s.length()) {
            if (!set[s.charAt(j)]) {
                set[s.charAt(j++)] = true;
                res = Math.max(res, j - i);
            }
            else {
                set[s.charAt(i++)] = false;
            }
        }
        return res;
    }


}
