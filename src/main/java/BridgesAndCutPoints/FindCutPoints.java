package BridgesAndCutPoints;

import GraphAdjExpression.Graph;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 去掉割点，图的联通分量会增加
 */
public class FindCutPoints {

    private Graph G;
    private boolean[] visited;

    // ord 记录遍历的顺序
    private int ord[];
    // low 记录通过该顶点能够回到的最早遍历到的点
    private int low[];
    private int cnt;

    private HashSet<Integer> res;

    public FindCutPoints(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        ord = new int[G.V()];
        low = new int[G.V()];
        cnt = 0;
        res = new HashSet<>();

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

        int child = 0;
        for(int w : G.adj(v)){
            if(!visited[w]){

                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);
                // 对于 边 v - w 如果 low[w] > ord[v]
                if(v != parent && low[w] >= ord[v]){
                    // v - w 是桥
                    res.add(v);
                }

                child++;
                // 对于根节点，有一个以上孩子，就可以被认为是割点
                if(v == parent && child > 1){
                    res.add(v);
                }
            }
            else if(w != parent){
                low[v] = Math.min(low[v], low[w]);
            }
        }

    }

    public HashSet<Integer> result(){
        return res;
    }

    public static void main(String[] args) {
        Graph g = new Graph("g4_bridge1.txt");
        FindCutPoints fc = new FindCutPoints(g);
        System.out.println(fc.result());

        Graph g2 = new Graph("g4_bridge2.txt");
        FindCutPoints fc2 = new FindCutPoints(g2);
        System.out.println(fc2.result());

        Graph g3 = new Graph("g4_bridge3.txt");
        FindCutPoints fc3 = new FindCutPoints(g3);
        System.out.println(fc3.result());
    }

}
