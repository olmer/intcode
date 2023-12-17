package aoc2023;

import static tools.Grid.CARDINAL;
import static tools.Grid.POSSIBLE_DIRS_LEFT_RIGHT;

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

public class Aoc2317 {

  public record Pos(Node node, Direction dirFrom, Integer heat) {}

  public record Node(Pair<Integer, Integer> coord, Integer cost, Integer dist) {}

  //from where: pairs of cost, coordinates
  private static Map<Direction, List<Pair<Direction, Node>>>[][] graph;

  //visited from {distance,direction}
  private static Set<Pair<Integer, Direction>>[][] visited;

  private static long part1(String[] input) {
    buildVisited(input);
    buildGraph(input, 1, 3);

    return dijkstra(input);
  }

  private static long part2(String[] input) {
    buildVisited(input);
    buildGraph(input, 4, 10);

    return dijkstra(input);
  }

  private static void buildVisited(String[] input) {
    visited = new Set[input.length][input[0].length()];
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length(); j++) {
        visited[i][j] = new HashSet<>();
      }
    }
  }

  private static void buildGraph(String[] input, int from, int to) {
    graph = new HashMap[input.length][input[0].length()];
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length(); j++) {
        graph[i][j] = new HashMap<>();
        for (Direction dir : CARDINAL) {
          Map<Direction, List<Pair<Character, Pair<Integer, Integer>>>> beams
            = Grid.getBeams(j, i, input, to, i == 0 && j == 0 ? new Direction[]{Direction.S, Direction.E} : POSSIBLE_DIRS_LEFT_RIGHT.get(dir));
          List<Pair<Direction, Node>> neighbours = new ArrayList<>();
          for (var neighboursSet : beams.entrySet()) {
            var neighboursInDirection = neighboursSet.getValue();
            int cost = 0;
            int c = 1;
            for (int dist = 0; dist < neighboursInDirection.size(); dist++) {
              cost += (neighboursInDirection.get(dist).getKey() - '0');
              if (c++ >= from) {
                neighbours.add(
                  new Pair<>(
                    Grid.OPPOSITES.get(neighboursSet.getKey()),
                    new Node(neighboursInDirection.get(dist).getValue(), cost, dist + 1)
                  )
                );
              }
            }
          }
          graph[i][j].put(dir, neighbours);
        }
      }
    }
  }

  private static long dijkstra(String[] input) {
    int[][] bestHeat = new int[input.length][input[0].length()];
    for (int i = 0; i < bestHeat.length; i++) {
      Arrays.fill(bestHeat[i], Integer.MAX_VALUE);
    }

    PriorityQueue<Pos> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.heat));

    q.offer(new Pos(new Node(new Pair<>(0, 0), 0, 1), Direction.N, 0));

    while (!q.isEmpty()) {
      Pos next = q.poll();
      int x = next.node.coord.getKey();
      int y = next.node.coord.getValue();
      var neigbours = graph[y][x].get(next.dirFrom);
      for (var neighbour : neigbours) {
        var visitedKey = new Pair<>(neighbour.getValue().dist, neighbour.getKey());
        if (!visited[neighbour.getValue().coord.getValue()][neighbour.getValue().coord.getKey()].add(
          visitedKey
        )) {
          continue;
        }
        int nextHeat = next.heat + neighbour.getValue().cost;
        bestHeat[neighbour.getValue().coord.getValue()][neighbour.getValue().coord.getKey()] = Math.min(
          bestHeat[neighbour.getValue().coord.getValue()][neighbour.getValue().coord.getKey()],
          nextHeat
        );

        q.offer(new Pos(neighbour.getValue(), neighbour.getKey(), nextHeat));
      }
    }

    for (int i = 0; i < bestHeat.length; i++) {
      for (int j = 0; j < bestHeat[i].length; j++) {
//        System.out.print(StringUtils.padInteger(bestHeat[i][j], 3));
      }
//      System.out.println();
    }

    return bestHeat[bestHeat.length - 1][bestHeat[0].length - 1];
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

  static int expected1 = 102;
  static int expected2 = 94;
  static String testStr =
    "2413432311323\n" +
    "3215453535623\n" +
    "3255245654254\n" +
    "3446585845452\n" +
    "4546657867536\n" +
    "1438598798454\n" +
    "4457876987766\n" +
    "3637877979653\n" +
    "4654967986887\n" +
    "4564679986453\n" +
    "1224686865563\n" +
    "2546548887735\n" +
    "4322674655533";
  static String realStr =
    "2413432311323\n" +
    "3215453535623\n" +
    "3255245654254\n" +
    "3446585845452\n" +
    "4546657867536\n" +
    "1438598798454\n" +
    "4457876987766\n" +
    "3637877979653\n" +
    "4654967986887\n" +
    "4564679986453\n" +
    "1224686865563\n" +
    "2546548887735\n" +
    "4322674655533";
}
