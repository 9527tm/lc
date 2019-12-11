/*
 * @lc app=leetcode id=480 lang=java
 *
 * [480] Sliding Window Median
 *
 * https://leetcode.com/problems/sliding-window-median/description/
 *
 * algorithms
 * Hard (33.89%)
 * Likes:    575
 * Dislikes: 60
 * Total Accepted:    35.9K
 * Total Submissions: 103.4K
 * Testcase Example:  '[1,3,-1,-3,5,3,6,7]\n3'
 *
 * Median is the middle value in an ordered integer list. If the size of the
 * list is even, there is no middle value. So the median is the mean of the two
 * middle value.
 * Examples: 
 * [2,3,4] , the median is 3
 * [2,3], the median is (2 + 3) / 2 = 2.5 
 * 
 * Given an array nums, there is a sliding window of size k which is moving
 * from the very left of the array to the very right. You can only see the k
 * numbers in the window. Each time the sliding window moves right by one
 * position. Your job is to output the median array for each window in the
 * original array.
 * 
 * For example,
 * Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
 * 
 * 
 * Window position                Median
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       1
 * ⁠1 [3  -1  -3] 5  3  6  7       -1
 * ⁠1  3 [-1  -3  5] 3  6  7       -1
 * ⁠1  3  -1 [-3  5  3] 6  7       3
 * ⁠1  3  -1  -3 [5  3  6] 7       5
 * ⁠1  3  -1  -3  5 [3  6  7]      6
 * 
 * 
 * Therefore, return the median sliding window as [1,-1,-1,3,5,6].
 * 
 * Note: 
 * You may assume k is always valid, ie: k is always smaller than input array's
 * size for non-empty array.
 */

// @lc code=start
class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        //return sol1(nums, k);        
        return sol2(nums, k);        
    }

    public double[] sol1(int[] array, int k) {
        //assume k > 0, array != null, array.length >= k
        double[] res = new double[array.length - k + 1];
        Counter smalls = new Counter();
        Counter larges = new Counter();
        for (int fast = 0; fast < array.length; fast++) {
            smalls.offer(array[fast]); //add a[f]
            larges.offer(smalls.pollLast());//associate

            if (fast >= k) {//remove a[f - k] (when f >= k)
                if (!larges.remove(array[fast - k])) {
                    smalls.remove(array[fast - k]);
                }
            }
            if (larges.size() > smalls.size()) {//balance
                smalls.offer(larges.pollFirst());
            }

            if (fast >= k - 1) {//update result of window [f - k + 1, ..., f]
                long sum = smalls.size() > larges.size() ?
                        (smalls.peekLast() * 2L) : (smalls.peekLast() * 1L + larges.peekFirst() * 1L); //H.W.: -int.inf
                res[fast - k + 1] = sum / 2.0;
            }
        }
        return res;
    }

    class Counter {
        private int count = 0;
        private TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        public int size() {
            return count;
        }
        public void offer(int key) {
            count++;
            treeMap.put(key, treeMap.getOrDefault(key, 0) + 1);
        }
        private void reduceCount(int key) {
            count--;
            int value = treeMap.get(key);
            if (value == 1) {
                treeMap.remove(key);
            }
            else {
                treeMap.put(key, value - 1);
            }
        }
        public boolean remove(int key) {
            if (!treeMap.containsKey(key)) {
                return false;
            }
            reduceCount(key);
            return true;
        }
        public int peekFirst() {
            return treeMap.firstKey();
        }
        public int peekLast() {
            return treeMap.lastKey();
        }
        public int pollFirst() {
            int key = treeMap.firstKey();
            reduceCount(key);
            return key;
        }
        public int pollLast() {
            int key = treeMap.lastKey();
            reduceCount(key);
            return key;
        }
    }


    public double[] sol2(int[] array, int k) {
        //assume k > 0, array != null, array.length >= k
        double[] res = new double[array.length - k + 1];
        MultiTreeSet<Integer> smalls = new MultiTreeSet<>();
        MultiTreeSet<Integer> larges = new MultiTreeSet<>();
        for (int fast = 0; fast < array.length; fast++) {
            smalls.add(array[fast]);     //1. add a[f]
            int largestSmallKey = smalls.lastKey();
            smalls.remove(largestSmallKey);
            larges.add(largestSmallKey); //2. associate
            if (fast >= k) {             //3. remove a[f - k] (when f >= k)
                if (!larges.remove(array[fast - k])) {
                    smalls.remove(array[fast - k]);
                }
            }
            if (larges.size() > smalls.size()) {//4. balance
                int smallestLargeKey = larges.firstKey();
                larges.remove(smallestLargeKey);
                smalls.add(smallestLargeKey);
            }
            if (fast >= k - 1) {         //5. update result of window: a[f - k + 1, ..., k]
                long sum = smalls.size() > larges.size() ?
                           (smalls.lastKey() * 2L) : (smalls.lastKey() * 1L + larges.firstKey());
                res[fast - k + 1] = sum / 2.0;
            }
        }
        return res;
    }

    class MultiTreeSet<E> {
        private int totalCount = 0;
        private TreeMap<E, Integer> treeMap = new TreeMap<>();
        public int size() {
            return totalCount;
        }
        public boolean add(E key) {
            totalCount++;//H.W.: forgot to update exposed global counter!
            treeMap.put(key, treeMap.getOrDefault(key, 0) + 1);
            return true;
        }
        public boolean remove(E key) {
            if (!treeMap.containsKey(key)) {
                return false;
            }
            totalCount--;//H.W. forgot to update exposed global counter!
                         //H.W.2 added it before false return 
            treeMap.compute(key, (k, v) -> {return v > 1 ? v - 1 : null;});
            return true;
        }
        public E firstKey() {
            return treeMap.firstKey();
        }
        public E lastKey() {
            return treeMap.lastKey();
        }
    }
}
// @lc code=end
