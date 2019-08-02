/*
 * @lc app=leetcode id=38 lang=java
 *
 * [38] Count and Say
 *
 * https://leetcode.com/problems/count-and-say/description/
 *
 * algorithms
 * Easy (40.74%)
 * Total Accepted:    300.9K
 * Total Submissions: 729.7K
 * Testcase Example:  '1'
 *
 * The count-and-say sequence is the sequence of integers with the first five
 * terms as following:
 * 
 * 
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 
 * 
 * 1 is read off as "one 1" or 11.
 * 11 is read off as "two 1s" or 21.
 * 21 is read off as "one 2, then one 1" or 1211.
 * 
 * Given an integer n where 1 ≤ n ≤ 30, generate the n^th term of the
 * count-and-say sequence.
 * 
 * Note: Each term of the sequence of integers will be represented as a
 * string.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: 1
 * Output: "1"
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: 4
 * Output: "1211"
 * 
 */
class Solution {
    public String countAndSay(int n) {
        //return sol1(n); 
        return sol2(n); 
    }

    private String sol1(int n) {
        String res = "1";
        for (int i = 1; i < n; i++) {
            int count = 0;
            String tmp = "";
            for (int j = 0; j < res.length(); j++) {
                count++;
                if (j >= res.length() - 1 || res.charAt(j) != res.charAt(j + 1)) {
                    tmp += count + "" + res.charAt(j); //H.W.: missing ... + " " + ...
                    count = 0;
                }
            }
            res = tmp;
        }
        return res;
    }

    private String sol2(int n) {
        StringBuilder res = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        res.append(1); //H.W.: missing initialization.
        for (int i = 1; i < n; i++) {
            int count = 0;
            for (int j = 0; j < res.length(); j++) {
                count++;
                if (j >= res.length() - 1 || res.charAt(j) != res.charAt(j + 1)) {
                    tmp.append(count);
                    tmp.append(res.charAt(j));
                    count = 0;
                }
            }
            StringBuilder save = res; //H.W.: wrongly include swap operations into 
            res = tmp;                //      for {
            tmp = save;               //        ...
            tmp.setLength(0);         //      }
        }
        return res.toString();
    }
}
