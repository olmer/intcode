package aoc2023;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.Grid;
import tools.Grid.Direction;
import tools.Pair;

public class Aoc2310 {
  private static final Map<Character, Direction[]> POSSIBLE_CONNECTIONS = new HashMap<>(){{
    put('|', new Direction[]{Direction.N, Direction.S});
    put('-', new Direction[]{Direction.W, Direction.E});
    put('L', new Direction[]{Direction.N, Direction.E});
    put('J', new Direction[]{Direction.N, Direction.W});
    put('7', new Direction[]{Direction.W, Direction.S});
    put('F', new Direction[]{Direction.S, Direction.E});
  }};
  private static final Map<Direction, List<Character>> CONNECTED_TO_ORIG = new HashMap<>(){{
    put(Direction.S, List.of('|', 'L', 'J'));
    put(Direction.E, List.of('-', 'J', '7'));
    put(Direction.W, List.of('-', 'L', 'F'));
    put(Direction.N, List.of('|', '7', 'F'));
  }};
  private static final Map<Direction, List<Character>> CONNECTED_TO = new HashMap<>(){{
    put(Direction.S, List.of('|', 'L', 'J', 'x', '*', 'S'));
    put(Direction.E, List.of('-', 'J', '7', 'x', '*', 'S'));
    put(Direction.W, List.of('-', 'L', 'F', 'x', '*', 'S'));
    put(Direction.N, List.of('|', '7', 'F', 'x', '*', 'S'));
  }};

  private static int part1(String[] input) {
    boolean[][] visited = new boolean[input.length][input[0].length()];
    int startx = 0;
    int starty = 0;
    for (int i = 0; i < input.length; i++) {
      var in = input[i];
      if (in.indexOf('S') != -1) {
        starty = i;
        startx = in.indexOf('S');
        visited[starty][startx] = true;
      }
    }
    int r = 0;
    ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();

    Grid.getValidSpecificNeighboursWithCoordsMapped(startx, starty, input, Grid.CARDINAL)
      .entrySet()
      .stream()
      .filter(entry -> CONNECTED_TO_ORIG.get(entry.getKey()).contains(entry.getValue().getKey()))
      .forEach(e -> q.add(e.getValue().getValue()));

    while (!q.isEmpty()) {
      r++;
      int size = q.size();
      while (size-- > 0) {
        var cur = q.poll();
        Grid.getValidSpecificNeighboursWithCoordsMapped(cur.getKey(), cur.getValue(), input, Grid.CARDINAL).entrySet()
          .stream()
          .filter(entry -> CONNECTED_TO.get(entry.getKey()).contains(entry.getValue().getKey()))
          .filter(entry -> {
            var p = entry.getValue().getValue();
            return !visited[p.getValue()][p.getKey()];
          })
          .forEach(entry -> {
            var p = entry.getValue().getValue();
            visited[p.getValue()][p.getKey()] = true;
            q.add(p);
          });
      }
    }

    return r;
  }

  static Map<Character, Character[][]> expandMap = new HashMap<>(){{
    put('.', new Character[][]{
      {'.', '.', '.'},
      {'.', '.', '.'},
      {'.', '.', '.'},
    });
    put('|', new Character[][]{
      {'.', '#', '.'},
      {'.', '#', '.'},
      {'.', '#', '.'},
    });
    put('-', new Character[][]{
      {'.', '.', '.'},
      {'#', '#', '#'},
      {'.', '.', '.'},
    });
    put('F', new Character[][]{
      {'.', '.', '.'},
      {'.', '#', '#'},
      {'.', '#', '.'},
    });
    put('7', new Character[][]{
      {'.', '.', '.'},
      {'#', '#', '.'},
      {'.', '#', '.'},
    });
    put('L', new Character[][]{
      {'.', '#', '.'},
      {'.', '#', '#'},
      {'.', '.', '.'},
    });
    put('J', new Character[][]{
      {'.', '#', '.'},
      {'#', '#', '.'},
      {'.', '.', '.'},
    });
    put('S', new Character[][]{
      {'.', '#', '.'},
      {'#', '#', '.'},
      {'.', '.', '.'},
    });
  }};

