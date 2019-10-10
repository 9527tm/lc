/*
 * @lc app=leetcode id=79 lang=java
 *
 * [79] Word Search
 *
 * https://leetcode.com/problems/word-search/description/
 *
 * algorithms
 * Medium (32.50%)
 * Total Accepted:    337.6K
 * Total Submissions: 1M
 * Testcase Example:  '[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]]\n"ABCCED"'
 *
 * Given a 2D board and a word, find if the word exists in the grid.
 * 
 * The word can be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring. The
 * same letter cell may not be used more than once.
 * 
 * Example:
 * 
 * 
 * board =
 * [
 * ⁠ ['A','B','C','E'],
 * ⁠ ['S','F','C','S'],
 * ⁠ ['A','D','E','E']
 * ]
 * 
 * Given word = "ABCCED", return true.
 * Given word = "SEE", return true.
 * Given word = "ABCB", return false.
 * 
 * 
 */
class Solution {
    public boolean exist(char[][] board, String word) {//DFS or DFS
        return sol1(board, word); //but hard to use BFS
        //Why? 1. need to record path for each state.
        //        https://leetcode.com/submissions/detail/268576763/
        //        error case: [["A","B","C","E"],["S","F","E","S"],["A","D","E","E"]], "ABCESEEEFS"
        //     2. TLE
        //        https://leetcode.com/submissions/detail/268665776/
    }
    
    private boolean sol1(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (dfs(board, word, i, j, 0)) {
                //if (bfs(board, word, i, j)) {//H.W.: TLE 
                    return true;
                }
            }
        }
        return false;
    }
    
    private final char VISITED_TOKEN = ' ';
    private boolean dfs(char[][] board, String word, int x, int y, int index) {
        if (index >= word.length()) {
            return true;
        }
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length ||
           board[x][y] == VISITED_TOKEN || board[x][y] != word.charAt(index)) {
            return false;
        }
        char backup = board[x][y];
        board[x][y] = VISITED_TOKEN;
        int[][] dirs = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        for (int[] dir : dirs) {
            int x2 = x + dir[0], y2 = y + dir[1];
            if (dfs(board, word, x2, y2, index + 1)) {
                board[x][y] = backup;
                return true;
            }
        }
        board[x][y] = backup;
        return false;
    }
    
    static class State {
        private int index; //matched index in target string
        private int x, y; //matched current position on the board
        private Set<List<Integer>> path; //all visited cells on board
        public State(int x, int y, int index, Set<List<Integer>> path) {
            this.x = x;
            this.y = y;
            this.index = index;
            this.path = path;
            this.path.add(Arrays.asList(x, y));
        }
    }
    
    private boolean bfs(char[][] board, String word, int x, int y) {
        if (word.length() <= 0 || board[x][y] != word.charAt(0)) {
            return false;
        }
        
        State root = new State(x, y, 0, new HashSet<>());
        Queue<State> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (state.index + 1 >= word.length()) {
                return true;
            }
            
            int[][] dirs = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
            for (int[] dir : dirs) {
                int x2 = state.x + dir[0], y2 = state.y + dir[1], index2 = state.index + 1;
                if (x2 < 0 || x2 >= board.length || y2 < 0 || y2 >= board[0].length || 
                   state.path.contains(Arrays.asList(x2, y2)) || 
                    board[x2][y2] != word.charAt(index2)) {
                    continue;
                }
                State state2 = new State(x2, y2, index2, new HashSet<>(state.path));
                queue.offer(state2);
            }
        }
        return false;
    }
}
