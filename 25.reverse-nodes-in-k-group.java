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
        //return sol1a(head, k);
        //return sol2a(head, k);
        //return sol3(head, k);
        //return sol3a(head, k);
        //return sol4(head, k);
        return sol5(head, k);
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
                newTail.next = next; // = curr; //H.W.: wrongly cut this line => 
                tail.next = prev;               //      so, the head of the next group cannot be saved.
                tail = newTail;
            }
        }

        if (i % k != 0) {
            ListNode newTail = tail.next;
            newTail.next = null;               //H.W.: wrongly cut this line
            tail.next = sol3(prev, i % k);     //      since, newTail.next is actually not to null
                                               //      but to the tail of the last group.
            //prev.next = null;                //this is safe.
        }

        return dummyHead.next;
    }

    private ListNode sol3a(ListNode head, int k) {
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
                newTail.next = next; //H.W.: lost the chaining
                tail.next = prev;
                tail = newTail;
            }
        }

        if (k > 0 && i % k != 0) {
            ListNode newTail = tail.next;
            newTail.next = null;     //H.W.: forgot to de-chain
            tail.next = sol3a(prev, i % k);
        }

        return dummyHead.next;
    }

    private ListNode sol4(ListNode head, int k) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        tail.next = head;
        
        ListNode curr = head, prev = null;
        for (int num = getLength(head); k > 0 && num >= k; num -= k) {
            for (int i = 0; i < k; i++) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            ListNode newTail = tail.next;
            tail.next = prev;
            newTail.next = curr;
            tail = newTail;
        }
        return dummyHead.next;
    }
    private int getLength(ListNode head) {
        int num = 0;
        for (ListNode curr = head; curr != null; curr = curr.next) {
            num++;
        }
        return num;
    }

    /*
Problem: LC25 - Reverse the linked list in a group of K nodes

Solution:

1. We need to maintain a result list.
   Assume: its head is saved in a "dummyHead" and its tail is "tail".

2. We use a "curr" pointer to iterate the given list from "head" node.
   We always try to find out the first K nodes starting from "curr" node.
   We group them as the first node "groupHead" and the last node "groupTail".

   There will be two cases: C1 and C2.

C1: If the group has less than K nodes:
    We chain the first one of the group after the tail of the result list
    and goto 3.

C2: Otherwise, we follow these procedures:
    P1: we move curr to the next node of groupTail (for next group).
    P2: we reverse the group of K nodes.
        after that, the group begins at groupTail and finishes at groupHead.
    P3: we chain the first node of reversed group after the result list.
        also note that the result list now has the new end at groupHead.
    P4: goto 2 to process the next group of nodes remained in the list.

3. We return the head of the result list as the answer.
     */
    
    private ListNode sol5(ListNode head, int k) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;

        ListNode curr = head;
        while (curr != null) {
            ListNode groupHead = curr;
            ListNode groupTail = findKth(curr, k);
            if (groupTail == null) {
                tail.next = groupHead;
                break;
            }
            curr = groupTail.next;
            reverse(groupHead, k);
            tail.next = groupTail;
            tail = groupHead;
        }
        return dummyHead.next;
    }

    private ListNode findKth(ListNode head, int k) {
        ListNode curr = head;
        for (int i = 1; i < k && curr != null; i++) {
            curr = curr.next;
        }
        return curr;
    }

    private void reverse(ListNode head, int k) {
        ListNode curr = head, prev = null;
        for (int i = 0; i < k; i++) {//curr is not null: guaranteed
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
    }
}
