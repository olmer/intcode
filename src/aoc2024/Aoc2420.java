package aoc2024;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import tools.Grid;
import tools.Pair;

public class Aoc2420 extends AbstractAoc {
  long part1(String[] in) {
    costs = new int[in.length][in[0].length()];
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        costs[i][j] = -1;
      }
    }

    Pair<Integer, Integer> start = new Pair<>(0, 0);
    Pair<Integer, Integer> end = new Pair<>(0, 0);
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (in[i].charAt(j) == 'S') {
          start.setKey(j);
          start.setValue(i);
        }
        if (in[i].charAt(j) == 'E') {
          end.setKey(j);
          end.setValue(i);
        }
      }
    }
    costs[start.getValue()][start.getKey()] = 0;
    bfs(start, end, in, Set.of());

    int maxDist = 2;
    int answLim = 0;

    Set<Set<Pair<Integer, Integer>>> cheats = new HashSet<>();
    Set<Pair<Integer, Set<Pair<Integer, Integer>>>> cheats2 = new HashSet<>();
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (costs[i][j] == -1) {
          continue;
        }
        for (int k = i - maxDist; k <= i + maxDist; k++) {
          for (int l = j - maxDist; l <= j + maxDist; l++) {
            if (i == k && j == l) {
              continue;
            }
            if (k < 0 || l < 0 || k >= in.length || l >= in[0].length()) {
              continue;
            }
            if (costs[k][l] == -1 || costs[i][j] == -1) {
              continue;
            }
            int dist = Math.abs(i - k) + Math.abs(j - l);
            if (dist > maxDist) {
              continue;
            }
            int saving = Math.abs(costs[i][j] - costs[k][l]);
            saving -= dist;
            if (saving >= answLim) {
              Set<Pair<Integer, Integer>> cheat = new HashSet<>();
              cheat.add(new Pair<>(j, i));
              cheat.add(new Pair<>(l, k));
              Pair<Integer, Set<Pair<Integer, Integer>>> cheat2 = new Pair<>(saving, cheat);
              cheats.add(cheat);
              cheats2.add(cheat2);
            }
          }
        }
      }
    }
    Map<Integer, Integer> counts = new TreeMap<>();
    for (Pair<Integer, Set<Pair<Integer, Integer>>> t : cheats2) {
      counts.put(t.getKey(), counts.getOrDefault(t.getKey(), 0) + 1);
    }
    return cheats.size();
  }

  int[][] costs;

  long part2(String[] in) {
    costs = new int[in.length][in[0].length()];
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        costs[i][j] = -1;
      }
    }

    Pair<Integer, Integer> start = new Pair<>(0, 0);
    Pair<Integer, Integer> end = new Pair<>(0, 0);
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (in[i].charAt(j) == 'S') {
          start.setKey(j);
          start.setValue(i);
        }
        if (in[i].charAt(j) == 'E') {
          end.setKey(j);
          end.setValue(i);
        }
      }
    }
    costs[start.getValue()][start.getKey()] = 0;
    bfs(start, end, in, Set.of());

    int maxDist = 20;
    int answLim = 0;

    Set<Set<Pair<Integer, Integer>>> cheats = new HashSet<>();
    Set<Pair<Integer, Set<Pair<Integer, Integer>>>> cheats2 = new HashSet<>();
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (costs[i][j] == -1) {
          continue;
        }
        for (int k = i - maxDist; k <= i + maxDist; k++) {
          for (int l = j - maxDist; l <= j + maxDist; l++) {
            if (i == k && j == l) {
              continue;
            }
            if (k < 0 || l < 0 || k >= in.length || l >= in[0].length()) {
              continue;
            }
            if (costs[k][l] == -1 || costs[i][j] == -1) {
              continue;
            }
            int dist = Math.abs(i - k) + Math.abs(j - l);
            if (dist > maxDist) {
              continue;
            }
            int saving = Math.abs(costs[i][j] - costs[k][l]);
            saving -= dist;
            if (saving >= answLim) {
              Set<Pair<Integer, Integer>> cheat = new HashSet<>();
              cheat.add(new Pair<>(j, i));
              cheat.add(new Pair<>(l, k));
              Pair<Integer, Set<Pair<Integer, Integer>>> cheat2 = new Pair<>(saving, cheat);
              cheats.add(cheat);
              cheats2.add(cheat2);
            }
          }
        }
      }
    }
    Map<Integer, Integer> counts = new TreeMap<>();
    for (Pair<Integer, Set<Pair<Integer, Integer>>> t : cheats2) {
      counts.put(t.getKey(), counts.getOrDefault(t.getKey(), 0) + 1);
    }
    return cheats.size();
  }

  long bfs(Pair<Integer, Integer> start, Pair<Integer, Integer> end, String[] in, Set<Pair<Integer, Integer>> cheats) {
    ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();
    Set<Pair<Integer, Integer>> visited = new HashSet<>();
    q.offer(start);
    visited.add(start);
    long r = 0;
    while (!q.isEmpty()) {
      r++;
      long size = q.size();
      while (size-- > 0) {
        Pair<Integer, Integer> p = q.poll();
        if (p.equals(end)) {
          return r;
        }
        List<Pair<Character, Pair<Integer, Integer>>> neighbours =
            Grid.getValidNeighboursWithCoords(p.getKey(), p.getValue(), in, Grid.CARDINAL);
        for (Pair<Character, Pair<Integer, Integer>> e : neighbours) {
          if (!visited.contains(e.getValue()) && (e.getKey() != '#' || cheats.contains(e.getValue()))) {
            q.offer(e.getValue());
            visited.add(e.getValue());
            costs[e.getValue().getValue()][e.getValue().getKey()] = (int) r;
          }
        }
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    (new Aoc2420()).start();
  }

  @Override
  long getTestExpected1() {
    return 211;
  }

  @Override
  long getTestExpected2() {
    return 3535;
  }

  @Override
  String getTestInput() {
    return "###############\n" +
        "#...#...#.....#\n" +
        "#.#.#.#.#.###.#\n" +
        "#S#...#.#.#...#\n" +
        "#######.#.#.###\n" +
        "#######.#.#...#\n" +
        "#######.#.###.#\n" +
        "###..E#...#...#\n" +
        "###.#######.###\n" +
        "#...###...#...#\n" +
        "#.#####.#.###.#\n" +
        "#.#...#.#.#...#\n" +
        "#.#.#.#.#.#.###\n" +
        "#...#...#...###\n" +
        "###############";
  }

  @Override
  String getRealInput() {
    return "###############\n" +
        "#...#...#.....#\n" +
        "#.#.#.#.#.###.#\n" +
        "#S#...#.#.#...#\n" +
        "#######.#.#.###\n" +
        "#######.#.#...#\n" +
        "#######.#.###.#\n" +
        "###..E#...#...#\n" +
        "###.#######.###\n" +
        "#...###...#...#\n" +
        "#.#####.#.###.#\n" +
        "#.#...#.#.#...#\n" +
        "#.#.#.#.#.#.###\n" +
        "#...#...#...###\n" +
        "###############";
  }
}
