/*
 * @lc app=leetcode id=12 lang=java
 *
 * [12] Integer to Roman
 *
 * https://leetcode.com/problems/integer-to-roman/description/
 *
 * algorithms
 * Medium (52.24%)
 * Total Accepted:    269K
 * Total Submissions: 514.6K
 * Testcase Example:  '3'
 *
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D
 * and M.
 * 
 * 
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 
 * For example, two is written as II in Roman numeral, just two one's added
 * together. Twelve is written as, XII, which is simply X + II. The number
 * twenty seven is written as XXVII, which is XX + V + II.
 * 
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is
 * written as IV. Because the one is before the five we subtract it making
 * four. The same principle applies to the number nine, which is written as IX.
 * There are six instances where subtraction is used:
 * 
 * 
 * I can be placed before V (5) and X (10) to make 4 and 9. 
 * X can be placed before L (50) and C (100) to make 40 and 90. 
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * 
 * 
 * Given an integer, convert it to a roman numeral. Input is guaranteed to be
 * within the range from 1 to 3999.
 * 
 * Example 1:
 * 
 * 
 * Input: 3
 * Output: "III"
 * 
 * Example 2:
 * 
 * 
 * Input: 4
 * Output: "IV"
 * 
 * Example 3:
 * 
 * 
 * Input: 9
 * Output: "IX"
 * 
 * Example 4:
 * 
 * 
 * Input: 58
 * Output: "LVIII"
 * Explanation: L = 50, V = 5, III = 3.
 * 
 * 
 * Example 5:
 * 
 * 
 * Input: 1994
 * Output: "MCMXCIV"
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 * 
 */
class Solution {
    public String intToRoman(int num) {
        //return sol1(num);   //4    scan on whole table [inefficient]  
        //return sol2(num);   //4.5  binary search [a little better than sol1] 
        //return sol3(num);   //5    bitmap encoding 
        //return sol3a(num);  //5    more concise than sol3 
        //return sol4(num);   //5.5  perfect jump tables: greedy scanning [descending, part, no padding, combine]
        return sol5(num);     //5.5  perfect jump tables: direct indexing [ascending, whole, padding, seperate]
    }                         //insight: how to construct jump tables?
                              //-- [ascending / descending, part / whole, zero padding / no padding, seperate / combine] 
    private String sol1(int num) {
        int[] weights = {3000, 2000, 1000, 
                         900, 800, 700, 600, 500, 400, 300, 200, 100, 
                         90, 80, 70, 60, 50, 40, 30, 20, 10, 
                         9, 8, 7, 6, 5, 4, 3, 2, 1};
        String[] scales = {"MMM", "MM", "M", 
                         "CM", "DCCC", "DCC", "DC", "D", "CD", "CCC", "CC", "C",  //H.W.: 800 => MCCC 
                         "XC", "LXXX", "LXX", "LX", "L", "XL", "XXX", "XX", "X",
                         "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"}; //H.W.: 8 => XIII
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < weights.length; i++) {
            if (num >= weights[i]) {
                builder.append(scales[i]);
                num -= weights[i];
            }
        }
        return builder.toString();
    }

    private String sol2(int num) {
        int[] weights = {3000, 2000, 1000, 
                         900, 800, 700, 600, 500, 400, 300, 200, 100, 
                         90, 80, 70, 60, 50, 40, 30, 20, 10, 
                         9, 8, 7, 6, 5, 4, 3, 2, 1};
        String[] scales = {"MMM", "MM", "M", 
                         "CM", "DCCC", "DCC", "DC", "D", "CD", "CCC", "CC", "C",  //H.W.: 800 => MCCC 
                         "XC", "LXXX", "LXX", "LX", "L", "XL", "XXX", "XX", "X",
                         "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"}; //H.W.: 8 => XIII
        StringBuilder builder = new StringBuilder();
        int left = 0, right = weights.length - 1;
        while (left <= right && num > 0) {
            int i = findLargestSmaller(weights, left, right, num); 
            builder.append(scales[i]);//guarantee to find
            num -= weights[i];
            left = i + 1;
        }
        return builder.toString();
    }

    private int findLargestSmaller(int[] weights, int left, int right, int target) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            /*if (weights[mid] == target) {
                return mid;
            }*/ //optimize searching from unique weights
            if (weights[mid] > target) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }
        return left;
    }

    private String sol3(int num) {
        char[][] scales = {{'M', '.', '?'}, {'C', 'D', 'M'}, {'X', 'L', 'C'}, {'I', 'V', 'X'}};
        int[][] encodings = {{}, {0}, {0, 0}, {0, 0, 0}, {0, 1}, {1}, {1, 0}, {1, 0, 0}, {1, 0, 0, 0}, {0, 2}};
        char[] buffer = new char[4 * 4 - 1]; //H.W.: longest string is NOT MMM-CCC-XXX-III
        int len = 0;                         //                        BUT MMM-DCCC-LXXX-VIII
        for (int i = 0, base = 1000; i < scales.length; i += 1, base /= 10) {
            //if (num >= base) {
                len = helper3(buffer, len, encodings[num / base], scales[i]);
                num %= base;
            //}
        }
        return new String(buffer, 0, len); //H.W.: no function call new String(buffer, len);
    }

    private int helper3(char[] buffer, int len, int[] encoding, char[] scale) {//1 <= digit <= 9
        for (int code : encoding) {
            buffer[len++] = scale[code];
        }
        return len;
    }

    private String sol3a(int num) {
        char[][] scales = {{'M', '.', '?'}, {'C', 'D', 'M'}, {'X', 'L', 'C'}, {'I', 'V', 'X'}};
        int[][] encodings = {{}, {0}, {0, 0}, {0, 0, 0}, {0, 1}, {1}, {1, 0}, {1, 0, 0}, {1, 0, 0, 0}, {0, 2}};
        char[] buffer = new char[4 * 4 - 1]; //H.W.: longest string is NOT MMM-CCC-XXX-III
        int len = 0;                         //                        BUT MMM-DCCC-LXXX-VIII
        for (int i = 0, base = 1000; i < scales.length; i += 1, base /= 10) {
            for (int code : encodings[num / base]) {
                buffer[len++] = scales[i][code];
            }
            num %= base;
        }
        return new String(buffer, 0, len); //H.W.: no function call new String(buffer, len);
    }

    private String sol4(int num) {
        int[] weights = {1000, 
                         900, 500, 400, 100, 
                         90, 50, 40, 10, 
                         9, 5, 4, 1};
        String[] scales = {"M", 
                         "CM", "D", "CD", "C",  
                         "XC", "L", "XL", "X",
                         "IX", "V", "IV", "I"}; 
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < weights.length; i++) {
            while (num >= weights[i]) {
                builder.append(scales[i]);
                num -= weights[i];
            }
        }
        return builder.toString();
    }

    private String sol5(int num) {
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds =  {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens =      {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones =      {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return new StringBuilder().
                append(thousands[num / 1000]).
                append(hundreds[(num % 1000) / 100]).
                append(tens[(num % 100) / 10]).
                append(ones[num % 10]).
                toString();
    }
}
