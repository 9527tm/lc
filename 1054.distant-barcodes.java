/*
 * @lc app=leetcode id=1054 lang=java
 *
 * [1054] Distant Barcodes
 *
 * https://leetcode.com/problems/distant-barcodes/description/
 *
 * algorithms
 * Medium (39.41%)
 * Total Accepted:    7.4K
 * Total Submissions: 18.5K
 * Testcase Example:  '[1,1,1,2,2,2]'
 *
 * In a warehouse, there is a row of barcodes, where the i-th barcode is
 * barcodes[i].
 * 
 * Rearrange the barcodes so that no two adjacent barcodes are equal.  You may
 * return any answer, and it is guaranteed an answer exists.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: [1,1,1,2,2,2]
 * Output: [2,1,2,1,2,1]
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [1,1,1,1,2,2,3,3]
 * Output: [1,3,1,3,2,1,2,1]
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * 1 <= barcodes.length <= 10000
 * 1 <= barcodes[i] <= 10000
 * 
 * 
 * 
 * 
 * 
 */
class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        return sol1(barcodes); 
    }

    static class Pair implements Comparable<Pair> {
        private int key;
        private int value;
        Pair(int key, int value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public int compareTo(Pair other) {
            return Integer.compare(other.value, this.value);
        }
    }

    private int[] sol1(int[] barcodes) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : barcodes) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        List<Pair> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            list.add(new Pair(entry.getKey(), entry.getValue()));
        }
        int i = 0;
        Pair prevPair = null;
        PriorityQueue<Pair> maxHeap = new PriorityQueue<>(list);
        while (!maxHeap.isEmpty()) {
            Pair pair = maxHeap.poll();
            barcodes[i++] = pair.key;
            if (i > 1) {
                if (prevPair.value-- > 1) {
                    maxHeap.offer(prevPair);
                }
            }
            prevPair = pair;
        }
        return barcodes;
    }
}
