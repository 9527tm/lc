/*
 * @lc app=leetcode id=981 lang=java
 *
 * [981] Time Based Key-Value Store
 *
 * https://leetcode.com/problems/time-based-key-value-store/description/
 *
 * algorithms
 * Medium (51.60%)
 * Likes:    374
 * Dislikes: 50
 * Total Accepted:    30.9K
 * Total Submissions: 59.8K
 * Testcase Example:  '["TimeMap","set","get","get","set","get","get"]\n' +
  '[[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]'
 *
 * Create a timebased key-value store class TimeMap, that supports two
 * operations.
 * 
 * 1. set(string key, string value, int timestamp)
 * 
 * 
 * Stores the key and value, along with the given timestamp.
 * 
 * 
 * 2. get(string key, int timestamp)
 * 
 * 
 * Returns a value such that set(key, value, timestamp_prev) was called
 * previously, with timestamp_prev <= timestamp.
 * If there are multiple such values, it returns the one with the largest
 * timestamp_prev.
 * If there are no values, it returns the empty string ("").
 * 
 * 
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: inputs = ["TimeMap","set","get","get","set","get","get"], inputs =
 * [[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]
 * Output: [null,null,"bar","bar",null,"bar2","bar2"]
 * Explanation:   
 * TimeMap kv;   
 * kv.set("foo", "bar", 1); // store the key "foo" and value "bar" along with
 * timestamp = 1   
 * kv.get("foo", 1);  // output "bar"   
 * kv.get("foo", 3); // output "bar" since there is no value corresponding to
 * foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 ie
 * "bar"   
 * kv.set("foo", "bar2", 4);   
 * kv.get("foo", 4); // output "bar2"   
 * kv.get("foo", 5); //output "bar2"   
 * 
 * 
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: inputs = ["TimeMap","set","set","get","get","get","get","get"],
 * inputs =
 * [[],["love","high",10],["love","low",20],["love",5],["love",10],["love",15],["love",20],["love",25]]
 * Output: [null,null,null,"","high","high","low","low"]
 * 
 * 
 * 
 * 
 * 
 * 
 * Note:
 * 
 * 
 * All key/value strings are lowercase.
 * All key/value strings have length in the range [1, 100]
 * The timestamps for all TimeMap.set operations are strictly increasing.
 * 1 <= timestamp <= 10^7
 * TimeMap.set and TimeMap.get functions will be called a total of 120000 times
 * (combined) per test case.
 * 
 * 
 */

// @lc code=start
class TimeMap {

    /** Initialize your data structure here. */
    Sol sol = new Sol1();
    public TimeMap() {
        
    }
    
    public void set(String key, String value, int timestamp) {
        sol.set(key, value, timestamp); 
    }
    
    public String get(String key, int timestamp) {
        return sol.get(key, timestamp); 
    }
}

interface Sol {
    public void set(String key, String value, int timestamp);
    public String get(String key, int timestamp); 
}

class Sol1 implements Sol {
    private Map<String, TreeMap<Integer, String>> db = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        TreeMap<Integer, String> table = db.get(key);
        if (table == null) {
            table = new TreeMap<>();
            db.put(key, table);
        }
        table.put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer, String> table = db.get(key);
        if (table == null) {
            return "";
        }
        Map.Entry<Integer, String> entry = table.floorEntry(timestamp);
        return entry != null ? entry.getValue() : "";
    }
}
    
/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
// @lc code=end
