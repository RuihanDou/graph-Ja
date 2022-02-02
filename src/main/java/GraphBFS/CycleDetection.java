package GraphBFS;

import GraphAdjExpression.Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CycleDetection {

    private Graph G;
    private boolean[] visited;
    private int[] pre;
    private boolean hasCycle = false;

    public CycleDetection(Graph G){

        if(G.isDirected()){
            throw new IllegalArgumentException("Cycle Detection only works in undirected graph.");
        }

        this.G = G;
        visited = new boolean[G.V()];
        pre = new int[G.V()];
        Arrays.fill(pre, -1);

        for(int v = 0; v < G.V(); v ++){
            if(!visited[v]){
                if(bfs(v)){
                    hasCycle = true;
                    break;
                }
            }
        }
    }

    private boolean bfs(int s) {

        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        pre[s] = s;

        while(!queue.isEmpty()){
            int v = queue.remove();
            for(int w: G.adj(v)){
                if(!visited[w]){
                    queue.add(w);
                    visited[w] = true;
                    pre[w] = v;
                }
                // w 被遍历过，且不是上一个顶点
                else if(pre[v] != w){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCycle(){
        return hasCycle;
    }

    public static void main(String[] args) {
        Graph g = new Graph("g2_bipartition.txt");
        CycleDetection cycleDetection = new CycleDetection(g);
        System.out.println(cycleDetection.hasCycle());

        Graph g2 = new Graph("g2_without_cycle.txt");
        CycleDetection cycleDetection2 = new CycleDetection(g2);
        System.out.println(cycleDetection2.hasCycle());
    }

}
