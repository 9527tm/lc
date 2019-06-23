/*
 * @lc app=leetcode id=19 lang=java
 *
 * [19] Remove Nth Node From End of List
 *
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/description/
 *
 * algorithms
 * Medium (34.31%)
 * Total Accepted:    403.5K
 * Total Submissions: 1.2M
 * Testcase Example:  '[1,2,3,4,5]\n2'
 *
 * Given a linked list, remove the n-th node from the end of list and return
 * its head.
 * 
 * Example:
 * 
 * 
 * Given linked list: 1->2->3->4->5, and n = 2.
 * 
 * After removing the second node from the end, the linked list becomes
 * 1->2->3->5.
 * 
 * 
 * Note:
 * 
 * Given n will always be valid.
 * 
 * Follow up:
 * 
 * Could you do this in one pass?
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
       //return sol1(head, n); 
       //return sol2(head, n); 
       //return sol3(head, n); 
       return sol4(head, n); 
    }
    
    private ListNode sol1(ListNode head, int n) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode fast = dummyHead, slow = fast;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummyHead.next;
    }

    private ListNode sol2(ListNode head, int n) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode slow = dummyHead;
        for (ListNode fast = dummyHead; fast.next != null; fast = fast.next) {
            if (n > 0) {
                n--;
            }
            else {
                slow = slow.next;
            }
        }
        slow.next = slow.next.next;
        return dummyHead.next;
    }

    private ListNode sol3(ListNode head, int n) {
        return sol3(head, new int[]{n});
    }
    private ListNode sol3(ListNode head, int[] count) {
        if (head == null) {
            return null;
        }
        head.next = sol3(head.next, count);
        return count[0]-- == 1 ? head.next : head; //potentially underflow: count[0]-- 
    }                                              //if num of nodes in list > Integer.MAX_VALUE

    private ListNode sol4(ListNode head, int n) {
        return sol4_recursion(head, n) == n ? head.next : head;
    }
    private int sol4_recursion(ListNode head, int n) {
        if (head == null) {
            return 0;
        }
        int m = sol4_recursion(head.next, n);
        if (m == n) {
            head.next = head.next.next;
        }
        return m + 1;
    }
}
