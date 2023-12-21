package aoc2023;

import java.util.ArrayDeque;

import tools.ArrayGrid;
import tools.Pair;

public class Aoc2321 {

  static boolean debug = false;

  private static long countFilled(int x, int y, int lim, ArrayGrid grid) {
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
          ArrayGrid.CARDINAL
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

//      System.out.println("step " + i + " size " + q.size());
      if (debug)
        print(grid);
    }
    return q.size();
  }

  private static long part1(String[] inputString) {
    ArrayGrid grid = new ArrayGrid(inputString);

    return countFilled(inputString.length / 2, inputString.length / 2, 64, grid);
  }

  private static long part2(String[] input) {
    int LIM = 26501365;

    int halfsize = input.length / 2;
    int fullsize = input.length;
    int fullminusone = fullsize - 1;

    ArrayGrid grid = new ArrayGrid(input);

    long fullGridResult = countFilled(halfsize, halfsize, fullsize, grid);//visited last corner, 7612
//    long fullGridResult = 7574;

    int stepsToFillGrid = input.length;
    int cycles = LIM / stepsToFillGrid;

//    debug = true;

//    cycles = 3;

    int remainderSteps = LIM % stepsToFillGrid;
    remainderSteps = halfsize + 1;

    int threequarters = fullsize + remainderSteps;

//    halfsize = remainderSteps;

    long fulls = 1;
    long quarters = cycles;
    long threeQuarters = cycles - 1;

    long temp = 0;
    for (int i = 0; i < cycles; i++) {
      fulls += temp;
      temp += 4;
    }

//    System.out.println("Half cycles: " + cycles);
//    System.out.println("Full: " + fulls);
//    System.out.println("Quarter: " + quarters * 4);
//    System.out.println("Three quarters: " + threeQuarters * 4);

    long r = 0;

    //619937159992774 small -> too small
    //619937159992774 small with 3/4 as 195 -> too low
    //623049717436686 full calc with 3/4 as 195 -> somewhere close!

    //629512648645448
    //629506623888950 -> not right
    //619939468224364 -> not right
    //619939489264008
    //621494544278648



//    var a = Long.MAX_VALUE > 623047466658012L;

    r += fulls * fullGridResult;
    r += quarters * countFilled(0, 0, remainderSteps, grid);
    r += quarters * countFilled(0, fullminusone, remainderSteps, grid);
    r += quarters * countFilled(fullminusone, 0, remainderSteps, grid);
    r += quarters * countFilled(fullminusone, fullminusone, remainderSteps, grid);
    r += threeQuarters * countFilled(0, 0, threequarters, grid);
    r += threeQuarters * countFilled(0, fullminusone, threequarters, grid);
    r += threeQuarters * countFilled(fullminusone, 0, threequarters, grid);
    r += threeQuarters * countFilled(fullminusone, fullminusone, threequarters, grid);
    r += countFilled(halfsize, 0, halfsize + remainderSteps, grid);
    r += countFilled(0, halfsize, halfsize + remainderSteps, grid);
    r += countFilled(halfsize, fullminusone, halfsize + remainderSteps, grid);
    r += countFilled(fullminusone, halfsize, halfsize + remainderSteps, grid);

//    System.out.println(countFilled(65, 65, 65, grid));

    return r;
  }

  private static void print(ArrayGrid input) {
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
