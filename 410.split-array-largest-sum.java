/*
 * @lc app=leetcode id=410 lang=java
 *
 * [410] Split Array Largest Sum
 *
 * https://leetcode.com/problems/split-array-largest-sum/description/
 *
 * algorithms
 * Hard (42.93%)
 * Likes:    1065
 * Dislikes: 58
 * Total Accepted:    59.5K
 * Total Submissions: 138K
 * Testcase Example:  '[7,2,5,10,8]\n2'
 *
 * Given an array which consists of non-negative integers and an integer m, you
 * can split the array into m non-empty continuous subarrays. Write an
 * algorithm to minimize the largest sum among these m subarrays.
 * 
 * 
 * Note:
 * If n is the length of array, assume the following constraints are
 * satisfied:
 * 
 * 1 ≤ n ≤ 1000
 * 1 ≤ m ≤ min(50, n)
 * 
 * 
 * 
 * Examples: 
 * 
 * Input:
 * nums = [7,2,5,10,8]
 * m = 2
 * 
 * Output:
 * 18
 * 
 * Explanation:
 * There are four ways to split nums into two subarrays.
 * The best way is to split it into [7,2,5] and [10,8],
 * where the largest sum among the two subarrays is only 18.
 * 
 * 
 */

// @lc code=start
class Solution {
    public int splitArray(int[] nums, int m) {
        return sol1(nums, m);        
    }

    //O(n * lgs) / O(1) 
    //n -- len(nums), s -- sum(nums)
    /*binary search: find the first occurrence 
      -- the smallest cap to split nums into m subarrays 
         so that the sum of each subarray is no larger than "the cap"
         and "that cap" actually equals to the sum of largest subarray.
    */
    //https://leetcode.com/problems/split-array-largest-sum/discuss/89820
    //https://leetcode.com/problems/split-array-largest-sum/discuss/89819
    private int sol1(int[] nums, int m) {
        int left = 0, right = Integer.MAX_VALUE;
        //int left = max(nums), right = sum(nums);
        while (left < right) {
            int mid = left + (right - left) / 2;
            int k = countSubarrays(nums, mid); //mid is actually the cap we're trying 
            if (k < m) {//the cap is too large so that too few subarrays we got 
                right = mid; //mid - 1; //left < right => search range is at least 2
            }
            else if (k > m) {//the cap is too small so that too many subarrays we got
                left = mid + 1;
            }
            else {//the cap is satisfying but there may be mulitple candidates
                right = mid;//we want the smallest one (first occurrence)
            }
        }
        return left;
    }

    private int countSubarrays(int[] nums, int cap) {
        int k = 1, subSum = 0; //H.W.: k is WRONGLY initialized to 0.
        for (int n : nums) {
            if (n > cap) {//H.W.: forgot to avoid small caps. ex: [7, 2, 5, 10 ,8], 7
                return Integer.MAX_VALUE;//if we don't break here and indicate too many subarrays 
            }                            //we will get k = 5 wrongly.
            if (subSum + n <= cap) {
                subSum += n;
            }
            else {
                k++;
                subSum = n;
            }
        }
        return k;
    }

    private int max(int[] nums) {
        int max = 0;
        for (int n : nums) {
            max = Math.max(max, n);
        }
        return max;
    }

    private int sum(int[] nums) {
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        return sum;
    }
}
// @lc code=end
