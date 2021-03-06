package DirectedGraphAlgo;

import GraphAdjExpression.Graph;

public class DirectedCycleDetection {

    private Graph G;
    private boolean[] visited;
    private boolean[] onPath;
    private boolean hasCycle = false;

    public DirectedCycleDetection(Graph G){

        if(!G.isDirected()){
            throw new IllegalArgumentException("Cycle Detection only works in directed graph.");
        }

        this.G = G;
        visited = new boolean[G.V()];
        onPath = new boolean[G.V()];
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
        onPath[v] = true;

        for(int w : G.adj(v)){
            if(!visited[w]){
                if(dfs(w, v)){
                    return true;
                }
            }
            else if(onPath[w]){
                return true;
            }
        }
        onPath[v] = false;
        return false;
    }

    public boolean hasCycle(){
        return hasCycle;
    }

    public static void main(String[] args) {
//        Graph g = new Graph("g9_directed_cycle_detection_no.txt", true);
        Graph g = new Graph("g9_directed_cycle_detection_yes.txt", true);
        DirectedCycleDetection detection = new DirectedCycleDetection(g);
        System.out.println(detection.hasCycle());
    }
}
