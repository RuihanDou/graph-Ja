package WeightedGraphAlgo;

import GraphAdjExpression.WeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BellmanFord {

    private WeightedGraph G;
    private int s;
    private int[] dis;
    private boolean hasNegativeCycle = false;
    private int[] pre;


    /**
     *  BellMan Ford 算法可以对有向图生效，存在负权边的图有效。
     *
     *  …… 但有负权环的图的最短路径没有意义
     *
     * @param G
     * @param s
     */
    public BellmanFord(WeightedGraph G, int s){
        this.G = G;
        G.validateVertex(s);
        this.s = s;

        dis = new int[G.V()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[s] = 0;

        pre = new int[G.V()];
        Arrays.fill(pre, -1);

        // 进行 v - 1 轮松弛操作
        for(int pass = 1; pass < G.V(); pass++){

            for(int v = 0; v < G.V(); v++){
                for(int w : G.adj(v)){
                    if(dis[v] != Integer.MAX_VALUE && dis[v] + G.getWeight(v, w) < dis[w]){
                        dis[w] = dis[v] + G.getWeight(v,w);
                        pre[w] = v;
                    }
                }
            }

        }
        // 增加一次松弛操作，判断是否有负权环
        for(int v = 0; v < G.V(); v++){
            for(int w : G.adj(v)){
                if(dis[v] != Integer.MAX_VALUE && dis[v] + G.getWeight(v, w) < dis[w]){
                    hasNegativeCycle = true;
                }
            }
        }

    }

    public boolean hasNegCycle(){
        return hasNegativeCycle;
    }

    public boolean isConnectedTo(int v){
        G.validateVertex(v);
        return dis[v] != Integer.MAX_VALUE;
    }

    public int distTo(int v){
        G.validateVertex(v);
        if(hasNegativeCycle){
            throw new RuntimeException("exist negative cycle.");
        }
        return dis[v];
    }

    public Iterable<Integer> path(int t){
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnectedTo(t)){
            return res;
        }
        int cur = t;
        while (cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
//        WeightedGraph g = new WeightedGraph("g7_dijkstra.txt");
        WeightedGraph g = new WeightedGraph("g7_bellman_ford_or_floyed_neg_cycle.txt");
        BellmanFord bf = new BellmanFord(g, 0);
        if(!bf.hasNegCycle()){
            for(int v = 0; v < g.V(); v++){
                System.out.print(bf.distTo(v) + " ");
            }
            System.out.println();
            System.out.println(bf.path(3));
        }
        else {
            System.out.println("exist negative cycle.");
        }
    }

}
