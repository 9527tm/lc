/*
 * @lc app=leetcode id=212 lang=java
 *
 * [212] Word Search II
 *
 * https://leetcode.com/problems/word-search-ii/description/
 *
 * algorithms
 * Hard (30.24%)
 * Total Accepted:    136.3K
 * Total Submissions: 448.8K
 * Testcase Example:  '[["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]]\n' +
  '["oath","pea","eat","rain"]'
 *
 * Given a 2D board and a list of words from the dictionary, find all words in
 * the board.
 * 
 * Each word must be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring. The
 * same letter cell may not be used more than once in a word.
 * 
 * 
 * 
 * Example:
 * 
 * 
 * Input: 
 * board = [
 * ⁠ ['o','a','a','n'],
 * ⁠ ['e','t','a','e'],
 * ⁠ ['i','h','k','r'],
 * ⁠ ['i','f','l','v']
 * ]
 * words = ["oath","pea","eat","rain"]
 * 
 * Output: ["eat","oath"]
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * All inputs are consist of lowercase letters a-z.
 * The values of words are distinct.
 * 
 * 
 */
class Solution {
    private final int VISITED_TOKEN = ' ';
    
    public List<String> findWords(char[][] board, String[] words) {//assumption: no word is emtpy.
        //return sol1(board, words); //DFS
        return sol2(board, words);   //Trie + DFS
    }
    
    private List<String> sol1(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        for (String word : words) {
            if (exist(board, word)) {
                res.add(word);
            }
        }
        return res;
    }
    
    private boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int x, int y, int index) {
        if (index >= word.length()) {
            return true;
        }
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length ||
            board[x][y] == VISITED_TOKEN || board[x][y] != word.charAt(index)) {
            return false;
        }
        board[x][y] = VISITED_TOKEN;
        
        boolean isFound = false;
        int[][] dirs = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        for (int[] dir : dirs) {
            int x2 = x + dir[0], y2 = y + dir[1];
            if (dfs(board, word, x2, y2, index + 1)) {
                isFound = true;
                break;
            }
        }
        
        board[x][y] = word.charAt(index);
        return isFound;
    }
    
    //DON'T do constant optimization: https://leetcode.com/submissions/detail/268874857/
    private final int SIZE = 26;
    class Trie {
        private Trie[] entries = new Trie[SIZE]; //H.W.: char[] entries = new char[SIZE];
        private String word = null;
    }
    private Trie createTree(String[] words) {
        Trie root = new Trie();        
        for (String word : words) {
            Trie curr = root;
            for (int i = 0; i < word.length(); i++) {
                int j = word.charAt(i) - 'a';
                if (curr.entries[j] == null) {
                    curr.entries[j] = new Trie();
                }
                curr = curr.entries[j];
            }
            curr.word = word;
        }
        return root;
    }
    
    private List<String> sol2(char[][] board, String[] words) {
        Set<String> set = new HashSet<>();
        Trie root = createTree(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, root, i, j, set);
            }
        }
        return new ArrayList<>(set);
    }
    
    private void dfs(char[][] board, Trie root, int x, int y, Set<String> set) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || 
           board[x][y] == VISITED_TOKEN || root.entries[board[x][y] - 'a'] == null) {
            return;
        }
        
        Trie curr = root.entries[board[x][y] - 'a'];
        if (curr.word != null) {
            set.add(curr.word);
        }
        
        char backup = board[x][y];
        board[x][y] = VISITED_TOKEN;
        int[][] dirs = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int[] dir : dirs) {
            int x2 = x + dir[0], y2 = y + dir[1]; //H.W.: typo: y2 = y + dir[0]
            dfs(board, curr, x2, y2, set); 
        }
        board[x][y] = backup;
    }
}

