/*
 * @lc app=leetcode id=76 lang=java
 *
 * [76] Minimum Window Substring
 *
 * https://leetcode.com/problems/minimum-window-substring/description/
 *
 * algorithms
 * Hard (32.04%)
 * Total Accepted:    280.3K
 * Total Submissions: 874.3K
 * Testcase Example:  '"ADOBECODEBANC"\n"ABC"'
 *
 * Given a string S and a string T, find the minimum window in S which will
 * contain all the characters in T in complexity O(n).
 * 
 * Example:
 * 
 * 
 * Input: S = "ADOBECODEBANC", T = "ABC"
 * Output: "BANC"
 * 
 * 
 * Note:
 * 
 * 
 * If there is no such window in S that covers all characters in T, return the
 * empty string "".
 * If there is such window, you are guaranteed that there will always be only
 * one unique minimum window in S.
 * 
 * 
 */
class Solution {
    public String minWindow(String s, String t) {
        //return sol1(s, t);        
        //return sol1a(s, t);        
        return sol1b(s, t);        
        //return sol2(s, t);        
    }

    private String sol1(String s, String t) {
        if (s.length() <= 0 || t.length() <= 0) {
            return "";
        }

        int[] map = new int[127];
        int types = 0;
        for (int i = 0; i < t.length(); i++) {
            map[t.charAt(i)]++;
            if (map[t.charAt(i)] == 1) {
                types++;
            }
        }

        int[] res = {0, Integer.MAX_VALUE};
        int i = 0; //i: start of substring
        for (int j = 0; j < s.length(); j++) {//j: end of substring
            map[s.charAt(j)]--;
            if (map[s.charAt(j)] == 0) {
                types--;
                while (types == 0) {
                    if (j - i < res[1] - res[0]) {
                        res[0] = i;
                        res[1] = j;
                    }
                    map[s.charAt(i)]++;
                    if (map[s.charAt(i)] == 1) {
                        types++;
                    }
                    i++;
                }
            }
        }
        
        return res[1] == Integer.MAX_VALUE ? "" : s.substring(res[0], res[1] + 1);
    }

    private String sol1a(String s, String t) {
        int[] map = new int[127];
        int types = 0;
        for (int i = 0; i < t.length(); i++) {
            if (map[t.charAt(i)]++ == 0) {
                types++;
            }
        }

        int[] res = {0, Integer.MAX_VALUE};
        int i = 0, j = -1; //i: [start, j: end)
        while (true) {
            if (types > 0) {
                if (j + 1 < s.length()) {
                    if (map[s.charAt(j + 1)]-- == 1) {
                        types--;
                    }
                    j++;
                }
                else {
                    break;
                }
            }
            else {
                if (j - i < res[1] - res[0]) {
                    res[0] = i;
                    res[1] = j;
                }
                if (i < s.length()) {
                    if (map[s.charAt(i)]++ == 0) {
                        types++;
                    }
                    i++;
                }
                else {
                    break;
                }
            }
        }

        return res[1] == Integer.MAX_VALUE ? "" : s.substring(res[0], res[1] + 1);
    }

    private String sol1b(String s, String t) {
        int[] map = new int[127];
        int types = 0;
        for (int i = 0; i < t.length(); i++) {
            if (map[t.charAt(i)]++ == 0) {
                types++;
            }
        }

        int[] res = {0, Integer.MAX_VALUE};
        int i = 0, j = 0;
        while (i < s.length()) {
            if (types > 0) {
                if (j >= s.length()) {
                    break;
                }
                if (map[s.charAt(j)]-- == 1) {
                    types--;
                }
                j++;
            }
            else {
                if (j - i < res[1] - res[0]) {
                    res[0] = i;
                    res[1] = j;
                }
                if (map[s.charAt(i)]++ == 0) {
                    types++;
                }
                i++;
            }
        }
        return res[1] == Integer.MAX_VALUE ? "" : s.substring(res[0], res[1]);
    }
    private String sol2(String s, String t) {
        int[] map = new int[127];
        int types = 0;
        for (int i = 0; i < t.length(); i++) {
            if (map[t.charAt(i)]++ == 0) {
                types++;
            }
        }

        int num = 0; 
        int[] indices = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (map[s.charAt(i)] > 0) {
                indices[num++] = i;
            }
        }

        int[] res = {0, Integer.MAX_VALUE};
        int i = 0, j = -1; //i: [start, j: end]
        while (i < num) {
            if (types == 0) {
                if (indices[j] - indices[i] < res[1] - res[0]) {
                    res[1] = indices[j];
                    res[0] = indices[i];
                }
                if (map[s.charAt(indices[i])]++ == 0) {
                    types++; 
                }
                i++;
            }
            else {
                if (j + 1 >= num) {//H.W.: inconsistent enqueuing char (types > 0) and querying its position (types == 0)
                    break;
                }
                if (map[s.charAt(indices[j + 1])]-- == 1) {
                    types--;
                }
                j++;
            }
        }

        return res[1] == Integer.MAX_VALUE ? "" : s.substring(res[0], res[1] + 1); //H.W.: forget + 1 for end position
    }
}
