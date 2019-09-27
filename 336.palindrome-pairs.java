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
        //return sol1(words); 
        return sol2(words);
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

    static class TrieNode {
        private final int SIZE = 26;
        private TrieNode[] entries;
        private List<Integer> valueList;
        public TrieNode() {
            entries = new TrieNode[SIZE];
            valueList = null;
        }
        public void insertPostfix(String str, int endIndex, int value) {
            TrieNode root = this;
            for (int i = str.length() - 1; i >= endIndex; i--) {
                int code = str.charAt(i) - 'a';
                if (root.entries[code] == null) {
                    root.entries[code] = new TrieNode();
                }
                root = root.entries[code];
            }
            if (root.valueList == null) {
                root.valueList = new ArrayList<>();
            }
            root.valueList.add(value);
            //System.out.println("insertPostfix: " + str + " " + endIndex + " " + root.valueList);
        }

        public List<Integer> queryPrefix(String str, int endIndex) {
            TrieNode root = this;
            for (int i = 0; i <= endIndex && root != null; i++) {
                root = root.entries[str.charAt(i) - 'a'];
            }
            /*if (root != null) {
                System.out.println(str);
            }*/
            return (root != null && root.valueList != null) ? root.valueList : new ArrayList<>();
        }
    }

    private boolean isPalindromeSubstr(String str, int startIndex, int endIndex) {
        int left = startIndex, right = endIndex;
        while (left < right && str.charAt(left) == str.charAt(right)) {
            left++;
            right--;
        }
        return left >= right;
    }

    private List<List<Integer>> sol2(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j <= words[i].length(); j++) {
                if (isPalindromeSubstr(words[i], 0, j - 1)) {
                    root.insertPostfix(words[i], j, i);
                }
            }
        }

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j <= words[i].length(); j++) {
                if (isPalindromeSubstr(words[i], j, words[i].length() - 1)) {
                    for (int k : root.queryPrefix(words[i], j - 1)) {
                        if (i != k && (j == words[i].length() || j == words[k].length())) {
                            res.add(Arrays.asList(i, k));
                        }
                    }
                }
            }
        } 
        return res;
    }
}
