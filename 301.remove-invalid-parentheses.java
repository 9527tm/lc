/*
 * @lc app=leetcode id=301 lang=java
 *
 * [301] Remove Invalid Parentheses
 *
 * https://leetcode.com/problems/remove-invalid-parentheses/description/
 *
 * algorithms
 * Hard (40.35%)
 * Total Accepted:    146.5K
 * Total Submissions: 363K
 * Testcase Example:  '"()())()"'
 *
 * Remove the minimum number of invalid parentheses in order to make the input
 * string valid. Return all possible results.
 * 
 * Note: The input string may contain letters other than the parentheses ( and
 * ).
 * 
 * Example 1:
 * 
 * 
 * Input: "()())()"
 * Output: ["()()()", "(())()"]
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: "(a)())()"
 * Output: ["(a)()()", "(a())()"]
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: ")("
 * Output: [""]
 * 
 */
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        //return sol1(s);        
        return sol2(s);        
    }

    private List<String> sol1(String s) {
        int[] counters = findValidPairsOfParentheses(s); 
        int parentNum = counters[0], otherCharNum = counters[1];
        List<String> res = new ArrayList<>();
        sol1(s, 0, parentNum, parentNum, otherCharNum, new char[parentNum * 2 + otherCharNum], res);
        return res;
    }

    private int[] findValidPairsOfParentheses(String s) {
        int left = 0, right = 0, other = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            }
            else if (s.charAt(i) == ')') {
                if (right < left) {
                    right++;
                }
            }
            else {
                other++;
            }
        }
        return new int[]{right, other};
    }

    //there are two deduplicating techniques for all subsets II. (LC90 - https://leetcode.com/submissions/detail/264188530/)
    //the better one restricts left and right nums by skipping non-adding parentheses (right branches in the recursion tree).
    private void sol1(String s, int i, int left, int right, int other, char[] tmp, List<String> res) {
        if (i >= s.length()) {
            if (left + right + other == 0) {
                res.add(new String(tmp));
            }
            return;
        }

        char ch = s.charAt(i);
        int pos = tmp.length - (left + right + other);
        if (ch != '(' && ch != ')') {
            tmp[pos] = ch; //H.W.: forgot the index definition => tmp[left + right + other]
            sol1(s, i + 1, left, right, other - 1, tmp, res);
        }
        else {
            if (ch == '(') {
                if (left > 0) {
                    tmp[pos] = ch; 
                    sol1(s, i + 1, left - 1, right, other, tmp, res);
                }
            }
            else if (ch == ')') {
                if (right > left) {
                    tmp[pos] = ch;
                    sol1(s, i + 1, left, right - 1, other, tmp, res);
                }
            }

            int j = i + 1;
            while (j < s.length() && s.charAt(j) == ch) {
                j++;
            }
            sol1(s, j, left, right, other, tmp, res);
        }
    }

    //there are two deduplicating techniques for all subsets II. (LC90 - https://leetcode.com/submissions/detail/264188530/)
    //https://leetcode.com/submissions/detail/264367225/  (sol2() is NOT a good example since it skips non-adding for invalid left and right)
    //the better one restricts invalid left and right nums by skipping adding parentheses (left branches in the recursion tree).
    private List<String> sol2(String s) {
        int[] delNums = findInvalidParenthesesNums(s);
        List<String> res = new ArrayList<>();
        sol2(s, 0, 0, 0, delNums[0], delNums[1], new StringBuilder(), res);
        return res;
    }
    
    private int[] findInvalidParenthesesNums(String s) {
        int left = 0, right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            }
            else if (s.charAt(i) == ')') {
                if (left > 0) {//H.W.: if (left > right)
                    left--;
                }
                else {
                    right++;
                }
            }
        }
        return new int[]{left, right};
    }

    private int[] findInvalidParenthesesNums0__EAZY_READ(String s) {
        int left = 0, right = 0, matched = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            }
            else if (s.charAt(i) == ')') {
                right++;
                if (left > matched) {
                    matched++;
                }
            }
        }
        return new int[]{left - matched, right - matched};
    }

    private void sol2(String s, int i, int left, int right, int delLeft, int delRight,
                        StringBuilder builder, List<String> res) {
        if (i == s.length()) {
            if (delLeft == 0 && delRight == 0) {// =E> left == right
                res.add(builder.toString());
            }
            return;
        }

        char ch = s.charAt(i);
        if (ch == '(' && delLeft > 0) {
            sol2(s, i + 1, left, right, delLeft - 1, delRight, builder, res);
        }
        if (ch == ')' && delRight > 0) {
            sol2(s, i + 1, left, right, delLeft, delRight - 1, builder, res);
        }

        int len = builder.length();
        while (i < s.length() && s.charAt(i) == ch) {//H.W.: https://leetcode.com/submissions/detail/264394583/
            builder.append(ch);
            left = (ch == '(') ? left + 1 : left;
            right = (ch == ')') ? right + 1 : right;
            i++;
        }
        if (left <= s.length() / 2 && right <= left) {
            sol2(s, i, left, right, delLeft, delRight, builder, res);
        }
        builder.setLength(len);
    }
}
