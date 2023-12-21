package aoc2023;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tools.Grid;
import tools.Grid.Direction;
import tools.Pair;

public class Aoc2316 {

  static Map<Direction, Direction> COMING_FOM_MAP = new HashMap<>(){{
    put(Direction.N, Direction.S);
    put(Direction.S, Direction.N);
    put(Direction.E, Direction.W);
    put(Direction.W, Direction.E);
  }};
  // tile : {from: [to]}
  static Map<Character, Map<Direction, Direction[]>> MIR = new HashMap<>(){{
    put('.', new HashMap<>(){{
      put(Direction.N, new Direction[]{Direction.S});
      put(Direction.S, new Direction[]{Direction.N});
      put(Direction.E, new Direction[]{Direction.W});
      put(Direction.W, new Direction[]{Direction.E});
    }});
    put('-', new HashMap<>(){{
      put(Direction.N, new Direction[]{Direction.E, Direction.W});
      put(Direction.S, new Direction[]{Direction.E, Direction.W});
      put(Direction.E, new Direction[]{Direction.W});
      put(Direction.W, new Direction[]{Direction.E});
    }});
    put('|', new HashMap<>(){{
      put(Direction.N, new Direction[]{Direction.S});
      put(Direction.S, new Direction[]{Direction.N});
      put(Direction.E, new Direction[]{Direction.S, Direction.N});
      put(Direction.W, new Direction[]{Direction.S, Direction.N});
    }});
    put('/', new HashMap<>(){{
      put(Direction.N, new Direction[]{Direction.W});
      put(Direction.S, new Direction[]{Direction.E});
      put(Direction.E, new Direction[]{Direction.S});
      put(Direction.W, new Direction[]{Direction.N});
    }});
    put('\\', new HashMap<>(){{
      put(Direction.N, new Direction[]{Direction.E});
      put(Direction.S, new Direction[]{Direction.W});
      put(Direction.E, new Direction[]{Direction.N});
      put(Direction.W, new Direction[]{Direction.S});
    }});
  }};

  private static long part1(String[] input) {
    ArrayDeque<Pair<Direction, Pair<Integer, Integer>>> q = new ArrayDeque<>();
    q.offer(new Pair<>(Direction.W, new Pair<>(0, 0)));

    Set<Direction>[][] visitedFrom = new HashSet[input.length][input[0].length()];
    visitedFrom[0][0] = new HashSet<>();
    visitedFrom[0][0].add(Direction.W);

    while (!q.isEmpty()) {
      var next = q.poll();
      var x = next.getValue().getKey();
      var y = next.getValue().getValue();
      char c = input[y].charAt(x);
      var intendedDirections = MIR.get(c).get(next.getKey());

      for (var ne : Grid.getValidNeighbourCoordinatesMapped(x, y, input, intendedDirections).entrySet()) {
        var intendedDirection = ne.getKey();
        var comingFrom = COMING_FOM_MAP.get(intendedDirection);
        var intendedX = ne.getValue().getKey();
        var intendedY = ne.getValue().getValue();
        if (visitedFrom[intendedY][intendedX] != null && visitedFrom[intendedY][intendedX].contains(comingFrom)) {
          continue;
        }
        if (visitedFrom[intendedY][intendedX] == null) {
          visitedFrom[intendedY][intendedX] = new HashSet<>();
        }
        visitedFrom[intendedY][intendedX].add(comingFrom);
        q.offer(new Pair<>(comingFrom, new Pair<>(intendedX, intendedY)));
      }
    }

    int cur = 0;

    for (var vrow : visitedFrom) {
      for (var v : vrow) {
        cur += (v == null ? 0 : 1);
      }
    }

    return cur;
  }

  private static long part2(String[] input) {
    ArrayDeque<Pair<Direction, Pair<Integer, Integer>>> qq = new ArrayDeque<>();
    for (int i = 0; i < input.length; i++) {
      qq.offer(new Pair<>(Direction.W, new Pair<>(0, i)));
      qq.offer(new Pair<>(Direction.E, new Pair<>(input.length - 1, i)));
    }
    for (int i = 0; i < input[0].length(); i++) {
      qq.offer(new Pair<>(Direction.N, new Pair<>(i, 0)));
      qq.offer(new Pair<>(Direction.S, new Pair<>(i, input[0].length() - 1)));
    }

    int r = 0;

    while (!qq.isEmpty()) {
      var check = qq.poll();
      ArrayDeque<Pair<Direction, Pair<Integer, Integer>>> q = new ArrayDeque<>();

      Set<Direction>[][] visitedFrom = new HashSet[input.length][input[0].length()];
      visitedFrom[check.getValue().getValue()][check.getValue().getKey()] = new HashSet<>();
      visitedFrom[check.getValue().getValue()][check.getValue().getKey()].add(check.getKey());

      q.offer(check);

      while (!q.isEmpty()) {
        var next = q.poll();
        var x = next.getValue().getKey();
        var y = next.getValue().getValue();
        char c = input[y].charAt(x);
        var intendedDirections = MIR.get(c).get(next.getKey());

        for (var ne : Grid.getValidNeighbourCoordinatesMapped(x, y, input, intendedDirections).entrySet()) {
          var intendedDirection = ne.getKey();
          var comingFrom = COMING_FOM_MAP.get(intendedDirection);
          var intendedX = ne.getValue().getKey();
          var intendedY = ne.getValue().getValue();
          if (visitedFrom[intendedY][intendedX] != null && visitedFrom[intendedY][intendedX].contains(comingFrom)) {
            continue;
          }
          if (visitedFrom[intendedY][intendedX] == null) {
            visitedFrom[intendedY][intendedX] = new HashSet<>();
          }
          visitedFrom[intendedY][intendedX].add(comingFrom);
          q.offer(new Pair<>(comingFrom, new Pair<>(intendedX, intendedY)));
        }
      }

      int cur = 0;

      for (var vrow : visitedFrom) {
        for (var v : vrow) {
          cur += (v == null ? 0 : 1);
        }
      }
      r = Math.max(r, cur);
    }

    return r;
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

  static int expected1 = 46;
  static int expected2 = 51;
  static String testStr =
    ".|...\\....\n" +
    "|.-.\\.....\n" +
    ".....|-...\n" +
    "........|.\n" +
    "..........\n" +
    ".........\\\n" +
    "..../.\\\\..\n" +
    ".-.-/..|..\n" +
    ".|....-|.\\\n" +
    "..//.|....";
  static String realStr =
    ".|...\\....\n" +
    "|.-.\\.....\n" +
    ".....|-...\n" +
    "........|.\n" +
    "..........\n" +
    ".........\\\n" +
    "..../.\\\\..\n" +
    ".-.-/..|..\n" +
    ".|....-|.\\\n" +
    "..//.|....";
}
