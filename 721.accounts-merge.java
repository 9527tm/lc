/*
 * @lc app=leetcode id=721 lang=java
 *
 * [721] Accounts Merge
 *
 * https://leetcode.com/problems/accounts-merge/description/
 *
 * algorithms
 * Medium (42.91%)
 * Likes:    859
 * Dislikes: 226
 * Total Accepted:    50.3K
 * Total Submissions: 114.6K
 * Testcase Example:  '[["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]'
 *
 * Given a list accounts, each element accounts[i] is a list of strings, where
 * the first element accounts[i][0] is a name, and the rest of the elements are
 * emails representing emails of the account.
 * 
 * Now, we would like to merge these accounts.  Two accounts definitely belong
 * to the same person if there is some email that is common to both accounts.
 * Note that even if two accounts have the same name, they may belong to
 * different people as people could have the same name.  A person can have any
 * number of accounts initially, but all of their accounts definitely have the
 * same name.
 * 
 * After merging the accounts, return the accounts in the following format: the
 * first element of each account is the name, and the rest of the elements are
 * emails in sorted order.  The accounts themselves can be returned in any
 * order.
 * 
 * Example 1:
 * 
 * Input: 
 * accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John",
 * "johnnybravo@mail.com"], ["John", "johnsmith@mail.com",
 * "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
 * Output: [["John", 'john00@mail.com', 'john_newyork@mail.com',
 * 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary",
 * "mary@mail.com"]]
 * Explanation: 
 * The first and third John's are the same person as they have the common email
 * "johnsmith@mail.com".
 * The second John and Mary are different people as none of their email
 * addresses are used by other accounts.
 * We could return these lists in any order, for example the answer [['Mary',
 * 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
 * ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']]
 * would still be accepted.
 * 
 * 
 * 
 * Note:
 * The length of accounts will be in the range [1, 1000].
 * The length of accounts[i] will be in the range [1, 10].
 * The length of accounts[i][j] will be in the range [1, 30].
 * 
 */

