/*
 * @lc app=leetcode id=1014 lang=java
 *
 * [1014] Best Sightseeing Pair
 *
 * https://leetcode.com/problems/best-sightseeing-pair/description/
 *
 * algorithms
 * Medium (50.31%)
 * Total Accepted:    9.7K
 * Total Submissions: 19.2K
 * Testcase Example:  '[8,1,5,2,6]'
 *
 * Given an array A of positive integers, A[i] represents the value of the i-th
 * sightseeing spot, and two sightseeing spots i and j have distance j - i
 * between them.
 * 
 * The score of a pair (i < j) of sightseeing spots is (A[i] + A[j] + i - j) :
 * the sum of the values of the sightseeing spots, minus the distance between
 * them.
 * 
 * Return the maximum score of a pair of sightseeing spots.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: [8,1,5,2,6]
 * Output: 11
 * Explanation: i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * 2 <= A.length <= 50000
 * 1 <= A[i] <= 1000
 * 
 */
class Solution {
    public int maxScoreSightseeingPair(int[] A) {
        return sol1(A);       
    }

    /* i < j:
       f(i, j) = A[i] + A[j] + i - j
               = (A[i] + i) + (A[j] - j)

  max{f(i, j)} = max{(A[i] + i) + (A[j] - j)}
              <= max{(A[i] + i)} + max{(A[j] - j)}
     *
     */
    private int sol0_NOT_WORK(int[] A) {//suitable for no restriction of i < j
        int[] max1 = {Integer.MIN_VALUE, Integer.MIN_VALUE};
        int[] max2 = {Integer.MIN_VALUE, Integer.MIN_VALUE};
        int index11 = -1, index21 = -1;

        for (int i = 0; i < A.length; i++) {
            int sum1 = A[i] + i;
            if (sum1 > max1[1]) {
                max1[0] = max1[1];
                max1[1] = sum1;
                index11 = i;
            }
            else if (sum1 > max1[0]) {
                max1[0] = sum1;
            }
            
            int sum2 = A[i] - i;
            if (sum2 > max2[1]) {
                max2[0] = max2[1];
                max2[1] = sum2;
                index21 = i;
            }
            else if (sum2 > max2[0]) {
                max2[0] = sum2;
            }
        }

        return (index11 != index21) ? max1[1] + max2[1] : 
                Math.max(max1[1] + max2[0], max1[0] + max2[1]);
    }

    private int sol1(int[] A) {
        int[] leftMax = new int[A.length]; //leftMax[i] = max{A[j] + j} (0 <= j <= i)
        leftMax[0] = A[0] + 0;
        for (int i = 1; i < A.length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], A[i] + i);
        }

        int rightMax = A[A.length - 1] - (A.length - 1);
        int res = rightMax + leftMax[A.length - 2];
        for (int i = A.length - 2; i >= 1; i--) {
            rightMax = Math.max(rightMax, A[i] - i);
            res = Math.max(res, rightMax + leftMax[i - 1]);
        }
        
        return res;
    }
}
