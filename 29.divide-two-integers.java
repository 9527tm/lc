/*
 * @lc app=leetcode id=29 lang=java
 *
 * [29] Divide Two Integers
 *
 * https://leetcode.com/problems/divide-two-integers/description/
 *
 * algorithms
 * Medium (16.15%)
 * Total Accepted:    203.5K
 * Total Submissions: 1.3M
 * Testcase Example:  '10\n3'
 *
 * Given two integers dividend and divisor, divide two integers without using
 * multiplication, division and mod operator.
 * 
 * Return the quotient after dividing dividend by divisor.
 * 
 * The integer division should truncate toward zero.
 * 
 * Example 1:
 * 
 * 
 * Input: dividend = 10, divisor = 3
 * Output: 3
 * 
 * Example 2:
 * 
 * 
 * Input: dividend = 7, divisor = -3
 * Output: -2
 * 
 * Note:
 * 
 * 
 * Both dividend and divisor will be 32-bit signed integers.
 * The divisor will never be 0.
 * Assume we are dealing with an environment which could only store integers
 * within the 32-bit signed integer range: [−2^31,  2^31 − 1]. For the purpose
 * of this problem, assume that your function returns 2^31 − 1 when the
 * division result overflows.
 * 
 * 
 */
class Solution {
    public int divide(int dividend, int divisor) {
       return sol1(dividend, divisor); 
    }

    private int sol1(int dividend, int divisor) {
        long dvd = dividend, dvs = divisor;
        long sign = (dvd > 0 && dvs > 0) || (dvd < 0 && dvs < 0) ? 1 : -1;
        dvd = dvd > 0 ? dvd : -dvd;
        dvs = dvs > 0 ? dvs : -dvs;
        
        long exp = dvs, quo = 1;
        while (exp < dvd) {
            exp <<= 1;
            quo <<= 1;
        }

        long res = 0;
        while (dvd >= dvs) {
            if (dvd >= exp) {
                dvd -= exp;
                res += quo;
            }
            exp >>= 1;
            quo >>= 1;
        }

        res = sign > 0 ? res : -res;
        return res >= Integer.MIN_VALUE && res <= Integer.MAX_VALUE ? (int) res : Integer.MAX_VALUE;
    }
}
