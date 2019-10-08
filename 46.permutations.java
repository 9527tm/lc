/*
 * @lc app=leetcode id=46 lang=java
 *
 * [46] Permutations
 *
 * https://leetcode.com/problems/permutations/description/
 *
 * algorithms
 * Medium (57.67%)
 * Total Accepted:    446K
 * Total Submissions: 771.6K
 * Testcase Example:  '[1,2,3]'
 *
 * Given a collection of distinct integers, return all possible permutations.
 * 
 * Example:
 * 
 * 
 * Input: [1,2,3]
 * Output:
 * [
 * ⁠ [1,2,3],
 * ⁠ [1,3,2],
 * ⁠ [2,1,3],
 * ⁠ [2,3,1],
 * ⁠ [3,1,2],
 * ⁠ [3,2,1]
 * ]
 * 
 * 
 */
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        //return sol1(nums); 
        return sol2(nums); 
    }

    private List<List<Integer>> sol1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        sol1(nums, 0, res);
        return res;
    }

    private void sol1(int[] nums, int i, List<List<Integer>> res) {
        if (i >= nums.length) {
            //H.W.: wrongly use "res.add(Arrays.asList(nums));" 
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < nums.length; j++) {
                list.add(nums[j]);
            }
            res.add(list);
        }
        for (int j = i; j < nums.length; j++) {
            swap(nums, i, j);
            sol1(nums, i + 1, res);
            swap(nums, i, j);
        }
    }

    private List<List<Integer>> sol2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        sol2(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void sol2(int[] nums, int i, List<Integer> list, List<List<Integer>> res) {
        if (i >= nums.length) {
            res.add(new ArrayList<>(list));
        }
        for (int j = i; j < nums.length; j++) {
            list.add(nums[j]);
            swap(nums, i, j);
            sol2(nums, i + 1, list, res);
            swap(nums, i, j);
            list.remove(list.size() - 1);
        }
    }


    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
