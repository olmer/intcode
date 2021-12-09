import java.util.ArrayList;
import java.util.Comparator;

public class Aoc2109 {
    public static void main(String[] args) {
        var r = new ArrayList<Integer>();
        var input = getInput();

        var heights = new int[input.length][input[0].length()];

        for (var i = 0; i < input.length; i++) {
            for (var j = 0; j < input[0].length(); j++) {
                heights[i][j] = input[i].charAt(j) - '0';
            }
        }

        for (var i = 0; i < heights.length; i++) {
            for (var j = 0; j < heights[i].length; j++) {
                var n1 = i - 1;
                var n2 = i + 1;
                var n3 = j - 1;
                var n4 = j + 1;
                if (n1 >= 0) {
                    if (heights[i][j] >= heights[n1][j]) continue;
                }
                if (n2 < heights.length) {
                    if (heights[i][j] >= heights[n2][j]) continue;
                }
                if (n3 >= 0) {
                    if (heights[i][j] >= heights[i][n3]) continue;
                }
                if (n4 < heights[i].length) {
                    if (heights[i][j] >= heights[i][n4]) continue;
                }

                r.add(countBasinSize(heights, i, j, new int[heights.length][heights[i].length]));
            }
        }

        r.sort(Comparator.reverseOrder());

        System.out.println(r.get(0) * r.get(1) * r.get(2));
    }

    private static int countBasinSize(int[][] data, int x, int y, int[][] visited) {
        if (x < 0 || x >= data.length) return 0;
        if (y < 0 || y >= data[x].length) return 0;

        if (visited[x][y] == 1) return 0;

        visited[x][y] = 1;
        if (data[x][y] == 9) {
            return 0;
        }

        var r = 1;
        r += countBasinSize(data, x - 1, y, visited);
        r += countBasinSize(data, x + 1, y, visited);
        r += countBasinSize(data, x, y - 1, visited);
        r += countBasinSize(data, x, y + 1, visited);

        return r;
    }

    private static String[] getInput() {
        return ("2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678").split("\n");
    }
}
