package ModelizationGraph;

public class LeetCode0695UnionFindSolution {

    class UF{

        private int[] parent;
        private int[] sz;

        /**
         * 并查集
         * @param n
         */
        public UF(int n){

            parent = new int[n];
            // 1. 我们需要一个 sz 数组，存储以第 i 个元素为根节点的集合的元素个数。
            sz = new int[n];
            for(int i = 0 ; i < n ; i ++){
                parent[i] = i;
                sz[i] = 1;
            }
        }

        // 查找元素p所对应的集合编号
        public int find(int p){
            if( p != parent[p] )
                parent[p] = find( parent[p] );
            return parent[p];
        }

        public boolean isConnected(int p , int q){
            return find(p) == find(q);
        }

        public void unionElements(int p, int q){

            int pRoot = find(p);
            int qRoot = find(q);

            if( pRoot == qRoot )
                return;

            parent[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }

        public int size(int p){
            return sz[find(p)];
        }
    }

    private int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int R, C;

    public int maxAreaOfIsland(int[][] grid) {

        if(grid == null) return 0;

        R = grid.length;
        if(R == 0) return 0;

        C = grid[0].length;
        if(C == 0) return 0;

        // 创建一个并查集，并查集中有 R * C 个元素
        UF uf = new UF(R * C);
        // 遍历每一个顶点
        for(int v = 0; v < R * C; v ++){
            int x = v / C, y = v % C;
            // 如果这个格子是“陆地”
            if(grid[x][y] == 1)
                // 我们向这个格子的四个方向查看
                for(int d = 0; d < 4; d ++){
                    // 获得 d 这个方向的坐标：nextx, nexty
                    int nextx = x + dirs[d][0], nexty = y + dirs[d][1];
                    // 如果 nextx, nexty 这个坐标是合法的，并且其所在的格子也是陆地
                    // 此时，我们就需要将 x, y 这个顶点，和 nextx, nexty 这个顶点合并起来
                    if(inArea(nextx, nexty) && grid[nextx][nexty] == 1){
                        int next = nextx * C + nexty;
                        uf.unionElements(v, next);
                    }
                }
        }

        int res = 0;
        for(int v = 0; v < R * C; v ++){
            int x = v / C, y = v % C;
            if(grid[x][y] == 1)
                res = Math.max(res, uf.size(v));
        }
        return res;
    }

    private boolean inArea(int x, int y){
        return x >= 0 && x < R && y >= 0 && y < C;
    }

}
