package WeightedGraphAlgo;

import GraphAdjExpression.WeightedGraph;

import java.util.Arrays;

public class Floyed {

    private WeightedGraph G;
    private int[][] dis;
    private boolean hasNegativeCycle = false;

    public Floyed(WeightedGraph G){
        this.G = G;
        dis = new int[G.V()][G.V()];

        // Floyed 算法初始化
        for(int i = 0; i < G.V(); i++){
            Arrays.fill(dis[i], Integer.MAX_VALUE);
        }

        for(int v = 0; v < G.V(); v++){
            dis[v][v] = 0;
            for(int w : G.adj(v)){
                dis[v][w] = G.getWeight(v, w);
            }
        }

        // 三重循环 t 为中间点， v 为起始点， w 为终止点
       for(int t = 0; t < G.V(); t++){
           for(int v = 0; v < G.V(); v++){
               for (int w = 0; w < G.V(); w++){
                   if(dis[v][t] != Integer.MAX_VALUE
                           && dis[t][w] != Integer.MAX_VALUE
                           && dis[v][t] + dis[t][w] < dis[v][w]){
                       dis[v][w] = dis[v][t] + dis[t][w];
                   }
               }
           }
       }

       for(int v = 0; v < G.V(); v++){
           if(dis[v][v] < 0){
               hasNegativeCycle = true;
           }
       }

    }

    public boolean hasNegCycle(){
        return hasNegativeCycle;
    }

    public boolean isConnectedTo(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return dis[v][w] != Integer.MAX_VALUE;
    }

    public int distTo(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return dis[v][w];
    }

    public static void main(String[] args) {
//        WeightedGraph g = new WeightedGraph("g7_dijkstra.txt");
        WeightedGraph g = new WeightedGraph("g7_bellman_ford_or_floyed_neg_cycle.txt");
        Floyed floyed = new Floyed(g);
        if(!floyed.hasNegCycle()){
            for (int v = 0; v < g.V(); v++){
                for (int w = 0; w < g.V(); w++){
                    System.out.print(floyed.distTo(v, w) + " ");
                }
                System.out.println();
            }
        }
        else {
            System.out.println("exist negative cycle.");
        }
    }

}
