package aoc2023;

import java.util.ArrayDeque;

import tools.MutableGrid;
import tools.Pair;

public class Aoc2321 {

  static boolean debug = false;

  private static long countFilled(int x, int y, int lim, MutableGrid grid) {
    ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();
    q.offer(new Pair<>(x, y));

    for (int i = 0; i < lim; i++) {
      int size = q.size();
      boolean[][] visited = new boolean[grid.cols()][grid.rows()];
      while (size-- > 0) {
        var next = q.poll();
        var neigs = grid.getValidNeighboursWithCoords(
          next.getKey(),
          next.getValue(),
          MutableGrid.CARDINAL
        );
        for (var neig : neigs) {
          if (neig.getKey() == '#') continue;
          if (visited[neig.getValue().getValue()][neig.getValue().getKey()]) continue;
          visited[neig.getValue().getValue()][neig.getValue().getKey()] = true;
          grid.set(neig.getValue(), 'O');
          q.offer(neig.getValue());
        }
        grid.set(next, '.');
      }

      if (debug)
        print(grid);
    }
    return q.size();
  }

  private static long part1(String[] inputString) {
    MutableGrid grid = new MutableGrid(inputString);

    return countFilled(inputString.length / 2, inputString.length / 2, 64, grid);
  }

  private static long part2(String[] input) {
    int steps = 26501365;
    int size = input.length;
    int gridWidth = steps / size - 1;
    long odd = (long)Math.pow(gridWidth / 2 * 2 + 1, 2);
    long even = (long)Math.pow((gridWidth + 1) / 2 * 2, 2);
    int halfsize = input.length / 2;

    MutableGrid grid = new MutableGrid(input);
    long oddPoints = countFilled(halfsize, halfsize, size * 2 + 1, grid);
    long evenPoints = countFilled(halfsize, halfsize, size * 2, grid);
    long topCorner = countFilled(halfsize, size - 1, size - 1, grid);
    long rightCorner = countFilled(0, halfsize, size - 1, grid);
    long botCorner = countFilled(halfsize, 0, size - 1, grid);
    long leftCorner = countFilled(size - 1, halfsize, size - 1, grid);

    long smallTopRight = countFilled(0, size - 1, size / 2 - 1, grid);
    long smallTopLeft = countFilled(size - 1, size - 1, size / 2 - 1, grid);
    long smallBotRight = countFilled(0, 0, size / 2 - 1, grid);
    long smallBotLeft = countFilled(size - 1, 0, size / 2 - 1, grid);

    long bigTopRight = countFilled(0, size - 1, size * 3 / 2 - 1, grid);
    long bigTopLeft = countFilled(size - 1, size - 1, size * 3 / 2 - 1, grid);
    long bigBotRight = countFilled(0, 0, size * 3 / 2 - 1, grid);
    long bigBotLeft = countFilled(size - 1, 0, size * 3 / 2 - 1, grid);

    long r = 0;
    r += odd * oddPoints;
    r += even * evenPoints;
    r += topCorner;
    r += rightCorner;
    r += botCorner;
    r += leftCorner;
    r += (gridWidth + 1) * (smallTopRight + smallTopLeft + smallBotRight + smallBotLeft);
    r += gridWidth * (bigTopRight + bigTopLeft + bigBotRight + bigBotLeft);

    return r;
  }

  private static void print(MutableGrid input) {
    System.out.println(input);
  }

  private static void test() {
//    var p1 = part1(getInput(true));
//    System.out.println("Part 1 test: " + p1 + (p1 == expected1 ? " PASSED" : " FAILED"));

    var p2 = part2(getInput(true));
    System.out.println("Part 2 test: " + p2 + (p2 == expected2 ? " PASSED" : " FAILED"));
  }

  public static void main(String[] args) {
//    test();
//    System.out.println(part1(getInput(false)));
    System.out.println(part2(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 16;
  static int expected2 = 0;
  static String testStr =
    "...........\n" +
    ".....###.#.\n" +
    ".###.##..#.\n" +
    "..#.#...#..\n" +
    "....#.#....\n" +
    ".##..S####.\n" +
    ".##..#...#.\n" +
    ".......##..\n" +
    ".##.#.####.\n" +
    ".##..##.##.\n" +
    "...........";
  static String realStr =
"";
}
