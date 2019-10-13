/*
 * @lc app=leetcode id=393 lang=java
 *
 * [393] UTF-8 Validation
 *
 * https://leetcode.com/problems/utf-8-validation/description/
 *
 * algorithms
 * Medium (36.42%)
 * Total Accepted:    40.7K
 * Total Submissions: 111.5K
 * Testcase Example:  '[197,130,1]'
 *
 * A character in UTF8 can be from 1 to 4 bytes long, subjected to the
 * following rules:
 * 
 * For 1-byte character, the first bit is a 0, followed by its unicode code.
 * For n-bytes character, the first n-bits are all one's, the n+1 bit is 0,
 * followed by n-1 bytes with most significant 2 bits being 10.
 * 
 * This is how the UTF-8 encoding would work:
 * 
 * ⁠  Char. number range  |        UTF-8 octet sequence
 * ⁠     (hexadecimal)    |              (binary)
 * ⁠  --------------------+---------------------------------------------
 * ⁠  0000 0000-0000 007F | 0xxxxxxx
 * ⁠  0000 0080-0000 07FF | 110xxxxx 10xxxxxx
 * ⁠  0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
 * ⁠  0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
 * 
 * 
 * Given an array of integers representing the data, return whether it is a
 * valid utf-8 encoding.
 * 
 * 
 * Note:
 * The input is an array of integers. Only the least significant 8 bits of each
 * integer is used to store the data. This means each integer represents only 1
 * byte of data.
 * 
 * 
 * 
 * Example 1:
 * 
 * data = [197, 130, 1], which represents the octet sequence: 11000101 10000010
 * 00000001.
 * 
 * Return true.
 * It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte
 * character.
 * 
 * 
 * 
 * 
 * Example 2:
 * 
 * data = [235, 140, 4], which represented the octet sequence: 11101011
 * 10001100 00000100.
 * 
 * Return false.
 * The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes
 * character.
 * The next byte is a continuation byte which starts with 10 and that's
 * correct.
 * But the second continuation byte does not start with 10, so it is invalid.
 * 
 * 
 */
class Solution {
    public boolean validUtf8(int[] data) {
        //return sol1(data); //5.0
        //return sol2(data); //5.5
        return sol3(data);   //6.0
    }
    
    private boolean sol1(int[] data) {
        int i = 0;
        while (i < data.length) {
            int size = checkLeader(data[i]);
            if (size <= 0 || i + size > data.length) {
                return false; 
            }                 
            for (int j = 1; j < size; j++) {
                if (!checkFollower(data[i + j])) {
                    return false;
                }
            }
            i += size;
        }
        return true;
    }
    
    private int checkLeader(int code) {
        //System.out.println("check leader: " + toBinaryString(code));
        int num = 0;
        for (int mask = 0x01 << 7; (mask & code) != 0; mask >>= 1) {
            num++;
        }
        if (num == 1 || num > 4) {//ret: -1 -- error
            return -1;//H.W.: forgot checking size > 4 : ex:, [250,145,145,145,145]
        }             //https://leetcode.com/problems/utf-8-validation/discuss/87468
        return num == 0 ? 1 : num;
    }
    
    private boolean checkFollower(int code) {
        //System.out.println("check follower: " + toBinaryString(code));
        int mask1 = 0x01 << 7;
        int mask2 = 0x01 << 6;
        return (code & mask1) != 0 && (code & mask2) == 0;
    }
    
    private String toBinaryString(int code) {
        return String.format("%8s", Integer.toBinaryString(code & 0xFF)).replace(' ', '0');
    }
    
    
    private boolean sol2(int[] data) {
        int i = 0;
        while (i < data.length) {
            int size = countOnes(data[i]); //check leader
            if (size == 1 || size > 4) {
                return false;
            }//valid sizes are: 0, 2, 3, 4
            
            for (int j = 1; j < size; j++) {//check follower
                if (i + j >= data.length || countOnes(data[i + j]) != 1) {
                    return false;
                }
            }
            i += (size == 0) ? 1 : size;
        }
        return true;
    }
    
    private int countOnes(int code) {
        int num = 0;
        for (int mask = 0x01 << 7; (mask & code) != 0; mask >>= 1) {
            num++;
        }
        return num;
    }
    
    private boolean sol3(int[] data) {
        int num = 0; //the number of followers
        for (int code : data) {
            if (num == 0) {
                if ((code >> 7) == 0b0) {
                    num = 0;
                }
                else if ((code >> 6) == 0b10) {
                    return false;
                }
                else if ((code >> 5) == 0b110) {
                    num = 1;
                }
                else if ((code >> 4) == 0b1110) {
                    num = 2;
                }
                else if ((code >> 3) == 0b11110) {
                    num = 3;
                }
                else {
                    return false;
                }
            }
            else {
                if ((code >> 6) != 0b10) {
                    return false;
                }
                num--;
            }
        }
        return num == 0;
    }
}
