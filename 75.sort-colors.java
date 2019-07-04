/*
 * @lc app=leetcode id=75 lang=java
 *
 * [75] Sort Colors
 *
 * https://leetcode.com/problems/sort-colors/description/
 *
 * algorithms
 * Medium (42.37%)
 * Total Accepted:    326.8K
 * Total Submissions: 770K
 * Testcase Example:  '[2,0,2,1,1,0]'
 *
 * Given an array with n objects colored red, white or blue, sort them in-place
 * so that objects of the same color are adjacent, with the colors in the order
 * red, white and blue.
 * 
 * Here, we will use the integers 0, 1, and 2 to represent the color red,
 * white, and blue respectively.
 * 
 * Note: You are not suppose to use the library's sort function for this
 * problem.
 * 
 * Example:
 * 
 * 
 * Input: [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 * 
 * Follow up:
 * 
 * 
 * A rather straight forward solution is a two-pass algorithm using counting
 * sort.
 * First, iterate the array counting number of 0's, 1's, and 2's, then
 * overwrite array with total number of 0's, then 1's and followed by 2's.
 * Could you come up with a one-pass algorithm using only constant space?
 * 
 * 
 */
class Solution {
    public void sortColors(int[] nums) {
        //SOLUTION         //VALUE //DETAIL
        //sol0(nums, 3);   //5     //Count Sort
        //sol1(nums);      //5     //DNF Dijkstra
        //sol1a(nums);         //3 //sol1 -> sol1a: no swap
        //sol2(nums);      //5     //KR partition: 2 + 1 => 3 ways -- CPL p87, CLRS p171
        //sol2a(nums);     //5     //sol2 -> sol2a: no swap
        //sol2b(nums);     //5     //sol2a -> sol2b: if nested
        //sol3(nums);          //3 //EPIJ, p43: 2 passes and 2 ways
        //sol4(nums);          //1 //Created from sol3, but low efficiency
        //sol5(nums);          //4 //LC top 1, left 2 + right 2 => 3 ways
        //sol5a(nums);         //4 //sol5 -> sol5a: simplify loops
        //sol5b(nums);         //3 //sol5a -> sol5b: immediately mid++, sol5b <=> sol1 TOTALLY
        //sol5c(nums);         //0 //sol5b -> sol5c: Not good just delayed i++ / k--
        //sol5d(nums);         //0 //sol5c -> sol5d: no swap
        //sol6(nums, 3);   //5     //Recursion, 2 => n ways
        //sol6a(nums, 3);      //4 //sol6 -> sol6a: reversely
        //sol7(nums, 3);   //5     //Iterative, 1 pass, 3 => n ways (sol2b)
        //sol8(nums, 3);       //4 //sol7 -> sol8: sol2b -> sol2
        sol8a(nums, 3);    //5     //sol8 -> sol8a: while => for
        //sol8b(nums, 3)       //4 //sol8a -> sol8b:  k => pivots[k]
        //sol9(nums, 3);   //5     //Iterative, O(1) space, 3 => n ways (sol1)
        //sol10(nums, 3);      //4 //sol9 -> sol10: 2 => n ways
    }

    private void sol0(int[] nums, int k) {//Count Sort
        int[] counts = new int[k];
        for (int n : nums) {
            counts[n]++;
        }
        int i = 0;
        for (int j = 0; j < counts.length; j++) {
            while (counts[j]-- > 0) {
                nums[i++] = j;
            }
        }
    }

    private void sol1(int[] nums) {//DNF Dijkstra
        int i = 0, j = 0, k = nums.length - 1;
        while (j <= k) {
            if (nums[j] == 0) {
                swap(nums, i++, j++);
            }
            else if (nums[j] == 1) {
                j++;
            }
            else {
                swap(nums, j, k--);
            }
        }
    }

    private void sol1a(int[] nums) {//sol1 -> sol1a: no swap
        int i = 0, j = 0, k = nums.length - 1;
        while (j <= k) {
            if (nums[j] == 0) {
                nums[j++] = 1;        //Tricky: nums[j++] = 1 FIRST
                nums[i++] = 0;        //        when j == i, no 1 is actually found yet.
            }
            else if (nums[j] == 1) {
                j++;
            }
            else {
                nums[j] = nums[k];
                nums[k--] = 2;
            }
        }
    }

