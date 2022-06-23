package MatchProblem;

import GraphDFS.BipartitionDetection;
import GraphInterface.Graph;

import java.util.Arrays;

public class HungarianDFS {

    private Graph G;
    private int maxMatching;
    // matching 存的是 v 对应的匹配点 matching[v] = w and matching[w] = v v和w是一组匹配
    private int[] matching;
    private boolean[] visited;

    public HungarianDFS(Graph G){

        BipartitionDetection bd = new BipartitionDetection(G);
        if(!bd.isBipartite()){
            throw new IllegalArgumentException("Hungarian only works for bipartite graph.");
        }

        this.G = G;

        int[] colors = bd.colors();
        matching = new int[G.V()];
        Arrays.fill(matching, -1);

        visited = new boolean[G.V()];

        // 只从左侧开始遍历
        for(int v = 0; v < G.V(); v++){

            if(colors[v] == 0 && matching[v] == -1){
                Arrays.fill(visited, false);
                if(dfs(v)){
                    maxMatching++;
                }
            }

        }

    }

    // v 一定是左半边
    private boolean dfs(int v) {
        visited[v] = true;
        for(int u : G.adj(v)){
            if(!visited[u]){
                visited[u] = true;
                if(matching[u] == -1 || dfs(matching[u])){
                    matching[v] = u;
                    matching[u] = v;
                    return true;
                }
            }
        }
        return false;
    }

    public int maxMatching(){
        return maxMatching;
    }

    public boolean isPerfectMatching(){
        return maxMatching * 2 == G.V();
    }

    public static void main(String[] args) {
        Graph graph = new GraphAdjExpression.Graph("g11_match1.txt");
        HungarianDFS hungarian = new HungarianDFS(graph);
        System.out.println(hungarian.maxMatching());

        Graph graph1 = new GraphAdjExpression.Graph("g11_match2.txt");
        HungarianDFS hungarian1 = new HungarianDFS(graph1);
        System.out.println(hungarian1.maxMatching());
    }

}
