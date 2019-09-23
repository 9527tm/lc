/*
 * @lc app=leetcode id=2 lang=java
 *
 * [2] Add Two Numbers
 *
 * https://leetcode.com/problems/add-two-numbers/description/
 *
 * algorithms
 * Medium (31.62%)
 * Total Accepted:    1M
 * Total Submissions: 3.2M
 * Testcase Example:  '[2,4,3]\n[5,6,4]'
 *
 * You are given two non-empty linked lists representing two non-negative
 * integers. The digits are stored in reverse order and each of their nodes
 * contain a single digit. Add the two numbers and return it as a linked list.
 * 
 * You may assume the two numbers do not contain any leading zero, except the
 * number 0 itself.
 * 
 * Example:
 * 
 * 
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //return sol1(l1, l2); 
        return sol2(l1, l2); 
    }

    private ListNode sol1(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode prev = dummyHead;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;
            int sum = val1 + val2 + carry;
            ListNode curr = new ListNode(sum % 10);
            prev.next = curr;
            prev = curr;
            carry = sum / 10;
            l1 = (l1 != null) ? l1.next : null;
            l2 = (l2 != null) ? l2.next : null;
        }
        return dummyHead.next;
    }

    private ListNode sol2(ListNode l1, ListNode l2) {
        ListNode h1 = reverse(l1), h2 = reverse(l2);
        ListNode head = sol2NonReverse(h1, h2);
        l1 = reverse(h1);
        l2 = reverse(h2);
        return reverse(head);
    }

    private ListNode sol2NonReverse(ListNode l1, ListNode l2) {
        int n1 = len(l1), n2 = len(l2);
        int[] carry = new int[1];
        ListNode head = n1 > n2 ? sol2Helper(l1, l2, n1 - n2, carry) : sol2Helper(l2, l1, n2 - n1, carry);
        if (carry[0] > 0) {
            ListNode extraHead = new ListNode(1);
            extraHead.next = head;
            head = extraHead;
        }
        return head;
    }

    private ListNode sol2Helper(ListNode l1, ListNode l2, int diff, int[] carry) {//len(l1) >= len(l2)
        if (l1 == null) {
            return null;
        }
        ListNode head = new ListNode(0);
        head.next = diff > 0 ? sol2Helper(l1.next, l2, diff - 1, carry) : sol2Helper(l1.next, l2.next, 0, carry);
        int sum = diff > 0 ? (l1.val + carry[0]) : (l1.val + l2.val + carry[0]);
        head.val = sum % 10;
        carry[0] = sum / 10;
        return head;
    }

    private int len(ListNode l1) {
        int num = 0;
        while (l1 != null) {
            l1 = l1.next;
            num++;
        }
        return num;
    }

    private ListNode reverse(ListNode l1) {
        if (l1 == null || l1.next == null) {
            return l1;
        }
        ListNode newHead = reverse(l1.next);
        l1.next.next = l1;
        l1.next = null;
        return newHead;
    }
}
