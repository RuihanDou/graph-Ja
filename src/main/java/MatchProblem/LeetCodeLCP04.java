package MatchProblem;


import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * LCP 04. 覆盖
 * 你有一块棋盘，棋盘上有一些格子已经坏掉了。你还有无穷块大小为1 * 2的多米诺骨牌，你想把这些骨牌不重叠地覆盖在完好的格子上，请找出你最多能在棋盘上放多少块骨牌？这些骨牌可以横着或者竖着放。
 *
 *
 *
 * 输入：n, m代表棋盘的大小；broken是一个b * 2的二维数组，其中每个元素代表棋盘上每一个坏掉的格子的位置。
 *
 * 输出：一个整数，代表最多能在棋盘上放的骨牌数。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 2, m = 3, broken = [[1, 0], [1, 1]]
 * 输出：2
 * 解释：我们最多可以放两块骨牌：[[0, 0], [0, 1]]以及[[0, 2], [1, 2]]。（见下图）
 *
 *
 *
 *
 * 示例 2：
 *
 * 输入：n = 3, m = 3, broken = []
 * 输出：4
 * 解释：下图是其中一种可行的摆放方式
 *
 *
 *
 *
 * 限制：
 *
 * 1 <= n <= 8
 * 1 <= m <= 8
 * 0 <= b <= n * m
 */
public class LeetCodeLCP04 {

    class Graph {

        private int V;
        private int E;
        private TreeSet<Integer>[] adj;
        private boolean directed;
        private int[] indegrees, outdegrees;

        public Graph(int V, boolean directed){
            this.V = V;
            this.directed = directed;
            this.E = 0;

            adj = new TreeSet[V];
            for(int i = 0; i < V; i ++)
                adj[i] = new TreeSet<>();
        }


        public Graph(TreeSet<Integer>[] adj, boolean directed){
            this.adj = adj;
            this.directed = directed;
            this.V = adj.length;
            this.E = 0;
            indegrees = new int[V];
            outdegrees = new int[V];

            for (int v = 0; v < V; v++){
                for (int w : adj[v]){
                    outdegrees[v]++;
                    indegrees[w]++;
                    this.E++;
                }
            }

            if(!directed){
                this.E /= 2;
            }
        }

        public Graph reverseGraph(){
            TreeSet<Integer>[] rAdj = new TreeSet[V];
            for(int i = 0; i < V; i++){
                rAdj[i] = new TreeSet<>();
            }
            for(int v = 0; v < V; v++){
                for(int w : adj(v)){
                    rAdj[w].add(v);
                }
            }

            return new Graph(rAdj, directed);
        }

        public boolean isDirected(){
            return directed;
        }

        public void validateVertex(int v){
            if(v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + "is invalid");
        }

        public int V(){
            return V;
        }

        public int E(){
            return E;
        }

        public boolean hasEdge(int v, int w){
            validateVertex(v);
            validateVertex(w);
            return adj[v].contains(w);
        }

        public Iterable<Integer> adj(int v){
            validateVertex(v);
            return adj[v];
        }

        // 只对无向图
        public int degree(int v){
            if(directed){
                throw new RuntimeException("degree only works in undirected graph.");
            }
            validateVertex(v);
            return adj[v].size();
        }

        public int indegree(int v){
            if(!directed){
                throw new RuntimeException("indegree only works in directed graph.");
            }
            validateVertex(v);
            return indegrees[v];
        }

        public int outdegree(int v){
            if(!directed){
                throw new RuntimeException("outdegree only works in directed graph.");
            }
            validateVertex(v);
            return outdegrees[v];
        }

        public void addEdge(int a, int b){

            validateVertex(a);
            validateVertex(b);

            if(a == b) throw new IllegalArgumentException("Self Loop is Detected!");
            if(adj[a].contains(b)) throw new IllegalArgumentException("Parallel Edges are Detected!");

            adj[a].add(b);
            if(!directed)
                adj[b].add(a);
            this.E ++;
        }


        public void removeEdge(int v, int w){
            validateVertex(v);
            validateVertex(w);

            if(adj[v].contains(w)){
                E--;

                if(directed){
                    outdegrees[v]--;
                    indegrees[w]--;
                }

            }

            adj[v].remove(w);
            if(!directed){
                adj[w].remove(v);
            }
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();

            sb.append(String.format("V = %d, E = %d, directed = %b\n", V, E, directed));
            for(int v = 0; v < V; v ++){
                sb.append(String.format("%d : ", v));
                for(int w : adj[v]){
                    sb.append(String.format("%d ", w));
                }
                sb.append('\n');
            }
            return sb.toString();
        }

    }

