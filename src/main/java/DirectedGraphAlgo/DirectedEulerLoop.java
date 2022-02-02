package DirectedGraphAlgo;

import GraphAdjExpression.Graph;
import GraphDFS.CC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class DirectedEulerLoop {

    private Graph G;

    public DirectedEulerLoop(Graph G){

        if(!G.isDirected()){
            throw new IllegalArgumentException("EulerLoop only works in directed graph.");
        }

        this.G = G;
    }

    /**
     * 已经默认该图联通
     *
     * 对于欧拉回路
     *
     * 存在欧拉回路的 充要条件是 图联通且每个点的度为偶数
     *
     * @return
     */
    public boolean hasEularLoop(){

//        CC cc = new CC(G);
//        if(cc.count() > 1){
//            return false;
//        }

        for(int v = 0; v < G.V(); v++){
            if(G.indegree(v) != G.outdegree(v)){
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
            if(g.outdegree(curv) != 0){
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
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {

//        Graph g = new Graph("g9_directed_cycle_detection_no.txt", true);
        Graph g = new Graph("g9_directed_euler_loop.txt", true);
        DirectedEulerLoop el = new DirectedEulerLoop(g);
        System.out.println(el.hasEularLoop());
        System.out.println(el.result());


    }

}
