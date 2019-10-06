/*
 * @lc app=leetcode id=49 lang=java
 *
 * [49] Group Anagrams
 *
 * https://leetcode.com/problems/group-anagrams/description/
 *
 * algorithms
 * Medium (49.63%)
 * Total Accepted:    408.2K
 * Total Submissions: 820.6K
 * Testcase Example:  '["eat","tea","tan","ate","nat","bat"]'
 *
 * Given an array of strings, group anagrams together.
 * 
 * Example:
 * 
 * 
 * Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * Output:
 * [
 * ⁠ ["ate","eat","tea"],
 * ⁠ ["nat","tan"],
 * ⁠ ["bat"]
 * ]
 * 
 * Note:
 * 
 * 
 * All inputs will be in lowercase.
 * The order of your output does not matter.
 * 
 * 
 */
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        //return sol1(strs); 
        //return sol2(strs); 
        return sol2a(strs); 
    }

    private List<List<String>> sol1(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            map.computeIfAbsent(key, (dummyArg) -> new ArrayList<>()).add(str);
        }
        return new ArrayList<>(map.values());
    }

    private List<List<String>> sol2(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            map.computeIfAbsent(getKey(str), (dummyArg) -> new ArrayList<>()).add(str);
        }
        return new ArrayList<>(map.values());
    }
    private String getKey(String str) {
        int[] map = new int['z' - 'a' + 1];
        for (int i = 0; i < str.length(); i++) {
            map[str.charAt(i) - 'a']++;
        }
        StringBuilder builder = new StringBuilder();
        for (int ch = 'a'; ch <= 'z'; ch++) {
            if (map[ch - 'a'] > 0) {
                builder.append(ch).append(map[ch - 'a']);
            }
        }
        return builder.toString();
    }

    private List<List<String>> sol2a(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String key = getKey(str);
            List<String> list = map.get(key);
            if (list == null) {
                list = new ArrayList<>();
                map.put(key, list);
                res.add(list);
            }
            list.add(str);
        }
        return res;
    }
}
