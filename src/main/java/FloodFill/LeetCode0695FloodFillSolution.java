package FloodFill;

/**
 * 使用 Flood Fill 算法 解决 LeetCode0695
 */
public class LeetCode0695FloodFillSolution {

//    private int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int R, C;
    private int[][] grid;

    private boolean[][] visted;

    public int maxAreaOfIsland(int[][] grid) {

        if(grid == null) {
            return 0;
        }

        R = grid.length;
        if(R == 0){
            return 0;
        }
        C = grid[0].length;
        if(C == 0){
            return 0;
        }

        this.grid = grid;

        int res = 0;
        visted = new boolean[R][C];
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                if(!visted[i][j] && grid[i][j] == 1){
                    res = Math.max(res, dfs(i, j));
                }
            }
        }
        return res;
    }

//    private int dfs(int x, int y) {
//        visted[x][y] = true;
//        int res = 1;
//
//        for(int d = 0; d < 4; d++){
//            int nextx = x + dirs[d][0], nexty = y + dirs[d][1];
//            if(inArea(nextx, nexty) && !visted[nextx][nexty] && grid[nextx][nexty] == 1){
//                res += dfs(nextx, nexty);
//            }
//        }
//
//        return res;
//    }

    private int dfs(int x, int y){
        if(x < 0 || x >= R || y < 0 || y >= C || grid[x][y] == 0){
            return 0;
        }

        grid[x][y] = 0;
        int res = 1;
        res += dfs(x - 1, y);
        res += dfs(x, y + 1);
        res += dfs(x + 1, y);
        res += dfs(x, y - 1);
        return res;
    }

    private boolean inArea(int x, int y) {
        return x >= 0 && x < R && y >= 0 && y < C;
    }

}
