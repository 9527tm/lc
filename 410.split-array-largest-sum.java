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
        //return sol1(nums, m); //6.0: binary search       
        //return sol2(nums, m); //5.5: DP       
        return sol2a(nums, m);  //5.5  DP
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


    /*
     * dp[i, j]: the minimum sum of i subarrays which are cut from the first j elements of input array -- nums.
                 0 < i <= m, i <= j <= len(nums)
       dp[1, j] = sum of first j elements (NO CUT AT ALL)
       dp[i, j] = min{max(dp[i - 1, k], sum(nums[k],...nums[j]))}, i - 1 <= k <= j - 1, i >= 2 (ONE CUT AT LEAST)

                  Why is k in [i - 1, j - 1]?
                  -- because the k-th cut happens when both dp[i - 1, k] and nums[k] are feasible.
                  1. k-1_th cut needs i - 1 elements at least.
                  2. k_th cut should make first j elements into two valid parts: 
                     part 1: nums[0, k - 1] and part 2: nums[k, j - 1] 
                     obviously, each one parts may have 1 element at least.
                     that's why we use cutting (transfom formular) when i >= 2.
                  
                  in this way, our algorithm can correctly process an input array of 
                               any elements no matter positive or negative.

       https://leetcode.com/problems/split-array-largest-sum/solution/
    */
    //DP: O(n * n * m) / (n * m)
    private int sol2(int[] nums, int m) {
        int n = nums.length;
        long[] sum = new long[n + 1];//H.W.: '[1, 2147483647]\n2'
        for (int j = 1; j <= n; j++) {
            sum[j] = sum[j - 1] + nums[j - 1];
        }

        long[][] dp = new long[m + 1][n + 1]; 
        for (int j = 1; j <= n; j ++) {
            dp[1][j] = sum[j];
        }

        for (int i = 2; i <= m; i++) {
            for (int j = i; j <= n; j++) {        
                dp[i][j] = Long.MAX_VALUE;        
                for (int k = i - 1; k < j; k++) {//i - 1 <= k <= j - 1: two valid parts from one cut
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[i - 1][k], sum[j] - sum[k]));
                }
            }
        }

        return (int)dp[m][n];
    }


    //DP: O(n * n * m) / O(n)
    private int sol2a(int[] nums, int m) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        for (int j = 1; j <= n; j++) {
            sum[j] = sum[j - 1] + nums[j - 1];
        }

        long[] dp = new long[n + 1];
        for (int j = 1; j <= n; j++) {//i = 1
            dp[j] = sum[j];
        }

        for (int i = 2; i <= m; i++) {
            for (int j = n; j >= i; j--) {//TRICKY: update backward since dp[j] <= dp[k], k <= j
                dp[j] = Long.MAX_VALUE;//TRICKY: don't have to use a temp value for dp[j]
                for (int k = i - 1; k < j; k++) {//since we have made clear k < j (two parts from one valid cut)
                    dp[j] = Math.min(dp[j], Math.max(dp[k], sum[j] - sum[k]));
                }
            }
        }
        return (int)dp[n];
    }
}
// @lc code=end
