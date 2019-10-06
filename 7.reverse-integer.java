/*
 * @lc app=leetcode id=7 lang=java
 *
 * [7] Reverse Integer
 *
 * https://leetcode.com/problems/reverse-integer/description/
 *
 * algorithms
 * Easy (25.50%)
 * Total Accepted:    824.1K
 * Total Submissions: 3.2M
 * Testcase Example:  '123'
 *
 * Given a 32-bit signed integer, reverse digits of an integer.
 * 
 * Example 1:
 * 
 * 
 * Input: 123
 * Output: 321
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: -123
 * Output: -321
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: 120
 * Output: 21
 * 
 * 
 * Note:
 * Assume we are dealing with an environment which could only store integers
 * within the 32-bit signed integer range: [−2^31,  2^31 − 1]. For the purpose
 * of this problem, assume that your function returns 0 when the reversed
 * integer overflows.
 * 
 */
class Solution {
    public int reverse(int x) {
        //return sol1(x); 
        //return sol2(x); 
        return sol3(x); 
    }

    private int sol1(int x) {
        long lx = x, res = 0;
        while (lx != 0) {
            long d = lx % 10;
            res = res * 10 + d;
            lx /= 10;
        }
        return res < Integer.MIN_VALUE || res > Integer.MAX_VALUE ? 0 : (int) res;
    }

    private int sol2(int x) {
        int res = 0;
        while (x != 0) {
            int d = x % 10;
            int tmp = res * 10 + d;
            if ((tmp - d) / 10 != res) {
                return 0;
            }
            res = tmp;
            x /= 10; //H.W.: forgot to update one of global variables
        }
        return res;
    }

    private int sol3(int x) {
        final int min1 = Integer.MIN_VALUE / 10, min2 = Integer.MIN_VALUE % 10;
        final int max1 = Integer.MAX_VALUE / 10, max2 = Integer.MAX_VALUE % 10;
        int res = 0;
        while (x != 0) {
            int d = x % 10;
            if (res < min1 || (res == min1 && d < min2) ||
                res > max1 || (res == max1 && d > max2)) {
                return 0;
            }
            res = res * 10 + d;
            x /= 10;
        }
        return res;
    }
}
