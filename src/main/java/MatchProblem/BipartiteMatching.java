package MatchProblem;

import GraphAdjExpression.WeightedGraph;
import GraphDFS.BipartitionDetection;
import GraphInterface.Graph;
import MaximumFlow.MaxFlow;

public class BipartiteMatching {

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

    public static void main(String[] args) {
//        Graph graph = new GraphAdjExpression.Graph("g11_match1.txt");
//        BipartiteMatching bm = new BipartiteMatching(graph);
//        System.out.println(bm.maxMatching());

        Graph graph = new GraphAdjExpression.Graph("g11_match2.txt");
        BipartiteMatching bm = new BipartiteMatching(graph);
        System.out.println(bm.maxMatching());
    }

}
