/*
 * @lc app=leetcode id=146 lang=java
 *
 * [146] LRU Cache
 *
 * https://leetcode.com/problems/lru-cache/description/
 *
 * algorithms
 * Medium (27.54%)
 * Likes:    4272
 * Dislikes: 183
 * Total Accepted:    410.1K
 * Total Submissions: 1.4M
 * Testcase Example:  '["LRUCache","put","put","get","put","get","put","get","get","get"]\n' +
  '[[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]'
 *
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and put.
 * 
 * get(key) - Get the value (will always be positive) of the key if the key
 * exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present.
 * When the cache reached its capacity, it should invalidate the least recently
 * used item before inserting a new item.
 * 
 * The cache is initialized with a positive capacity.
 * 
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 * 
 * Example:
 * 
 * 
 * LRUCache cache = new LRUCache( 2 /* capacity */ );
 * 
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.put(4, 4);    // evicts key 1
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 * 
 * 
 * 
 * 
 */

// @lc code=start
class LRUCache {
    Sol1 sol1;
    public LRUCache(int capacity) {
        sol1 = new Sol1(capacity);
    }
    
    public int get(int key) {
        return sol1.get(key);
    }
    
    public void put(int key, int value) {
        sol1.put(key, value);
    }
}

class Sol1 {
    class ListNode {
        private ListNode prev, next;
        private int key, val;

        public ListNode(int key, int val) {
            this.key = key;
            this.val = val;
            prev = null;
            next = null;
        }
        //attachAfter / detach apply nodes 
        //other than dummyHead and dummyTail
        public void attachAfter(ListNode prevNode) {
            next = prevNode.next;
            next.prev = this;
            prev = prevNode;
            prevNode.next = this;
        }
        public void detachSelf() {
            prev.next = next;
            next.prev = prev;
        }
    }

    private int capacity;
    private ListNode dummyHead, dummyTail;
    private Map<Integer, ListNode> map;

    public Sol1(int capacity) {
        this.capacity = capacity; //assume capacity > 0
        dummyHead = new ListNode(0, 0);
        dummyTail = new ListNode(0, 0);
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
        map = new HashMap<>();
    }

    public int get(int key) {
        ListNode node = map.get(key);
        if (node == null) {
            return -1;
        }
        node.detachSelf();
        node.attachAfter(dummyHead);
        return node.val;
    }

    public void put(int key, int value) {
        ListNode node = map.get(key);
        if (node == null) {
            node = new ListNode(key, value);
            map.put(key, node);
        }
        else {
            node.detachSelf();
            node.val = value; //H.W.: forgot to update the value for a known key
        }
        node.attachAfter(dummyHead);
        if (map.size() > capacity) {
            ListNode node2 = dummyTail.prev;
            node2.detachSelf();
            map.remove(node2.key);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
// @lc code=end
