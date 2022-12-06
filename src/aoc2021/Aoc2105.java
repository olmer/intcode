package aoc2021;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Aoc2105 {
    public static void main(String[] args) {
        var size = 10;

        var lines = Arrays.stream(getInput())
            .map(e -> Arrays.stream(e.split(" -> "))
                .map(f -> Arrays.stream(f.split(",")).mapToInt(Integer::parseInt).toArray())
                .collect(Collectors.toList()))
            .collect(Collectors.toList());

        int[][] map = new int[size][size];

        for (var line : lines) {
            if (line.get(0)[0] == line.get(1)[0]) {
                var x = line.get(0)[0];
                for (var y = Math.min(line.get(0)[1], line.get(1)[1]); y <= Math.max(line.get(0)[1], line.get(1)[1]); y++) {
                    map[y][x]++;
                }
            } else if (line.get(0)[1] == line.get(1)[1]) {
                var y = line.get(0)[1];
                for (var x = Math.min(line.get(0)[0], line.get(1)[0]); x <= Math.max(line.get(0)[0], line.get(1)[0]); x++) {
                    map[y][x]++;
                }
            } else {
                var xmod = line.get(0)[0] > line.get(1)[0] ? -1 : 1;
                var ymox = line.get(0)[1] > line.get(1)[1] ? -1 : 1;
                var x = line.get(0)[0];
                var y = line.get(0)[1];
                while (true) {
                    map[y][x]++;
                    if (x == line.get(1)[0]) break;
                    x += xmod;
                    y += ymox;
                }
            }
        }

        var r = 0;
        for (var i = 0; i < size; i++) {
            for (var j = 0; j < size; j++) {
                if (map[i][j] > 1) {
                    r++;
                }
            }
        }

        System.out.println(r);
    }

    private static String[] getInput() {
        return ("0,9 -> 5,9\n" +
            "8,0 -> 0,8\n" +
            "9,4 -> 3,4\n" +
            "2,2 -> 2,1\n" +
            "7,0 -> 7,4\n" +
            "6,4 -> 2,0\n" +
            "0,9 -> 2,9\n" +
            "3,4 -> 1,4\n" +
            "0,0 -> 8,8\n" +
            "5,5 -> 8,2").split("\n");
    }
}