    class WeightedGraph {

        private int V;
        private int E;
        private TreeMap<Integer, Integer>[] adj;
        private boolean directed;

        public WeightedGraph(int V, boolean directed){
            this.V = V;
            this.directed = directed;
            this.E = 0;

            adj = new TreeMap[V];
            for(int i = 0; i < V; i++){
                adj[i] = new TreeMap<>();
            }
        }

        public void addEdge(int a, int b, int weight){
            validateVertex(a);
            validateVertex(b);

            if(a == b){
                throw new IllegalArgumentException("Self Loop is Detected!");
            }

            if(adj[a].containsKey(b)){
                throw new IllegalArgumentException("Parallel Edges are Detected!");
            }

            adj[a].put(b, weight);
            if(!directed){
                adj[b].put(a, weight);
            }

            this.E++;
        }

        public boolean isDirected(){
            return directed;
        }

        public void validateVertex(int v){
            if(v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is invalid");
        }

        public int V(){
            return V;
        }

        public int E(){
            return E;
        }

        public boolean hasEdge(int v, int w){
            validateVertex(v);
            validateVertex(w);
            return adj[v].containsKey(w);
        }

        public Iterable<Integer> adj(int v){
            validateVertex(v);
            return adj[v].keySet();
        }

        public int getWeight(int v, int w){
            if(hasEdge(v, w)){
                return adj[v].get(w);
            }
            throw new IllegalArgumentException(String.format("No edge %d - %d", v, w));
        }

        public void setWeight(int v, int w, int newWeight){
            if(!hasEdge(v, w)){
                throw new IllegalArgumentException(String.format("No edge %d - %d", v, w));
            }
            adj[v].put(w, newWeight);
            if(!directed){
                adj[w].put(v, newWeight);
            }
        }

        // 对于无向图生效
        public int degree(int v){
            if(directed){
                throw new RuntimeException("degree only works in undirected graph.");
            }
            validateVertex(v);
            return adj[v].size();
        }

        public void removeEdge(int v, int w){
            validateVertex(v);
            validateVertex(w);

            if(adj[v].containsKey(w)){
                E--;
            }

            adj[v].remove(w);
            adj[w].remove(v);
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();

            sb.append(String.format("V = %d, E = %d, directed = %b\n", V, E, directed));
            for(int v = 0; v < V; v ++){
                sb.append(String.format("%d : ", v));
                for(Map.Entry<Integer, Integer> entry : this.adj[v].entrySet()){
                    sb.append(String.format("(%d: %d) ", entry.getKey(), entry.getValue() ));
                }
                sb.append('\n');
            }
            return sb.toString();
        }

    }

    class BipartitionDetection {

        private Graph G;

        private boolean[] visited;
        private int[] colors;
        private boolean isBipartite = true;

        public BipartitionDetection(Graph G){
            this.G = G;
            visited = new boolean[G.V()];
            colors = new int[G.V()];
            Arrays.fill(colors, -1);

            for(int v = 0; v < G.V(); v++){
                if(!visited[v]){
                    if(!dfs(v, 0)){
                        isBipartite = false;
                        break;
                    }
                }
            }
        }

        private boolean dfs(int v, int color) {
            visited[v] = true;
            colors[v] = color;

            for(int w : G.adj(v)){
                if(!visited[w]){
                    if(!dfs(w, 1 - color)){
                        return false;
                    }
                }
                else if(colors[w] == colors[v]){
                    return false;
                }
            }
            return true;
        }

        public boolean isBipartite(){
            return isBipartite;
        }

        public int[] colors(){
            return colors;
        }


    }

    class MaxFlow {

        private WeightedGraph network;
        // 源点 和 汇点
        private int s, t;

        private WeightedGraph rG;

        private int maxFlow = 0;