    private void sol2(int[] nums) {//KR partition: 2 + 1 => 3 ways -- CPL p87, CLRS p171
        int i = 0, j = 0, k = 0;
        for (int n : nums) {
            if (n == 0) {
                swap(nums, j, k++);
                swap(nums, i++, j++);
            }
            else if (n == 1) {
                swap(nums, j++, k++);
            }
            else {
                k++;
            }
        }
    }

    private void sol2a(int[] nums) {//sol2 -> sol2a: no swap
        int i = 0, j = 0, k = 0;
        for (int n : nums) {
            if (n == 0) {
                nums[k++] = 2;
                nums[j++] = 1;
                nums[i++] = 0;
            }
            else if (n == 1) {
                nums[k++] = 2;
                nums[j++] = 1;
            }
            else {
                nums[k++] = 2;
            }
        }
    }

    private void sol2b(int[] nums) {//sol2a -> sol2b (if nested)
        int i = 0, j = 0, k = 0;
        for (int n : nums) {
            if (n <= 2) {
                nums[k++] = 2;
                if (n <= 1) {
                    nums[j++] = 1;
                    if (n <= 0) {
                        nums[i++] = 0;
                    }
                }
            }
        }
    }

    private void sol3(int[] nums) {//EPIJ, p43
        int count0 = sol3(nums, 0, nums.length - 1, 0);
        int count1 = sol3(nums, count0, nums.length - 1, 1);
    }
    private int sol3(int[] nums, int left, int right, int smaller) {//Hoare, CLRS p185
        while (left <= right) {
            if (nums[left] == smaller) {
                left++;
            }
            else if (nums[right] != smaller) {
                right--;
            }
            else {
                swap(nums, left++, right--);
            }
        }
        return left;
    }

    private void sol4(int[] nums) {//Created from sol3, but low efficiency
        int mid = sol4(nums, 0, nums.length - 1, 0, 2);
        sol3(nums, 0, mid - 1, 0);
        sol3(nums, mid, nums.length - 1, 1);
    }
    private int sol4(int[] nums, int left, int right, int smallest, int largest) {
        while (left <= right) {
            if (nums[left] != largest) {
                left++;
            }
            else if (nums[right] != smallest) {
                right--;
            }
            else {
                swap(nums, left++, right--);
            }
        }
        return left;
    }
                                   //LC top 1, left 2 + right 2 => 3 ways
    private void sol5(int[] nums) {///discuss/26472/Share-my-at-most-two-pass-constant-space-10-line-solution
        int left = 0, right = nums.length - 1; //[left, right]: from first 1 to last 1, 
        for (int i = left; i <= right; i++) {  //               both are inclusive.
            while (i <= right && nums[i] == 2) {
                swap(nums, i, right--);
            }                                  //H.W.: wrongly check 0 before 2, ex:[1,2,0] 
            while (left <= i && nums[i] == 0) {
                swap(nums, left++, i);
            }
        }
    }

    private void sol5a(int[] nums) {//sol5 -> sol5a: simplify loops
        int left = 0, mid = 0, right = nums.length - 1;
        while (mid <= right) {
            if (nums[mid] == 2) {
                swap(nums, mid, right--);
            }
            else if (left <= mid && nums[mid] == 0) {
                swap(nums, left++, mid);
            }
            else {
                mid++;
            }
        }
    }

    private void sol5b(int[] nums) { //sol5a -> sol5b:  immediately mid++, totally equal to <=> sol1()    
        int left = 0, mid = 0, right = nums.length - 1;
        while (mid <= right) {
            if (nums[mid] == 2) {
                swap(nums, mid, right--);
            }
            else if (nums[mid] == 0) {
                swap(nums, left++, mid++);
            }
            else {
                mid++;
            }
        }
    }
                                    //sol5b -> sol5c: delayed i++ / k--  when leading 0 or tailing 2
                                    //but it is hard to read and has no gains since i and k are the same physical meanings as sol1.
                                    //when algorithm completes, i and k are points to the first and last 1 if any. 
                                    //So it's not really a good algorithm although many people on LC may think it in niubility.
    private void sol5c(int[] nums) {///discuss/26637/Simple-one-pass-solution
        int i = 0, j = 0, k = nums.length - 1; //test case: [0, 1, 0]
        while (j <= k) {
            if (nums[j] == 0 && j > i) {
                swap(nums, i++, j);
            }
            else if (nums[j] == 2 && j < k) {
                swap(nums, j, k--);
            }
            else {
                j++;
            }
        }
    }
                                    //sol5c -> sol5d: no swap
    private void sol5d(int[] nums) {///discuss/195342/O(n)-one-pass-Java-solution-beat-100
        int i = 0, j = 0, k = nums.length - 1;
        while (j <= k) {
            if (nums[j] == 0 && j > i) {
                nums[j] = nums[i];
                nums[i++] = 0;
            }
            else if (nums[j] == 2 && j < k) {
                nums[j] = nums[k];
                nums[k--] = 2;
            }
            else {
                j++;
            }
        }
    }

