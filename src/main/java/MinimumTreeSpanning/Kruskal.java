package MinimumTreeSpanning;

import GraphAdjExpression.WeightedEdge;
import GraphAdjExpression.WeightedGraph;
import GraphDFS.CC;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 贪心过程（用切分定理可以证明），从最小的边（权值最小的边）开始看
 *
 * 如果边上有 现有树里没有的点，则加入该边到最小生成树中
 *
 */
public class Kruskal {

    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;

    public Kruskal(WeightedGraph G){
        this.G = G;
        mst = new ArrayList<>();

        CC cc = new CC(G);
        if(cc.count() > 1){
            return;
        }

        // Kruskal
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        for(int v = 0; v < G.V(); v++){
            for(int w : G.adj(v)){
                if(v < w){
                    edges.add(new WeightedEdge(v, w, G.getWeight(v, w)));
                }
            }
        }

        Collections.sort(edges);

        UF uf = new UF(G.V());
        for(WeightedEdge edge : edges){
            int v = edge.getV();
            int w = edge.getW();
            if(!uf.isConnected(v, w)){
                mst.add(edge);
                uf.unionElements(v, w);
            }

        }
    }

    public ArrayList<WeightedEdge> result() {
        return mst;
    }

    public static void main(String[] args) {

        WeightedGraph g = new WeightedGraph("g7_weighted_graph.txt");
        Kruskal kruskal = new Kruskal(g);
        System.out.println(kruskal.result());
    }
}
