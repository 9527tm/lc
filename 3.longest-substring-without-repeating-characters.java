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
        //return sol1(s);        
        //return sol1a(s);        
        //return sol2(s);        
        return sol2a(s);        
        //return sol3_WRONG(s); //NO! D & C (unlike LC53)
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

    private int sol1a(String s) {
        Set<Character> set = new HashSet<>();
        int i = 0, res = 0;
        for (int j = 0; j < s.length(); j++) {
            while (set.contains(s.charAt(j))) {
                set.remove(s.charAt(i++));
            }
            set.add(s.charAt(j));
            res = Math.max(res, j - i + 1);
        }
        return res;
    }

    private int sol2(String s) {
        int[] map = new int[256];
        int start = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            start = Math.max(start, map[s.charAt(i)]);
            res = Math.max(res, i - start + 1);
            map[s.charAt(i)] = i + 1;
        }
        return res;
    }

    private int sol2a(String s) {
        int start = 0, res = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            start = Math.max(start, map.getOrDefault(s.charAt(i), 0));
            res = Math.max(res, i - start + 1);
            map.put(s.charAt(i), i + 1);
        }
        return res;
    }

    //unsuccessful attempts to DC (Unlike LC 53 -- maximum subarray)
    //center expanding [first LEFT then RIGHT]  failed at "dvdf" 
    //center expanding [LEFT / RIGHT alternate] failed at "loddktdji" 
    //https://leetcode.com/submissions/detail/266079901/ 
    //https://leetcode.com/submissions/detail/266082735/

    //According to a googler, Qiang, optimal solutions of subproblems cannot be combined into a global optimal solution.
    //That's, two longest substrings w/o duplicate chars cannot form into one.
}
