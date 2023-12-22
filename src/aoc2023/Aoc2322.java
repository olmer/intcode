package aoc2023;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import tools.Parse;

public class Aoc2322 {

  private static long part1(String[] input) {
    int maxX = 0;
    int maxY = 0;
    int maxZ = 0;
    for (var in : input) {
      var t = Parse.integers(in);
      maxX = Math.max(maxX, Math.max(t.get(0), t.get(3)));
      maxY = Math.max(maxY, Math.max(t.get(1), t.get(4)));
      maxZ = Math.max(maxZ, Math.max(t.get(2), t.get(5)));
    }
    int[][][] grid = new int[maxZ + 1][maxY + 1][maxX + 1];
    List<List<Integer>> bricks = Arrays.stream(input).map(Parse::integers).collect(Collectors.toList());
    bricks.sort(Comparator.comparingInt(a -> Math.min(a.get(2), a.get(5))));

    Set<Integer> invalidBlocks = new HashSet<>();
    Set<Integer> droppedBlocks = new HashSet<>();

    for (int idx = 1; idx <= bricks.size(); idx++) {
      droppedBlocks.add(idx);
      List<Integer> brick = bricks.get(idx - 1);
      Set<Integer> blocksUnder = blocksUnder(brick, grid);
      if (!blocksUnder.isEmpty()) {
        put(brick, grid, idx);
        if (blocksUnder.size() == 1) {
          invalidBlocks.addAll(blocksUnder);
        }
      } else {
        brick.set(2, brick.get(2) - 1);
        brick.set(5, brick.get(5) - 1);
        idx--;
      }
    }
    droppedBlocks.removeAll(invalidBlocks);
    return droppedBlocks.size();
  }

  private static long part2(String[] input) {
    int maxX = 0;
    int maxY = 0;
    int maxZ = 0;
    for (var in : input) {
      var t = Parse.integers(in);
      maxX = Math.max(maxX, Math.max(t.get(0), t.get(3)));
      maxY = Math.max(maxY, Math.max(t.get(1), t.get(4)));
      maxZ = Math.max(maxZ, Math.max(t.get(2), t.get(5)));
    }
    int[][][] grid = new int[maxZ + 1][maxY + 1][maxX + 1];
    List<List<Integer>> bricks = Arrays.stream(input).map(Parse::integers).collect(Collectors.toList());
    bricks.sort(Comparator.comparingInt(a -> Math.min(a.get(2), a.get(5))));

    Map<Integer, Set<Integer>> topologicalMap = new HashMap<>();
    long r = 0;
    for (int idx = 1; idx <= bricks.size(); idx++) {
      List<Integer> brick = bricks.get(idx - 1);
      Set<Integer> blocksUnder = blocksUnder(brick, grid);
      topologicalMap.put(idx, blocksUnder);
      if (!blocksUnder.isEmpty()) {
        put(brick, grid, idx);
      } else {
        brick.set(2, brick.get(2) - 1);
        brick.set(5, brick.get(5) - 1);
        idx--;
      }
    }

    //try every entry for chain reaction
    for (var tryEntry : topologicalMap.entrySet()) {
      Map<Integer, Set<Integer>> mapCopy = new HashMap<>();
      for (var t : topologicalMap.entrySet()) {
        mapCopy.put(t.getKey(), new HashSet<>(t.getValue()));
      }

      //topological sort
      ArrayDeque<Integer> q = new ArrayDeque<>();
      q.offer(tryEntry.getKey());
      while (!q.isEmpty()) {
        var removingIdx = q.poll();
        for (var t : mapCopy.entrySet()) {
          if (t.getValue().contains(removingIdx)) {
            t.getValue().remove(removingIdx);
            if (t.getValue().isEmpty()) {
              q.offer(t.getKey());
              r++;
            }
          }
        }
      }
    }

    return r;
  }

  private static Set<Integer> blocksUnder(List<Integer> brick, int[][][] grid) {
    Set<Integer> blocksUnderneath = new HashSet<>();
    int z = Math.min(brick.get(2), brick.get(5));
    if (z == 0) return Set.of(-1);
    for (int y = Math.min(brick.get(1), brick.get(4)); y <= Math.max(brick.get(1), brick.get(4)); y++) {
      for (int x = Math.min(brick.get(0), brick.get(3)); x <= Math.max(brick.get(0), brick.get(3)); x++) {
        if (grid[z - 1][y][x] > 0) {
          blocksUnderneath.add(grid[z - 1][y][x]);
        }
      }
    }
    return blocksUnderneath;
  }

  private static void put(List<Integer> brick, int[][][] grid, int idx) {
    for (int z = Math.min(brick.get(2), brick.get(5)); z <= Math.max(brick.get(2), brick.get(5)); z++) {
      for (int y = Math.min(brick.get(1), brick.get(4)); y <= Math.max(brick.get(1), brick.get(4)); y++) {
        for (int x = Math.min(brick.get(0), brick.get(3)); x <= Math.max(brick.get(0), brick.get(3)); x++) {
          grid[z][y][x] = idx;
        }
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
    System.out.println(part1(getInput(false)));
    System.out.println(part2(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 5;
  static int expected2 = 7;
  static String testStr = "1,0,1~1,2,1\n" +
    "0,0,2~2,0,2\n" +
    "0,2,3~2,2,3\n" +
    "0,0,4~0,2,4\n" +
    "2,0,5~2,2,5\n" +
    "0,1,6~2,1,6\n" +
    "1,1,8~1,1,9";
  static String realStr = "1,0,1~1,2,1\n" +
    "0,0,2~2,0,2\n" +
    "0,2,3~2,2,3\n" +
    "0,0,4~0,2,4\n" +
    "2,0,5~2,2,5\n" +
    "0,1,6~2,1,6\n" +
    "1,1,8~1,1,9";
}
