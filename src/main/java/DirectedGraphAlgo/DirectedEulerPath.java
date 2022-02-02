package DirectedGraphAlgo;

import GraphAdjExpression.Graph;
import GraphDFS.CC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class DirectedEulerPath {

    private Graph G;
    private int s;

    public DirectedEulerPath(Graph G, int s){

        if(!G.isDirected()){
            throw new IllegalArgumentException("DirectedEulerPath only works in directed graph.");
        }

        this.G = G;
        this.s = s;
    }

    /**
     *
     * 默认图已经联通
     *
     * @return
     */
    public boolean hasEularPath(){

//        CC cc = new CC(G);
//        if(cc.count() > 1){
//            return false;
//        }

        int start = 0;
        int end = 0;
        for(int v = 0; v < G.V(); v++){
            if(G.indegree(v) < G.outdegree(v)){
                start++;
            } else if(G.indegree(v) > G.outdegree(v)){
                end++;
            }
        }

        if(start == 0 && end == 0){
            return true;
        }
        else if(start == 1 && end == 1 && G.indegree(s) < G.outdegree(s)){
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<Integer> result(){
        ArrayList<Integer> res = new ArrayList<>();
        if(!hasEularPath()){
            return res;
        }

        Graph g = (Graph) G.clone();

        Stack<Integer> stack = new Stack<>();
        int curv = s;
        stack.push(s);

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
        Collections.reverse(res);
        return res;
    }

}
