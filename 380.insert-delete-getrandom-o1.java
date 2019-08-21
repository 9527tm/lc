/*
 * @lc app=leetcode id=380 lang=java
 *
 * [380] Insert Delete GetRandom O(1)
 *
 * https://leetcode.com/problems/insert-delete-getrandom-o1/description/
 *
 * algorithms
 * Medium (42.95%)
 * Total Accepted:    128.5K
 * Total Submissions: 295.4K
 * Testcase Example:  '["RandomizedSet","insert","remove","insert","getRandom","remove","insert","getRandom"]\n[[],[1],[2],[2],[],[1],[2],[]]'
 *
 * Design a data structure that supports all following operations in average
 * O(1) time.
 * 
 * 
 * 
 * insert(val): Inserts an item val to the set if not already present.
 * remove(val): Removes an item val from the set if present.
 * getRandom: Returns a random element from current set of elements. Each
 * element must have the same probability of being returned.
 * 
 * 
 * 
 * Example:
 * 
 * // Init an empty set.
 * RandomizedSet randomSet = new RandomizedSet();
 * 
 * // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 * randomSet.insert(1);
 * 
 * // Returns false as 2 does not exist in the set.
 * randomSet.remove(2);
 * 
 * // Inserts 2 to the set, returns true. Set now contains [1,2].
 * randomSet.insert(2);
 * 
 * // getRandom should return either 1 or 2 randomly.
 * randomSet.getRandom();
 * 
 * // Removes 1 from the set, returns true. Set now contains [2].
 * randomSet.remove(1);
 * 
 * // 2 was already in the set, so return false.
 * randomSet.insert(2);
 * 
 * // Since 2 is the only number in the set, getRandom always return 2.
 * randomSet.getRandom();
 * 
 * 
 */
class RandomizedSet {
    private List<Integer> list;
    private Map<Integer, Integer> map;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        map.put(val, list.size());
        list.add(val);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        //return sol1(val);
        //return sol2(val);
        //return sol3(val);
        return sol4(val);
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        //assume at least one elem
        int randIndex = (int)(Math.random() * list.size());
        return list.get(randIndex);
    }

    private boolean sol1(int val) {
        Integer index = map.remove(val);
        if (index == null) {
            return false;
        }
        Integer tailVal = list.remove(list.size() - 1); //careful removal by int: index in O(1) instead of by value in O(N)
        if (index < list.size()) {
            list.set(index, tailVal); //overwrite the deleted elem by the tail elem
            map.put(tailVal, index);
        }
        return true;
    }

    private boolean sol2(int val) {
        Integer index = map.remove(val);
        if (index == null) {
            return false;
        }
        if (index == list.size() - 1) {
            Integer dummyVal = list.remove(list.size() - 1); //remove by int(index) in O(1);
            return true;
        }
        Integer tailVal = list.remove(list.size() - 1); //remove by int(index) in O(1);
        list.set(index, tailVal);
        map.put(tailVal, index);
        return true;
    }

    private boolean sol3(int val) {
        Integer index = map.remove(val);
        if (index == null) {
            return false;
        }
        list.set(index, list.get(list.size() - 1));
        list.remove(list.size() - 1);
        if (index < list.size()) {
            map.put(list.get(index), index);
        }
        return true;
    }

    private boolean sol4(int val) {
        Integer index = map.get(val);
        if (index == null) {
            return false;
        }
        if (index == list.size() - 1) {
            list.remove(list.size() - 1);
            map.remove(val);
        }
        else {
            list.set(index, list.get(list.size() - 1));
            map.put(list.get(index), index);
            list.remove(list.size() - 1);
            map.remove(val);
        }
        return true;
    }

}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
