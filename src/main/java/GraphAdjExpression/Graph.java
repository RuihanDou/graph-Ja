package GraphAdjExpression;

import GraphAdjExpression.AdjSet;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class Graph implements GraphInterface.Graph, Cloneable {

    private int V;
    private int E;
    private TreeSet<Integer>[] adj;
    private boolean directed;

    public Graph(String filename, boolean directed){

        this.directed = directed;

        File file = new File(filename);

        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }
            adj = new TreeSet[V];
            for(int i = 0; i < V; i++){
                adj[i] = new TreeSet<Integer>();
            }

            E = scanner.nextInt();
            if(E < 0){
                throw new IllegalArgumentException("E must be non-negative");
            }

            for(int i = 0; i < E; i ++){
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);

                if(a == b){
                    throw new IllegalArgumentException("Self Loop is Detected!");
                }
                if(adj[a].contains(b)){
                    throw new IllegalArgumentException("Parallel Edges are Detected!");
                }

                adj[a].add(b);
                if(!directed){
                    adj[b].add(a);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Graph(String filename){
        this(filename, false);
    }

    public boolean isDirected(){
        return directed;
    }

    public void validateVertex(int v){
        if(v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is invalid");
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public boolean hasEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        return adj[v].contains(w);
    }

    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v];
    }

    // 只对无向图
    public int degree(int v){
        validateVertex(v);
        return adj[v].size();
    }

    public void removeEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);

        adj[v].remove(w);
        if(!directed){
            adj[w].remove(v);
        }
    }

    @Override
    public Object clone(){
        try {
            Graph cloned = (Graph)super.clone();
            cloned.adj = new TreeSet[V];
            for(int v = 0; v < V; v++){
                cloned.adj[v] = new TreeSet<>();
                for(int w : this.adj[v]){
                    cloned.adj[v].add(w);
                }
            }
            return cloned;
        }
        catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("V = %d, E = %d, directed = %b\n", V, E, directed));
        for(int v = 0; v < V; v ++){
            sb.append(String.format("%d : ", v));
            for(int w : adj[v]){
                sb.append(String.format("%d ", w));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args){

//        AdjSet adjSet = new AdjSet("g0.txt");
        Graph g = new Graph("g8_directed.txt", true);
        System.out.print(g);
    }

}
