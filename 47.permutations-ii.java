/*
 * @lc app=leetcode id=47 lang=java
 *
 * [47] Permutations II
 *
 * https://leetcode.com/problems/permutations-ii/description/
 *
 * algorithms
 * Medium (42.38%)
 * Total Accepted:    278.2K
 * Total Submissions: 654.8K
 * Testcase Example:  '[1,1,2]'
 *
 * Given a collection of numbers that might contain duplicates, return all
 * possible unique permutations.
 * 
 * Example:
 * 
 * 
 * Input: [1,1,2]
 * Output:
 * [
 * ⁠ [1,1,2],
 * ⁠ [1,2,1],
 * ⁠ [2,1,1]
 * ]
 * 
 * 
 */
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        return sol1(nums); 
    }

    private List<List<Integer>> sol1(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        //sol1(nums, 0, new ArrayList<>(), res);
        sol1a(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void sol1(int[] nums, int i, List<Integer> list, List<List<Integer>> res) {
        if (i >= nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int j = i; j < nums.length; j++) {
            //H.W.: wrongly deduplicate by if (j > i && nums[j] == nums[j - 1]) { <= 1[2]3[2]
            //                          or      j > i && nums[j] == nums[i] <= [2,2,1,1]  
            int k = j - 1;
            while (k >= i && nums[k] != nums[j]) { //scan between nums[i, j - 1] 
                k--;                               //to find the same elem as nums[j]
            }
            if (k >= i) {
                continue; //there exists one duplicate before.
            }
            list.add(nums[j]);                     
            swap(nums, i, j);
            sol1(nums, i + 1, list, res);
            swap(nums, i, j);
            list.remove(list.size() - 1);
        }
    }

    private void sol1a(int[] nums, int i, List<Integer> list, List<List<Integer>> res) {
        if (i >= nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int j = i; j < nums.length; j++) {
            if (hasExistedDupBetween(nums, i, j - 1, nums[j])) {
                continue;
            }
            list.add(nums[j]);                     
            swap(nums, i, j);
            sol1a(nums, i + 1, list, res);
            swap(nums, i, j);
            list.remove(list.size() - 1);
        }
    }

    private boolean hasExistedDupBetween(int[] nums, int left, int right, int target) {//[left, right]
        while (left <= right && nums[left] != target) {
            left++;
        }
        return left <= right;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
