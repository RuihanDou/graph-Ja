package HamiltonLoopPath;

import GraphAdjExpression.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class HamiltonLoopPro {

    private Graph G;
    private boolean[] visited;
    private int[] pre;
    private int end;

    public HamiltonLoopPro(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        pre = new int[G.V()];
        end = -1;

        dfs(0, 0, G.V());
    }

    private boolean dfs(int v, int parent, int left) {

        visited[v] = true;
        pre[v] = parent;
        left--;
        if(left == 0 && G.hasEdge(v, 0)){
            end = v;
            return true;
        }

        for(int w : G.adj(v)){
            if(!visited[w]){
                if(dfs(w, v, left)){
                    return true;
                }
            }
//            else if(w == 0 && left == 0){
//                end = v;
//                return true;
//            }
        }
        visited[v] = false;
//        left++; 不影响递归上层 的 left 值 上层的left = 这层处理过的left + 1
        return false;

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
        HamiltonLoopPro hl = new HamiltonLoopPro(g);
        System.out.println(hl.result());

        Graph g2 = new Graph("g5_hamilton2.txt");
        HamiltonLoopPro hl2 = new HamiltonLoopPro(g2);
        System.out.println(hl2.result());
    }

}