    //k >= 2, nums[i] belongs to [0, 1, 2, k - 1]
    private void sol6(int[] nums, int k) {//Recursion, 2 => n ways
        sol6(nums, 0, nums.length - 1, 0, k);
    }
    private void  sol6(int[] nums, int left, int right, int target, int k) {
        if (target + 2 > k) {
            return;
        }
        int i = left;
        for (int j = left; j <= right; j++) {
            if (nums[j] == target) {
                swap(nums, i++, j);
            }
        }
        sol6(nums, i, right, target + 1, k);
    }

    private void sol6a(int[] nums, int k) {//sol6 -> sol6a: reversely
        sol6a(nums, 0, nums.length - 1, k - 1);
    }
    private void sol6a(int[] nums, int left, int right, int target) {
        if (target < 1) {
            return;
        }
        int i = right;
        for (int j = right; j >= left; j--) {
            if (nums[j] == target) {
                swap(nums, i--, j);
            }
        }
        sol6a(nums, left, i, target - 1);
    }

    private void sol7(int[] nums, int k) {//Iterative, 1 pass, 3 => n ways (sol2b)
        int[] pointers = new int[k];
        for (int n : nums) {
            for (int i = k - 1; i >= n; i--) { // i >= 0 && i >= n
                nums[pointers[i]++] = i;
            }
        }
    }

    private void sol8(int[] nums, int k) {//sol7 -> sol8: sol2b -> sol2
        int[] pointers = new int[k];
        for (int n : nums) {
            int j = k - 1;
            while (j - 1 >= n) {// j >= 1 && j - 1 >= n //H.W.: wrongly use nums[i] for n
                //System.out.println(n + " " + j + " " + pointers[j - 1]+ " " + pointers[j]);
                swap(nums, pointers[j - 1], pointers[j]++);//   because it has been swaped and changed.
                j--;
            }
            pointers[j]++;
        }
    }

    private void sol8a(int[] nums, int k) {//sol8 -> sol8a: while => for
        int[] pointers = new int[k];
        for (int n : nums) {
            for (int j = k - 2; j >= n; j--) {
                swap(nums, pointers[j], pointers[j + 1]++);
            }
            pointers[n]++;
        }
    }
 
    private void sol8b(int[] nums, int k) {//sol8a -> sol8b:  k => pivots[k]
        int[] pointers = new int[k];
        
        int[] pivots = new int[k]; //
        for (int j = 0; j < k; j++) {
            pivots[j] = j;
        }

        for (int n : nums) {
            int j = 0;
            for (j = k - 2; j >= 0 && pivots[j] >= n; j--) {
                swap(nums, pointers[j], pointers[j + 1]++);
            }
            pointers[j + 1]++;
        }
    }
                                         //Iterative, O(1) space, 3 => n ways (sol1)
    private void sol9(int[] nums, int k) {//discuss/26654/Sort-colors-and-sort-k-colors-C++-solution
        int left = 0, right = nums.length - 1;//www.lintcode.com/en/problem/sort-colors-ii/
        for (int minColor = 0, maxColor = k - 1; minColor < maxColor; minColor++, maxColor--) {
            int i = left, j = left, l = right;
            while (j <= l) {
                if (nums[j] == minColor) {
                    swap(nums, i++, j++);
                }
                else if (nums[j] == maxColor) {
                    swap(nums, j, l--);
                }
                else {
                    j++;
                }
            }
            left = i;
            right = l;
        }
    }
        
    private void sol10(int[] nums, int k) {//sol9 -> sol10: 2 => n ways
        int left = 0, right = nums.length - 1;
        for (int minColor = 0; minColor < k - 1; minColor++) {
            int i = left;
            for (int j = left; j <= right; j++) {
                if (nums[j] == minColor) {
                    swap(nums, i++, j);
                }
            }
            left = i;
        }
    }

   private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

