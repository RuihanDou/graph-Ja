package HamiltonLoopPath;

import GraphAdjExpression.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class HamiltonLoopCompression {

    private Graph G;
//    private int visited;
    private int[] pre;
    private int end;

    public HamiltonLoopCompression(Graph G){
        this.G = G;
        int visited = 0;
        pre = new int[G.V()];
        end = -1;

        dfs(visited, 0, 0, G.V());
    }

    private boolean dfs(int visited, int v, int parent, int left) {

        visited += (1 << v);
        pre[v] = parent;
        left--;
        if(left == 0 && G.hasEdge(v, 0)){
            end = v;
            return true;
        }

        for(int w : G.adj(v)){
            if((visited & (1 << w)) == 0){
                if(dfs(visited, w, v, left)){
                    return true;
                }
            }
//            else if(w == 0 && left == 0){
//                end = v;
//                return true;
//            }
        }
        visited -= (1 << v);
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
