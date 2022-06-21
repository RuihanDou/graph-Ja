package DirectedGraphAlgo;


import GraphDFS.CC;
import GraphDFS.GraphDFS;
import GraphInterface.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 使用 Kosaraju 算法求 有向图的强连通分量
 *
 */
public class SCC {

    private Graph G;
    // 标记强连通分量的id
    private int[] visited;
    private int scccount = 0;

    public SCC(Graph G){
        if(!G.isDirected()){
            throw new IllegalArgumentException("SCC only works in directed graph.");
        }

        this.G = G;
        // visited[v] 里存联通分量的id
        visited = new int[G.V()];
        Arrays.fill(visited, -1);

        GraphDFS dfs = new GraphDFS(G.reverseGraph());
        // 根据Kosaraju算法，先获取遍历图的顺序，这个顺序是 该图反图的后序遍历结果的逆序
        ArrayList<Integer> order = new ArrayList<>();
        for(int v : dfs.post()){
            order.add(v);
        }
        Collections.reverse(order);

        // 根据 order 使用 dfs 取连通分量
        for(int v : order){
            if(visited[v] == -1){
                dfs(v, scccount);
                scccount++;
            }
        }

    }

    private void dfs(int v, int sccid){
        visited[v] = sccid;
        for(int w : G.adj(v)){
            if(visited[w] == -1){
                dfs(w, sccid);
            }
        }
    }

    public int count(){
        return scccount;
    }

    public boolean isStronglyConnected(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return visited[v] == visited[w];
    }

    public ArrayList<Integer>[] components() {
        ArrayList<Integer>[] res = new ArrayList[scccount];
        for (int i = 0; i < scccount; i++){
            res[i] = new ArrayList<>();
        }
        for(int v = 0; v < G.V(); v++){
            res[visited[v]].add(v);
        }
        return res;
    }

    public static void main(String[] args) {
        Graph g = new GraphAdjExpression.Graph("ug.txt", true);
        SCC scc = new SCC(g);
        System.out.println(scc.count());

        ArrayList<Integer>[] comp = scc.components();
        for(int ccid = 0; ccid < comp.length; ccid ++){
            System.out.print(ccid + " : ");
            for(int w: comp[ccid]){
                System.out.print(w + " ");
            }
            System.out.println();
        }
    }

}
