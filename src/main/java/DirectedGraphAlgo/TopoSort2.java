package DirectedGraphAlgo;

import GraphAdjExpression.Graph;
import GraphDFS.GraphDFS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用深度优先的后序遍历进行拓扑排序，前提是无环，不能进行环检测
 */
public class TopoSort2 {

    private Graph G;

    private ArrayList<Integer> res;
    private boolean hasCycle = false;

    public TopoSort2(Graph G){

        if(!G.isDirected()){
            throw new IllegalArgumentException("TopoSort only works in directed graph.");
        }

        this.G = G;

        res = new ArrayList<>();

        hasCycle = (new DirectedCycleDetection(G)).hasCycle();
        if(hasCycle){
            return;
        }

        GraphDFS graphDFS = new GraphDFS(G);
        for (int v : graphDFS.post()){
            res.add(v);
        }

        Collections.reverse(res);
    }

    public boolean hasCycle(){
        return hasCycle;
    }

    public ArrayList<Integer> result() {
        return res;
    }

    public static void main(String[] args) {

        Graph g = new Graph("g10_topo_sort1.txt", true);
//        Graph g = new Graph("g10_topo_sort2.txt", true);
        TopoSort topoSort = new TopoSort(g);

        System.out.println(topoSort.hasCycle());
        System.out.println(topoSort.result());
    }

}
