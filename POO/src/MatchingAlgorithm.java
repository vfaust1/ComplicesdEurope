import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MatchingAlgorithm {
    private int[][] costMatrix;
    private int numVisitors, numHosts;
    private int[] matchHosts; // Stocke l'appariement visiteur -> hôte
    private boolean[] visitorMatched, hostMatched;

    public MatchingAlgorithm(int[][] matrix) {
        this.numVisitors = matrix.length;
        this.numHosts = matrix[0].length;
        this.costMatrix = matrix;

        matchHosts = new int[numVisitors];
        visitorMatched = new boolean[numVisitors];
        hostMatched = new boolean[numHosts];

        Arrays.fill(matchHosts, -1);
    }

    public Map<Integer, Integer> findOptimalMatching() {
        Map<Integer, Integer> pairs = new HashMap<>();

        for (int v = 0; v < numVisitors; v++) {
            int bestHost = findLeastCostMatch(v);
            if (bestHost != -1) { // Si une correspondance est trouvée
                matchHosts[v] = bestHost;
                pairs.put(v, bestHost);
                visitorMatched[v] = true;
                hostMatched[bestHost] = true;
            }
        }

        return pairs;
    }

    private int findLeastCostMatch(int visitor) {
        int minCost = Integer.MAX_VALUE;
        int bestHost = -1;

        for (int h = 0; h < numHosts; h++) {
            if (!hostMatched[h] && costMatrix[visitor][h] != -1 && costMatrix[visitor][h] < minCost) {
                minCost = costMatrix[visitor][h];
                bestHost = h;
            }
        }

        return bestHost;
    }
}
