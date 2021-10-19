package GraphDFS;

import GraphAdjExpression.AdjList;
import GraphAdjExpression.AdjMatrix;
import GraphAdjExpression.AdjSet;
import GraphInterface.Graph;

import java.util.ArrayList;

public class GraphDFS {

    private Graph G;
    private boolean[] visited;
    private ArrayList<Integer> preOrder = new ArrayList<>();
    private ArrayList<Integer> postOrder = new ArrayList<>();

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
        preOrder.add(v);
        for(int w : G.adj(v)){
            if(!visited[w]){
                dfs(w);
            }
        }
        postOrder.add(v);
    }

    public Iterable<Integer> pre(){
        return preOrder;
    }

    public Iterable<Integer> post(){
        return postOrder;
    }

    public static void main(String[] args) {

        Graph g1 = new AdjSet("g1_not_conected.txt");
        GraphDFS graphDFS1 = new GraphDFS(g1);
        System.out.println("DFS preOrder : " + graphDFS1.pre());
        System.out.println("DFS postOrder : " + graphDFS1.post());
        System.out.println();

        Graph g2 = new AdjList("g1_not_conected.txt");
        GraphDFS graphDFS2 = new GraphDFS(g2);
        System.out.println("DFS preOrder : " + graphDFS2.pre());
        System.out.println("DFS postOrder : " + graphDFS2.post());
        System.out.println();

        Graph g3 = new AdjMatrix("g1_not_conected.txt");
        GraphDFS graphDFS3 = new GraphDFS(g3);
        System.out.println("DFS preOrder : " + graphDFS3.pre());
        System.out.println("DFS postOrder : " + graphDFS3.post());
        System.out.println();

    }

}
