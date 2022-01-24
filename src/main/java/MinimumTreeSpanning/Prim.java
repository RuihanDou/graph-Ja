package MinimumTreeSpanning;

import GraphAdjExpression.WeightedEdge;
import GraphAdjExpression.WeightedGraph;
import GraphDFS.CC;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Prim {

    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;

    public Prim(WeightedGraph G){
        this.G = G;
        mst = new ArrayList<>();

        CC cc = new CC(G);
        if(cc.count() > 1){
            return;
        }

//        // Prim
//        boolean[] visited = new boolean[G.V()];
//        visited[0] = true;
//
//        for(int i = 1; i < G.V(); i++){
//
//            WeightedEdge minEdge = new WeightedEdge(-1, -1, Integer.MAX_VALUE);
//            for(int v = 0; v < G.V(); v++){
//                if(visited[v]){
//                    for (int w : G.adj(v)){
//                        if(!visited[w] && G.getWeight(v, w) < minEdge.getWeight()){
//                            minEdge = new WeightedEdge(v,w,G.getWeight(v, w));
//                        }
//                    }
//                }
//            }
//            mst.add(minEdge);
//            visited[minEdge.getV()] = true;
//            visited[minEdge.getW()] = true;
//        }
        // Prim
        boolean[] visited = new boolean[G.V()];
        visited[0] = true;
        Queue pq = new PriorityQueue<WeightedEdge>();
        for(int w : G.adj(0)){
            pq.add(new WeightedEdge(0, w, G.getWeight(0, w)));
        }
        while (!pq.isEmpty()){
            WeightedEdge minEdge = (WeightedEdge) pq.remove();
            if(visited[minEdge.getV()] && visited[minEdge.getW()]){
                continue;
            }
            mst.add(minEdge);
            int newv = visited[minEdge.getV()] ? minEdge.getW() : minEdge.getV();
            visited[newv] = true;

            for (int w : G.adj(newv)){
                if(!visited[w]){
                    pq.add(new WeightedEdge(newv, w, G.getWeight(newv, w)));
                }
            }
        }
    }

    public ArrayList<WeightedEdge> result() {
        return mst;
    }

    public static void main(String[] args) {
        WeightedGraph g = new WeightedGraph("g7_weighted_graph.txt");
        Prim prim = new Prim(g);
        System.out.println(prim.result());
    }
}
