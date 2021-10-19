package GraphDFS;

import java.util.ArrayList;

public class GraphDFS {

    private Graph G;
    private boolean[] visited;
    private ArrayList<Integer> order = new ArrayList<>();

    public GraphDFS(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        for(int v = 0; v < G.V(); v++){
            if(!visited[v]){
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        visited[v] = true;
        order.add(v);
        for(int w : G.adj(v)){
            if(!visited[w]){
                dfs(w);
            }
        }
    }

    public Iterable<Integer> order(){
        return order;
    }

    public static void main(String[] args) {

//        Graph g = new Graph("g1_well_conected.txt");
        Graph g = new Graph("g1_not_conected.txt");
        GraphDFS graphDFS = new GraphDFS(g);
        System.out.print(graphDFS.order());

    }

}
