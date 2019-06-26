/*
 * @lc app=leetcode id=23 lang=java
 *
 * [23] Merge k Sorted Lists
 *
 * https://leetcode.com/problems/merge-k-sorted-lists/description/
 *
 * algorithms
 * Hard (34.78%)
 * Total Accepted:    400.6K
 * Total Submissions: 1.2M
 * Testcase Example:  '[[1,4,5],[1,3,4],[2,6]]'
 *
 * Merge k sorted linked lists and return it as one sorted list. Analyze and
 * describe its complexity.
 * 
 * Example:
 * 
 * 
 * Input:
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
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
    public ListNode mergeKLists(ListNode[] lists) {
        //return sol1(lists);  //Heap:     O(N * lgk) / O(k)
        //return sol2(lists);  //BF1:      O(N * k)   / O(1)
        //return sol3(lists);  //DC:       O(N * lgk) / O(lgk)
        //return sol3a(lists); //DC:       O(N * lgk) / O(1)
        //return sol4(lists);  //BF2:      O(N * k)   / O(1)
        //return sol5(lists);  //Sort:     O(N * lgN) / O(N)
        //return sol6(lists);  //TreeMap:  O(N * lgN) / O(N)
        return sol7(lists);    //Heap:     O(N * lgN) / O(N)
    }

    private ListNode sol1(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(17, 
                  (p1, p2) -> Integer.compare(p1.val, p2.val));
        for (ListNode h : lists) {
            if (h != null) {
                minHeap.offer(h);
            }
        }
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (!minHeap.isEmpty()) {
            ListNode p = minHeap.poll();
            tail.next = p;
            tail = p;
            if (p.next != null) {
                minHeap.offer(p.next);
            }
        }
        return dummyHead.next;
    }

    private ListNode sol2(ListNode[] lists) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;

        while (true) {
            int minIndex = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null && (minIndex == -1 || lists[i].val < lists[minIndex].val)) {
                    minIndex = i;
                }
            }
            if (minIndex < 0) {
                break;
            }
            tail.next = lists[minIndex];
            tail = tail.next;
            lists[minIndex] = lists[minIndex].next;
        }

        return dummyHead.next;
    }

    private ListNode sol3(ListNode[] lists) {
        return lists.length > 0 ? sol3(lists, 0, lists.length - 1) : null;
    }
    private ListNode sol3(ListNode[] lists, int left, int right) {
        if (left >= right) {
            return lists[left];
        }
        int mid = left + (right - left) / 2;
        ListNode leftRes = sol3(lists, left, mid);
        ListNode rightRes = sol3(lists, mid + 1, right);
        return merge(leftRes, rightRes);
    }
    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                tail.next = head1;
                head1 = head1.next;
            }
            else {
                tail.next = head2;
                head2 = head2.next;
            }
            tail = tail.next;
        }
        tail.next = head1 != null ? head1 : head2; //H.W.: forgot post-processing
        return dummyHead.next;
    }


    private ListNode sol3a(ListNode[] lists) {//discuss/10719/DC-python-solution-O(nk-log-k)-runtime-O(1)-space
        for (int num = lists.length; num > 1; num = (num + 1) / 2) {
            for (int left = 0, right = num - 1; left < right; left++, right--) {
                lists[left] = merge(lists[left], lists[right]);
            }
        }
        return lists.length > 0 ? lists[0] : null;
    }

    private ListNode sol4(ListNode[] lists) {
        for (int i = 1; i < lists.length; i++) {
            lists[0] = merge(lists[0], lists[i]);
        }
        return lists.length > 0 ? lists[0] : null;
    }

    private ListNode sol5(ListNode[] lists) {
        List<ListNode> oneList = new ArrayList<>();
        for (ListNode head : lists) {
            for (ListNode curr = head; curr != null; curr = curr.next) {
                oneList.add(curr);
            }
        }
        Collections.sort(oneList, (node1, node2) -> Integer.compare(node1.val, node2.val));
     
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        for (ListNode curr : oneList) {
            tail.next = curr;
            tail = tail.next;
        }
        tail.next = null;
        return dummyHead.next;
    }

    private ListNode sol6(ListNode[] lists) {
        Map<Integer, ListNode[]> map = new TreeMap<>();
        for (ListNode head : lists) {
            for (ListNode curr = head; curr != null; curr = curr.next) {
                ListNode[] frontRearPair = map.get(curr.val);
                if (frontRearPair == null) {
                    frontRearPair = new ListNode[]{curr, curr};
                    map.put(curr.val, frontRearPair);
                }
                else {
                    frontRearPair[1].next = curr;
                    frontRearPair[1] = curr;
                }
            }
        }

        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        for (ListNode[] frontRearPair : map.values()) {
            tail.next = frontRearPair[0];
            tail = frontRearPair[1];
        }
        tail.next = null;

        return dummyHead.next;
    }

    private ListNode sol7(ListNode[] lists) {
        Queue<ListNode> minHeap = new PriorityQueue<>(17, (p1, p2) -> Integer.compare(p1.val, p2.val));
        for (ListNode head : lists) {
            for (ListNode curr = head; curr != null; curr = curr.next) {
                minHeap.offer(curr);
            }
        }

        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (!minHeap.isEmpty()) {
            tail.next = minHeap.poll();
            tail = tail.next;
        }
        tail.next = null;

        return dummyHead.next;
    }
}

