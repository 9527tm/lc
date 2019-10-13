/*
 * @lc app=leetcode id=140 lang=java
 *
 * [140] Word Break II
 *
 * https://leetcode.com/problems/word-break-ii/description/
 *
 * algorithms
 * Hard (28.34%)
 * Total Accepted:    178.4K
 * Total Submissions: 627.3K
 * Testcase Example:  '"catsanddog"\n["cat","cats","and","sand","dog"]'
 *
 * Given a non-empty string s and a dictionary wordDict containing a list of
 * non-empty words, add spaces in s to construct a sentence where each word is
 * a valid dictionary word. Return all such possible sentences.
 * 
 * Note:
 * 
 * 
 * The same word in the dictionary may be reused multiple times in the
 * segmentation.
 * You may assume the dictionary does not contain duplicate words.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input:
 * s = "catsanddog"
 * wordDict = ["cat", "cats", "and", "sand", "dog"]
 * Output:
 * [
 * "cats and dog",
 * "cat sand dog"
 * ]
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * s = "pineapplepenapple"
 * wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 * Output:
 * [
 * "pine apple pen apple",
 * "pineapple pen apple",
 * "pine applepen apple"
 * ]
 * Explanation: Note that you are allowed to reuse a dictionary word.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input:
 * s = "catsandog"
 * wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output:
 * []
 * 
 */
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        //return sol1(s, wordDict);
        return sol2(s, wordDict);
    }

    //DP Improve: O(n * m) + O(X) / O(n) + O(k * m) + O(X)
    //                 n -- len(s), k -- size(wordDict), m -- max(len(word))
    //                 X -- # of(solutions) -- O(2 ^ n)
    private List<String> sol1(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        int maxWordLen = init1a(wordDict, set);
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(0, new ArrayList<>());
        map.get(0).add("");

        int lastValidIndex = 0;
        for (int i = 1; i <= s.length(); i++) {
            int start = Math.max(0, i - maxWordLen);
            List<String> list = new ArrayList<>();
            map.put(i, list);
            for (int j = start; j < i; j++) {
                List<String> list2 = map.get(j);
                String word = s.substring(j, i);
                if (list2.isEmpty() || !set.contains(word)) {
                    continue;
                }
                String postfix = (j == 0) ? word : " " + word;
                for (String prefix : map.get(j)) {
                    list.add(prefix + postfix);
                }
            }

            if (!list.isEmpty()) {//["aaaaaaaabaaaaaa", ["a", "aa"]]
                lastValidIndex = i;//H.W.:
            }
            else if (lastValidIndex - maxWordLen > i) {
                return new ArrayList<>();
            }
        }
        return map.get(s.length());
    }

    private int init1a(List<String> wordDict, Set<String> set) {
        int maxWordLen = 0;
        for (String word : wordDict) {
            set.add(word);
            maxWordLen = Math.max(maxWordLen, word.length());
        }
        return maxWordLen;
    }

    //DP Mem Improve: O(n * m) + O(X) / O(n) + O(k * m) + O(X)
    private List<String> sol2(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        int maxWordLen = init1a(wordDict, set);
        Map<Integer, List<String>> map = new HashMap<>();
        dfs(s, 0, set, maxWordLen, map);
        return map.get(0);
    }

    private void dfs(String s, int start, Set<String> set, int maxWordLen,
                    Map<Integer, List<String>> map) {
        List<String> list = map.get(start);
        if (list != null) {
            return;
        }
        list = new ArrayList<>();
        map.put(start, list);

        if (start >= s.length()) {
            list.add("");
            return;
        }
        int end = Math.min(start + maxWordLen, s.length());
        for (int i = start + 1; i <= end; i++) {
            String word = s.substring(start, i);
            if (set.contains(word)) {
                dfs(s, i, set, maxWordLen, map);
                for (String postfix : map.get(i)) {
                    String sentence = postfix.isEmpty() ? word : word + " " + postfix;
                    list.add(sentence);
                }
            }
        }
    }
}

