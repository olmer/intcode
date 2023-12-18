package aoc2023;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import tools.Pair;
import tools.Parse;

public class Aoc2318 {

  private static long part1(String[] input) {
    int c = 10000;
    int r = 10000;
    int x = 5000;
    int y = 5000;
    int[][] visited = new int[r][c];
    visited[y][x] = 1;

    for (String t : input) {
      String[] in = t.split(" ");

      int len = Parse.integers(in[1]).get(0);
      int[] dir = new int[2];
      switch (in[0]) {
        case "U" -> {dir = new int[]{0, -1};}
        case "R" -> {dir = new int[]{1, 0};}
        case "D" -> {dir = new int[]{0, 1};}
        case "L" -> {dir = new int[]{-1, 0};}
      }
      while (len-- > 0) {
        x += dir[0];
        y += dir[1];
        visited[y][x] = 1;
      }
    }
    ArrayDeque<int[]> q = new ArrayDeque<>();
    for (int i = 0; i < visited.length; i++) {
      q.offer(new int[]{0, i});
      visited[i][0] = 2;
      q.offer(new int[]{visited[i].length - 1, i});
      visited[i][visited[i].length - 1] = 2;
    }
    for (int i = 0; i < visited[0].length; i++) {
      q.offer(new int[]{i, 0});
      visited[0][i] = 2;
      q.offer(new int[]{i, visited.length - 1});
      visited[visited.length - 1][i] = 2;
    }
    long res = 0;
    while (!q.isEmpty()) {
      var next = q.poll();
      int[] coord = new int[] {1, 0, -1, 0, 1};
      for (int i = 0; i < 4; i++) {
        int newx = next[0] + coord[i];
        int newy = next[1] + coord[i + 1];
        if (newx < 0 || newy < 0 || newx >= visited[0].length || newy >= visited.length)
          continue;

        if (visited[newy][newx] > 0)
          continue;

        visited[newy][newx] = 2;
        q.offer(new int[]{newx, newy});
      }
    }
    for (int i = 0; i < visited.length; i++) {
      for (int j = 0; j < visited[i].length; j++) {
        if (visited[i][j] < 2)
          res++;
      }
    }
    return res;
  }

  private static long part2(String[] input) {
    List<Pair<Long, Long>> coords = new ArrayList<>();
    long x = 0;
    long y = 0;
    long d = 0;
    for (String t : input) {
      String[] in = t.split(" ");

      long len = Long.parseLong(in[2].substring(2, in[2].length() - 2), 16);
      long[] dir = new long[2];
      switch (in[2].substring(7, 8)) {
        case "3" -> {dir = new long[]{0, -1};}
        case "0" -> {dir = new long[]{1, 0};}
        case "1" -> {dir = new long[]{0, 1};}
        case "2" -> {dir = new long[]{-1, 0};}
      }
      x += dir[0] * len;
      y += dir[1] * len;
      d += len;
      coords.add(new Pair<>(x, y));
    }
    long a = 0;
    for (int i = 0; i < coords.size(); i++) {
      if (i == coords.size() - 1) {
        a += (coords.get(i).getKey() * coords.get(0).getValue() - coords.get(0).getKey() * coords.get(i).getValue());
      } else {
        a += (coords.get(i).getKey() * coords.get(i + 1).getValue() - coords.get(i + 1).getKey() * coords.get(i).getValue());
      }
    }
    return a / 2 + d / 2 + 1;
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

  static int expected1 = 62;
  static long expected2 = 952408144115L;
  static String testStr = "R 6 (#70c710)\n" +
    "D 5 (#0dc571)\n" +
    "L 2 (#5713f0)\n" +
    "D 2 (#d2c081)\n" +
    "R 2 (#59c680)\n" +
    "D 2 (#411b91)\n" +
    "L 5 (#8ceee2)\n" +
    "U 2 (#caa173)\n" +
    "L 1 (#1b58a2)\n" +
    "U 2 (#caa171)\n" +
    "R 2 (#7807d2)\n" +
    "U 3 (#a77fa3)\n" +
    "L 2 (#015232)\n" +
    "U 2 (#7a21e3)";
  static String realStr = "R 6 (#70c710)\n" +
    "D 5 (#0dc571)\n" +
    "L 2 (#5713f0)\n" +
    "D 2 (#d2c081)\n" +
    "R 2 (#59c680)\n" +
    "D 2 (#411b91)\n" +
    "L 5 (#8ceee2)\n" +
    "U 2 (#caa173)\n" +
    "L 1 (#1b58a2)\n" +
    "U 2 (#caa171)\n" +
    "R 2 (#7807d2)\n" +
    "U 3 (#a77fa3)\n" +
    "L 2 (#015232)\n" +
    "U 2 (#7a21e3)";
}
