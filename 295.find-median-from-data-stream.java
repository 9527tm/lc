/*
 * @lc app=leetcode id=295 lang=java
 *
 * [295] Find Median from Data Stream
 *
 * https://leetcode.com/problems/find-median-from-data-stream/description/
 *
 * algorithms
 * Hard (38.06%)
 * Total Accepted:    128.3K
 * Total Submissions: 332.8K
 * Testcase Example:  '["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]\n[[],[1],[2],[],[3],[]]'
 *
 * Median is the middle value in an ordered integer list. If the size of the
 * list is even, there is no middle value. So the median is the mean of the two
 * middle value.
 * For example,
 * 
 * [2,3,4], the median is 3
 * 
 * [2,3], the median is (2 + 3) / 2 = 2.5
 * 
 * Design a data structure that supports the following two operations:
 * 
 * 
 * void addNum(int num) - Add a integer number from the data stream to the data
 * structure.
 * double findMedian() - Return the median of all elements so far.
 * 
 * 
 * 
 * 
 * Example:
 * 
 * 
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3) 
 * findMedian() -> 2
 * 
 * 
 * 
 * 
 * Follow up:
 * 
 * 
 * If all integer numbers from the stream are between 0 and 100, how would you
 * optimize it?
 * If 99% of all integer numbers from the stream are between 0 and 100, how
 * would you optimize it?
 * 
 * 
 */
class MedianFinder {

    /** initialize your data structure here. */
    //private Sol1 sol = new Sol1();
    //private Sol2 sol = new Sol2();
    private Sol3 sol = new Sol3();
    public MedianFinder() {
    }
    
    public void addNum(int num) {
        sol.addNum(num);
    }
    
    public double findMedian() {
        return sol.findMedian();
    }
}

class Sol1 {
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;

    public Sol1() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((n1, n2) -> Integer.compare(n2, n1));
        minHeap.offer(Integer.MAX_VALUE);
        maxHeap.offer(Integer.MIN_VALUE);
    }

    public void addNum(int num) {
        if (num < maxHeap.peek()) {
            maxHeap.offer(num);
        }
        else {
            minHeap.offer(num);
        }

        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        }
        else if (minHeap.size() > maxHeap.size() + 1) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        long sum = (minHeap.size() > maxHeap.size()) ? 2L * minHeap.peek():
                  (maxHeap.size() > minHeap.size()) ? 2L * maxHeap.peek():
                  (1L * minHeap.peek() + 1L * maxHeap.peek());
        return sum / 2.0;
    }


}

class Sol2 {
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); //for the small half elements
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(); //for the large half elements
    public void addNum(int num) {//H.W.: forgot to initialize maxHeap with reverseOrder()
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        int sum = maxHeap.size() > minHeap.size() ? 
                  (maxHeap.peek() * 2) : (maxHeap.peek() + minHeap.peek());
        return sum / 2.0;
    }
}


class Sol3 {
    Counter smallCounter = new Counter();
    Counter largeCounter = new Counter();
    public void addNum(int num) {
        smallCounter.offer(num);
        largeCounter.offer(smallCounter.pollLast());
        if (largeCounter.size() > smallCounter.size()) {
            smallCounter.offer(largeCounter.pollFirst());
        }
    }
    public double findMedian() {
        int sum = smallCounter.size() > largeCounter.size() ?
                  (smallCounter.peekLast() * 2) : (smallCounter.peekLast() + largeCounter.peekFirst());
        return sum / 2.0;
    }

    class Counter {
        private int count = 0;
        private TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        public int size() {
            return count;
        }
        public void offer(int num) {
            count++;
            treeMap.put(num, treeMap.getOrDefault(num, 0) + 1);
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
    }
}



/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
