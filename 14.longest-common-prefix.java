/*
 * @lc app=leetcode id=14 lang=java
 *
 * [14] Longest Common Prefix
 *
 * https://leetcode.com/problems/longest-common-prefix/description/
 *
 * algorithms
 * Easy (33.65%)
 * Total Accepted:    480.8K
 * Total Submissions: 1.4M
 * Testcase Example:  '["flower","flow","flight"]'
 *
 * Write a function to find the longest common prefix string amongst an array
 * of strings.
 * 
 * If there is no common prefix, return an empty string "".
 * 
 * Example 1:
 * 
 * 
 * Input: ["flower","flow","flight"]
 * Output: "fl"
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 * 
 * 
 * Note:
 * 
 * All given inputs are in lowercase letters a-z.
 * 
 */
class Solution {
    public String longestCommonPrefix(String[] strs) {
        //return sol1(strs);
        return sol2(strs);
    }

    private String sol1(String[] strs) {
        if (strs.length <= 0) {
            return "";
        }
        for (int i = 0; i < strs[0].length(); i++) {
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || 
                    strs[j].charAt(i) !=  strs[0].charAt(i)) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    private String sol2(String[] strs) {
        if (strs.length <= 0) {
            return "";
        }
        Arrays.sort(strs);
        for (int i = 0; i < strs[0].length(); i++) {
            if (i >= strs[strs.length - 1].length() ||
                strs[strs.length - 1].charAt(i) != strs[0].charAt(i)) {
                return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }


}
