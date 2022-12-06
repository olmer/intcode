package aoc2021;

public class Aoc2111 {
    public static void main(String[] args) throws Exception {
        var input = getInput();
        var data = new int[10][10];
        for (var y = 0; y < getInput().length; y++) {
            for (var x = 0; x < input[y].length(); x++) {
                data[y][x] = input[y].charAt(x) - '0';
            }
        }

        var r = 0;

        for (var step = 1; step <= 500; step++) {
            for (var y = 0; y < data.length; y++) {
                for (var x = 0; x < data[y].length; x++) {
                    data[y][x]++;
                }
            }

            r = chainFlash(data);
            if (r == 100) {
                r = step;
                break;
            } else {
                r = 0;
            }
        }

        System.out.println(r);
    }

    private static int chainFlash(int[][] data) {
        var flashedAtLeastOnce = false;
        var alreadyFlashed = new int[10][10];
        var flashes = 0;
        do {
            flashedAtLeastOnce = false;
            for (var y = 0; y < data.length; y++) {
                for (var x = 0; x < data[y].length; x++) {
                    if (data[y][x] > 9) {
                        flashedAtLeastOnce = true;
                        flashes++;
                        data[y][x] = 0;
                        alreadyFlashed[y][x] = 1;
                        increaseNeighbours(data, x, y, alreadyFlashed);
                    }
                }
            }
        } while (flashedAtLeastOnce && flashes != 100);

        return flashes;
    }

    private static void increaseNeighbours(int[][] data, int x, int y, int[][] alreadyFlashed) {
        for (var i = y - 1; i <= y + 1; i++) {
            if (i < 0 || i >= data.length) continue;
            for (var j = x - 1; j <= x + 1; j++) {
                if (j < 0 || j >= data[i].length || x == j && y == i || alreadyFlashed[i][j] == 1) continue;
                data[i][j]++;
            }
        }
    }

    private static String[] getInput() {
        return ("3172537688\n" +
            "4566483125\n" +
            "6374512653\n" +
            "8321148885\n" +
            "4342747758\n" +
            "1362188582\n" +
            "7582213132\n" +
            "6887875268\n" +
            "7635112787\n" +
            "7242787273").split("\n");
    }
}
