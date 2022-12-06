package aoc2020;

import static java.util.Arrays.stream;

public class Aoc2017 {
    public static void main(String[] args) {

        var rawInput = ("#.#.##.#\n" +
            "#.####.#\n" +
            "...##...\n" +
            "#####.##\n" +
            "#....###\n" +
            "##..##..\n" +
            "#..####.\n" +
            "#...#.#.").split("\\n");
        var data = new byte[8][8];

        for (var i = 0; i < rawInput.length; i++) {
            for (var j = 0; j < rawInput[i].length(); j++) {
                data[i][j] = rawInput[i].charAt(j) == '#' ? Byte.parseByte("1") : Byte.parseByte("0");
            }
        }

        var a = new Conway3D(data, 50, 50, 50, 50);

        System.out.println(a.countActive());
        a.iterate();
        System.out.println(a.countActive());
        a.iterate();
        System.out.println(a.countActive());
        a.iterate();
        System.out.println(a.countActive());
        a.iterate();
        System.out.println(a.countActive());
        a.iterate();
        System.out.println(a.countActive());
        a.iterate();

        System.out.println(a.countActive());
    }

    static class Conway3D {

        private final int width;
        private final int height;
        private final int depth;
        private final int time;

        byte[][][][] data;

        public Conway3D(byte[][] initialData, int width, int height, int depth, int time) {
            this.width = width;
            this.height = height;
            this.depth = depth;
            this.time = time;

            this.data = new byte[depth][height][width][time];

            var ioff = height / 2;
            for (var i = 0; i < initialData.length; i++) {
                var joff = width / 2;
                for (var j = 0; j < initialData[i].length; j++) {
                    this.data[time / 2][depth / 2][ioff + i][joff + j] = initialData[i][j];
                }
            }
        }

        public void iterate() {
            var newState = deepCopy(data);

            for (var l = 0; l < time; l++) {
                for (var i = 0; i < depth; i++) {
                    for (var j = 0; j < height; j++) {
                        for (var k = 0; k < width; k++) {
                            var aliveNeigh = countNeighbours(l, i, j, k);
                            if (data[l][i][j][k] == 1) {
                                if (aliveNeigh < 2 || aliveNeigh > 3) {
                                    newState[l][i][j][k] = 0;
                                }
                            } else {
                                if (aliveNeigh == 3) {
                                    newState[l][i][j][k] = 1;
                                }
                            }
                        }
                    }
                }
            }

            data = newState;
        }

        public int countActive() {
            var count = 0;
            for (var l = 0; l < time; l++) {
                for (var i = 0; i < depth; i++) {
                    for (var j = 0; j < height; j++) {
                        for (var k = 0; k < width; k++) {
                            if (data[l][i][j][k] == 1) {
                                count++;
                            }
                        }
                    }
                }
            }

            return count;
        }

        private int countNeighbours(int v, int z, int y, int x) {
            var count = 0;
            for (var l = v - 1; l <= v + 1; l++) {
                if (l < 0 || l >= time) {
                    continue;
                }
                for (var i = z - 1; i <= z + 1; i++) {
                    if (i < 0 || i >= depth) {
                        continue;
                    }
                    for (var j = y - 1; j <= y + 1; j++) {
                        if (j < 0 || j >= height) {
                            continue;
                        }
                        for (var k = x - 1; k <= x + 1; k++) {
                            if (k < 0 || k >= width || k == x && i == z && j == y && l == v) {
                                continue;
                            }
                            if (data[l][i][j][k] == 1) {
                                count++;
                            }
                        }
                    }
                }
            }
            return count;
        }

        byte[][][][] deepCopy(byte[][][][] matrix) {
            return stream(matrix)
                .map(el -> stream(el)
                    .map(el2 -> stream(el2)
                        .map(el3 -> el3.clone())
                        .toArray($ -> el2.clone()))
                    .toArray($ -> el.clone()))
                .toArray($ -> matrix.clone());
        }
    }

}
