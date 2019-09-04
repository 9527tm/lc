/*
 * @lc app=leetcode id=819 lang=java
 *
 * [819] Most Common Word
 *
 * https://leetcode.com/problems/most-common-word/description/
 *
 * algorithms
 * Easy (42.39%)
 * Total Accepted:    73.2K
 * Total Submissions: 172K
 * Testcase Example:  '"Bob hit a ball, the hit BALL flew far after it was hit."\n["hit"]'
 *
 * Given a paragraph and a list of banned words, return the most frequent word
 * that is not in the list of banned words.  It is guaranteed there is at least
 * one word that isn't banned, and that the answer is unique.
 * 
 * Words in the list of banned words are given in lowercase, and free of
 * punctuation.  Words in the paragraph are not case sensitive.  The answer is
 * in lowercase.
 * 
 * 
 * 
 * Example:
 * 
 * 
 * Input: 
 * paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
 * banned = ["hit"]
 * Output: "ball"
 * Explanation: 
 * "hit" occurs 3 times, but it is a banned word.
 * "ball" occurs twice (and no other word does), so it is the most frequent
 * non-banned word in the paragraph. 
 * Note that words in the paragraph are not case sensitive,
 * that punctuation is ignored (even if adjacent to words, such as "ball,"), 
 * and that "hit" isn't the answer even though it occurs more because it is
 * banned.
 * 
 * 
 * 
 * 
 * Note: 
 * 
 * 
 * 1 <= paragraph.length <= 1000.
 * 0 <= banned.length <= 100.
 * 1 <= banned[i].length <= 10.
 * The answer is unique, and written in lowercase (even if its occurrences in
 * paragraph may have uppercase symbols, and even if it is a proper noun.)
 * paragraph only consists of letters, spaces, or the punctuation symbols
 * !?',;.
 * There are no hyphens or hyphenated words.
 * Words only consist of letters, never apostrophes or other punctuation
 * symbols.
 * 
 * 
 */
class Solution {
    public String mostCommonWord(String paragraph, String[] banned) {
        //return sol1(paragraph, banned);      
        return sol2(paragraph, banned);      
    }

    private String sol1(String paragraph, String[] banned) {
        Set<String> set = new HashSet<>();
        for (String str : banned) {
            set.add(str);
        }
        Map<String, Integer> map = new HashMap<>();

        String resStr = null;
        int resCount = 0;
        int i = 0, j = 0;
        while (i < paragraph.length()) {
            i = j;
            while (i < paragraph.length() && !Character.isLetter(paragraph.charAt(i))) {
                i++;
            }//i stops at a letter
            j = i;
            while (j < paragraph.length() && Character.isLetter(paragraph.charAt(j))) {
                j++;
            }//j stops at a non-letter
            if (i < paragraph.length()) {//extract the substring from i to j
                String substr = paragraph.substring(i, j).toLowerCase();
                if (!set.contains(substr)) {
                    int count = map.getOrDefault(substr, 0) + 1;
                    map.put(substr, count);
                    if (count > resCount) {
                        resCount = count;
                        resStr = substr;
                    }
                }
            }
        }
        return resStr;
    }

    private String sol2(String paragraph, String[] banned) {
        Set<String> set = new HashSet<>();
        for (String str : banned) {
            set.add(str);
        }

        String resStr = null;
        int resCount = 0;
        Map<String, Integer> map = new HashMap<>();
        String[] words = paragraph.toLowerCase().split("[ !?',;.]+");
        for (String word : words) {
            if (!set.contains(word)) {
                int count = map.getOrDefault(word, 0) + 1;
                map.put(word, count);
                if (resCount < count) {
                    resCount = count;
                    resStr = word;
                }
            }
        }

        return resStr;
    }
}
