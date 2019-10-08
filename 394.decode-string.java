/*
 * @lc app=leetcode id=394 lang=java
 *
 * [394] Decode String
 *
 * https://leetcode.com/problems/decode-string/description/
 *
 * algorithms
 * Medium (46.51%)
 * Total Accepted:    134.6K
 * Total Submissions: 288.9K
 * Testcase Example:  '"3[a]2[bc]"'
 *
 * Given an encoded string, return its decoded string.
 * 
 * The encoding rule is: k[encoded_string], where the encoded_string inside the
 * square brackets is being repeated exactly k times. Note that k is guaranteed
 * to be a positive integer.
 * 
 * You may assume that the input string is always valid; No extra white spaces,
 * square brackets are well-formed, etc.
 * 
 * Furthermore, you may assume that the original data does not contain any
 * digits and that digits are only for those repeat numbers, k. For example,
 * there won't be input like 3a or 2[4].
 * 
 * Examples:
 * 
 * 
 * s = "3[a]2[bc]", return "aaabcbc".
 * s = "3[a2[c]]", return "accaccacc".
 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 * 
 * 
 * 
 * 
 */
class Solution {
    public String decodeString(String s) {
        return sol1(s);    
    }

    private String sol1(String s) {
        StringBuilder builder = new StringBuilder();
        Deque<int[]> stack = new LinkedList<>();
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isLetter(ch)) {
                builder.append(ch);
            }
            else if (Character.isDigit(ch)) {
                num = num * 10 + ch - '0';
            }
            else if (ch == '[') {
                stack.offerFirst(new int[]{num, builder.length()}); //H.W.: WRONGLY offer from stack INSTEAD OF offerFirst
                num = 0;
            }
            else if (ch == ']') {
                int[] pair = stack.pollFirst();//H.W.: WRONGLY poll from stack INSTEAD OF pollFirst
                int times = pair[0], start = pair[1], end = builder.length();
                if (times == 0) {
                    builder.setLength(start);
                    continue;
                }
                for (int j = 1; j < times; j++) {
                    for (int k = start; k < end; k++) {
                        builder.append(builder.charAt(k));
                    }
                }
            }
        }
        return builder.toString();
    }
}
