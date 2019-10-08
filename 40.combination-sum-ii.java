/*
 * @lc app=leetcode id=40 lang=java
 *
 * [40] Combination Sum II
 *
 * https://leetcode.com/problems/combination-sum-ii/description/
 *
 * algorithms
 * Medium (43.66%)
 * Total Accepted:    254.9K
 * Total Submissions: 582.2K
 * Testcase Example:  '[10,1,2,7,6,1,5]\n8'
 *
 * Given a collection of candidate numbers (candidates) and a target number
 * (target), find all unique combinations in candidates where the candidate
 * numbers sums to target.
 * 
 * Each number in candidates may only be used once in the combination.
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
 * Input: candidates = [10,1,2,7,6,1,5], target = 8,
 * A solution set is:
 * [
 * ⁠ [1, 7],
 * ⁠ [1, 2, 5],
 * ⁠ [2, 6],
 * ⁠ [1, 1, 6]
 * ]
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: candidates = [2,5,2,1,2], target = 5,
 * A solution set is:
 * [
 * [1,2,2],
 * [5]
 * ]
 * 
 * 
 */
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        return sol1(candidates, target);
    }

    private List<List<Integer>> sol1(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
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

        //H.W.: forgot to check candidates[i] <= target to guaruantee target non-negative
        list.add(candidates[i]);
        sol1(candidates, target - candidates[i], i + 1, list, res);
        list.remove(list.size() - 1);

        while (i + 1 < candidates.length && candidates[i + 1] == candidates[i]) {
            i++;
        }
        sol1(candidates, target, i + 1, list, res);
    }
}
