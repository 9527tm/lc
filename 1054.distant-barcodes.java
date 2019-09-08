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
        //return sol1(barcodes); 
        //return sol2(barcodes); 
        //return sol3(barcodes); 
        return sol4(barcodes); 
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

    private int[] sol2(int[] barcodes) {
        final int SIZE = 10001;
        int[] counters = new int[SIZE];
        for (int n : barcodes) {
            counters[n]++;
        }
        int prevIndex = -1;
        for (int i = 0; i < barcodes.length; i++) {
            int maxIndex = -1, maxCount = 0;
            for (int j = 0; j < counters.length; j++) {
                if (j != prevIndex && counters[j] > maxCount) {
                    maxIndex = j;
                    maxCount = counters[j];
                }
            }//assert(counters[maxIndex] = maxCount > 0
            barcodes[i] = maxIndex;
            counters[maxIndex]--;
            prevIndex = maxIndex; //H.W.: forgot to update the globa variable
        }
        return barcodes;
    }

    private int[] sol3(int[] barcodes) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : barcodes) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = 
                new PriorityQueue<>(map.size(), (e1, e2) -> 
                        Integer.compare(e2.getValue(), e1.getValue()));
        maxHeap.addAll(map.entrySet());
        Map.Entry<Integer, Integer> prevEntry = null;
        for (int i = 0; i < barcodes.length; i++) {
            Map.Entry<Integer, Integer> entry = maxHeap.poll();//Guarranteed:
            barcodes[i] = entry.getKey();                      //A solution exists always!
            if (prevEntry != null) {
                Integer count = prevEntry.getValue() - 1;
                if (count > 0) {
                    prevEntry.setValue(count);
                    maxHeap.offer(prevEntry);
                }
            }
            prevEntry = entry;
        }
        return barcodes;
    }

    //https://leetcode.com/problems/distant-barcodes/discuss/299371/C++-with-picture-O(N)
    private int[] sol4(int[] barcodes) {
        final int SIZE = 10001;
        int[] map = new int[SIZE];
        int maxN = 0;
        for (int n : barcodes) {
            if (map[n]++ >= map[maxN]) {
                maxN = n;
            }
        }
        int pos = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int n = (maxN + i) % SIZE; map[n] > 0; map[n]--) {
                barcodes[pos] = n;
                pos = pos + 2 < barcodes.length ? pos + 2: 1;
            }
        }
        return barcodes;
    } 
}
