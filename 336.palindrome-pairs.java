/*
 * @lc app=leetcode id=336 lang=java
 *
 * [336] Palindrome Pairs
 *
 * https://leetcode.com/problems/palindrome-pairs/description/
 *
 * algorithms
 * Hard (31.86%)
 * Total Accepted:    79.7K
 * Total Submissions: 250K
 * Testcase Example:  '["abcd","dcba","lls","s","sssll"]'
 *
 * Given a list of unique words, find all pairs of distinct indices (i, j) in
 * the given list, so that the concatenation of the two words, i.e. words[i] +
 * words[j] is a palindrome.
 * 
 * Example 1:
 * 
 * 
 * 
 * Input: ["abcd","dcba","lls","s","sssll"]
 * Output: [[0,1],[1,0],[3,2],[2,4]] 
 * Explanation: The palindromes are
 * ["dcbaabcd","abcddcba","slls","llssssll"]
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: ["bat","tab","cat"]
 * Output: [[0,1],[1,0]] 
 * Explanation: The palindromes are ["battab","tabbat"]
 * 
 * 
 * 
 * 
 */
class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        return sol1(words); 
    }

    private List<List<Integer>> sol1(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i != j && isPalindromePair(words[i], words[j])) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }
        return res;
    }

    private boolean isPalindromePair(String s1, String s2) {
        int left = 0, right = s1.length() + s2.length() - 1;
        while (left < right) {
            char ch1 = left < s1.length() ? s1.charAt(left) : s2.charAt(left - s1.length());
            char ch2 = right < s1.length() ? s1.charAt(right) : s2.charAt(right - s1.length());
            if (ch1 != ch2) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
