package GraphDFS;

import GraphInterface.Graph;

import java.util.Arrays;

public class BipartitionDetection {

    private Graph G;

    private boolean[] visited;
    private int[] colors;
    private boolean isBipartite = true;

    public BipartitionDetection(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        colors = new int[G.V()];
        Arrays.fill(colors, -1);

        for(int v = 0; v < G.V(); v++){
            if(!visited[v]){
                if(!dfs(v, 0)){
                    isBipartite = false;
                    break;
                }
            }
        }
    }

    private boolean dfs(int v, int color) {
        visited[v] = true;
        colors[v] = color;

        for(int w : G.adj(v)){
            if(!visited[w]){
                if(!dfs(w, 1 - color)){
                    return false;
                }
            }
            else if(colors[w] == colors[v]){
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite(){
        return isBipartite;
    }

    public int[] colors(){
        return colors;
    }

    public static void main(String[] args) {

        Graph g = new GraphAdjExpression.Graph("g2_bipartition.txt");
        BipartitionDetection bipartitionDetection = new BipartitionDetection(g);
        System.out.println(bipartitionDetection.isBipartite());

        Graph g1 = new GraphAdjExpression.Graph("g2_not_bipartition1.txt");
        BipartitionDetection bipartitionDetection1 = new BipartitionDetection(g1);
        System.out.println(bipartitionDetection1.isBipartite());

        Graph g2 = new GraphAdjExpression.Graph("g2_bipartition1.txt");
        BipartitionDetection bipartitionDetection2 = new BipartitionDetection(g2);
        System.out.println(bipartitionDetection2.isBipartite());

    }

}
