package aoc2023;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.ArrayGrid;
import tools.ArrayGrid.Direction;
import tools.Pair;

public class Aoc2314 {

  private static long part1(String[] input) {
    var grid = new ArrayGrid(input);
    var rocks = grid.find('O');
    Pair<Integer, Integer> newCoord;
    for (int i = 0; i < rocks.size(); i++) {
      while (true) {
        newCoord = grid.move(rocks.get(i), Direction.N);
        if (newCoord.getKey() == rocks.get(i).getKey() && newCoord.getValue() == rocks.get(i).getValue()) {
          break;
        }
        rocks.get(i).setKey(newCoord.getKey());
        rocks.get(i).setValue(newCoord.getValue());
      }
    }
    return rocks.stream().map(e -> e.getValue()).reduce(0, (acc, cur) -> acc + input.length - cur);
  }

  static Set<String> visited = new HashSet<>();

  private static long part2(String[] input) {
    // Order to roll stones
    // N W S E
    ArrayGrid grid = new ArrayGrid(input);
    List<Pair<Integer, Integer>> rocks = grid.find('O');
    Pair<Integer, Integer> newCoord = new Pair<>(0, 0);

    long firstSeen = 0;
    String firstSeenGrid = "";
    long lim = 1_000_000_000;
    for (long cycle = 0; cycle < lim; cycle++) {
      rocks.sort((a, b) -> a.getValue() - b.getValue());
      roll(rocks, newCoord, grid, Direction.N);
      rocks.sort((a, b) -> a.getKey() - b.getKey());
      roll(rocks, newCoord, grid, Direction.W);
      rocks.sort((a, b) -> b.getValue() - a.getValue());
      roll(rocks, newCoord, grid, Direction.S);
      rocks.sort((a, b) -> b.getKey() - a.getKey());
      roll(rocks, newCoord, grid, Direction.E);

      if (visited.contains(grid.toString()) && firstSeen == 0) {//State we've already seen
        firstSeen = cycle;
        firstSeenGrid = grid.toString();
//        System.out.println("Visited at " + cycle);
      } else if (firstSeenGrid.equals(grid.toString())) {//State we've already seen repeats again, cycle detected
        long cycleLength = cycle - firstSeen;
//        System.out.println("Again visited at " + cycle + ", cycle length " + cycleLength);
        long remainder = ((lim - firstSeen) % cycleLength);
//        System.out.println("Number of same states are " + ((lim - firstSeen) / cycleLength) + " remainder is " + remainder);
        cycle = lim - remainder;
      } else {
        visited.add(grid.toString());
      }
    }

    return rocks.stream().map(e -> e.getValue()).reduce(0, (acc, cur) -> acc + input.length - cur);
  }

  private static void roll(List<Pair<Integer, Integer>> rocks, Pair<Integer, Integer> newCoord, ArrayGrid grid, Direction dir) {
    for (int i = 0; i < rocks.size(); i++) {
      while (true) {
        newCoord = grid.move(rocks.get(i), dir);
        if (newCoord.getKey() == rocks.get(i).getKey() && newCoord.getValue() == rocks.get(i).getValue()) {
          break;
        }
        rocks.get(i).setKey(newCoord.getKey());
        rocks.get(i).setValue(newCoord.getValue());
      }
    }
  }

  private static void test() {
    var p1 = part1(getInput(true));
    System.out.println("Part 1 test: " + p1 + (p1 == expected1 ? " PASSED" : " FAILED"));

    var p2 = part2(getInput(true));
    System.out.println("Part 2 test: " + p2 + (p2 == expected2 ? " PASSED" : " FAILED"));
  }

  public static void main(String[] args) {
    test();
    System.out.println("Part 1 real: " + part1(getInput(false)));
    System.out.println("Part 2 real: " + part2(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 136;
  static int expected2 = 64;
  static String testStr =
    "O....#....\n" +
    "O.OO#....#\n" +
    ".....##...\n" +
    "OO.#O....O\n" +
    ".O.....O#.\n" +
    "O.#..O.#.#\n" +
    "..O..#O..O\n" +
    ".......O..\n" +
    "#....###..\n" +
    "#OO..#....";
  static String realStr = "";
}
