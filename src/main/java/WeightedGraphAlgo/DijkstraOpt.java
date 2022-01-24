package WeightedGraphAlgo;

import GraphAdjExpression.WeightedGraph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class DijkstraOpt {

    private WeightedGraph G;
    private int s;
    private int [] dis;
    private boolean[] visited;

    private class Node implements Comparable<Node>{
        public  int v, dis;
        public Node(int v, int dis){
            this.v = v;
            this.dis = dis;
        }

        @Override
        public int compareTo(Node another){
            return dis - another.dis;
        }
    }

    // O(ElogE)
    public DijkstraOpt(WeightedGraph G, int s){
        this.G = G;
        G.validateVertex(s);
        this.s = s;

        dis = new int[G.V()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[s] = 0;

        visited = new boolean[G.V()];

        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        pq.add(new Node(s, 0));

        while (!pq.isEmpty()){

            // 从整个图中寻找 到 s 点最近的点 cur
            int cur = pq.remove().v;
            if(visited[cur]){
                continue;
            }

            // 标记cur 为已访问
            visited[cur] = true;

            // 更新与cur之间有联系的其他相邻点(vertex) w 与 s 的距离
            for (int w : G.adj(cur)){
                if(!visited[w]){
                    if(dis[cur] + G.getWeight(cur, w) < dis[w]){
                        dis[w] = dis[cur] + G.getWeight(cur, w);
                        pq.add(new Node(w, dis[w]));
                    }
                }
            }
        }

    }

    public boolean isConnectedTo(int v){
        G.validateVertex(v);
        return visited[v];
    }

    public int distTo(int v){
        G.validateVertex(v);
        return dis[v];
    }

    public static void main(String[] args) {

        WeightedGraph g = new WeightedGraph("g7_dijkstra.txt");
        DijkstraOpt dij = new DijkstraOpt(g, 0);
        for(int v = 0; v < g.V(); v++){
            System.out.print(dij.distTo(v) + " ");
        }
        System.out.println();
    }

}
