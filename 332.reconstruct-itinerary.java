/*
 * @lc app=leetcode id=332 lang=java
 *
 * [332] Reconstruct Itinerary
 *
 * https://leetcode.com/problems/reconstruct-itinerary/description/
 *
 * algorithms
 * Medium (32.71%)
 * Total Accepted:    99.7K
 * Total Submissions: 302.9K
 * Testcase Example:  '[["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]'
 *
 * Given a list of airline tickets represented by pairs of departure and
 * arrival airports [from, to], reconstruct the itinerary in order. All of the
 * tickets belong to a man who departs from JFK. Thus, the itinerary must begin
 * with JFK.
 * 
 * Note:
 * 
 * 
 * If there are multiple valid itineraries, you should return the itinerary
 * that has the smallest lexical order when read as a single string. For
 * example, the itinerary ["JFK", "LGA"] has a smaller lexical order than
 * ["JFK", "LGB"].
 * All airports are represented by three capital letters (IATA code).
 * You may assume all tickets form at least one valid itinerary.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
 * Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
 * 
 * 
 * Example 2:
 * 
 * 
 * Input:
 * [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * Explanation: Another possible reconstruction is
 * ["JFK","SFO","ATL","JFK","ATL","SFO"].
 * But it is larger in lexical order.
 * 
 * 
 */
class Solution {
    /*A - Euler Path/Curcuit exists if:
      1. Graph is connected (BFS / DFS)
      2. Either of both subconditions holds:
      2.1. Euler Path: 
           All vertices except one (Source) and the other (Sink) have even degrees, 
           and for each vertex indegree equals outdegree. 
           (Source: indegree = outdegree - 1; Sink: indegree = outdegree + q)
      2.2. Euler Curcuit:
           All vertices have even degrees, and for each vertex indegree equals outdegree.
           Each vertex can be a Source and natually the vertex will be the Sink.
      
      B - How to find Euler Path/Curcuit?
      1. Brutal Force: O(k ^ n) + O(n!) / O(n))
      2. Hierholzer:   O(n) / O(n)
      
      https://zhuanlan.zhihu.com/p/37693521
      https://www.laioffer.com/zh/videos/2018-03-14-332-reconstruct-itinerary/
    */
    
    public List<String> findItinerary(List<List<String>> tickets) {
        //return sol1(tickets);  //5.0: BF -- 1. choose key to dedup a ticket (avoid revisiting)
                                 //        -- 2. reverse sort adj city list and add/remove from tail
        
        //return sol2(tickets);  //6.0: Hier. -- 1. adj city list is minHeap (pq)
                                 //Recursive  -- 2. post order traversal
                                 //           -- 3. reverse the result list at last (like topological sort)
        
        return sol2a(tickets);   //6.0: Hier. -- 1. 3. the same as sol2
                                 //Iterative  -- 2. update source city when a ticket is put into stack
    
        //Why we prefer min heap to reverse sort list to store dst citys from a src city?
        //1. it is more clear to represent dst citys semanticly as a queue: 
        //   each dst city is offerred to / polled from adj min heap once and only once.
        //2. we avoid modifying a list (remove the last city) while iterating on it.
        //   though a list works: https://leetcode.com/submissions/detail/270752870/
    }
    
    //O(n) + O(n * lgn) + O(k ^ n) / O(m + n) + O(n) + O(n)
    //n -- # of tickets, m -- # of cities, k -- max(# of dist citys of a src city)
    //O(k ^ n) <= O(n!), O(m) <= (n)
    private List<String> sol1(List<List<String>> tickets) {
        Map<String, List<Integer>> graph = createGraph(tickets);
        List<String> res = new ArrayList<>();
        res.add("JFK");
        dfs(graph, tickets, "JFK", new HashSet<>(), res);
        return res;
    }
    
    //key of visited set (edges here) is ticket-index
    //but it can also be a combinative string: "src-city-string" + "dst-city-index in adj-city-list"
    //JFK -> [SFO, DFW, LAS]: the ticket JFK->DFW has the key JFK1 and JFK->LAS has JFK2.
    private boolean dfs(Map<String, List<Integer>> graph, List<List<String>> tickets, 
                        String src, Set<Integer> edges, List<String> res) {
        if (edges.size() >= tickets.size()) {
            return true;
        }
        List<Integer> adjList = graph.get(src);
        if (adjList != null) {
            for (int i : adjList) {
                if (edges.add(i)) {
                    String dst = tickets.get(i).get(1);
                    res.add(dst);
                    if (dfs(graph, tickets, dst, edges, res)) {
                        return true;
                    }
                    res.remove(res.size() - 1);
                    edges.remove(i);
                }
            }
        }
        return false;
    }
    
    private Map<String, List<Integer>> createGraph(List<List<String>> tickets) {
        Map<String, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < tickets.size(); i++) {
            List<String> trip = tickets.get(i);
            String src = trip.get(0);
            List<Integer> adjList = graph.get(src);
            if (adjList == null) {
                adjList = new ArrayList<>();
                graph.put(src, adjList);
            }
            adjList.add(i);
        }
        
        for (List<Integer> adjList : graph.values()) {
            Collections.sort(adjList, (i, j) -> tickets.get(i).get(1).compareTo(tickets.get(j).get(1)));
        }
        return graph;
    }
    
    //O(n * lgk) + O(n * lgk)  + O(n) / O(m + n) + O(n)
    private List<String> sol2(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = initGraph(tickets);
        List<String> res = new ArrayList<>();
        dfs(graph, "JFK", res); //Hierholzer
        Collections.reverse(res); //Tricky 2: reverse result
        return res;
    }
    
    private void dfs(Map<String, PriorityQueue<String>> map, String src, List<String> res) {
        PriorityQueue<String> dstMinHeap = map.get(src);
        while (dstMinHeap != null && !dstMinHeap.isEmpty()) {//H.W.: wrongly use if (....) 
            dfs(map, dstMinHeap.poll(), res); //Tricky 3: must remove to avoid revisiting
        }
        res.add(src); //Tricky 1: post order traversal
    }
    
    private Map<String, PriorityQueue<String>> initGraph(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> ticket : tickets) {
            String src = ticket.get(0), dst = ticket.get(1);
            PriorityQueue<String> dstMinHeap = graph.get(src);
            if (dstMinHeap == null) {
                dstMinHeap = new PriorityQueue<>();
                graph.put(src, dstMinHeap);
            }
            dstMinHeap.add(dst);
        }
        return graph;
    }
    
    //O(n * lgk) + O(n * lgk) + (n) / O(m + n) + (n)
    private List<String> sol2a(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (List<String> ticket : tickets) {
            String src = ticket.get(0), dst = ticket.get(1);
            PriorityQueue<String> dstMinHeap = map.get(src);
            if (dstMinHeap == null) {
                dstMinHeap = new PriorityQueue<>();
                map.put(src, dstMinHeap);
            }
            dstMinHeap.add(dst);
        }
        
        List<String> res = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        stack.offerFirst("JFK");
        while (!stack.isEmpty()) {
            String src = stack.peekFirst();
            PriorityQueue<String> dstMinHeap = map.get(src);
            while (dstMinHeap != null && !dstMinHeap.isEmpty()) {
                stack.offerFirst(dstMinHeap.poll());
                src = stack.peekFirst(); //TRICKY: update src from the latest stack top
                dstMinHeap = map.get(src);
            }
            res.add(stack.pollFirst());
        }
        Collections.reverse(res);
        return res;
    }
}
