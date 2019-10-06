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
        //return sol2a(strs); 
        //return sol3(strs); 
        return sol4(strs); 
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

    //leetcode.com/problems/group-anagrams/discuss/19176/Share-my-short-JAVA-solution/193036
    static class CharCounter {
        private final int[] array;//https://stackoverflow.com/questions/3737769/java-unmodifiable-array
        private final int hash;   //unmodifiable list is better than final array here.

        CharCounter(String str) {
            array = new int['z' - 'a' + 1];
            for (int i = 0; i < str.length(); i++) {
                array[str.charAt(i) - 'a']++;
            }
            hash = Arrays.hashCode(array);
        }

        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (other == this) {
                return true;
            }
            CharCounter oth = (CharCounter) other;
            return (hash == oth.hash) && Arrays.equals(this.array, oth.array);
        }

        public int hashCode() {
            return hash;
        }
    }

    private List<List<String>> sol3(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<CharCounter, List<String>> map = new HashMap<>();
        for (String str : strs) {
            CharCounter counter = new CharCounter(str);
            List<String> list = map.get(counter);
            if (list == null) {
                list = new ArrayList<>();
                map.put(counter, list);
                res.add(list);
            }
            list.add(str);
        }
        return res;
    }

    //https://leetcode.com/problems/group-anagrams/discuss/19183/Java-beat-100!!!-use-prime-number
    private long hashCode(String str) {//safe length of string will be up to Long.MAX_VALUE / 101 / 26
        long[] primes = new long[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
               31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
        long hash = 1L;
        for (int i = 0; i < str.length(); i++) {
            hash *= primes[str.charAt(i) - 'a'];
        }
        return hash;
    }

    private List<List<String>> sol4(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<Long, List<String>> map = new HashMap<>();
        for (String str : strs) {
            Long key = hashCode(str);
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
