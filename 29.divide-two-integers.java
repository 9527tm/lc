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
       //return sol1(dividend, divisor); 
       return sol2(dividend, divisor); 
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

    private int sol2(int dividend, int divisor) {
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            }
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
            if (divisor == 2) {
                return Integer.MIN_VALUE >> 1;
            }
            if (divisor == -2) {
                return -(Integer.MIN_VALUE >> 1);
            }
            dividend = Integer.MIN_VALUE + 1;
        }
        int dvd = dividend > 0 ? dividend : -dividend;
        int dvs = divisor > 0 ? divisor : -divisor;
        int sign = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0) ? 1 : -1; //H.W.: missing the sign processing
        
        int exp = dvs, quo = 1;
        while (exp < dvd && exp <= (Integer.MAX_VALUE >> 1)) {//H.W.: exp overflow. ex: MAX_VALUE, 1
            exp <<= 1;                                        //H.W.: wrong coverage (...&&exp < MAX_VALUE >> 1). 
            quo <<= 1;                                        //      ex: 2147483646, 1073741823
        }
        int res = 0;
        while (dvd >= dvs) {
            if (dvd >= exp) {
                dvd -= exp;
                res += quo;
            }
            exp >>= 1;
            quo >>= 1;
        }
        return sign > 0 ? res : -res;
    }
    
    private int sol2a(int dividend, int divisor) {
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        if (dividend == Integer.MIN_VALUE) {
            if (divisor < -2 || divisor > 2) {
                dividend = Integer.MIN_VALUE + 1;
            }
            else if (divisor == -2 || divisor == 2) {
                dividend >>= 1;
                divisor >>= 1;
            }
            else {
                return divisor == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
        }
        
        int dvd = dividend > 0 ? dividend : -dividend;
        int dvs = divisor > 0 ? divisor : -divisor;
        int sign = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0) ? 1 : -1;

        int exp = dvs, quo = 1;
        int lmt = Integer.MAX_VALUE >> 1;
        
        lmt = dvd < lmt ? dvd : lmt; 
        while (exp <= lmt) {
            exp <<= 1;
            quo <<=1;
        }

        int res = 0;
        while (dvd > 0) {
            if (dvd >= exp) {
                dvd -= exp;
                res += quo;
            }
            exp >>= 1;
            quo >>= 1;
        }

        return res;
    }
}
