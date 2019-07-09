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
        //return sol1(haystack, needle); //Brute Force: O(m * n) / O(1)
        //return sol2(haystack, needle); //Rabin-Karp:  O(m * n) / O(1) 
        return sol3a(haystack, needle);  //Brute Force (KMP Pre):  O(m * n) / O(1) 
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
            if (i + j < 0 || i + j >= haystack.length() || haystack.charAt(i + j) != needle.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    final long smallPrime = 11L, bigPrime = 257L;

    private int sol2(String haystack, String needle) {
        if (needle.length() <= 0) {
            return 0;
        }
        long needleHashCode = 0L, baseFactor = 1L;
        for (int i = 0; i < needle.length(); i++) {
            needleHashCode = updateHC(needle, i - needle.length(), i, needleHashCode, baseFactor); 
            baseFactor = (baseFactor * smallPrime) % bigPrime;
        }
        long substrHashCode = 0L;
        for (int i = 0; i < haystack.length(); i++) {
            substrHashCode = updateHC(haystack, i - needle.length(), i, substrHashCode, baseFactor);
            if (substrHashCode == needleHashCode && isMatched(haystack, i - needle.length() + 1, needle)) {
                return i - needle.length() + 1; //H.W.: many times of wrongly taking i for start index.
            }
        }
        return -1;
    }
    private long updateHC(String str, int oldCharPos, int newCharPos, long oldHashCode, long baseFactor) {
        oldHashCode = (oldHashCode * smallPrime) % bigPrime;
        if (oldCharPos >= 0) {
            oldHashCode = (oldHashCode + baseFactor * bigPrime * 256 - baseFactor * str.charAt(oldCharPos)) % bigPrime; 
        }//H.W.: many times of the same mistake for updating oldHashCode when the oldest char is ejected! 
        oldHashCode = (oldHashCode + str.charAt(newCharPos)) % bigPrime;
        return oldHashCode;
    }

    private int sol3a(String haystack, String needle) {
        int i = 0, j = 0;
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }
            else {
                i = i - j + 1;
                j = 0;
            }
        }
        return j == needle.length() ? i - j : -1;
    }
}
