/*
 * @lc app=leetcode id=273 lang=java
 *
 * [273] Integer to English Words
 *
 * https://leetcode.com/problems/integer-to-english-words/description/
 *
 * algorithms
 * Hard (25.00%)
 * Total Accepted:    120.9K
 * Total Submissions: 483.2K
 * Testcase Example:  '123'
 *
 * Convert a non-negative integer to its english words representation. Given
 * input is guaranteed to be less than 2^31 - 1.
 * 
 * Example 1:
 * 
 * 
 * Input: 123
 * Output: "One Hundred Twenty Three"
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: 12345
 * Output: "Twelve Thousand Three Hundred Forty Five"
 * 
 * Example 3:
 * 
 * 
 * Input: 1234567
 * Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty
 * Seven"
 * 
 * 
 * Example 4:
 * 
 * 
 * Input: 1234567891
 * Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty
 * Seven Thousand Eight Hundred Ninety One"
 * 
 * 
 */
class Solution {
    public String numberToWords(int num) {
        return sol1(num); 
    }

    private String sol1(int num) {
        if (num <= 0) {
            return "Zero";
        }
        String[] thousands = {"Billion", "Million", "Thousand"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0, base = 1000 * 1000 * 1000; i < thousands.length; i += 1, base /= 1000) {
            if (num >= base) {
                helper1(builder, num / base);
                builder.append(thousands[i]).append(' ');
                num %= base;
            }
        }
        if (num >= 1) {//postprocessing: base = 1
            helper1(builder, num);//H.W. ignore double tailing spaces: 1000 => "1000" + ' ' + "" + ' ' = "1000'  '"
        }                         //Only "Bi.", "Mi.", and "Th." should have a seperating space. Instead, "" don't need.
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    private void helper1(StringBuilder builder, int num) {//1 <= num <= 999
        String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] ones = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", 
                         "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        if (num >= 100) {
            builder.append(ones[num / 100]).append(" Hundred ");
            num %= 100;
        }
        if (num >= 20) {
            builder.append(tens[num / 10]).append(' ');
            num %= 10;
        }
        if (num >= 1) {
            builder.append(ones[num]).append(' ');
        }
    }
}
