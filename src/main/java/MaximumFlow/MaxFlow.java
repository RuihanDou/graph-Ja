package MaximumFlow;

import GraphAdjExpression.WeightedGraph;

import java.util.*;

/**
 *  Ford-Fulkerson 思想的复杂度是 O(maxflow * E)
 *
 *  Edmonds-Karp 算法的复杂度 O(V * E * E)
 *
 */
public class MaxFlow {

    private WeightedGraph network;
    // 源点 和 汇点
    private int s, t;

    private WeightedGraph rG;

    private int maxFlow = 0;

    public MaxFlow(WeightedGraph network, int s, int t){

        if(!network.isDirected()){
            throw new IllegalArgumentException("MaxFlow only works in directed graph.");
        }

        if(network.V() < 2){
            throw new IllegalArgumentException("The netword should has at least 2 vertices.");
        }

        network.validateVertex(s);
        network.validateVertex(t);

        if(s == t){
            throw new IllegalArgumentException("s and t should be different.");
        }

        this.network = network;
        this.s = s;
        this.t = t;

        // 创建残量图
        this.rG = new WeightedGraph(network.V(), true);

        for (int v = 0; v < network.V(); v++){
            for(int w : network.adj(v)){
                int c = network.getWeight(v, w);
                rG.addEdge(v, w, c);
                rG.addEdge(w, v, 0);
            }
        }

        while (true){

            ArrayList<Integer> augPath = getAugmentingPath();

            if(augPath.size() == 0){
                break;
            }

            int f = Integer.MAX_VALUE;
            // 计算增广路径上的最小值
            for(int i = 1; i < augPath.size(); i++){
                int v = augPath.get(i - 1);
                int w = augPath.get(i);
                f = Math.min(f, rG.getWeight(v, w));
            }
            maxFlow += f;

            // 根据增广路径更新 rG 残量图
            for(int i = 1; i < augPath.size(); i++){
                int v = augPath.get(i - 1);
                int w = augPath.get(i);

//                // v -> w 是正向边
//                if(network.hasEdge(v, w)){
//                    rG.setWeight(v, w, rG.getWeight(v, w) - f);
//                    rG.setWeight(w, v, rG.getWeight(w, v) + f);
//                }
//                // v -> w 是反向边
//                else {
//                    rG.setWeight(w, v, rG.getWeight(w, v) + f);
//                    rG.setWeight(v, w, rG.getWeight(v, w) - f);
//                }

                rG.setWeight(v, w, rG.getWeight(v, w) - f);
                rG.setWeight(w, v, rG.getWeight(w, v) + f);
            }

        }

    }

    private ArrayList<Integer> getAugmentingPath() {

        Queue<Integer> q = new LinkedList<>();
        int[] pre = new int[network.V()];
        Arrays.fill(pre, -1);

        q.add(s);
        pre[s] = s;
        while (!q.isEmpty()){

            int cur = q.remove();

            if(cur == t){
                break;
            }

            for (int next : rG.adj(cur)){
                if(pre[next] == -1 && rG.getWeight(cur, next) > 0){
                    pre[next] = cur;
                    q.add(next);
                }
            }
        }

        ArrayList<Integer> res = new ArrayList<>();
        if(pre[t] == -1){
            return res;
        }

        int curr = t;
        while (curr != s){
            res.add(curr);
            curr = pre[curr];
        }
        res.add(s);

        Collections.reverse(res);
        return res;

    }

    public int result(){
        return maxFlow;
    }

    public int flow(int v, int w){

        if(!network.hasEdge(v, w)){
            throw new IllegalArgumentException(String.format("No edge %d - %d", v, w));
        }

        // 残量图里反向权重
        return rG.getWeight(w, v);
    }

    public static void main(String[] args) {
        WeightedGraph network = new WeightedGraph("network.txt", true);
        MaxFlow maxFlow = new MaxFlow(network, 0, 3);
        System.out.println(maxFlow.result());

        for(int v = 0; v < network.V(); v++){
            for(int w : network.adj(v)){
                System.out.println(String.format("%d - %d : %d / %d", v, w, maxFlow.flow(v, w), network.getWeight(v, w)));
            }
        }

        WeightedGraph network2 = new WeightedGraph("network2.txt", true);
        MaxFlow maxFlow2 = new MaxFlow(network2, 0, 5);
        System.out.println(maxFlow2.result());

        for(int v = 0; v < network2.V(); v++){
            for(int w : network2.adj(v)){
                System.out.println(String.format("%d - %d : %d / %d", v, w, maxFlow2.flow(v, w), network2.getWeight(v, w)));
            }
        }
    }

}
