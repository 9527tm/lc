/*
 * @lc app=leetcode id=347 lang=java
 *
 * [347] Top K Frequent Elements
 *
 * https://leetcode.com/problems/top-k-frequent-elements/description/
 *
 * algorithms
 * Medium (56.79%)
 * Likes:    2072
 * Dislikes: 138
 * Total Accepted:    276.2K
 * Total Submissions: 478.8K
 * Testcase Example:  '[1,1,1,2,2,3]\n2'
 *
 * Given a non-empty array of integers, return the k most frequent elements.
 * 
 * Example 1:
 * 
 * 
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: nums = [1], k = 1
 * Output: [1]
 * 
 * 
 * Note: 
 * 
 * 
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Your algorithm's time complexity must be better than O(n log n), where n is
 * the array's size.
 * 
 * 
 */

// @lc code=start
class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        return sol1(nums, k); 
        //return sol2(nums, k); 
    }

    //O(n + mlgk) / O(n)
    private List<Integer> sol1(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {//O(n) / O(n)
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
     
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = 
            new PriorityQueue<>((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue()));
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {//O(mlgk) / O(k)
            if (minHeap.size() < k) {
                minHeap.offer(entry);
            }
            else if (minHeap.peek().getValue() < entry.getValue()) {
                minHeap.poll();
                minHeap.offer(entry);
            }
        }
     
        List<Integer> res = new ArrayList<>(); 
        while (!minHeap.isEmpty()) {//O(klgk) / O(k)
            res.add(minHeap.poll().getKey());
        }
        Collections.reverse(res); //O(n) / O(1)
        return res;
    }

    //O(n + nlgm) / O(n)
    private List<Integer> sol2(int[] nums, int k) {
        TopFreqTracker tracker = new TopFreqTracker();
        for (int n : nums) {//O(n + nlgm) / O(n)
            tracker.add(n);
        }
        return tracker.get(k);//O(klgm) / O(1)
    }

    class TopFreqTracker {
        private Map<Integer, Integer> freqMap = new HashMap<>(); //db
        private TreeMap<Integer, Set<Integer>> rankMap = new TreeMap<>(Collections.reverseOrder()); //index

        public void add(int n) {//O(lgm) / O(1)
            int freq = freqMap.getOrDefault(n, 0);//n times: O(n) / O(n)
            freqMap.put(n, freq + 1);
            if (freq > 0) {
                rankMap.get(freq).remove(n);
            }
            rankMap.computeIfAbsent(freq + 1, k -> new HashSet<>()).add(n);//n times: O(nlgm) / O(m + n)
        }

        public List<Integer> get(int k) {//O(klgm) / O(1)
            List<Integer> res = new ArrayList<>();
            for (Set<Integer> set : rankMap.values()) {//O(klgm) / O(1)
                for (int n : set) {//O(k) / O(1)
                    res.add(n);
                    if (res.size() >= k) {
                        return res;
                    }
                }
            }
            return res;//Never be there
        }
    }
}
// @lc code=end
