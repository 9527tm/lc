/*
 * @lc app=leetcode id=20 lang=java
 *
 * [20] Valid Parentheses
 *
 * https://leetcode.com/problems/valid-parentheses/description/
 *
 * algorithms
 * Easy (36.66%)
 * Total Accepted:    610.8K
 * Total Submissions: 1.7M
 * Testcase Example:  '"()"'
 *
 * Given a string containing just the characters '(', ')', '{', '}', '[' and
 * ']', determine if the input string is valid.
 * 
 * An input string is valid if:
 * 
 * 
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * 
 * 
 * Note that an empty string is also considered valid.
 * 
 * Example 1:
 * 
 * 
 * Input: "()"
 * Output: true
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: "()[]{}"
 * Output: true
 * 
 * 
 * Example 3:
 * 
 * 
 * Input: "(]"
 * Output: false
 * 
 * 
 * Example 4:
 * 
 * 
 * Input: "([)]"
 * Output: false
 * 
 * 
 * Example 5:
 * 
 * 
 * Input: "{[]}"
 * Output: true
 * 
 * 
 */
class Solution {
    public boolean isValid(String s) {
        //return sol1(s); 
        //return sol2(s); 
        //return sol22(s); 
        //return sol3(s); 
        return sol4(s); 
        //return sol5(s); 

    }

    private boolean sol1(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')'); map.put('[', ']'); map.put('{', '}');
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map.containsKey(ch)) {
                stack.offerFirst(ch);
            }
            else if (!stack.isEmpty() && map.get(stack.peekFirst()) == ch) {
                stack.pollFirst();
            }
            else {
                return false;
            }
        }
        return stack.isEmpty();
    }

    private boolean sol2(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.offerFirst(ch);
            }
            else {
                if (!stack.isEmpty()) {
                    char ch2 = stack.peekFirst();
                    if ((ch == ')' && ch2 == '(') ||
                        (ch == ']' && ch2 == '[') ||
                        (ch == '}' && ch2 == '{')) {
                        stack.pollFirst();
                    }
                    else {
                        return false;
                    }
                }
                else {//H.W.: missing the case -- "]"
                    return false;
                }
            } 
        }
        return stack.isEmpty();
    }

    private boolean sol22(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.offerFirst(ch);
            }
            else {
                if (stack.isEmpty()) {
                    return false;
                }
                char ch2 = stack.pollFirst();
                if ((ch2 == '(' && ch != ')') || 
                    (ch2 == '[' && ch != ']') || 
                    (ch2 == '{' && ch != '}')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private boolean sol3(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.offerFirst(')');
            }
            else if (ch == '[') {
                stack.offerFirst(']');
            }
            else if (ch == '{') {
                stack.offerFirst('}');
            }
            else if (stack.isEmpty() || stack.pollFirst() != ch) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    private boolean sol4(String s) {
        char[] array = s.toCharArray();
        int i = 0;
        for (int j = 0; j < array.length; j++) {
            switch(array[j]) {
                case '(': 
                    array[i++] = ')'; 
                    break;
                case '[': 
                    array[i++] = ']'; 
                    break;
                case '{': 
                    array[i++] = '}'; 
                    break;
                default:
                    if (i <= 0 || array[--i] != array[j]) {
                        return false;
                    }
            }
        }
        return i == 0;
    }

    private boolean sol5(String s) {
        String table = "()[]{}";
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int pos = table.indexOf(ch);
            if (pos % 2 == 0) {
                stack.offerFirst(pos + 1);
            }
            else if (stack.isEmpty() || stack.pollFirst() != pos) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
