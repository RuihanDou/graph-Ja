package EulerLoopPath;

import GraphAdjExpression.Graph;
import GraphDFS.CC;

import java.util.ArrayList;
import java.util.Stack;

public class EulerLoop {

    private Graph G;

    public EulerLoop(Graph G){
        this.G = G;
    }

    /**
     * 对于欧拉回路
     *
     * 存在欧拉回路的 充要条件是 图联通且每个点的度为偶数
     *
     * @return
     */
    public boolean hasEularLoop(){

        CC cc = new CC(G);
        if(cc.count() > 1){
            return false;
        }

        for(int v = 0; v < G.V(); v++){
            if(G.degree(v) % 2 == 1){
                return false;
            }
        }

        return true;
    }

    /**
     * 使用 Hierholzer 算法求取欧拉回路
     * @return
     */
    public ArrayList<Integer> result(){
        ArrayList<Integer> res = new ArrayList<>();
        if(!hasEularLoop()){
            return res;
        }

        Graph g = (Graph) G.clone();

        Stack<Integer> stack = new Stack<>();
        int curv = 0;
        stack.push(curv);

        while (!stack.isEmpty()){
            if(g.degree(curv) != 0){
                stack.push(curv);
                int w = g.adj(curv).iterator().next();
                g.removeEdge(curv, w);
                curv = w;
            }
            else {
                res.add(curv);
                curv = stack.pop();
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Graph g = new Graph("g6_euler1.txt");
        EulerLoop el = new EulerLoop(g);
        System.out.println(el.result());

        Graph g2 = new Graph("g6_euler2.txt");
        EulerLoop el2 = new EulerLoop(g2);
        System.out.println(el2.result());

    }

}
