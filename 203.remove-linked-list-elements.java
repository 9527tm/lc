/*
 * @lc app=leetcode id=203 lang=java
 *
 * [203] Remove Linked List Elements
 *
 * https://leetcode.com/problems/remove-linked-list-elements/description/
 *
 * algorithms
 * Easy (35.89%)
 * Total Accepted:    231K
 * Total Submissions: 642.9K
 * Testcase Example:  '[1,2,6,3,4,5,6]\n6'
 *
 * Remove all elements from a linked list of integers that have value val.
 * 
 * Example:
 * 
 * 
 * Input:  1->2->6->3->4->5->6, val = 6
 * Output: 1->2->3->4->5
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
    public ListNode removeElements(ListNode head, int val) {
        //return sol1(head, val); 
        //return sol2(head, val); 
        return sol3(head, val); 
    }

    private ListNode sol1(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        head.next = sol1(head.next, val);
        return head.val != val ? head : head.next;
    }

    private ListNode sol2(ListNode head, int val) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        for (ListNode curr = head; curr != null; curr = curr.next) {
            if (curr.val != val) {
                tail.next = curr;
                tail = tail.next;
            }
        }
        tail.next = null; //H.W.: forgot post-processing
        return dummyHead.next;
    }

    private ListNode sol3(ListNode head, int val) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode prev = dummyHead;
        while (prev.next != null) {
            if (prev.next.val != val) {
                prev = prev.next;
            }
            else {
                prev.next = prev.next.next;
            }
        }
        return dummyHead.next;
    }
}
