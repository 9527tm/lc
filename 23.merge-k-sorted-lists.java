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
        //return sol1(lists); 
        //return sol2(lists); 
        return sol3(lists); 
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

    private ListNode sol2(ListNode[] lists) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;

        while (true) {
            int minIndex = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null && (minIndex == -1 || lists[i].val < lists[minIndex].val)) {
                    minIndex = i;
                }
            }
            if (minIndex < 0) {
                break;
            }
            tail.next = lists[minIndex];
            tail = tail.next;
            lists[minIndex] = lists[minIndex].next;
        }

        return dummyHead.next;
    }

    private ListNode sol3(ListNode[] lists) {
        return lists.length > 0 ? sol3(lists, 0, lists.length - 1) : null;
    }
    private ListNode sol3(ListNode[] lists, int left, int right) {
        if (left >= right) {
            return lists[left];
        }
        int mid = left + (right - left) / 2;
        ListNode leftRes = sol3(lists, left, mid);
        ListNode rightRes = sol3(lists, mid + 1, right);
        return merge(leftRes, rightRes);
    }
    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                tail.next = head1;
                head1 = head1.next;
            }
            else {
                tail.next = head2;
                head2 = head2.next;
            }
            tail = tail.next;
        }
        tail.next = head1 != null ? head1 : head2; //H.W.: forgot post-processing
        return dummyHead.next;
    }
}
