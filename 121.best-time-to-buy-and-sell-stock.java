/*
 * @lc app=leetcode id=121 lang=java
 *
 * [121] Best Time to Buy and Sell Stock
 *
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
 *
 * algorithms
 * Easy (48.32%)
 * Total Accepted:    603.1K
 * Total Submissions: 1.2M
 * Testcase Example:  '[7,1,5,3,6,4]'
 *
 * Say you have an array for which the i^th element is the price of a given
 * stock on day i.
 * 
 * If you were only permitted to complete at most one transaction (i.e., buy
 * one and sell one share of the stock), design an algorithm to find the
 * maximum profit.
 * 
 * Note that you cannot sell a stock before you buy one.
 * 
 * Example 1:
 * 
 * 
 * Input: [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit
 * = 6-1 = 5.
 * Not 7-1 = 6, as selling price needs to be larger than buying price.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 * 
 * 
 */
class Solution {
    public int maxProfit(int[] prices) {
        //return sol1(prices);  //O(N * N) BF: [min] curr->
        //return sol2(prices);  //O(N) DP: [min] curr->
        return sol3(prices);    //O(N) DP: <-curr [max]
    }

    private int sol1(int[] prices) {
        int res = 0;
        for (int i = 0; i < prices.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                min = Math.min(min, prices[j]);
            }
            res = Math.max(res, prices[i] - min);
        }
        return res;
    }

    private int sol2(int[] prices) {
        int res = 0, min = Integer.MAX_VALUE;
        for (int p : prices) {
            res = Math.max(res, p - min);
            min = Math.min(min, p);
        }
        return res;
    }

    private int sol3(int[] prices) {
        int res = 0, max = 0;
        for (int i = prices.length - 1; i >= 0; i--) {
            res = Math.max(res, max - prices[i]);
            max = Math.max(max, prices[i]);
        }
        return res;
    }
}
