package WeightedGraphAlgo;

import GraphAdjExpression.WeightedGraph;

import java.util.Arrays;

public class DijkstraFirst {

    private WeightedGraph G;
    private int s;
    private int [] dis;
    private boolean[] visited;

    //O(E^2)
    public DijkstraFirst(WeightedGraph G, int s){
        this.G = G;
        G.validateVertex(s);
        this.s = s;

        dis = new int[G.V()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[s] = 0;

        visited = new boolean[G.V()];

        while (true){

            // 注意，visited[s] = false, 第一轮for循环结束，cur = s， curdis = 0
            // 从整个图中寻找 到 s 点最近的点 cur
            int curdis = Integer.MAX_VALUE, cur = -1;
            for(int v = 0; v < G.V(); v++){
                if(!visited[v] && dis[v] < curdis){
                    curdis = dis[v];
                    cur = v;
                }
            }
            if(cur == -1){
                break;
            }

            // 标记cur 为已访问
            visited[cur] = true;

            // 更新与cur之间有联系的其他相邻点(vertex) w 与 s 的距离
            for (int w : G.adj(cur)){
                if(!visited[w]){
                    if(dis[cur] + G.getWeight(cur, w) < dis[w]){
                        dis[w] = dis[cur] + G.getWeight(cur, w);
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
        DijkstraFirst dij = new DijkstraFirst(g, 0);
        for(int v = 0; v < g.V(); v++){
            System.out.print(dij.distTo(v) + " ");
        }
        System.out.println();
    }

}
