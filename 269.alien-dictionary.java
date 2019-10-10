/*
 * @lc app=leetcode id=269 lang=java
 *
 * [269] Alien Dictionary
 *
 * https://leetcode.com/problems/alien-dictionary/description/
 *
 * algorithms
 * Hard (32.42%)
 * Total Accepted:    94K
 * Total Submissions: 289.3K
 * Testcase Example:  '["wrt","wrf","er","ett","rftt"]'
 *
 * There is a new alien language which uses the latin alphabet. However, the
 * order among letters are unknown to you. You receive a list of non-empty
 * words from the dictionary, where words are sorted lexicographically by the
 * rules of this new language. Derive the order of letters in this language.
 * 
 * Example 1:
 * 
 * 
 * Input:
 * [
 * ⁠ "wrt",
 * ⁠ "wrf",
 * ⁠ "er",
 * ⁠ "ett",
 * ⁠ "rftt"
 * ]
 * 
 * Output: "wertf"
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * [
 * ⁠ "z",
 * ⁠ "x"
 * ]
 * 
 * Output: "zx"
 * 
 * 
 * Example 3:
 * 
 * 
 * Input:
 * [
 * ⁠ "z",
 * ⁠ "x",
 * ⁠ "z"
 * ] 
 * 
 * Output: "" 
 * 
 * Explanation: The order is invalid, so return "".
 * 
 * 
 * Note:
 * 
 * 
 * You may assume all letters are in lowercase.
 * You may assume that if a is a prefix of b, then a must appear before b in
 * the given dictionary.
 * If the order is invalid, return an empty string.
 * There may be multiple valid order of letters, return any one of them is
 * fine.
 * 
 * 
 */
class Solution {
    public String alienOrder(String[] words) {//edges of graph:  partial orders among characters
        //return sol1(words);//bfs +       create graph: compare two adjacent words to find edges
        return sol2(words);  //dfs +       create graph: the same as above
        //return sol3(words) //dfs / bfs + create graph: develop trie to find edges
    }
    
    private String sol1(String[] words) {
        Map<Character, List<Character>> adjMap = new HashMap<>();
        Map<Character, Integer> indegreeMap = new HashMap<>();
        createGraph(words, adjMap, indegreeMap);
        return bfs(adjMap, indegreeMap);
    }
    
    private String bfs(Map<Character, List<Character>> adjMap, Map<Character, Integer> indegreeMap) {
        Queue<Character> queue = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : indegreeMap.entrySet()) {
            if (entry.getValue() == 0) {//H.W.: negative logic: > 0
                queue.offer(entry.getKey());
            }
        }
        
        StringBuilder builder = new StringBuilder();
        while (!queue.isEmpty()) {
            char root = queue.poll();
            builder.append(root);
            if (!adjMap.containsKey(root)) {//H.W.: negative logic: missing ! character
                continue;
            }
            for (char child : adjMap.get(root)) {
                int num = indegreeMap.get(child) - 1;
                indegreeMap.put(child, num);
                if (num == 0) {
                    queue.offer(child);
                }
            }
        }
        
        return builder.length() == indegreeMap.size() ? builder.toString() : "";
    }
    
    private void createGraph(String[] words, 
                Map<Character, List<Character>> adjMap, Map<Character, Integer> indegreeMap) {
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                indegreeMap.put(word.charAt(i), 0);
            }
        }
        
        for (int j = 1; j < words.length; j++) {
            int minLen = Math.min(words[j - 1].length(), words[j].length());
            for (int k = 0; k < minLen; k++) {
                char ch1 = words[j - 1].charAt(k); //H.W.: typo int ch1 = ...
                char ch2 = words[j].charAt(k);
                if (ch1 != ch2) {
                    List<Character> adjList = adjMap.get(ch1);
                    if (adjList == null) {
                        adjList = new ArrayList<>();
                        adjMap.put(ch1, adjList);
                    }
                    adjList.add(ch2);
                    
                    int num = indegreeMap.get(ch2) + 1;//H.W.: miss updating indegreeMap
                    indegreeMap.put(ch2, num);
                    break;
                }
            }
        }
    }
    
    private String sol2(String[] words) {
        Map<Character, List<Character>> graph = initGraph(words);
        Map<Character, Integer> visitedMap = new HashMap<>();
        StringBuilder builder = new StringBuilder(); //work as stack
        for (char root : graph.keySet()) {
            if (!dfs(graph, visitedMap, root, builder)) {
                return "";
            }
        }
        return builder.reverse().toString();
    }
    
    private final int COLOR_WHITE = 0, COLOR_GRAY = 1, COLOR_BLACK = 2;
    private boolean dfs(Map<Character, List<Character>> graph, 
                        Map<Character, Integer> visitedMap, char root, StringBuilder builder) {
        Integer oldColor = visitedMap.get(root);
        if (oldColor != null) {
            return oldColor == COLOR_BLACK;
        }
        visitedMap.put(root, COLOR_GRAY);
        
        
        for (char child : graph.get(root)) {
            if (!dfs(graph, visitedMap, child, builder)) {
                return false;
            }
        }
        builder.append(root);//H.W. TRICKY! wrongly add current element before recursions to children.
        
        visitedMap.put(root, COLOR_BLACK);
        return true; //H.W.: missing return statement
    }
    
    private Map<Character, List<Character>> initGraph(String[] words) {
        Map<Character, List<Character>> graph = new HashMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                if (!graph.containsKey(word.charAt(i))) {
                    graph.put(word.charAt(i), new ArrayList<>());
                }
            }
        }
        
        for (int j = 1; j < words.length; j++) {
            int minLen = Math.min(words[j - 1].length(), words[j].length());
            for (int k = 0; k < minLen; k++) {
                char ch1 = words[j - 1].charAt(k);//H.W: typo TWICE: int ch1 = ...
                char ch2 = words[j].charAt(k);
                if (ch1 != ch2) {
                    List<Character> list = graph.get(ch1);
                    if (list == null) {
                        list = new ArrayList<>();
                        graph.put(ch1, list);
                    }
                    list.add(ch2);
                    break;
                }
            }
        }
        return graph;
    }
    
    //sol3() -- use trie developing partial orders (edges)
    //https://leetcode.com/problems/alien-dictionary/discuss/70152/Java-Trie+Topological-sorting-for-early-termination
    //https://leetcode.com/submissions/detail/181201834/
}
