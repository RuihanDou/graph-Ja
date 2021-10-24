package GraphInterface;

public interface Graph {
    int V();
    int E();
    void validateVertex(int v);
    boolean hasEdge(int v, int w);
    Iterable<Integer> adj(int v);
    int degree(int v);
}
