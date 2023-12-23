package aoc2023;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import tools.Grid;
import tools.Grid.Direction;
import tools.Pair;

public class Aoc2323 {
  private static int part1LastId = 1;
  private static int part2BestLength = 0;

  private static long part1(String[] input) {
    ArrayDeque<Node> q = new ArrayDeque<>();
    int[][] costs = new int[input.length][input[0].length()];
    int[][] ids = new int[input.length][input[0].length()];
    for (int i = 0; i < costs.length; i++) {
      Arrays.fill(costs[i], -1);
    }
    ids[0][1] = part1LastId;
    costs[0][1] = 0;
    q.offer(new Node(new Pair<>(1, 0), 0, part1LastId, Direction.N));
    while (!q.isEmpty()) {
      var next = q.poll();
      var coord = next.c;
      var dirs = Grid.CARDINAL;
      if (SLOPES.containsKey(input[coord.getValue()].charAt(coord.getKey()))) {
        dirs = new Direction[]{SLOPES.get(input[coord.getValue()].charAt(coord.getKey()))};
      }
      var neighbours = Grid.getValidNeighboursWithCoordsMapped(coord.getKey(), coord.getValue(), input, dirs);
      boolean branched = false;
      for (var neig : neighbours.entrySet()) {
        if (neig.getKey() == next.from) continue;
        int id = next.id;
        char c = neig.getValue().getKey();
        if (c == '#') continue;
        int neigX = neig.getValue().getValue().getKey();
        int neigY = neig.getValue().getValue().getValue();
        if (ids[neigY][neigX] == next.id) continue;
        int cost = next.cost + 1;
        if (cost <= costs[neigY][neigX]) continue;

        if (branched) {
          id = ++part1LastId;
        }
        branched = true;
        ids[neigY][neigX] = id;
        costs[neigY][neigX] = cost;
        q.offer(new Node(neig.getValue().getValue(), cost, id, Grid.OPPOSITES.get(neig.getKey())));
      }
    }
    return costs[costs.length - 1][costs[0].length - 2];
  }

  private static long part2(String[] input) {
    //from => [(cost, to)]
    Map<Pair<Integer, Integer>, Set<NodeSimple>> adjList = new HashMap<>();
    Pair<Integer, Integer> goal = new Pair<>(input[0].length() - 2, input.length - 1);
    Set<Pair<Integer, Integer>> visited = new HashSet<>();

    //build adj list
    ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();
    visited.add(new Pair<>(1, 0));
    q.offer(new Pair<>(1, 0));
    while (!q.isEmpty()) {
      var knotNode = q.poll();
      ArrayDeque<NodeSimple> bfs = new ArrayDeque<>();
      bfs.offer(new NodeSimple(knotNode, 0));
      while (!bfs.isEmpty()) {
        var cur = bfs.poll();
        var neigs =
          Grid.getValidNeighboursWithCoords(cur.c.getKey(), cur.c.getValue(), input, Grid.CARDINAL)
            .stream()
            .filter(e -> e.getKey() != '#')
            .collect(Collectors.toList())
          ;
        if ((neigs.size() > 2 && cur.cost > 0 || cur.c.equals(goal)) && !cur.c.equals(knotNode)) {
          //next knot node found
          adjList.putIfAbsent(knotNode, new HashSet<>());
          adjList.putIfAbsent(cur.c, new HashSet<>());
          adjList.get(knotNode).add(new NodeSimple(cur.c, cur.cost));
          adjList.get(cur.c).add(new NodeSimple(knotNode, cur.cost));

          if (!cur.c.equals(goal))
            q.offer(cur.c);
        } else {
          //if not knot - continue BFS to find one
          neigs
            .stream()
            .filter(neig -> !visited.contains(neig.getValue()) || adjList.containsKey(neig.getValue()))
            .forEach(neig -> {
              visited.add(neig.getValue());
              bfs.offer(new NodeSimple(neig.getValue(), cur.cost + 1));
            });
        }
      }
    }

    //find the longest path
    Set<Pair<Integer, Integer>> searchVisited = new HashSet<>();
    searchVisited.add(new Pair<>(1, 0));
    Stack<PermutationState> stack = new Stack<>();
    stack.push(new PermutationState(new Pair<>(1, 0), searchVisited, 0));

    while (!stack.isEmpty()) {
      PermutationState currentState = stack.pop();

      if (currentState.current.equals(goal)) {
        part2BestLength = Math.max(part2BestLength, currentState.cost);
      } else {
        for (var neig : adjList.get(currentState.current)) {
          if (!currentState.used.contains(neig.c)) {
            currentState.used.add(neig.c);
            stack.push(new PermutationState(neig.c, currentState.used, currentState.cost + neig.cost));
            currentState.used.remove(neig.c);
          }
        }
      }
    }
    return part2BestLength;
  }
  public record Node(Pair<Integer, Integer> c, int cost, int id, Direction from) {}
  public record NodeSimple(Pair<Integer, Integer> c, int cost) {}
  private static class PermutationState {
    Pair<Integer, Integer> current;
    int cost;
    Set<Pair<Integer, Integer>> used;

