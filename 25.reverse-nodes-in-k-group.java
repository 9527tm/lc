/*
 * @lc app=leetcode id=25 lang=java
 *
 * [25] Reverse Nodes in k-Group
 *
 * https://leetcode.com/problems/reverse-nodes-in-k-group/description/
 *
 * algorithms
 * Hard (36.71%)
 * Total Accepted:    188.3K
 * Total Submissions: 512.8K
 * Testcase Example:  '[1,2,3,4,5]\n2'
 *
 * Given a linked list, reverse the nodes of a linked list k at a time and
 * return its modified list.
 * 
 * k is a positive integer and is less than or equal to the length of the
 * linked list. If the number of nodes is not a multiple of k then left-out
 * nodes in the end should remain as it is.
 * 
 * 
 * 
 * 
 * Example:
 * 
 * Given this linked list: 1->2->3->4->5
 * 
 * For k = 2, you should return: 2->1->4->3->5
 * 
 * For k = 3, you should return: 3->2->1->4->5
 * 
 * Note:
 * 
 * 
 * Only constant extra memory is allowed.
 * You may not alter the values in the list's nodes, only nodes itself may be
 * changed.
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
    public ListNode reverseKGroup(ListNode head, int k) {
        return sol1(head, k);    
    }

    private ListNode sol1(ListNode head, int k) {
        ListNode head2 = head;
        int i = 0;
        while (i < k && head2 != null) {
            head2 = head2.next;
            i++;
        }
        if (i < k) {//H.W.: wrongly count number by if (head2 == null) {
            return head;
        }

        ListNode newHead = sol1_reverse1Node(head, head2);
        head.next = sol1(head2, k);
        return newHead;
    }
    private ListNode sol1_reverse1Node(ListNode head, ListNode tail) {
        if (head.next == tail) {
            return head;
        }
        ListNode newHead = sol1_reverse1Node(head.next, tail);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
