/*
 * @lc app=leetcode id=249 lang=java
 *
 * [249] Group Shifted Strings
 *
 * https://leetcode.com/problems/group-shifted-strings/description/
 *
 * algorithms
 * Medium (50.06%)
 * Likes:    324
 * Dislikes: 56
 * Total Accepted:    55.4K
 * Total Submissions: 109.8K
 * Testcase Example:  '["abc","bcd","acef","xyz","az","ba","a","z"]'
 *
 * Given a string, we can "shift" each of its letter to its successive letter,
 * for example: "abc" -> "bcd". We can keep "shifting" which forms the
 * sequence:
 * 
 * 
 * "abc" -> "bcd" -> ... -> "xyz"
 * 
 * Given a list of strings which contains only lowercase alphabets, group all
 * strings that belong to the same shifting sequence.
 * 
 * Example:
 * 
 * 
 * Input: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
 * Output: 
 * [
 * ⁠ ["abc","bcd","xyz"],
 * ⁠ ["az","ba"],
 * ⁠ ["acef"],
 * ⁠ ["a","z"]
 * ]
 * 
 * 
 */

// @lc code=start
class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        return sol1(strings);        
    }

    //O(n * m) / O(n * m)
    private List<List<String>> sol1(String[] strings) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strings) {
            String key = normalize(str);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        return new ArrayList<>(map.values());
    }

    private String normalize(String str) {
        if (str.length() <= 0) {
            return "";
        }
        int d = str.charAt(0) - 'a';
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char)(array[i] - d); //H.W.: type conversion
            if (array[i] < 'a') {
                array[i] += 26;
            }
        }
        return new String(array);
    }
}
// @lc code=end
