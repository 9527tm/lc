/*
 * @lc app=leetcode id=937 lang=java
 *
 * [937] Reorder Log Files
 *
 * https://leetcode.com/problems/reorder-log-files/description/
 *
 * algorithms
 * Easy (55.97%)
 * Total Accepted:    38.3K
 * Total Submissions: 68.6K
 * Testcase Example:  '["a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"]'
 *
 * You have an array of logs.  Each log is a space delimited string of words.
 * 
 * For each log, the first word in each log is an alphanumeric identifier.
 * Then, either:
 * 
 * 
 * Each word after the identifier will consist only of lowercase letters,
 * or;
 * Each word after the identifier will consist only of digits.
 * 
 * 
 * We will call these two varieties of logs letter-logs and digit-logs.  It is
 * guaranteed that each log has at least one word after its identifier.
 * 
 * Reorder the logs so that all of the letter-logs come before any digit-log.
 * The letter-logs are ordered lexicographically ignoring identifier, with the
 * identifier used in case of ties.  The digit-logs should be put in their
 * original order.
 * 
 * Return the final order of the logs.
 * 
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: ["a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"]
 * Output: ["g1 act car","a8 act zoo","ab1 off key dog","a1 9 2 3 1","zo4 4
 * 7"]
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * 0 <= logs.length <= 100
 * 3 <= logs[i].length <= 100
 * logs[i] is guaranteed to have an identifier, and a word after the
 * identifier.
 * 
 * 
 * 
 */
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        return sol1(logs); 
    }

    
    private String[] sol1(String[] logs) {
        Arrays.sort(logs, 
                    (log1, log2) -> 
                    {
                        String[] logArr1 = log1.split(" ", 2);
                        String[] logArr2 = log2.split(" ", 2);
                        String id1 = logArr1[0], text1 = logArr1[1];
                        String id2 = logArr2[0], text2 = logArr2[1];
                        boolean isDigitLog1 = judgeDigitLog(text1);
                        boolean isDigitLog2 = judgeDigitLog(text2);
                        if (isDigitLog1 && isDigitLog2) {
                            return 0;
                        }
                        else if (!isDigitLog1 && !isDigitLog2) {
                            int cmp = text1.compareTo(text2);
                            return cmp != 0 ? cmp : id1.compareTo(id2);
                        }
                        return isDigitLog1 ? 1 : -1;
                    });
        return logs;
    }

    private boolean judgeDigitLog(String log) {
        for (int i = 0; i < log.length(); i++) {
            char ch = log.charAt(i);
            if (ch != ' ' && !Character.isDigit(ch)) {//H.W.: missing spaces inside the text
                return false;
            }
        }
        return true;
    }
}