// @lc code=start
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        //return sol1(accounts); 
        return sol2(accounts); 
    }

    //O(n^2*mk) / O(nmk)
    private List<List<String>> sol1(List<List<String>> accounts) {
        //assume accounts is not null nor empty and one email for one name at least
        Map<String, Set<Set<String>>> map = new HashMap<>(); //name -> [email-set1, email-set2]
        for (List<String> list : accounts) {
            String name = list.get(0);
            Set<String> emails = new HashSet<>(list);
            emails.remove(name);

            Set<Set<String>> emailsSetUnderOneName = map.get(name);
            if (emailsSetUnderOneName == null) {
                emailsSetUnderOneName = new HashSet<>();
                map.put(name, emailsSetUnderOneName);
            }
            
            Set<Set<String>> toBeDeletedEmails = new HashSet<>();
            for (Set<String> existedEmails : emailsSetUnderOneName) {
                if (isIntersected(existedEmails, emails)) {
                    emails.addAll(existedEmails); //H.W.: forgot the case [[1, 4], [6, 9]]
                    toBeDeletedEmails.add(existedEmails);//merged by [2, 7]
                }
            }
            emailsSetUnderOneName.removeAll(toBeDeletedEmails); //H.W.: Concurrent Modification Error
            emailsSetUnderOneName.add(emails);
        }

        List<List<String>> res = new ArrayList<>();
        for (String name : map.keySet()) {
            Set<Set<String>> emailsSetUnderOneName = map.get(name);
            for (Set<String> emails : emailsSetUnderOneName) {
                List<String> list = new ArrayList<>();
                res.add(list); //H.W.: forgot to add to reslult
                list.add(name);

                List<String> formattedEmails = new ArrayList<>(emails);
                Collections.sort(formattedEmails);
                list.addAll(formattedEmails); //H.W.: miss the requirement of sorted order emails 
            }                                 //      intentionally hidden by interviewer
        }

        return res;
    }

    private boolean isIntersected(Set<String> set1, Set<String> set2) {
        if (set1.size() > set2.size()) {
            return isIntersected(set2, set1);
        }
        for (String s : set1) {
            if (set2.contains(s)) {
                return true;
            }
        }
        return false;
    }

    //https://leetcode.com/problems/accounts-merge/discuss/140978/Easy-to-Understand-Union-Find-in-Java-95
    //O(nm*lgnm) / O(nm)
    private List<List<String>> sol2(List<List<String>> accounts) {
        UnionFind uf = new UnionFind(accounts.size());//O(n) / O(n)
        Map<String, Integer> map1 = new HashMap<>();//email -> userId
        for (int i = 0; i < accounts.size(); i++) {//O(nm*lgn) / O(nm)
            for (int j = 1; j < accounts.get(i).size(); j++) {
                Integer userId = map1.putIfAbsent(accounts.get(i).get(j), i);
                if (userId != null) {
                    uf.union(uf.find(i), uf.find(userId));
                }
            }
        }
        
        Map<Integer, Set<String>> map2 = new HashMap<>(); //userId -> emails
        for (int i = 0; i < accounts.size(); i++) {//O(nm*lgn) / O(nm)
            Set<String> set = map2.computeIfAbsent(uf.find(i), k -> new HashSet<>());
            set.addAll(accounts.get(i));
            set.remove(accounts.get(i).get(0)); //exclude leading name
        }

        List<List<String>> res = new ArrayList<>();
        for (int i : map2.keySet()) {//O(nm*lgnm) / O(nm)
            List<String> list = new ArrayList<>(map2.get(i));
            Collections.sort(list);
            list.add(0, accounts.get(i).get(0));
            res.add(list);
        }
        return res;
    }

    class UnionFind {
        private int[] parent;
        private int[] size;
        public UnionFind(int capacity) {
            parent = new int[capacity];
            size = new int[capacity];
            for (int i = 0; i < capacity; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        public int find(int i) {
            while (parent[i] != i) {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return i;
        }
        public void union(int i, int j) {
            int root1 = find(i);
            int root2 = find(j);
            if (root1 == root2) {
                return;
            }
            if (size[root1] < size[root2]) {
                parent[root1] = root2;
                size[root2] += size[root1];
            }
            else {
                parent[root2] = root1;
                size[root1] += size[root2];
            }
        }
    }

    /* WRONG to recursively union

    //O(mn) + O(mn*lg(mn)) / O(mn) + O(mn)
    private List<List<String>> sol2(List<List<String>> accounts) {
        Map<String, List<Integer>> map = new HashMap<>();//email -> [index1, index2]
        for (int i = 0; i < accounts.size(); i++) {
            List<Integer> groupList = null;
            for (int j = 1; j < accounts.get(i).size() && groupList == null; j++) {
                groupList = map.get(accounts.get(i).get(j)); //H.W.: j wrongly starts 0
            }                                                //only emails to be processed
            if (groupList == null) {
                groupList = new ArrayList<>();
            }
            
            groupList.add(i);
            for (int j = 1; j < accounts.get(i).size(); j++) {//H.W.: j wrongly starts 0
                map.put(accounts.get(i).get(j), groupList);
            }
        }

        List<List<String>> res = new ArrayList<>();
        Set<List<Integer>> uniqGroupLists = new HashSet<>(map.values()); //H.W.: forget to dedup
        for (List<Integer> groupList : uniqGroupLists) {
            Set<String> set = new HashSet<>();
            for (int i : groupList) {
                set.addAll(accounts.get(i));
            }
            String name = accounts.get(groupList.get(0)).get(0);
            set.remove(name);
            List<String> list = new ArrayList<>(set);//emails => list
            Collections.sort(list);
            list.add(0, name);//name + emails => list
            res.add(list);
        }
        return res;
    }
    */
}
// @lc code=end
