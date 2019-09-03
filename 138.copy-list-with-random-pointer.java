/*
 * @lc app=leetcode id=138 lang=java
 *
 * [138] Copy List with Random Pointer
 *
 * https://leetcode.com/problems/copy-list-with-random-pointer/description/
 *
 * algorithms
 * Medium (28.34%)
 * Total Accepted:    271.9K
 * Total Submissions: 952.6K
 * Testcase Example:  '{"$id":"1","next":{"$id":"2","next":null,"random":{"$ref":"2"},"val":2},"random":{"$ref":"2"},"val":1}'
 *
 * A linked list is given such that each node contains an additional random
 * pointer which could point to any node in the list or null.
 * 
 * Return a deep copy of the list.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * 
 * 
 * Input:
 * 
 * {"$id":"1","next":{"$id":"2","next":null,"random":{"$ref":"2"},"val":2},"random":{"$ref":"2"},"val":1}
 * 
 * Explanation:
 * Node 1's value is 1, both of its next and random pointer points to Node 2.
 * Node 2's value is 2, its next pointer points to null and its random pointer
 * points to itself.
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * You must return the copy of the given head as a reference to the cloned
 * list.
 * 
 * 
 */
/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
*/
class Solution {
    public Node copyRandomList(Node head) {
        //return sol1(head); 
        //return sol2(head); 
        return sol3(head); 
    }

    private Node sol1(Node head) {
        Map<Node, Node> map = new HashMap<>();
        Node dummyHead = new Node();
        Node tail = dummyHead;
        for (Node curr = head; curr != null; curr = curr.next) {
            Node copy = new Node(curr.val, null, null);
            map.put(curr, copy);
            tail.next = copy;
            tail = tail.next;
        }

        tail = dummyHead.next;
        for (Node curr = head; curr != null; curr = curr.next) {
            if (curr.random != null) {
                tail.random = map.get(curr.random);
            }
            tail = tail.next;
        }
        return dummyHead.next;
    }

    private Node sol2(Node head) {
        if (head == null) {
            return null;
        }
        Map<Node, Node> map = new HashMap<>();
        map.put(head, new Node());
        for (Node curr = head; curr != null; curr = curr.next) {
            Node copy = map.get(curr);
            copy.val = curr.val;
            if (curr.next != null) {
                Node copyNext = map.get(curr.next);
                if (copyNext == null) {
                    copyNext = new Node();
                    map.put(curr.next, copyNext);
                }
                copy.next = copyNext;
            }
            if (curr.random != null) {
                Node copyRandom = map.get(curr.random);
                if (copyRandom == null) {
                    copyRandom = new Node();
                    map.put(curr.random, copyRandom);
                }
                copy.random = copyRandom;
            } 
        }
        return map.get(head);
    }

    ///problems/copy-list-with-random-pointer/discuss/43491/A-solution-with-constant-space-complexity-O(1)-and-linear-time-complexity-O(N)
    private Node sol3(Node head) {
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val, curr.next, null); //H.W.: wrongly think => copy.random = curr
            curr.next = copy;
            curr = copy.next;
        }

        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        Node copyDummyHead = new Node();
        Node copyPrev = copyDummyHead;
        curr = head;
        while (curr != null) {
            copyPrev.next = curr.next;
            copyPrev = copyPrev.next;
            curr.next = curr.next.next;
            curr = curr.next;
        }
        return copyDummyHead.next;
    }
}
