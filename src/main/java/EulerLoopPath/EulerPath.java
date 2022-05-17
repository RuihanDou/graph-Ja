package EulerLoopPath;

import GraphAdjExpression.Graph;
import GraphDFS.CC;

import java.util.ArrayList;
import java.util.Stack;

public class EulerPath {

    private Graph G;
    private int s;

    public EulerPath(Graph G, int s){

        if(G.isDirected()){
            throw new IllegalArgumentException("EulerPath only works in undirected graph.");
        }

        this.G = G;
        this.s = s;
    }

    public boolean hasEularPath(){

        CC cc = new CC(G);
        if(cc.count() > 1){
            return false;
        }

        int odd = 0;
        for(int v = 0; v < G.V(); v++){
            if(G.degree(v) % 2 == 1){
                odd++;
            }
        }

        if(odd == 0){
            return true;
        }

        // 有两个点的度是奇数，其他的点的度都是偶数，且起始点的度为奇数也存在
        else if(odd == 2 && G.degree(s) % 2 == 1){
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
        return res;
    }

    public static void main(String[] args) {
        Graph g = new Graph("g6_euler3.txt");
        EulerPath ep = new EulerPath(g, 0);
        System.out.println(ep.result());
    }

}
