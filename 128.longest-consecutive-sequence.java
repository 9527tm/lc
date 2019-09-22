/*
 * @lc app=leetcode id=128 lang=java
 *
 * [128] Longest Consecutive Sequence
 *
 * https://leetcode.com/problems/longest-consecutive-sequence/description/
 *
 * algorithms
 * Hard (42.44%)
 * Total Accepted:    231.3K
 * Total Submissions: 541.4K
 * Testcase Example:  '[100,4,200,1,3,2]'
 *
 * Given an unsorted array of integers, find the length of the longest
 * consecutive elements sequence.
 * 
 * Your algorithm should run in O(n) complexity.
 * 
 * Example:
 * 
 * 
 * Input: [100, 4, 200, 1, 3, 2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4].
 * Therefore its length is 4.
 * 
 * 
 */
class Solution {
    public int longestConsecutive(int[] nums) {
        //return sol1(nums); 
        return sol2(nums); 
    }

    private int sol1(int[] nums) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (map.containsKey(n)) {//why not duplicate: [1,2,2,3]
                continue;            //https://leetcode.com/submissions/detail/263205151/
            }
            map.put(n, n);
            int start = map.getOrDefault(n - 1, n);
            int end = map.getOrDefault(n + 1, n);
            map.put(start, end);
            map.put(end, start);
            res = Math.max(res, end - start + 1);
        }
        return res;
    }

    private int sol2(int[] nums) {
        int res = 0;
        UnionFind uf = new UnionFind(nums);
        for (int i = 0; i < nums.length; i++) {
            int root1 = uf.isExisted(nums[i] - 1) ? uf.union(nums[i], uf.find(nums[i] - 1)) : nums[i];
            int root2 = uf.isExisted(nums[i] + 1) ? uf.union(nums[i], uf.find(nums[i] + 1)) : nums[i];
            int root = uf.union(root1, root2);
            res = Math.max(res, uf.getSize(root));
        }
        return res;
    }

    static class UnionFind {
        private Map<Integer, Integer> parent;
        private Map<Integer, Integer> size;
        public UnionFind(int[] nums) {
            parent = new HashMap<>();
            size = new HashMap<>();
            for (int n : nums) {
                parent.put(n, n);
                size.put(n, 1);
            }
        }

        public int find(int n) {
            while (n != parent.get(n)) {
                parent.put(n, parent.get(parent.get(n)));
                n = parent.get(n);
            }
            return n;
        }

        public int union(int n1, int n2) {
            int root1 = find(n1);
            int root2 = find(n2);
            if (root1 == root2) {
                return root1;
            }

            if (size.get(root2) > size.get(root1)) {
                parent.put(root1, root2);
                size.put(root2, size.get(root2) + size.get(root1));
                return root2;
            }
            else {
                parent.put(root2, root1);
                size.put(root1, size.get(root1) + size.get(root2));
                return root1;
            }
        }

        public boolean isExisted(int n) {
            return parent.containsKey(n);
        }

        public int getSize(int n) {
            return size.get(find(n));
        }
    }
}
