package GraphDFS;

import GraphAdjExpression.AdjSet;
import GraphInterface.Graph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Connected Component
 *
 * 求连通分量个数
 */
public class CC {

    private Graph G;
    private int[] visited;
    private int cccount = 0;

    public CC(Graph G){
        if(G.isDirected()){
            throw new IllegalArgumentException("CC only works in undirected graph.");
        }

        this.G = G;
        // visited[v] 里存联通分量的id
        visited = new int[G.V()];
        Arrays.fill(visited, -1);
        for(int v = 0; v < G.V(); v++){
            if(visited[v] == -1){
                dfs(v, cccount);
                cccount++;
            }
        }
    }

    private void dfs(int v, int ccid){
        visited[v] = ccid;
        for(int w : G.adj(v)){
            if(visited[w] == -1){
                dfs(w, ccid);
            }
        }
    }

    public int count(){
        return cccount;
    }

    public boolean isConnected(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return visited[v] == visited[w];
    }

    public ArrayList<Integer>[] components() {
        ArrayList<Integer>[] res = new ArrayList[cccount];
        for (int i = 0; i < cccount; i++){
            res[i] = new ArrayList<>();
        }
        for(int v = 0; v < G.V(); v++){
            res[visited[v]].add(v);
        }
        return res;
    }

    public static void main(String[] args) {
        Graph g = new GraphAdjExpression.Graph("g1_not_connected.txt");
        CC cc = new CC(g);
        System.out.println(cc.count());

        System.out.println(cc.isConnected(0,6));
        System.out.println(cc.isConnected(0,5));

        ArrayList<Integer>[] comp = cc.components();
        for(int ccid = 0; ccid < comp.length; ccid ++){
            System.out.print(ccid + " : ");
            for(int w: comp[ccid]){
                System.out.print(w + " ");
            }
            System.out.println();
        }
    }

}
