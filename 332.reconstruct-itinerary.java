class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        //return sol1(tickets);
        //return sol2(tickets);
        return sol3(tickets);
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
    
    //O(n) + O(n * lgn) + O(n) + O(n) / O(m + n) + (n)
    private List<String> sol2(List<List<String>> tickets) {
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
    
    //O(n) + O(n * lgn) + O(n) + O(n) / O(m + n) + (n)
    private List<String> sol3(List<List<String>> tickets) {
        Map<String, List<String>> map = new HashMap<>();
        for (List<String> ticket : tickets) {
            String src = ticket.get(0), dst = ticket.get(1);
            List<String> dstList = map.get(src);
            if (dstList == null) {
                dstList = new ArrayList<>();
                map.put(src, dstList);
            }
            dstList.add(dst);
        }
        for (List<String> dstList : map.values()) {//H.W.: missing reverse order sort
            Collections.sort(dstList, Collections.reverseOrder());  
        }
        
        List<String> res = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        stack.offerFirst("JFK");
        while (!stack.isEmpty()) {
            for (List<String> dstList = map.get(stack.peekFirst());
                dstList != null && !dstList.isEmpty();
                 dstList = map.get(stack.peekFirst())) {
                String dst = dstList.remove(dstList.size() - 1);
                stack.offerFirst(dst);
            }
            String src = stack.pollFirst();
            res.add(src);
        }
        Collections.reverse(res);
        return res;
    }
}