        public MaxFlow(WeightedGraph network, int s, int t){

            if(!network.isDirected()){
                throw new IllegalArgumentException("MaxFlow only works in directed graph.");
            }

            if(network.V() < 2){
                throw new IllegalArgumentException("The netword should has at least 2 vertices.");
            }

            network.validateVertex(s);
            network.validateVertex(t);

            if(s == t){
                throw new IllegalArgumentException("s and t should be different.");
            }

            this.network = network;
            this.s = s;
            this.t = t;

            // 创建残量图
            this.rG = new WeightedGraph(network.V(), true);

            for (int v = 0; v < network.V(); v++){
                for(int w : network.adj(v)){
                    int c = network.getWeight(v, w);
                    rG.addEdge(v, w, c);
                    rG.addEdge(w, v, 0);
                }
            }

            while (true){

                ArrayList<Integer> augPath = getAugmentingPath();

                if(augPath.size() == 0){
                    break;
                }

                int f = Integer.MAX_VALUE;
                // 计算增广路径上的最小值
                for(int i = 1; i < augPath.size(); i++){
                    int v = augPath.get(i - 1);
                    int w = augPath.get(i);
                    f = Math.min(f, rG.getWeight(v, w));
                }
                maxFlow += f;

                // 根据增广路径更新 rG 残量图
                for(int i = 1; i < augPath.size(); i++){
                    int v = augPath.get(i - 1);
                    int w = augPath.get(i);

//                // v -> w 是正向边
//                if(network.hasEdge(v, w)){
//                    rG.setWeight(v, w, rG.getWeight(v, w) - f);
//                    rG.setWeight(w, v, rG.getWeight(w, v) + f);
//                }
//                // v -> w 是反向边
//                else {
//                    rG.setWeight(w, v, rG.getWeight(w, v) + f);
//                    rG.setWeight(v, w, rG.getWeight(v, w) - f);
//                }

                    rG.setWeight(v, w, rG.getWeight(v, w) - f);
                    rG.setWeight(w, v, rG.getWeight(w, v) + f);
                }

            }

        }

        private ArrayList<Integer> getAugmentingPath() {

            Queue<Integer> q = new LinkedList<>();
            int[] pre = new int[network.V()];
            Arrays.fill(pre, -1);

            q.add(s);
            pre[s] = s;
            while (!q.isEmpty()){

                int cur = q.remove();

                if(cur == t){
                    break;
                }

                for (int next : rG.adj(cur)){
                    if(pre[next] == -1 && rG.getWeight(cur, next) > 0){
                        pre[next] = cur;
                        q.add(next);
                    }
                }
            }

            ArrayList<Integer> res = new ArrayList<>();
            if(pre[t] == -1){
                return res;
            }

            int curr = t;
            while (curr != s){
                res.add(curr);
                curr = pre[curr];
            }
            res.add(s);

            Collections.reverse(res);
            return res;

        }

        public int result(){
            return maxFlow;
        }

        public int flow(int v, int w){

            if(!network.hasEdge(v, w)){
                throw new IllegalArgumentException(String.format("No edge %d - %d", v, w));
            }

            // 残量图里反向权重
            return rG.getWeight(w, v);
        }

    }

    class BipartiteMatching {

        private Graph G;
        private int maxMatching;

        public BipartiteMatching(Graph G){

            BipartitionDetection bd = new BipartitionDetection(G);
            if(!bd.isBipartite()){
                throw new IllegalArgumentException("BipartiteMatching only works for bipartite graph.");
            }

            this.G = G;

            int[] colors = bd.colors();

            // 源点设置为 V， 汇点设置为 V + 1
            WeightedGraph network = new WeightedGraph(G.V() + 2, true);

            for(int v = 0; v < G.V(); v++){

                if(colors[v] == 0){
                    network.addEdge(G.V(), v, 1);
                }
                else {
                    network.addEdge(v, G.V() + 1, 1);
                }

                for(int w : G.adj(v)){
                    // v < w 使得只遍历一次
                    if(v < w){
                        if(colors[v] == 0){
                            network.addEdge(v, w, 1);
                        }
                        else {
                            network.addEdge(w, v, 1);
                        }
                    }
                }
            }

            MaxFlow maxFlow = new MaxFlow(network, G.V(), G.V() + 1);
            maxMatching = maxFlow.result();

        }

        public int maxMatching(){
            return maxMatching;
        }

        public boolean isPerfectMatching(){
            return maxMatching * 2 == G.V();
        }

    }

    public int domino(int n, int m, int[][] broken) {

        // board中 0 是好格子，1是坏掉的格子
        int[][] board = new int[n][m];
        for(int[] p : broken){
            board[p[0]][p[1]] = 1;
        }
        Graph g = new Graph(n * m, false);

        // 只考虑 右向 下向 的连接
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(j + 1 < m && board[i][j] == 0 && board[i][j + 1] == 0){
                    g.addEdge(i * m + j, i * m + (j + 1));
                }
                if(i + 1 < n && board[i][j] == 0 && board[i + 1][j] == 0){
                    g.addEdge(i * m + j, (i + 1) * m + j);
                }
            }
        }

        BipartiteMatching bm = new BipartiteMatching(g);
        return bm.maxMatching();
    }

    public static void main(String[] args){

        int[][] broken = new int[0][2];
        System.out.println((new LeetCodeLCP04()).domino(3, 3, broken));
    }
}
