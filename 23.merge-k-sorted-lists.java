/*
 * @lc app=leetcode id=23 lang=java
 *
 * [23] Merge k Sorted Lists
 *
 * https://leetcode.com/problems/merge-k-sorted-lists/description/
 *
 * algorithms
 * Hard (34.78%)
 * Total Accepted:    400.6K
 * Total Submissions: 1.2M
 * Testcase Example:  '[[1,4,5],[1,3,4],[2,6]]'
 *
 * Merge k sorted linked lists and return it as one sorted list. Analyze and
 * describe its complexity.
 * 
 * Example:
 * 
 * 
 * Input:
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 * 
 * 
 */
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        return sol1(lists); 
    }

    private ListNode sol1(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(17, 
                  (p1, p2) -> {return Integer.compare(p1.val, p2.val);});
        for (ListNode h : lists) {
            if (h != null) {
                minHeap.offer(h);
            }
        }
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (!minHeap.isEmpty()) {
            ListNode p = minHeap.poll();
            tail.next = p;
            tail = p;
            if (p.next != null) {
                minHeap.offer(p.next);
            }
        }
        return dummyHead.next;
    }
}
