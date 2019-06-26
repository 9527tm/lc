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
        //return sol1(head, k);    
        //return sol2(head, k);    
        return sol1a(head, k);
        //return sol2a(head, k);
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

    private ListNode sol1a(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        ListNode nextK = head;
        for (int i = 0; i < k; i++) {//H.W.: missing the case: [],0
            if (nextK == null) {
                return head;
            }
            nextK = nextK.next;
        }
        ListNode newHead = sol1a_r1(head, nextK);
        head.next = sol1a(nextK, k);
        return newHead;
    }
    private ListNode sol1a_r1(ListNode head, ListNode tail) {
        if (head.next == tail) {
            return head;
        }
        ListNode newHead = sol1a_r1(head.next, tail);
        head.next.next = head;
        //head.next = tail; // = null;
        return newHead;
    }

    private ListNode sol2(ListNode head, int k) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        ListNode curr = head;
        while (curr != null) {
            int i = 0;
            ListNode curr2 = curr;
            while (i < k && curr2 != null) {
                i++;
                curr2 = curr2.next;
            }
            if (i < k) {
                tail.next = curr; //H.W.: missing the chaining
                break;
            }

            ListNode prev = null, curr3 = curr;
            while (curr3 != curr2) {
                ListNode next = curr3.next;
                curr3.next = prev;
                prev = curr3;
                curr3 = next;
            }
         
            tail.next = prev;
            curr.next = curr2;
            tail = curr;
            curr = curr2;
        }
        return dummyHead.next;
    }

    private ListNode sol2a(ListNode head, int k) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        tail.next = head;

        ListNode currK = head;
        while (currK != null) {
            ListNode nextK = currK;
            for (int i = 0; i < k; i++) {
                if (nextK == null) {
                    return dummyHead.next;
                }
                nextK = nextK.next;
            }

            ListNode prev = nextK, curr = currK; //prev = null;
            while (curr != nextK) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            //currK.next = nextK;
            tail.next = prev;

            tail = currK;
            currK = nextK;
        }

        return dummyHead.next;
    }

    private ListNode sol3(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        tail.next = head;

        int i = 0;
        ListNode curr = head, prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;

            if (++i % k == 0) {
                ListNode newTail = tail.next;
                newTail.next = next; // = curr;
                tail.next = prev;
                tail = newTail;
            }
        }

        if (i % k != 0) {
            //ListNode newTail = tail.next;
            //newTail.next = null;
            tail.next = sol3(prev, i % k);
            //prev.next = null;
        }

        return dummyHead.next;
    }
}
