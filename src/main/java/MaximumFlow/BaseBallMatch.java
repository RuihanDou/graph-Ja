package MaximumFlow;


import GraphAdjExpression.WeightedGraph;

/**
 * Team              win         loss         left          NY      Bal     Bos     Tor     Det
 *
 * New York          75           59           28            0       3       8       7       3
 * Baltimore         71           63           28            3       0       2       7       4
 * Boston            69           66           27            8       2       0       0       0
 * Toronto           63           72           27            7       7       0       0       0
 * Detroit           49           86           27            3       4       0       0       0
 *
 * 图构建
 *
 * s                  0
 *
 * NY - Bal            1
 * NY - Bos            2
 * NY - Tor            3
 * Bal - Bos           4
 * Bal - Tor           5
 *
 * NY                  6
 * Bal                 7
 * Bos                 8
 * Tor                 9
 *
 * t                   10
 *
 */
public class BaseBallMatch {

    public static void main(String[] args) {
        WeightedGraph network = new WeightedGraph("baseball.txt", true);
        MaxFlow maxFlow = new MaxFlow(network, 0, 10);
        System.out.println(maxFlow.result());

        for(int v = 0; v < network.V(); v++){
            for(int w : network.adj(v)){
                System.out.println(String.format("%d - %d : %d / %d", v, w, maxFlow.flow(v, w), network.getWeight(v, w)));
            }
        }
    }

}
