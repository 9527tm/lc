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
    //https://www.cnblogs.com/grandyang/p/4359825.html
    public List<List<Integer>> permuteUnique(int[] nums) {
        //return sol1(nums); //How to dedup? Opt 1: scan a new number from tried number subarray [O(N!*N) / O(N)]
        //return sol2(nums); //              Opt 2: add a new number to tried number set [O(N!*N) / O(N*N)]
        return sol3(nums);   //              Opt 3: sort and find next permutations [O(N!*N) / O(1)]
    }

    private List<List<Integer>> sol1(int[] nums) {
        //Arrays.sort(nums); //https://www.cnblogs.com/grandyang/p/4359825.html#4314885
        List<List<Integer>> res = new ArrayList<>();
        sol1(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void sol1(int[] nums, int i, List<Integer> list, List<List<Integer>> res) {
        if (i >= nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int j = i; j < nums.length; j++) {
            if (hasExistedDupBetween(nums, i, j - 1, nums[j])) { //H.W.: wrongly deduplicating by:
                continue;                                        //      j > i && nums[j] != nums[j - 1]
            }                                                    //or    j > i && nums[j] != nums[i]
            list.add(nums[j]);                      //https://leetcode.com/submissions/detail/267960641/ 
            swap(nums, i, j);                       //https://leetcode.com/submissions/detail/267960644/
            sol1(nums, i + 1, list, res);
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

    private List<List<Integer>> sol2(int[] nums) {
        //No sort required: //https://www.cnblogs.com/grandyang/p/4359825.html#4314885
        List<List<Integer>> res = new ArrayList<>();
        sol2(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void sol2(int[] nums, int i, List<Integer> list, List<List<Integer>> res) {
        if (i >= nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }
        Set<Integer> set = new HashSet<>();
        for (int j = i; j < nums.length; j++) {
            if (!set.add(nums[j])) {
                continue;
            }
            list.add(nums[j]);
            swap(nums, i, j);
            sol2(nums, i + 1, list, res);
            swap(nums, i, j);
            list.remove(list.size() - 1);
        }
    }

    private List<List<Integer>> sol3(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        do {
            res.add(array2List(nums));
        } while (nextPermutation(nums));
        return res;
    }

    //step 1: two negative scans: one for lower, the other for greater
    //step 2: exchange the lower with the greater => increase the smallest delta value
    //step 3: reverse rear range (lower, end) => reset from largest to least number
    private boolean nextPermutation(int[] nums) {//123 -> 132 -> 
        //step 1.1: first pass scan for lower digit
        int i = nums.length - 1;                 //[(1)32 -> (1)3(2) -> (2)3(1) -> 2(31) -> 2(13)]
        while (i > 0 && nums[i] <= nums[i - 1]) {//scan the descending range from back to forth
            i--;
        }
        if (i <= 0) {
            return false;//no lower digit can be prompted, like: 321.
        }//here the case: 132
        int j = i - 1; //the lower digit found at j: (1)32

        //step 1.2: second pass scan for greater digit
        i = nums.length - 1;
        while (i > j && nums[i] <= nums[j]) {
            i--;
        }//the greater digit found at i: 13(2)

        //step 2: exchange lower with greater
        swap(nums, i, j); //(1)3(2) -> (2)3(1) 

        //step 3: reverse the rear range
        reverse(nums, j + 1, nums.length - 1);//2(31) -> 2(13)
        return true;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left++, right--);
        }
    }        

    private List<Integer> array2List(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int n : nums) {
            list.add(n);
        }
        return list;
    }
}
