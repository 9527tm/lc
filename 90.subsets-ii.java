/*
 * @lc app=leetcode id=90 lang=java
 *
 * [90] Subsets II
 *
 * https://leetcode.com/problems/subsets-ii/description/
 *
 * algorithms
 * Medium (43.76%)
 * Total Accepted:    224.9K
 * Total Submissions: 513.7K
 * Testcase Example:  '[1,2,2]'
 *
 * Given a collection of integers that might contain duplicates, nums, return
 * all possible subsets (the power set).
 * 
 * Note: The solution set must not contain duplicate subsets.
 * 
 * Example:
 * 
 * 
 * Input: [1,2,2]
 * Output:
 * [
 * ⁠ [2],
 * ⁠ [1],
 * ⁠ [1,2,2],
 * ⁠ [2,2],
 * ⁠ [1,2],
 * ⁠ []
 * ]
 * 
 * 
 */
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        return sol1(nums); 
    }

    private List<List<Integer>> sol1(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        sol1(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void sol1(int[] nums, int index, List<Integer> tmp, List<List<Integer>> res) {
        if (index == nums.length) {
            res.add(new ArrayList<>(tmp));
            return;
        }

        tmp.add(nums[index]);
        sol1(nums, index + 1, tmp, res);
        tmp.remove(tmp.size() - 1);

        int j = index + 1;
        while (j < nums.length && nums[j] == nums[j - 1]) {
            j++;
        }
        sol1(nums, j, tmp, res);
    }
}
