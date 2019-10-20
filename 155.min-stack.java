/*
 * @lc app=leetcode id=155 lang=java
 *
 * [155] Min Stack
 *
 * https://leetcode.com/problems/min-stack/description/
 *
 * algorithms
 * Easy (38.95%)
 * Likes:    2195
 * Dislikes: 240
 * Total Accepted:    360.9K
 * Total Submissions: 919.6K
 * Testcase Example:  '["MinStack","push","push","push","getMin","pop","top","getMin"]\n' +
  '[[],[-2],[0],[-3],[],[],[],[]]'
 *
 * Design a stack that supports push, pop, top, and retrieving the minimum
 * element in constant time.
 * 
 * 
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 * 
 * 
 * 
 * 
 * Example:
 * 
 * 
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> Returns -3.
 * minStack.pop();
 * minStack.top();      --> Returns 0.
 * minStack.getMin();   --> Returns -2.
 * 
 * 
 * 
 * 
 */

// @lc code=start
class MinStack {

    /** initialize your data structure here. */
    private Sol sol = new Sol1();
    public MinStack() {
    }
    
    public void push(int x) {
        sol.push(x); 
    }
    
    public void pop() {
        sol.pop(); 
    }
    
    public int top() {
        return sol.top(); 
    }
    
    public int getMin() {
        return sol.getMin(); 
    }
}

interface Sol {
    public void push(int x);
    public void pop();
    public int top();
    public int getMin();
}

class Sol1 implements Sol {
    private Deque<Integer> s1, s2;
    public Sol1() {
        s1 = new LinkedList<>(); //all stack
        s2 = new LinkedList<>(); //min stack
    }
    public void push(int x) {
        if (s2.isEmpty() || s2.peekFirst() >= x) {//H.W.: wrongly use comparator: ... <= x
            s2.offerFirst(x);
        }
        s1.offerFirst(x);
    }
    public void pop() {
        //assert s1 / s2 is not empty
        if (s2.peekFirst().equals(s1.peekFirst())) {//H.W.: wrongly use == for Integers
            s2.pollFirst();
        }
        s1.pollFirst();
    }
    public int top() {
        //assert s1 / s2 is not empty
        return s1.peekFirst();
    }
    public int getMin() {
        //assert s1 / s2 is not empty
        return s2.peekFirst();
    }
}



/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
// @lc code=end
