package aoc2024;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import tools.Grid;
import tools.Grid.Direction;
import tools.Pair;

public class Aoc2418 extends AbstractAoc {
  record Node(int col, int row, Grid.Direction facing, long cost, List<Pair<Integer, Integer>> path) {}

  record NodeShort(int col, int row, Grid.Direction facing) {}

  long part1(String[] in) {
    int startRow = 0, startCol = 0;
    int endRow = 0, endCol = 0;
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (in[i].charAt(j) == 'S') {
          startRow = i;
          startCol = j;
        }
        if (in[i].charAt(j) == 'E') {
          endRow = i;
          endCol = j;
        }
      }
    }

    Set<NodeShort> visited = new HashSet<>();
    PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingLong(a -> a.cost));

    q.offer(new Node(startCol, startRow, Direction.E, 0, new ArrayList<>()));
    long r = Long.MAX_VALUE;
    while (!q.isEmpty()) {
      Node n = q.poll();
      NodeShort nShort = new NodeShort(n.col, n.row, n.facing);
      visited.add(nShort);
      if (n.row == endRow && n.col == endCol) {
        r = Math.min(n.cost, r);
      }
      Grid.Direction[] canGo = Arrays.stream(Grid.CARDINAL).filter(d -> d != Grid.OPPOSITES.get(n.facing)).toArray(Grid.Direction[]::new);
      Grid.getValidNeighboursWithCoordsMapped(n.col, n.row, in, canGo).forEach((dir, neigh) -> {
        if (neigh.getKey() == '#') {
          return;
        }
        Node next = new Node(neigh.getValue().getKey(), neigh.getValue().getValue(), dir, n.cost + (dir == n.facing ? 1 : 1001), new ArrayList<>(n.path));
        NodeShort nextShort = new NodeShort(next.col, next.row, next.facing);
        if (!visited.add(nextShort)) {
          return;
        }
        q.offer(next);
      });
    }
    return r;
  }

  long part2(String[] in) {
    int startRow = 0, startCol = 0;
    int endRow = 0, endCol = 0;
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (in[i].charAt(j) == 'S') {
          startRow = i;
          startCol = j;
        }
        if (in[i].charAt(j) == 'E') {
          endRow = i;
          endCol = j;
        }
      }
    }

    ArrayDeque<Node> q = new ArrayDeque<>();

    Map<Grid.Direction, Long>[][] costs = new HashMap[in.length][in[0].length()];

    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        costs[i][j] = new HashMap<>();
      }
    }

    costs[startRow][startCol].put(Direction.E, 0L);

    q.offer(new Node(startCol, startRow, Direction.E, 0, new ArrayList<>()));

    Set<List<Pair<Integer, Integer>>> bestPaths = new HashSet<>();
    long bestCost = Long.MAX_VALUE;

    while (!q.isEmpty()) {
      Node n = q.poll();
      if (n.row == endRow && n.col == endCol) {
        if (n.cost < bestCost) {
          bestCost = n.cost;
          bestPaths.clear();
          bestPaths.add(n.path);
        } else if (n.cost == bestCost) {
          bestPaths.add(n.path);
        }
      }
      Grid.Direction[] canGo = Arrays.stream(Grid.CARDINAL).filter(d -> d != Grid.OPPOSITES.get(n.facing)).toArray(Grid.Direction[]::new);
      Grid.getValidNeighboursWithCoordsMapped(n.col, n.row, in, canGo).forEach((dir, neigh) -> {
        if (neigh.getKey() == '#') {
          return;
        }
        Node next = new Node(neigh.getValue().getKey(), neigh.getValue().getValue(), dir, n.cost + (dir == n.facing ? 1 : 1001), new ArrayList<>(n.path));
        if (next.cost > costs[next.row][next.col].getOrDefault(next.facing, Long.MAX_VALUE)) {
          return;
        }
        costs[next.row][next.col].put(next.facing, next.cost);
        next.path.add(new Pair<>(next.col, next.row));
        q.offer(next);
      });
    }

    Set<Pair<Integer, Integer>> result = new HashSet<>();
    bestPaths.forEach(result::addAll);

    return result.size();
  }

  public static void main(String[] args) {
    (new Aoc2418()).start();
  }

  @Override
  long getTestExpected1() {
    return 7036;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }

  String getTestInput1() {
    return "#####\n" +
        "#...#\n" +
        "#S#E#\n" +
        "#...#\n" +
        "#####";
  }

  String getTestInput() {
    return
        "#################\n" +
            "#...#...#...#..E#\n" +
            "#.#.#.#.#.#.#.#.#\n" +
            "#.#.#.#...#...#.#\n" +
            "#.#.#.#.###.#.#.#\n" +
            "#...#.#.#.....#.#\n" +
            "#.#.#.#.#.#####.#\n" +
            "#.#...#.#.#.....#\n" +
            "#.#.#####.#.###.#\n" +
            "#.#.#.......#...#\n" +
            "#.#.###.#####.###\n" +
            "#.#.#...#.....#.#\n" +
            "#.#.#.#####.###.#\n" +
            "#.#.#.........#.#\n" +
            "#.#.#.#########.#\n" +
            "#S#.............#\n" +
            "#################";
  }

  @Override
  String getRealInput() {
    return "#################\n" +
        "#...#...#...#..E#\n" +
        "#.#.#.#.#.#.#.#.#\n" +
        "#.#.#.#...#...#.#\n" +
        "#.#.#.#.###.#.#.#\n" +
        "#...#.#.#.....#.#\n" +
        "#.#.#.#.#.#####.#\n" +
        "#.#...#.#.#.....#\n" +
        "#.#.#####.#.###.#\n" +
        "#.#.#.......#...#\n" +
        "#.#.###.#####.###\n" +
        "#.#.#...#.....#.#\n" +
        "#.#.#.#####.###.#\n" +
        "#.#.#.........#.#\n" +
        "#.#.#.#########.#\n" +
        "#S#.............#\n" +
        "#################";
  }
}
