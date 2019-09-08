/*
 * @lc app=leetcode id=767 lang=java
 *
 * [767] Reorganize String
 *
 * https://leetcode.com/problems/reorganize-string/description/
 *
 * algorithms
 * Medium (43.48%)
 * Total Accepted:    34.4K
 * Total Submissions: 78.8K
 * Testcase Example:  '"aab"'
 *
 * Given a string S, check if the letters can be rearranged so that two
 * characters that are adjacent to each other are not the same.
 * 
 * If possible, output any possible result.  If not possible, return the empty
 * string.
 * 
 * Example 1:
 * 
 * 
 * Input: S = "aab"
 * Output: "aba"
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: S = "aaab"
 * Output: ""
 * 
 * 
 * Note:
 * 
 * 
 * S will consist of lowercase letters and have length in range [1, 500].
 * 
 * 
 * 
 * 
 */
class Solution {
    public String reorganizeString(String S) {
        return sol1(S);        
    }

    private String sol1(String S) {//O(n + 26) / O(26)
        final int SIZE = 26;
        int[] map = new int[SIZE];
        int maxN = 0;
        for (int i = 0; i < S.length(); i++) {
            int n = S.charAt(i) - 'a';
            if (map[n]++ >= map[maxN]) {
                maxN = n;
            }
        }

        if (map[maxN] * 2 > S.length() + 1) {//feassibility: 
            return "";                       //maxCount <= len(S) - maxCount + 1
        }//[1,2,1,2,1] is OK

        int pos = 0;
        char[] buffer = new char[S.length()];
        for (int i = 0; i < SIZE; i++) {
            for (int n = (i + maxN) % SIZE; map[n] > 0; map[n]--) {
                buffer[pos] = (char)(n + 'a');
                pos = pos + 2 < buffer.length ? pos + 2 : 1; //first even then odd
            }
        }
        return new String(buffer);
    }
}
