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
    // Compute quotient: dvd = q0 * 1 * dvs + q1 * 2 * dvs + q2 * 4 * dvs + q3 * 8 * dvs + ...
    //              <=>  dvd = dvs * (q0 * 1 + q1 * 2 + q2 * 4 + q3 * 8 + ...)
    //                         qi = 0, 1
    public int divide(int dividend, int divisor) {
       //return sol1(dividend, divisor);   //5.5: long int64 extending
       //return sol1a(dividend, divisor);  //6.0: one for() loop only: sol1 => sol1a
       //return sol2(dividend, divisor);   //4.5: a little more verbose and readable than sol2b
       //return sol2a(dividend, divisor);  //4.0: alpha version with H.W.
       //return sol2b(dividend, divisor);  //5.0: int32 restriction with comments
       return sol2c(dividend, divisor);  //5.0: different overflow criteria: sol2b => sol2c
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

    //www.geeksforgeeks.org/divide-two-integers-without-using-multiplication-division-mod-operator/
    private int sol1a(int dividend, int divisor) {
        long dvd = dividend, dvs = divisor; //H.W.: wrongly use -dividend: "long dvd = dividend > 0 ? dividend : -dividend;"
        int sig = (dvd > 0 && dvs > 0) || (dvd < 0 && dvs < 0) ? 1 : -1;
        dvd = dvd > 0 ? dvd : -dvd; //Warning: -dvd is OK; -dividend is KO!
        dvs = dvs > 0 ? dvs : -dvs;

        long res = 0;
        for (int i = 31; i >= 0; i--) {//Optimization: i >= 0 && dvd >= dvs
            long exp = dvs << i;
            long quo = 1L << i;  //H.W.: wrongly use 1 << 32: "long quo = 1 << i;"
            if (dvd >= exp) {    //Warning: 1L << 32 is OK; 1 << 32 is KO!
                dvd -= exp;
                res += quo;
            }
        }

        res = sig > 0 ? res : -res;
        return res >= Integer.MIN_VALUE && res <= Integer.MAX_VALUE ? (int)res : Integer.MAX_VALUE;
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
        int sig = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0) ? 1 : -1;

        int exp = dvs, quo = 1;
        int lmt = Integer.MAX_VALUE >> 1;
        
        lmt = dvd < lmt ? dvd : lmt; 
        while (exp <= lmt) {
            exp <<= 1;
            quo <<=1;
        }

        int res = 0;
        while (dvd >= dvs) {//H.W.: dvd > 0 and H.W.: dvd > dvs
            if (dvd >= exp) {
                dvd -= exp;
                res += quo;
            }
            exp >>= 1;
            quo >>= 1;
        }

        return sig > 0 ? res : -res;
    }

    
    private int sol2b(int dividend, int divisor) {
        //0. Preprocess for Integer.MIN_VALUE that causes overflow 
        if (divisor == Integer.MIN_VALUE) {//when x = -Integer.MIN_VALUE or x = Integer.MIN_VALUE / -1;
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
        //1. Preprocess for absolutions
        int dvd = dividend > 0 ? dividend : -dividend;
        int dvs = divisor > 0 ? divisor : -divisor;
        int sig = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0) ? 1 : -1;
        
        //2. Compute exponential table: dvs * 1, dvs * 2, dvs * 4, dvs * 8, ... 
        int exp = dvs, quo = 1;
        while (exp <= (Integer.MAX_VALUE >> 1)) {//Optimization: while (exp <= dvd && exp <= (Integer.MAX_VALUE >> 1)) {...}
            exp <<= 1;
            quo <<= 1;
        }

        //3. Compute quotient: dvd = q0 * 1 * dvs + q1 * 2 * dvs + q2 * 4 * dvs + q3 * 8 * dvs + ...
        //              <=>  dvd = dvs * (q0 * 1 + q1 * 2 + q2 * 4 + q3 * 8 + ...)
        //                         qi = 0, 1
        int res = 0;
        while (exp >= dvs) {//Optimization: while (exp >= dvs && dvd >= dvs) {...}
            if (dvd >= exp) {
                dvd -= exp;
                res += quo;
            }
            exp >>=1;
            quo >>= 1;
        }

        //4. Postprocess for sign of result 
        return sig > 0 ? res : -res;
    }

    private int sol2c(int dividend, int divisor) {
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        if (dividend == Integer.MIN_VALUE) {
            if (divisor > 2 || divisor < -2) {
                dividend = Integer.MIN_VALUE + 1;
            }
            else if (divisor == 2 || divisor == -2) {
                dividend >>= 1;
                divisor >>= 1;
            }
            else {
                return divisor == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
        }

        int sig = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0) ? 1 : -1;
        int dvd = dividend > 0 ? dividend : -dividend;
        int dvs = divisor > 0 ? divisor : -divisor;
        return sig > 0 ? divideAbs(dvd, dvs) : -divideAbs(dvd, dvs);
    }
    private int divideAbs(int dvd, int dvs) {
        int n = 0;
        for (int i = 1; (dvs << i) > 0; i++) {//dvs left shift by 1 each step =>
            n = i;                            //dvs changes to negative when overflow happens.
        }
        
        int res = 0;
        for (int i = n; i >= 0; i--) {
            if (dvd >= (dvs << i)) {
                dvd -= (dvs << i);
                res += (1 << i);
            }
        }
        return res;
    }    
}
