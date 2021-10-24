package GraphDFS;

import GraphAdjExpression.AdjMatrix;

import java.util.ArrayList;

public class AdjMatrixDFS {

    private AdjMatrix G;
    private boolean [] visited;

    private ArrayList<Integer> preOrder = new ArrayList<>();
    private ArrayList<Integer> postOrder = new ArrayList<>();

    public AdjMatrixDFS(AdjMatrix G){
        this.G = G;
        visited = new boolean[G.V()];
        for(int v = 0; v < G.V(); v ++){
            if(!visited[v]){
                dfs(v);
            }
        }
    }

    private void dfs(int v){
        visited[v] = true;
        preOrder.add(v);
        for (int w : G.adj(v)){
            if(!visited[w]){
                dfs(w);
            }
        }
        postOrder.add(v);
    }

    public Iterable<Integer> preOrder(){
        return preOrder;
    }

    public Iterable<Integer> postOrder(){
        return postOrder;
    }

    public static void main(String[] args){

        AdjMatrix g = new AdjMatrix("g1_not_connected.txt");
        AdjMatrixDFS graphDFS = new AdjMatrixDFS(g);
        System.out.println("DFS preOrder : " + graphDFS.preOrder());
        System.out.println("DFS postOrder : " + graphDFS.postOrder());
    }

}