  private static long part2(String[] rawIn) {
    int startx = 0;
    int starty = 0;
    boolean[][] includedInMainLoop = new boolean[rawIn.length][rawIn[0].length()];

    //expand input
    StringBuilder[] texpanded = new StringBuilder[rawIn.length * 3];
    for (int i = 0; i < rawIn.length; i++) {
      texpanded[i * 3] = new StringBuilder();
      texpanded[i * 3 + 1] = new StringBuilder();
      texpanded[i * 3 + 2] = new StringBuilder();
      for (int j = 0; j < rawIn[0].length(); j++) {
        if (rawIn[i].charAt(j) == 'S') {
          startx = j;
          starty = i;
          includedInMainLoop[starty][startx] = true;
        }
        for (int t = 0; t < 3; t++) {
          for (int tt = 0; tt < 3; tt++) {
            texpanded[i * 3 + t].append(expandMap.get(rawIn[i].charAt(j))[t][tt]);
          }
        }
      }
    }
    String[] expanded = new String[rawIn.length * 3];
    for (int i = 0; i < expanded.length; i++) {
      expanded[i] = texpanded[i].toString();
    }

    //detect main loop
    final ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();

    System.out.println("Main loop");
    Grid.getValidSpecificNeighboursWithCoordsMapped(startx, starty, rawIn, Grid.CARDINAL)
      .entrySet()
      .stream()
      .filter(entry -> CONNECTED_TO_ORIG.get(entry.getKey()).contains(entry.getValue().getKey()))
      .forEach(e -> q.add(e.getValue().getValue()));
    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) {
        var cur = q.poll();
        Grid.getValidSpecificNeighboursWithCoordsMapped(cur.getKey(), cur.getValue(), rawIn, POSSIBLE_CONNECTIONS.get(rawIn[cur.getValue()].charAt(cur.getKey()))).entrySet()
          .stream()
          .filter(entry -> CONNECTED_TO_ORIG.get(entry.getKey()).contains(entry.getValue().getKey()))
          .filter(entry -> {
            var p = entry.getValue().getValue();
            return !includedInMainLoop[p.getValue()][p.getKey()];
          })
          .forEach(entry -> {
            var p = entry.getValue().getValue();
            includedInMainLoop[p.getValue()][p.getKey()] = true;
            q.add(p);
          });
      }
    }

    System.out.println("Main loop");
    for (int i = 0; i < includedInMainLoop.length; i++) {
      for (int j = 0; j < includedInMainLoop[0].length; j++) {
        System.out.print(includedInMainLoop[i][j] ? '#' : '.');
      }
      System.out.println();
    }

    System.out.println("Expanded input");
    Arrays.stream(expanded).forEach(System.out::println);

    boolean[][] flooded = new boolean[expanded.length][expanded[0].length()];
    q.clear();
    for (int i = 0; i < expanded.length; i++) {
      for (int j = 0; j < expanded[0].length(); j++) {
        if (i == 0 || j == 0 || i == expanded.length - 1 || j == expanded[0].length() - 1) {
          if (expanded[i].charAt(j) == '.') {
            q.offer(new Pair<>(j, i));
            flooded[i][j] = true;
          }
        }
      }
    }

    System.out.println("Before flood");
    for (int i = 0; i < flooded.length; i++) {
      for (int j = 0; j < flooded[0].length; j++) {
        System.out.print(flooded[i][j] ? '.' : ' ');
      }
      System.out.println();
    }

    while (!q.isEmpty()) {
      var cur = q.poll();
      Grid.getValidSpecificNeighboursWithCoordsMapped(cur.getKey(), cur.getValue(), expanded, Grid.CARDINAL).entrySet().stream()
        .filter(e -> e.getValue().getKey() == '.')
        .filter(e -> {var p = e.getValue().getValue(); return !flooded[p.getValue()][p.getKey()];})
        .forEach(e -> {
          var p = e.getValue().getValue();
          flooded[p.getValue()][p.getKey()] = true;
          q.offer(p);
        });
    }

    System.out.println("after flood");
    for (int i = 0; i < flooded.length; i++) {
      for (int j = 0; j < flooded[0].length; j++) {
        System.out.print(flooded[i][j] ? '.' : ' ');
      }
      System.out.println();
    }

    System.out.println("Final");
    int r = 0;
    for (int i = 0; i < flooded.length; i += 3) {
      for (int j = 0; j < flooded[0].length; j += 3) {
        boolean y = true;
        for (int t = i; t < i + 3; t++) {
          for (int tt = j; tt < j + 3; tt++) {
            if (flooded[t][tt] || includedInMainLoop[t / 3][tt / 3]) {
              y = false;
            }
          }
        }
        if (y) {
          r++;
        }
      }
      System.out.println();
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
//    System.out.println(part1(getInput(false)));
//    System.out.println(part2(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 80;
  static int expected2 = 0;
  static String testStr =
"FF7FSF7F7F7F7F7F---7\n" +
  "L|LJ||||||||||||F--J\n" +
  "FL-7LJLJ||||||LJL-77\n" +
  "F--JF--7||LJLJ7F7FJ-\n" +
  "L---JF-JLJ.||-FJLJJ7\n" +
  "|F|F-JF---7F7-L7L|7|\n" +
  "|FFJF7L7F-JF7|JL---7\n" +
  "7-L-JL7||F7|L7F-7F7|\n" +
  "L.L7LFJ|||||FJL7||LJ\n" +
  "L7JLJL-JLJLJL--JLJ.L";
  static String realStr = "";
}
