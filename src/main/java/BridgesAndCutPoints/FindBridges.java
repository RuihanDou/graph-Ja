package BridgesAndCutPoints;


import GraphAdjExpression.Edge;
import GraphAdjExpression.Graph;

import java.util.ArrayList;

/**
 *  只能用 DFS 寻找桥
 *
 *  因为 DFS 的 非遍历树 的 边 可以指向 树的祖先节点 行成 回向边（后向边）
 *  利用这个性质寻找桥
 */
public class FindBridges {

    private Graph G;
    private boolean[] visited;

    private int ord[];
    private int low[];
    private int cnt;

    private ArrayList<Edge> res;

    public FindBridges(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        ord = new int[G.V()];
        low = new int[G.V()];
        cnt = 0;
        res = new ArrayList<>();

        for(int v = 0; v < G.V(); v++){
            if(!visited[v]){
                dfs(v, v);
            }
        }
    }

    private void dfs(int v, int parent) {
        visited[v] = true;
        ord[v] = cnt;
        low[v] = ord[v];
        cnt++;

        for(int w : G.adj(v)){
            if(!visited[w]){
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);
                if(low[w] > ord[v]){
                    // v - w 是桥
                    res.add(new Edge(v, w));
                }
            }
            else if(w != parent){
                low[v] = Math.min(low[v], low[w]);
            }
        }

    }

    public ArrayList<Edge> result(){
        return res;
    }

    public static void main(String[] args) {
        Graph g = new Graph("g4_bridge1.txt");
        FindBridges fb = new FindBridges(g);
        System.out.println(fb.result());

        Graph g2 = new Graph("g4_bridge2.txt");
        FindBridges fb2 = new FindBridges(g2);
        System.out.println(fb2.result());

        Graph g3 = new Graph("g4_bridge3.txt");
        FindBridges fb3 = new FindBridges(g3);
        System.out.println(fb3.result());
    }

}
