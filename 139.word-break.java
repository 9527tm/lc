/*
 * @lc app=leetcode id=139 lang=java
 *
 * [139] Word Break
 *
 * https://leetcode.com/problems/word-break/description/
 *
 * algorithms
 * Medium (36.72%)
 * Total Accepted:    396.2K
 * Total Submissions: 1.1M
 * Testcase Example:  '"leetcode"\n["leet","code"]'
 *
 * Given a non-empty string s and a dictionary wordDict containing a list of
 * non-empty words, determine if s can be segmented into a space-separated
 * sequence of one or more dictionary words.
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
 * Input: s = "leetcode", wordDict = ["leet", "code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet
 * code".
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: s = "applepenapple", wordDict = ["apple", "pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple
 * pen apple".
 * Note that you are allowed to reuse a dictionary word.
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output: false
 * 
 * 
 */

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        //return sol1(s, wordDict);  DP Basic
        //return sol1a(s, wordDict); DP Improve
        //return sol2(s, wordDict);  DFS Navie
        //return sol2a(s, wordDict); DFS Mem
        //return sol2b(s, wordDict); DFS Mem Improve
        //return sol3(s, wordDict);  BFS Navie
        //return sol3a(s, wordDict); BFS Mem Improve
        return sol4(s, wordDict);  //DFS Trie Mem
    }

    //DP Basic: O(n ^ 3) + O(k * m) / O(n) + O(k * m)
    //          n -- len(s) / k -- size(wordDict) / m -- max(len(word))
    private boolean sol1(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i && !dp[i]; j++) {//H.W.: missing !dp[i] (!!!)
                dp[i] = dp[j] && set.contains(s.substring(j, i));
            }
        }
        return dp[s.length()];
    }

    //DP Improve: O(n * m * m) + O(k * m) / O(n) + O(k * m)
    private boolean sol1a(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        int maxWordLen = init1a(wordDict, set);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            int start = Math.max(0, i - maxWordLen);
            for (int j = start; j < i && !dp[i]; j++) {
                dp[i] = dp[j] && set.contains(s.substring(j, i));
            }
        }
        return dp[s.length()];
    }
    private int init1a(List<String> wordDict, Set<String> set) {
        int maxWordLen = 0;
        for (String word : wordDict) {
            set.add(word);
            maxWordLen = Math.max(maxWordLen, word.length());
        }
        return maxWordLen;
    }

    //DFS Naive: O(n ^ n) + O(k * m) / O(n) + O(k * m)
    //           TLE: https://leetcode.com/submissions/detail/269223582
    private boolean sol2(String s, List<String> wordDict) {
        return dfs(s, 0, new HashSet<>(wordDict));
    }
    private boolean dfs(String s, int start, Set<String> set) {
        if (start >= s.length()) {
            return true;
        }
        for (int i = start + 1; i <= s.length(); i++) {
            if (set.contains(s.substring(start, i)) && dfs(s, i, set)) {
                return true;
            }
        }
        return false;
    }

    //DFS Mem: O(n ^ 3) + O(k * m) / O(n) + O(k * m)
    private boolean sol2a(String s, List<String> wordDict) {
        return dfs(s, 0, new boolean[s.length()], new HashSet<>(wordDict));
    }
    private boolean dfs(String s, int start, boolean[] isVisited, Set<String> set) {
        if (start >= s.length()) {
            return true;
        }
        if (isVisited[start]) {
            return false;
        }
        isVisited[start] = true;

        for (int i = start + 1; i <= s.length(); i++) {
            if (set.contains(s.substring(start, i)) && dfs(s, i, isVisited, set)) {
                return true;
            }
        }
        return false;
    }

    //DFS Mem Improve: O(n * m * m) + O(k * m) / O(n) + O(k * m)
    private boolean sol2b(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        int maxWordLen = init1a(wordDict, set);
        return dfs(s, 0, new boolean[s.length()], set, maxWordLen);
    }
    private boolean dfs(String s, int start, boolean[] isVisited, Set<String> set, int maxWordLen) {
        if (start >= s.length()) {
            return true;
        }
        if (isVisited[start]) {
            return false;
        }
        isVisited[start] = true;

        int end = Math.min(s.length(), start + maxWordLen);
        for (int i = start + 1; i <= end; i++) {
            if (set.contains(s.substring(start, i)) && dfs(s, i, isVisited, set, maxWordLen)) {
                return true;
            }
        }
        return false;
    }

    //BFS Navie: O(n ^ n) + O(k * m) / O(n ^ n) + O(k * m)
    //           TLE: https://leetcode.com/submissions/detail/269231495/
    private boolean sol3(String s, List<String> wordDict) {
        return bfs(s, new HashSet<>(wordDict));
    }
    private boolean bfs(String s, Set<String> set) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int start = queue.poll();
            if (start >= s.length()) {
                return true;
            }
            for (int i = start + 1; i <= s.length(); i++) {
                if (set.contains(s.substring(start, i))) {
                    queue.offer(i);
                }
            }
        }
        return false;
    }

    //BFS Mem Improve: O(n * m * m) + O(k * m) / O(n) + O(k * m)
    private boolean sol3a(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        int maxWordLen = init1a(wordDict, set);
        return bfs(s, new boolean[s.length() + 1], set, maxWordLen);
    }
    private boolean bfs(String s, boolean[] isVisited, Set<String> set, int maxWordLen) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        isVisited[0] = true;
        while (!queue.isEmpty()) {
            int start = queue.poll();
            if (start >= s.length()) {
                return true;
            }
            int end = Math.min(start + maxWordLen, s.length());
            for (int i = start + 1; i <= end; i++) {
                //if (set.contains(s.substring(start, i) && !isVisited[i])) //transfer to below
                if (!isVisited[i] && set.contains(s.substring(start, i))) {//H.W.: Tricky!!!
                    queue.offer(i);
                    isVisited[i] = true;
                }//isVisited[i] aims only to prevent multiple reaching the same node.
            }    //ex.: ["abab", [ab, cdef]]
        }
        return false;
    }

    //DFS + Trie: O(n * m) + O(k * m) / O(n) + O(26 ^ k)
    private boolean sol4(String s, List<String> wordDict) {
        return dfs(s, 0, new boolean[s.length() + 1], createTrie(wordDict));
    }

    private final int SIZE = 26;
    class Trie {
        private Trie[] entries = new Trie[SIZE];
        private String word = null;
    }
    private Trie createTrie(List<String> wordDict) {
        Trie root = new Trie();
        for (String word : wordDict) {
            Trie curr = root;
            for (int i = 0; i < word.length(); i++) {
                int j = word.charAt(i) - 'a';
                if (curr.entries[j] == null) {
                    curr.entries[j] = new Trie();
                }
                curr = curr.entries[j];
            }
            curr.word = word;
        }
        return root;
    }

    private boolean dfs(String s, int start, boolean[] isVisited, Trie root) {
        if (start >= s.length()) {
            return true;
        }

    /**/if (isVisited[start]) {
            return false;
        }
    /**/isVisited[start] = true;

        Trie curr = root;
        for (int i = start; i < s.length(); i++) {
            int j = s.charAt(i) - 'a';
            curr = curr.entries[j];
            if (curr == null) {
                return false;
            }
            /*if (curr.word != null && !isVisited[i + 1]) {
                isVisited[i + 1] = true;
                if (dfs(s, i + 1, isVisited, root)) {
                    return true;
                }
            }
            */ //visit[] checking can be moved above to /**/ ... /**/
            if (curr.word != null && dfs(s, i + 1, isVisited, root)) {
                return true;
            }
        }
        return false;
    }
}

