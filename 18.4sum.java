/*
 * @lc app=leetcode id=18 lang=java
 *
 * [18] 4Sum
 *
 * https://leetcode.com/problems/4sum/description/
 *
 * algorithms
 * Medium (30.73%)
 * Total Accepted:    239.3K
 * Total Submissions: 778.7K
 * Testcase Example:  '[1,0,-1,0,-2,2]\n0'
 *
 * Given an array nums of n integers and an integer target, are there elements
 * a, b, c, and d in nums such that a + b + c + d = target? Find all unique
 * quadruplets in the array which gives the sum of target.
 * 
 * Note:
 * 
 * The solution set must not contain duplicate quadruplets.
 * 
 * Example:
 * 
 * 
 * Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.
 * 
 * A solution set is:
 * [
 * ⁠ [-1,  0, 0, 1],
 * ⁠ [-2, -1, 1, 2],
 * ⁠ [-2,  0, 0, 2]
 * ]
 * 
 * 
 */
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
       //return sol1(nums, target); 
       //return sol2(nums, target); 
       return sol3(nums, target); 
    }

    private List<List<Integer>> sol1(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int sum2 = nums[i] + nums[j];
                int left = j + 1, right = nums.length - 1;
                while (left < right) {
                    if (left > j + 1 && nums[left] == nums[left - 1]) {
                        left++;
                        continue;
                    }
                    int sum4 = sum2 + nums[left] + nums[right];
                    if (sum4 < target) {
                        left++;
                    }
                    else if (sum4 > target) {
                        right--;
                    }
                    else {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        left++;
                        right--;
                    }
                }
            }
        }
        return res;
    }

    private List<List<Integer>> sol2(int[] nums, int target) {
        Arrays.sort(nums); //H.W.: missing the preprocessing -- SORT

        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int sum = nums[i] + nums[j];
                List<int[]> list = map.get(sum);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(sum, list);
                }
                list.add(new int[]{i, j});
            }
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int k = nums.length - 1; k >= 0; k--) {
            if (k < nums.length - 1 && nums[k + 1] == nums[k]) {
                continue;
            }
            for (int l = k - 1; l >= 0; l--) {
                if (l < k - 1 && nums[l + 1] == nums[l]) {
                    continue;
                }
                List<int[]> list = map.get(target - nums[k] - nums[l]);
                if (list != null) {
                    for (int n = list.size() - 1; n >= 0 && list.get(n)[1] < l; n--) {
                        int i = list.get(n)[0], j = list.get(n)[1];
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[k]));
                    }
                }
            }
        }

        return res;
    }

    private List<List<Integer>> sol3(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        sol3(nums, 4, 0, target, new ArrayList<>(), res);
        return res;
    }
    private void sol3(int[] nums, int k, int i, int target, List<Integer> tmp, List<List<Integer>> res) {
        if (k == 2) {//assume: k >= 2, i >= 0
            int left = i, right = nums.length - 1; //H.W.: left = i + 1
            while (left < right) {
                if (left > i && nums[left] == nums[left - 1]) {//H.W.: left > i + 1
                    left++;
                    continue;
                }
                int sum = nums[left] + nums[right];
                if (sum < target) {
                    left++;
                }
                else if (sum > target) {
                    right--;
                }
                else {
                    tmp.add(nums[left++]);
                    tmp.add(nums[right--]);
                    res.add(new ArrayList<>(tmp));
                    tmp.remove(tmp.size() - 1);
                    tmp.remove(tmp.size() - 1);
                }
            }
            return;
        }

        for (int j = i; j < nums.length; j++) {
            if (j > i && nums[j] == nums[j - 1]) {
                continue;
            }
            tmp.add(nums[j]);
            sol3(nums, k - 1, j + 1, target - nums[j], tmp, res);
            tmp.remove(tmp.size() - 1);
        }
    }
}
