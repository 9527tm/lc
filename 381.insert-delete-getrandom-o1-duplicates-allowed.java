/*
 * @lc app=leetcode id=381 lang=java
 *
 * [381] Insert Delete GetRandom O(1) - Duplicates allowed
 *
 * https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/description/
 *
 * algorithms
 * Hard (32.10%)
 * Total Accepted:    45.8K
 * Total Submissions: 141.1K
 * Testcase Example:  '["RandomizedCollection","insert","insert","insert","getRandom","remove","getRandom"]\n[[],[1],[1],[2],[],[1],[]]'
 *
 * Design a data structure that supports all following operations in average
 * O(1) time.
 * Note: Duplicate elements are allowed.
 * 
 * 
 * insert(val): Inserts an item val to the collection.
 * remove(val): Removes an item val from the collection if present.
 * getRandom: Returns a random element from current collection of elements. The
 * probability of each element being returned is linearly related to the number
 * of same value the collection contains.
 * 
 * 
 * 
 * Example:
 * 
 * // Init an empty collection.
 * RandomizedCollection collection = new RandomizedCollection();
 * 
 * // Inserts 1 to the collection. Returns true as the collection did not
 * contain 1.
 * collection.insert(1);
 * 
 * // Inserts another 1 to the collection. Returns false as the collection
 * contained 1. Collection now contains [1,1].
 * collection.insert(1);
 * 
 * // Inserts 2 to the collection, returns true. Collection now contains
 * [1,1,2].
 * collection.insert(2);
 * 
 * // getRandom should return 1 with the probability 2/3, and returns 2 with
 * the probability 1/3.
 * collection.getRandom();
 * 
 * // Removes 1 from the collection, returns true. Collection now contains
 * [1,2].
 * collection.remove(1);
 * 
 * // getRandom should return 1 and 2 both equally likely.
 * collection.getRandom();
 * 
 * 
 */
class RandomizedCollection {
    private Sol1 sol;
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        sol = new Sol1();
    }
    
    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        return sol.insert(val);
    }
    
    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        return sol.remove(val);
    }
    
    /** Get a random element from the collection. */
    public int getRandom() {
        return sol.getRandom();
    }

    public interface InfRC {
        boolean insert(int val);
        boolean remove(int val);
        int getRandom();
    }

    static class Sol1 implements InfRC {
        private List<Integer> dbList;
        private Map<Integer, Set<Integer>> indexMap;
        public Sol1() {
            dbList = new ArrayList<>();
            indexMap = new HashMap<>();
        }
        public boolean insert(int val) {
            Set<Integer> indexSet = indexMap.get(val);
            if (indexSet == null) {
                indexSet = new HashSet<>();
                indexMap.put(val, indexSet);
            }
            indexSet.add(dbList.size());
            dbList.add(val);
            return indexSet.size() == 1;
        }
        public boolean remove(int val) {
            Set<Integer> indexSet = indexMap.get(val);
            if (indexSet == null) {
                return false;
            }
            int targetIndex = indexSet.iterator().next();
            indexSet.remove(targetIndex);
            if (indexSet.isEmpty()) {
                indexMap.remove(val);
            }
            int lastIndex = dbList.size() - 1;
            int tailVal = dbList.remove(lastIndex);
            if (targetIndex < lastIndex) {//targetIndex is not the old last index
                Set<Integer> tailIndexSet = indexMap.get(tailVal);
                tailIndexSet.remove(lastIndex);
                tailIndexSet.add(targetIndex);
                dbList.set(targetIndex, tailVal);
            }
            return true;
        }
        public int getRandom() {
            //assume dbList is not empty
            return dbList.get((int)(dbList.size() * Math.random()));
        }
    }
 }

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
