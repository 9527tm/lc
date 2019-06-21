/*
 * @lc app=leetcode id=17 lang=java
 *
 * [17] Letter Combinations of a Phone Number
 *
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/description/
 *
 * algorithms
 * Medium (41.84%)
 * Total Accepted:    399.6K
 * Total Submissions: 955K
 * Testcase Example:  '"23"'
 *
 * Given a string containing digits from 2-9 inclusive, return all possible
 * letter combinations that the number could represent.
 * 
 * A mapping of digit to letters (just like on the telephone buttons) is given
 * below. Note that 1 does not map to any letters.
 * 
 * 
 * 
 * Example:
 * 
 * 
 * Input: "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * 
 * 
 * Note:
 * 
 * Although the above answer is in lexicographical order, your answer could be
 * in any order you want.
 * 
 */
class Solution {
    public List<String> letterCombinations(String digits) {
        return sol1(digits);
    }

    private List<String> sol1(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.length() <= 0) {//H.W.: missing corner case <= to be confirmed with interviewer.
            return res;
        }
        String[] phonePad = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        sol1(digits, phonePad, 0, new char[digits.length()], res);
        return res;
    }
    private void sol1(String digits, String[] phonePad, int i, char[] tmp, List<String> res) {
        if (i >= digits.length()) {
            res.add(new String(tmp));
            return;
        }
        int d = digits.charAt(i) - '0';
        for (int j = 0; j < phonePad[d].length(); j++) {
            tmp[i] = phonePad[d].charAt(j);
            sol1(digits, phonePad, i + 1, tmp, res);
        }
    }
}
