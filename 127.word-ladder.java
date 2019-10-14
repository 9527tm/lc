/*
 * @lc app=leetcode id=127 lang=java
 *
 * [127] Word Ladder
 *
 * https://leetcode.com/problems/word-ladder/description/
 *
 * algorithms
 * Medium (25.73%)
 * Total Accepted:    309K
 * Total Submissions: 1.2M
 * Testcase Example:  '"hit"\n"cog"\n["hot","dot","dog","lot","log","cog"]'
 *
 * Given two words (beginWord and endWord), and a dictionary's word list, find
 * the length of shortest transformation sequence from beginWord to endWord,
 * such that:
 * 
 * 
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list. Note that beginWord is
 * not a transformed word.
 * 
 * 
 * Note:
 * 
 * 
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 * 
 * Output: 5
 * 
 * Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" ->
 * "dog" -> "cog",
 * return its length 5.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 * 
 * Output: 0
 * 
 * Explanation: The endWord "cog" is not in wordList, therefore no possible
 * transformation.
 * 
 * 
 * 
 * 
 * 
 */
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //return sol1(beginWord, endWord, wordList);
        return sol2(beginWord, endWord, wordList);
    }

    //BFS: O(n * m * c) / O(n)
    //     n -- size(wordList), m -- max(len(word)), c -- 26('a' - 'z')
    //or,
    //     O((b ^ d) * m * c) / O(b ^ d)
    //     b -- branching factor (the average outdegree)
    //     d -- transforming distance from beginWord to endWord
    //     the worst case is O(b ^ d) = O(n) and d is the tree height h.
    //or,
    //     O(|V| + |E|) / O(|V| + |E|)
    //     V -- the vertex set, E -- the edge set
    //     but ithis does not apply this problem since the graph is not implemented by edge lists:
    //     that's, no edge is represented between two words explictly although |V| is the wordSet.
    private int sol1(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        for (int depth = 1; !queue.isEmpty(); depth++) {//depth is initialized:
            int size = queue.size();                    //to 1 if check goal when expanding;
            for (int num = 0; num < size; num++) {      //to 2 if check goal when generating.
                String currWord = queue.poll();
                if (currWord.equals(endWord)) {//beginWord != endWord intially,
                    return depth;              //so they must be transformed along wordList.
                }                              //Don't worry about that endWord is not in wordList.
                char[] array = currWord.toCharArray();
                for (int i = 0; i < array.length; i++) {
                    char currChar = array[i];
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        array[i] = ch;
                        String nextWord = new String(array);
                        if (wordSet.remove(nextWord)) {//H.W.: used a visitedSet
                            queue.offer(nextWord);     //      but forgot to check wordSet first
                        }                              //TIP: remove wordSet only!
                    }
                    array[i] = currChar;
                }
            }
        }
        return 0;
    }

    //BFS Bidirectional:     O(2 * (b ^ (d / 2)) * m * c) / O(2 * b ^ (d / 2))
    //                       (2 * b ^ (d / 2)) is much smaller than (b ^ d)
    private int sol2(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.remove(endWord)) {
            return 0;
        }
        Set<String> srcSet = new HashSet<>();
        Set<String> dstSet = new HashSet<>();
        srcSet.add(beginWord);
        dstSet.add(endWord);

        for (int depth = 2; !srcSet.isEmpty() && !dstSet.isEmpty(); depth++) {
            Set<String> tempSet = new HashSet<>();
            for (String currWord : srcSet) {
                char[] array = currWord.toCharArray();
                for (int i = 0; i < array.length; i++) {
                    char backupChar = array[i];
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        array[i] = ch;
                        String nextWord = new String(array);
                        if (dstSet.contains(nextWord)) {//Tricky: check goal before removing wordSet
                            return depth;               //        since the gola has been removed by
                        }                               //        the opposite search!
                        if (wordSet.remove(nextWord)) { //so, we need to check endWord in wordSet
                            tempSet.add(nextWord);      //    for a valid induction base:
                        }                               //    if at least one of beginWord and endWord is
                    }                                   //    in wordSet, both are transformed.
                    array[i] = backupChar;              //    since beginWord may not be in wordSet,
                }                                       //    endWord must be in wordSet.
            }

            srcSet = tempSet;
            if (srcSet.size() > dstSet.size()) {
                Set<String> tmp = srcSet;
                srcSet = dstSet;
                dstSet = tmp;
            }
        }
        return 0;
    }

}

