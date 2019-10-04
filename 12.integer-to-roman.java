/*
 * @lc app=leetcode id=12 lang=java
 *
 * [12] Integer to Roman
 *
 * https://leetcode.com/problems/integer-to-roman/description/
 *
 * algorithms
 * Medium (52.24%)
 * Total Accepted:    269K
 * Total Submissions: 514.6K
 * Testcase Example:  '3'
 *
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D
 * and M.
 * 
 * 
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 
 * For example, two is written as II in Roman numeral, just two one's added
 * together. Twelve is written as, XII, which is simply X + II. The number
 * twenty seven is written as XXVII, which is XX + V + II.
 * 
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is
 * written as IV. Because the one is before the five we subtract it making
 * four. The same principle applies to the number nine, which is written as IX.
 * There are six instances where subtraction is used:
 * 
 * 
 * I can be placed before V (5) and X (10) to make 4 and 9. 
 * X can be placed before L (50) and C (100) to make 40 and 90. 
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * 
 * 
 * Given an integer, convert it to a roman numeral. Input is guaranteed to be
 * within the range from 1 to 3999.
 * 
 * Example 1:
 * 
 * 
 * Input: 3
 * Output: "III"
 * 
 * Example 2:
 * 
 * 
 * Input: 4
 * Output: "IV"
 * 
 * Example 3:
 * 
 * 
 * Input: 9
 * Output: "IX"
 * 
 * Example 4:
 * 
 * 
 * Input: 58
 * Output: "LVIII"
 * Explanation: L = 50, V = 5, III = 3.
 * 
 * 
 * Example 5:
 * 
 * 
 * Input: 1994
 * Output: "MCMXCIV"
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 * 
 */
class Solution {
    public String intToRoman(int num) {
        return sol1(num); 
    }

    private String sol1(int num) {
        int[] weights = {3000, 2000, 1000, 
                         900, 800, 700, 600, 500, 400, 300, 200, 100, 
                         90, 80, 70, 60, 50, 40, 30, 20, 10, 
                         9, 8, 7, 6, 5, 4, 3, 2, 1};
        String[] scales = {"MMM", "MM", "M", 
                         "CM", "DCCC", "DCC", "DC", "D", "CD", "CCC", "CC", "C", 
                         "XC", "LXXX", "LXX", "LX", "L", "XL", "XXX", "XX", "X",
                         "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < weights.length; i++) {
            if (num >= weights[i]) {
                builder.append(scales[i]);
                num -= weights[i];
            }
        }
        return builder.toString();
    }
}
