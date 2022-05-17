package HamiltonLoopPath;

import GraphAdjExpression.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class HamiltonLoop {

    private Graph G;
    private boolean[] visited;
    private int[] pre;
    private int end;

    public HamiltonLoop(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        pre = new int[G.V()];
        end = -1;

        dfs(0, 0);
    }

    private boolean dfs(int v, int parent) {

        visited[v] = true;
        pre[v] = parent;
        for(int w : G.adj(v)){
            if(!visited[w]){
                if(dfs(w, v)){
                    return true;
                }
            }
            // 如果 w 被访问过，并且所有点被访问过，并且 w 是出发点0
            else if(w == 0 && allVisited()){
                // 回到 0 之前的最后一个点
                end = v;
                return true;
            }
        }
        visited[v] = false;
        return false;

    }

    private boolean allVisited() {
        for(int v = 0; v < G.V(); v++){
            if(!visited[v]){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> result(){

        ArrayList<Integer> res = new ArrayList<>();
        if(end == -1){
            return res;
        }

        int cur = end;
        while (cur != 0){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(0);

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph g = new Graph("g5_hamilton1.txt");
        HamiltonLoop hl = new HamiltonLoop(g);
        System.out.println(hl.result());

        Graph g2 = new Graph("g5_hamilton2.txt");
        HamiltonLoop hl2 = new HamiltonLoop(g2);
        System.out.println(hl2.result());
    }
}