    PermutationState(Pair<Integer, Integer> current, Set<Pair<Integer, Integer>> used, int cost) {
      this.current = current;
      this.used = new HashSet<>(used);
      this.cost = cost;
    }
  }
  private static final Map<Character, Direction> SLOPES = new HashMap<>(){{
    put('^', Direction.N);
    put('>', Direction.E);
    put('v', Direction.S);
    put('<', Direction.W);
  }};

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

  static int expected1 = 94;
  static int expected2 = 154;
  static String testStr =
    "#.#####################\n" +
    "#.......#########...###\n" +
    "#######.#########.#.###\n" +
    "###.....#.>.>.###.#.###\n" +
    "###v#####.#v#.###.#.###\n" +
    "###.>...#.#.#.....#...#\n" +
    "###v###.#.#.#########.#\n" +
    "###...#.#.#.......#...#\n" +
    "#####.#.#.#######.#.###\n" +
    "#.....#.#.#.......#...#\n" +
    "#.#####.#.#.#########v#\n" +
    "#.#...#...#...###...>.#\n" +
    "#.#.#v#######v###.###v#\n" +
    "#...#.>.#...>.>.#.###.#\n" +
    "#####v#.#.###v#.#.###.#\n" +
    "#.....#...#...#.#.#...#\n" +
    "#.#########.###.#.#.###\n" +
    "#...###...#...#...#.###\n" +
    "###.###.#.###v#####v###\n" +
    "#...#...#.#.>.>.#.>.###\n" +
    "#.###.###.#.###.#.#v###\n" +
    "#.....###...###...#...#\n" +
    "#####################.#";
  static String realStr =     "#.#####################\n" +
    "#.......#########...###\n" +
    "#######.#########.#.###\n" +
    "###.....#.>.>.###.#.###\n" +
    "###v#####.#v#.###.#.###\n" +
    "###.>...#.#.#.....#...#\n" +
    "###v###.#.#.#########.#\n" +
    "###...#.#.#.......#...#\n" +
    "#####.#.#.#######.#.###\n" +
    "#.....#.#.#.......#...#\n" +
    "#.#####.#.#.#########v#\n" +
    "#.#...#...#...###...>.#\n" +
    "#.#.#v#######v###.###v#\n" +
    "#...#.>.#...>.>.#.###.#\n" +
    "#####v#.#.###v#.#.###.#\n" +
    "#.....#...#...#.#.#...#\n" +
    "#.#########.###.#.#.###\n" +
    "#...###...#...#...#.###\n" +
    "###.###.#.###v#####v###\n" +
    "#...#...#.#.>.>.#.>.###\n" +
    "#.###.###.#.###.#.#v###\n" +
    "#.....###...###...#...#\n" +
    "#####################.#";
}
