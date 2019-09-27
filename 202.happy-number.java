/*
 * @lc app=leetcode id=202 lang=java
 *
 * [202] Happy Number
 *
 * https://leetcode.com/problems/happy-number/description/
 *
 * algorithms
 * Easy (46.65%)
 * Total Accepted:    269.4K
 * Total Submissions: 576.8K
 * Testcase Example:  '19'
 *
 * Write an algorithm to determine if a number is "happy".
 * 
 * A happy number is a number defined by the following process: Starting with
 * any positive integer, replace the number by the sum of the squares of its
 * digits, and repeat the process until the number equals 1 (where it will
 * stay), or it loops endlessly in a cycle which does not include 1. Those
 * numbers for which this process ends in 1 are happy numbers.
 * 
 * Example: 
 * 
 * 
 * Input: 19
 * Output: true
 * Explanation: 
 * 12 + 92 = 82
 * 82 + 22 = 68
 * 62 + 82 = 100
 * 12 + 02 + 02 = 1
 * 
 */
class Solution {
    public boolean isHappy(int n) {
        //return sol1(n); 
        //return sol2(n); 
        return sol3(n); 
    }

    private boolean sol1(int n) {
        Set<Integer> set = new HashSet<>();
        while (n != 1) {
            if (!set.add(n)) {
                return false;
            }
            n = sumDigitSquare(n);
        }
        return true;
    }

    private int sumDigitSquare(int n) {
        int s = 0;
        while (n != 0) {
            int d = n % 10;
            s += d * d;
            n /= 10;
        }
        return s;
    }

    private boolean sol2(int n) {
        int fast = n, slow = n;
        while (fast != 1 && slow != 1) {
            fast = sumDigitSquare(fast);
            fast = sumDigitSquare(fast);
            slow = sumDigitSquare(slow);
            if (fast == slow) {
                break;
            }
        }
        return fast == 1 || slow == 1;
    }

    //Time Complexity: O(243)
    //https://leetcode.com/problems/happy-number/discuss/57111/No-hashset-O(1)-time-check-whether-is-in-the-loop-with-mathematical-proof
    /*   n <= 2,147,483,647 < 9,999,999,999
      => f(n) < f(9,999,999,999) < (9 * 9) * 10 = 810
      => f(810) < f(999) = 243
     */
    private boolean sol3(int n) {
        boolean[] visited = new boolean[243];
        n = sumDigitSquare(sumDigitSquare(n));
        while (n != 1 && !visited[n]) {
            visited[n] = true;
            n = sumDigitSquare(n);
        }
        return n == 1;
    }
}
