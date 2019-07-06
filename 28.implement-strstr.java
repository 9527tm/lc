/*
 * @lc app=leetcode id=28 lang=java
 *
 * [28] Implement strStr()
 *
 * https://leetcode.com/problems/implement-strstr/description/
 *
 * algorithms
 * Easy (32.17%)
 * Total Accepted:    443.1K
 * Total Submissions: 1.4M
 * Testcase Example:  '"hello"\n"ll"'
 *
 * Implement strStr().
 * 
 * Return the index of the first occurrence of needle in haystack, or -1 if
 * needle is not part of haystack.
 * 
 * Example 1:
 * 
 * 
 * Input: haystack = "hello", needle = "ll"
 * Output: 2
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: haystack = "aaaaa", needle = "bba"
 * Output: -1
 * 
 * 
 * Clarification:
 * 
 * What should we return when needle is an empty string? This is a great
 * question to ask during an interview.
 * 
 * For the purpose of this problem, we will return 0 when needle is an empty
 * string. This is consistent to C's strstr() and Java's indexOf().
 * 
 */
class Solution {
    public int strStr(String haystack, String needle) {
        return sol1(haystack, needle); 
    }

    private int sol1(String haystack, String needle) {
        for (int i = 0; i < haystack.length(); i++) {
            if (isMatched(haystack, i, needle)) {
                return i;
            }
        }
        return needle.length() <= 0 ? 0 : -1;
    }
    private boolean isMatched(String haystack, int i, String needle) {
        for (int j = 0; j < needle.length(); j++) {
            if (i + j >= haystack.length() || haystack.charAt(i + j) != needle.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
