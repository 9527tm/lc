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
        //return sol2(words);
        //return sol3(words);
        return sol3a(words);
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

    //https://leetcode.com/problems/palindrome-pairs/discuss/79210/The-Easy-to-unserstand-JAVA-Solution [the complete 4 cases]
    //https://leetcode.com/problems/palindrome-pairs/discuss/79199/150-ms-45-lines-JAVA-solution [sol3 inspired from]
    private List<List<Integer>> sol3(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j <= words[i].length(); j++) {// corner case 1: j == words[i].length()
                String leftSubstr = words[i].substring(0, j); //why? if there is an empty string in words.
                String rightSubstr = words[i].substring(j);   //like ["", "aba"]
                if (isPalindrome(leftSubstr)) {
                    Integer k = map.get(new StringBuilder(rightSubstr).reverse().toString());
                    if (k != null && k != i) {
                        res.add(Arrays.asList(k, i));
                    }
                }
                if (isPalindrome(rightSubstr) && !rightSubstr.isEmpty()) {// corner case 2: rightSubstr is not an empty string
                //if (isPalindrome(rightSubstr)) {//H.W. <= ["ab", "ba"] duplicate results
                    Integer k = map.get(new StringBuilder(leftSubstr).reverse().toString());
                    if (k != null && k != i) {
                        res.add(Arrays.asList(i, k));
                    }
                }
            }
        }
        return res;
    }

    private List<List<Integer>> sol3a(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            //case 1: ["", "aba"]
            Integer k = map.get("");
            if (k != null && k != i && isPalindrome(words[i])) {
                res.add(Arrays.asList(i, k));
                res.add(Arrays.asList(k, i));
            }
            //case 2: ["abc", "cba"]
            k = map.get(new StringBuilder(words[i]).reverse().toString());
            if (k != null && k != i) {
                res.add(Arrays.asList(i, k));
            }
            //case 3: ["abb", "a"]
            //case 4: ["aab", "b"]
            for (int j = 1; j < words[i].length(); j++) {//j is the length of leftSubstr
                String leftSubstr = words[i].substring(0, j);
                String rightSubstr = words[i].substring(j);
                k = map.get(new StringBuilder(rightSubstr).reverse().toString());
                if (k != null && isPalindrome(leftSubstr)) {//xxxx-leftSubstr-rightSubstr => (rightSubstr)'-leftSubstr-rightSubstr
                    res.add(Arrays.asList(k, i));
                }
                k = map.get(new StringBuilder(leftSubstr).reverse().toString());
                if (k != null && isPalindrome(rightSubstr)){//leftSubstr-rightSubstr-xxxx => leftSubstr-rightSubstr-(leftSubstr)'
                    res.add(Arrays.asList(i, k));
                }
            }
        }

        return res;
    }

    private List<List<Integer>> sol3b(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            pairIndex(map, res, i, "", words[i], null); //case 1 ["aba" + ""] 
            pairIndex(map, res, i, null, words[i], ""); //case 1 ["" + "aba"]
            pairIndex(map, res, i, words[i], "", null); //case 2 ["abc" + "cba"]
            for (int j = 1; j < words[i].length(); j++) {//j is the length of words[i]
                String leftSubstr = words[i].substring(0, j);
                String rightSubstr = words[i].substring(j); //neither leftSubstr nor rigthSubstr is empty
                pairIndex(map, res, i, leftSubstr, rightSubstr, null);//case 3
                pairIndex(map, res, i, null, leftSubstr, rightSubstr);//case 4
            }
        }
        return res;
    }

    private void pairIndex(Map<String, Integer> map, List<List<Integer>> res, int i,
                            String leftStr, String middleStr, String rightStr) {
        String endStr = leftStr != null ? leftStr : rightStr;
        Integer k = map.get(new StringBuilder(endStr).reverse().toString());
        if (k != null && k != i && isPalindrome(middleStr)) {
            List<Integer> pair = leftStr != null ? Arrays.asList(i, k) : Arrays.asList(k, i);
            res.add(pair);
        }
    }


    private boolean isPalindrome(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right && str.charAt(left) == str.charAt(right)) {
            left++;
            right--;
        }
        return left >= right;
    }
}
