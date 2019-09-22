/*
 * @lc app=leetcode id=200 lang=java
 *
 * [200] Number of Islands
 *
 * https://leetcode.com/problems/number-of-islands/description/
 *
 * algorithms
 * Medium (42.51%)
 * Total Accepted:    427.9K
 * Total Submissions: 997K
 * Testcase Example:  '[["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]'
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of
 * islands. An island is surrounded by water and is formed by connecting
 * adjacent lands horizontally or vertically. You may assume all four edges of
 * the grid are all surrounded by water.
 * 
 * Example 1:
 * 
 * 
 * Input:
 * 11110
 * 11010
 * 11000
 * 00000
 * 
 * Output: 1
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * 11000
 * 11000
 * 00100
 * 00011
 * 
 * Output: 3
 * 
 */
class Solution {
    public int numIslands(char[][] grid) {
        //return sol1(grid); //dfs
        //return sol2(grid); //bfs
        return sol3(grid); //union find        
    }

    private int sol3(char[][] grid) {
        if (grid.length <= 0 || grid[0].length <= 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;

        int totalNum = 0;
        UnionFind uf = new UnionFind(m * n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != '0') {
                    totalNum++;
                    int root = i * n + j;
                    int root1 = (i > 0 && grid[i - 1][j] != '0') ? uf.find((i - 1) * n + j) : root;
                    int root2 = (j > 0 && grid[i][j - 1] != '0') ? uf.find(i * n + (j - 1)) : root;
                    uf.union(root, uf.union(root1, root2));
                }
            }
        }

        return totalNum - uf.getUnitedNum();
    }

    static class UnionFind {
        private int[] parent;
        private int[] size;
        private int unitedNum;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
            unitedNum = 0;
        }

        public int find(int i) {
            while (parent[i] != i) {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return i;
        }

        public int union(int i, int j) {
            int root1 = find(i);
            int root2 = find(j);
            if (root1 == root2) {
                return root1;
            }
            unitedNum++;
            if (size[root2] > size[root1]) {
                parent[root1] = root2;
                size[root2] += size[root1];
                return root2;
            }
            else {
                parent[root2] = root1;
                size[root1] += size[root2];
                return root1;
            }
        }

        public int getUnitedNum() {
            return unitedNum;
        }
    }
}
