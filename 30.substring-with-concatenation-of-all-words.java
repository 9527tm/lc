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
        //return sol1(s, words);  //5   O(m * (l + n * m)) / O(n * m) => O(m * l) / O(n * m)
        //return sol1a(s, words); //5.5 Best one solution at the same complexity as sol1().
        return sol1b(s, words);   //5   Only one map: sol1a => sol1b.
        //return sol2(s, words);  //5   O(m * (l + n))     / O(n * m) => O(m * l) / O(n * m)
    }                             //    since l >= n * m

    private List<Integer> sol1(String s, String[] words) {//O(m * (l + n * m)) / O(n * m) : copy HashMap is O(n * m)
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

    private List<Integer> sol1a(String s, String[] words) {//O(m * (l + n * m)) / O(n * m)
        List<Integer> res = new ArrayList<>();             //<=> O(m * l) / O(n * m)
        if (words.length <= 0 || words[0].length() <= 0 || //since l >= n * m
            s.length() < words.length * words[0].length()) {
            return res;
        }
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {//O(n * m) / O(n * m)
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        for (int i = 0; i < words[0].length(); i++) {//O(m * (l + n * m)) / O(n * m)
            find1a(s, words, i, new HashMap<>(map), res);
        }
        return res;
    }
    private void find1a(String s, String[] words, int i, Map<String, Integer> map, List<Integer> res) {//O(l / m * m * 2) / O(m) 
        int type = map.size();
        int n = words.length, m = words[0].length();
        int slow = i, fast = i;
        while (fast + m <= s.length()) {
            String newSubStr = s.substring(fast, fast + m);
            Integer newNum = map.get(newSubStr);
            if (newNum != null) {
                if (map.put(newSubStr, newNum - 1) == 1 && type-- == 1) {
                    res.add(slow);
                }
            }
            fast += m;
            if (fast - slow < n * m) {
                continue;
            }
            String oldSubStr = s.substring(slow, slow + m);
            Integer oldNum = map.get(oldSubStr);
            if (oldNum != null) {
                if (map.put(oldSubStr, oldNum + 1) == 0) {
                    type++;
                }
            }
            slow += m;
        }
    }

    private List<Integer> sol1b(String s, String[] words) {//O(m * (l + n)) / O(n * m)
        List<Integer> res = new ArrayList<>();
        if (s.length() <= 0 || words.length <= 0 || words[0].length() <= 0 ||
            s.length() < words.length * words[0].length()) {//H.W.: careless corner case <= "l <= n *m "
            return res;
        }
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        for (int i = 0; i < words[0].length(); i++) {
            find1b(s, words, map, i, res);
        }
        return res;
    }
    private void find1b(String s, String[] words, Map<String, Integer> map, int i, List<Integer> res) {
        int type = map.size();//only one map: /discuss/300985/C++-99.9-Beat-Single-Map-Solution
        int n = words.length, m = words[0].length();
        int fast = i, slow = fast - n * m;
        while (fast < s.length() || slow < s.length()) {
            if (fast >= i && fast + m <= s.length()) {
                String newSubStr = s.substring(fast, fast + m);
                Integer newNum = map.get(newSubStr);
                if (newNum != null) {
                    if (map.put(newSubStr, newNum - 1) == 1 && type-- == 1) {
                        res.add(slow + m); //H.W.: confused start point <= res.add(slow);
                    }
                }
            }
            fast += m;
            slow += m;
            if (slow >= i && slow + m <= s.length()) {
                String oldSubStr = s.substring(slow, slow + m);
                Integer oldNum = map.get(oldSubStr);
                if (oldNum != null) {
                    if (map.put(oldSubStr, oldNum + 1) == 0) {
                        type++;
                    }
                }
            }
        }
    }

    private List<Integer> sol2(String s, String[] words) {//O(m * (l + n)) / O(n * m)
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
