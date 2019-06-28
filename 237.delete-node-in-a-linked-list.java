/*
 * @lc app=leetcode id=237 lang=java
 *
 * [237] Delete Node in a Linked List
 *
 * https://leetcode.com/problems/delete-node-in-a-linked-list/description/
 *
 * algorithms
 * Easy (53.89%)
 * Total Accepted:    292.4K
 * Total Submissions: 541.6K
 * Testcase Example:  '[4,5,1,9]\n5'
 *
 * Write a function to delete a node (except the tail) in a singly linked list,
 * given only access to that node.
 * 
 * Given linked list -- head = [4,5,1,9], which looks like following:
 * 
 * 
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: head = [4,5,1,9], node = 5
 * Output: [4,1,9]
 * Explanation: You are given the second node with value 5, the linked list
 * should become 4 -> 1 -> 9 after calling your function.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: head = [4,5,1,9], node = 1
 * Output: [4,5,9]
 * Explanation: You are given the third node with value 1, the linked list
 * should become 4 -> 5 -> 9 after calling your function.
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * The linked list will have at least two elements.
 * All of the nodes' values will be unique.
 * The given node will not be the tail and it will always be a valid node of
 * the linked list.
 * Do not return anything from your function.
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
    public void deleteNode(ListNode node) {// Wierd question!
        sol(node);    //O(1) / O(1)

        //sol1(node); //O(N) / O(1)
        //sol2(node); //O(N) / O(1)
        //sol3(node); //O(N) / O(N)
    }

    private void sol(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    private void sol1(ListNode node) {
        while (node.next.next != null) {
            node.val = node.next.val;
            node = node.next;
        }
        node.val = node.next.val;
        node.next = null;
    }

    private void sol2(ListNode node) {
        ListNode prev2 = null, prev = node;
        for (ListNode curr = node.next; curr != null; curr = curr.next) {
            prev.val = curr.val;
            prev2 = prev;
            prev = curr;
        }
        prev2.next = null;
    }

    private void sol3(ListNode node) {
        node.val = node.next.val;
        if (node.next.next == null) {
            node.next = null;
            return;
        }
        sol3(node.next);
    }
}
