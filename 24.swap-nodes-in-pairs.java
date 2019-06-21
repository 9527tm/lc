/*
 * @lc app=leetcode id=24 lang=java
 *
 * [24] Swap Nodes in Pairs
 *
 * https://leetcode.com/problems/swap-nodes-in-pairs/description/
 *
 * algorithms
 * Medium (45.00%)
 * Total Accepted:    322.4K
 * Total Submissions: 716.5K
 * Testcase Example:  '[1,2,3,4]'
 *
 * Given a linked list, swap every two adjacent nodes and return its head.
 * 
 * You may not modify the values in the list's nodes, only nodes itself may be
 * changed.
 * 
 * 
 * 
 * Example:
 * 
 * 
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
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
    public ListNode swapPairs(ListNode head) {
        return sol0(head);
    }

    private ListNode sol0(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        //split
        ListNode head1 = head, curr1 = head1;
        ListNode head2 = head.next, curr2 = head2;
        int i = 0;
        for (ListNode p = head.next.next; p != null; p = p.next) {
            if (i++ % 2 == 0) {
                curr1.next = p;
                curr1 = p;
            }
            else {
                curr2.next = p;
                curr2 = p;
            }
        }
        curr1.next = null;
        curr2.next = null;

        //merge
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        curr1 = head1;
        curr2 = head2;
        while (curr2 != null) {
            tail.next = curr2;
            tail = curr2;
            curr2 = curr2.next;

            tail.next = curr1;
            tail = curr1;
            curr1 = curr1.next;
        }
        tail.next = curr1;

        return dummyHead.next;
    }
}
