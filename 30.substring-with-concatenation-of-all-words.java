/*
 * @lc app=leetcode id=30 lang=java
 *
 * [30] Substring with Concatenation of All Words
 *
 * https://leetcode.com/problems/substring-with-concatenation-of-all-words/description/
 *
 * algorithms
 * Hard (23.70%)
 * Total Accepted:    138.1K
 * Total Submissions: 579.9K
 * Testcase Example:  '"barfoothefoobarman"\n["foo","bar"]'
 *
 * You are given a string, s, and a list of words, words, that are all of the
 * same length. Find all starting indices of substring(s) in s that is a
 * concatenation of each word in words exactly once and without any intervening
 * characters.
 * 
 * Example 1:
 * 
 * 
 * Input:
 * ⁠ s = "barfoothefoobarman",
 * ⁠ words = ["foo","bar"]
 * Output: [0,9]
 * Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar"
 * respectively.
 * The output order does not matter, returning [9,0] is fine too.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * ⁠ s = "wordgoodgoodgoodbestword",
 * ⁠ words = ["word","good","best","word"]
 * Output: []
 * 
 * 
 */
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        //return sol1(s, words); 
        return sol2(s, words); 
    }

    private List<Integer> sol1(String s, String[] words) {//O(l * (n + m)) / O(n * m) : copy HashMap O(n)
        List<Integer> res = new ArrayList<>(); //l = len(s), n = len(words), m = len(words[0])
        if (s.length() <= 0 || words.length <= 0 || words[0].length() <= 0) {
            return res;
        }

        int type = 0; //total type of words
        Map<String, Integer> map = new HashMap<>(); //distribution map of words
        for (String word : words) {
            int num = map.getOrDefault(word, 0);
            map.put(word, num + 1);
            type = (num == 0) ? type + 1 : type;
        }

        for (int i = 0; i < words[0].length(); i++) {
            find(s, words, res, i, new HashMap<String, Integer>(map), type);
        }

        return res;
    }

    private void find(String s, String[] words, List<Integer> res, int i, Map<String, Integer> map, int type) {
        int n = words.length, m = words[0].length();
        int slow = i, fast = i;
        while (fast + m <= s.length()) {
            String substr = s.substring(fast, fast + m);
            Integer num = map.get(substr);
            if (num != null) {
                num--;
                map.put(substr, num);
                if (num == 0) {
                    type--;
                    if (type == 0) {
                        res.add(slow);
                    }
                }
            }

            fast += m;
            if (fast - slow >= m * n) {
                String oldSubStr = s.substring(slow, slow + m);
                Integer oldNum = map.get(oldSubStr);
                if (oldNum != null) {
                    oldNum++;
                    map.put(oldSubStr, oldNum); //H.W.: forgot to update map MANY MANY TIMES!
                    if (oldNum == 1) {
                        type++;
                    }
                }
                slow += m;
            }
        }
    }

    private List<Integer> sol2(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (words.length <= 0 || words[0].length() <= 0) {
            return res;
        }
        Map<String, Integer> targetMap = new HashMap<>();
        for (String word : words) {
            targetMap.put(word, targetMap.getOrDefault(word, 0) + 1);
        }
        Map<String, Integer> currentMap = new HashMap<>();
        for (int i = 0; i < words[0].length(); i++) {
            search(s, words, targetMap, i, currentMap, res);
        }
        return res;
    }
    private void search(String s, String[] words, Map<String, Integer> targetMap, 
                    int i, Map<String, Integer> currentMap, List<Integer> res) {
        int totalType = 0;
        int n = words.length, m = words[0].length();
        int slow = i, fast = i;
        while (fast + m <= s.length()) {
            String substr = s.substring(fast, fast + m);
            Integer targetNum = targetMap.get(substr);
            if (targetNum != null) {
                int currentNum = currentMap.getOrDefault(substr, 0) + 1;
                currentMap.put(substr, currentNum);
                if (currentNum == targetNum) {
                    if (++totalType == targetMap.size()) {
                        res.add(slow);
                    }
                }
            }
            fast += m;

            if (fast - slow < n * m) {
                continue;
            }

            String oldSubStr = s.substring(slow, slow + m);
            Integer oldTargetNum = targetMap.get(oldSubStr);
            if (oldTargetNum != null) {
                int oldCurrentNum = currentMap.getOrDefault(oldSubStr, 0);
                currentMap.put(oldSubStr, oldCurrentNum - 1);
                if (oldCurrentNum == oldTargetNum) {
                    totalType--;
                }
            }
            slow += m;
        }
        currentMap.clear();
    }
}
