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

    private List<String> sol2(String s) {
        int[] invalidNums = findInvalidParenthesesNums(s);
        //System.out.println(invalidNums[0] + " " + invalidNums[1]);
        List<String> res = new ArrayList<>();
        sol2(s, 0, 0, 0, invalidNums[0], invalidNums[1], new StringBuilder(), res);
        return res;
    }
    private int[] findInvalidParenthesesNums(String s) {
        int left = 0, right = 0, matched = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            left = (ch == '(') ? left + 1 : left;
            right = (ch == ')') ? right + 1 : right;
            matched = (ch == ')' && left > matched) ? matched + 1 : matched; //H.W.: (ch == ')' && left >= right)
        }
        return new int[]{left - matched, right - matched};
    }
    private void sol2(String s, int i, int left, int right, int invalidLeft, int invalidRight, 
                    StringBuilder builder, List<String> res) {
        if (i == s.length()) {
            if (invalidLeft == 0 &&  invalidRight == 0) {
                res.add(builder.toString());
            }
            return;
        }
        char ch = s.charAt(i);
        int newLeft = (ch == '(') ? left + 1 : left;
        int newRight = (ch == ')') ? right + 1 : right;
        if (newLeft <= s.length() / 2 && newRight <= newLeft) {
            builder.append(ch);
            sol2(s, i + 1, newLeft, newRight, invalidLeft, invalidRight, builder, res);
            builder.setLength(builder.length() - 1);
        }
        if (ch == '(' || ch == ')') {
            int invalidNum = (ch == '(') ? invalidLeft : invalidRight;
            int j = i;
            //while ((j < s.length()) && (s.charAt(j) == ch) && (invalidNum > 0)) {
            while ((j < s.length()) && (s.charAt(j) == ch)) {
                invalidNum--;
                j++;
            }
            //if (j > i) {
                int newInvalidLeft = (ch == '(') ? invalidNum : invalidLeft;
                int newInvalidRight = (ch == ')') ? invalidNum : invalidRight;
                sol2(s, j, left, right, newInvalidLeft, newInvalidRight, builder, res);
            //}
        }
    }
}
