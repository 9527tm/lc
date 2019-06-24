/*
 * @lc app=leetcode id=21 lang=java
 *
 * [21] Merge Two Sorted Lists
 *
 * https://leetcode.com/problems/merge-two-sorted-lists/description/
 *
 * algorithms
 * Easy (47.58%)
 * Total Accepted:    601.1K
 * Total Submissions: 1.3M
 * Testcase Example:  '[1,2,4]\n[1,3,4]'
 *
 * Merge two sorted linked lists and return it as a new list. The new list
 * should be made by splicing together the nodes of the first two lists.
 * 
 * Example:
 * 
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        //return sol1(l1, l2);
        //return sol2(l1, l2);
        return sol22(l1, l2);
    }

    private ListNode sol1(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead, p1 = l1, p2 = l2;
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                tail.next = p1;
                p1 = p1.next;
            }
            else {
                tail.next = p2;
                p2 = p2.next;
            }
            tail = tail.next;
        }
        tail.next = p1 != null ? p1 : p2;
        return dummyHead.next;
    }

    private ListNode sol2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = sol2(l1.next, l2);
            return l1;
        }
        l2.next = sol2(l1, l2.next);
        return l2;
    }

    private ListNode sol22(ListNode l1, ListNode l2) {
        if (l1 != null && l2 != null) {
            if (l2.val < l1.val) {
                ListNode tmp = l1;
                l1 = l2;
                l2 = tmp;
            }
            l1.next = sol22(l1.next, l2);
        }
        return l1 != null ? l1 : l2;
    }
}
