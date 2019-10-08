/*
 * @lc app=leetcode id=39 lang=java
 *
 * [39] Combination Sum
 *
 * https://leetcode.com/problems/combination-sum/description/
 *
 * algorithms
 * Medium (50.96%)
 * Total Accepted:    404.4K
 * Total Submissions: 791.4K
 * Testcase Example:  '[2,3,6,7]\n7'
 *
 * Given a set of candidate numbers (candidates) (without duplicates) and a
 * target number (target), find all unique combinations in candidates where the
 * candidate numbers sums to target.
 * 
 * The same repeated number may be chosen from candidates unlimited number of
 * times.
 * 
 * Note:
 * 
 * 
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: candidates = [2,3,6,7], target = 7,
 * A solution set is:
 * [
 * ⁠ [7],
 * ⁠ [2,2,3]
 * ]
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: candidates = [2,3,5], target = 8,
 * A solution set is:
 * [
 * [2,2,2,2],
 * [2,3,3],
 * [3,5]
 * ]
 * 
 * 
 */
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        //return sol1(candidates, target);       
        return sol2(candidates, target);       
    }

    private List<List<Integer>> sol1(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        sol1(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void sol1(int[] candidates, int target, int i, List<Integer> list, List<List<Integer>> res) {
        if (i >= candidates.length || target <= 0) {
            if (target == 0) {
                res.add(new ArrayList<>(list));
            }
            return;
        }
        for (int j = 0; j * candidates[i] <= target; j++) {//H.W.: j starts 1 indicating candidates[i] must be chosen.
            if (j > 0) {
                list.add(candidates[i]);
            }
            sol1(candidates, target - j * candidates[i], i + 1, list, res);
        }
        for (int j = 1; j * candidates[i] <= target; j++) {
            list.remove(list.size() - 1);
        }
    }    

    private List<List<Integer>> sol2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        sol2(candidates, target, 0, new int[candidates.length], res);
        return res;
    }

    private void sol2(int[] candidates, int target, int i, int[] nums, List<List<Integer>> res) {
        if (i >= candidates.length) {
            if (target == 0) {
                List<Integer> list = new ArrayList<>();
                for (int j = 0; j < candidates.length; j++) {
                    for (int k = 0; k < nums[j]; k++) {
                        list.add(candidates[j]);
                    }
                }
                res.add(list);
            }
            return;
        }

        for (int j = 0; j * candidates[i] <= target; j++) {
            nums[i] = j;
            sol2(candidates, target - j * candidates[i], i + 1, nums, res);
        }
    }
}
