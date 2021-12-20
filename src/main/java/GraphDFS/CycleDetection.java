package GraphDFS;

import GraphAdjExpression.Graph;

import java.util.ArrayList;

public class CycleDetection {

    private Graph G;
    private boolean[] visited;
    private boolean hasCycle = false;

    public CycleDetection(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        for(int v = 0; v < G.V(); v++){
            if(!visited[v]){
                if(dfs(v, v)){
                    hasCycle = true;
                    break;
                }
            }
        }
    }

    // 从顶点 v 开始，判断图中是否有环
    private boolean dfs(int v, int parent) {
        visited[v] = true;

        for(int w : G.adj(v)){
            if(!visited[w]){
                if(dfs(w, v)){
                    return true;
                }
            }
            else if(w != parent){
                return true;
            }
        }

        return false;
    }

    public boolean hasCycle(){
        return hasCycle;
    }

    public static void main(String[] args) {
        Graph g = new Graph("g1_not_connected.txt");

        CycleDetection cycleDetection = new CycleDetection(g);

        System.out.println(cycleDetection.hasCycle());


        Graph g1 = new Graph("g2_without_cycle.txt");

        CycleDetection cycleDetection1 = new CycleDetection(g1);

        System.out.println(cycleDetection1.hasCycle());
    }


}
