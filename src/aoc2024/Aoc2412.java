package aoc2024;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import tools.Grid;
import tools.Grid.Direction;
import tools.Pair;

public class Aoc2412 extends AbstractAoc {
  boolean[][] visited;
  String[] in;

  long part1(String[] in) {
    this.in = in;
    long r = 0;
    visited = new boolean[in.length][in[0].length()];
    for (int row = 0; row < in.length; row++) {
      for (int col = 0; col < in[row].length(); col++) {
        if (!visited[row][col]) {
          visited[row][col] = true;
          r += fill(new Pair<>(col, row));
        }
      }
    }
    return r;
  }

  long fill(Pair<Integer, Integer> node) {
    ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();
    Set<Set<Pair<Integer, Integer>>> fences = new HashSet<>();
    q.add(node);
    long fence = 0;
    long area = 1;
    while (!q.isEmpty()) {
      Pair<Integer, Integer> n = q.poll();
      int row = n.getValue();
      int col = n.getKey();
      char c = in[row].charAt(col);
      List<Pair<Character, Pair<Integer, Integer>>> neighs = Grid.getValidNeighboursWithCoords(col, row, in, Grid.CARDINAL);
      fence += (4 - neighs.size());
      for (Pair<Character, Pair<Integer, Integer>> neigh : neighs) {
        int nr = neigh.getValue().getValue();
        int nc = neigh.getValue().getKey();
        if (neigh.getKey() != c) {
          Set<Pair<Integer, Integer>> f = new HashSet<>();
          f.add(neigh.getValue());
          f.add(n);
          if (fences.add(f)) {
            fence++;
          }
        } else if (!visited[nr][nc]) {
          visited[nr][nc] = true;
          area++;
          q.add(neigh.getValue());
        }
      }
    }
    return fence * area;
  }

  long part2(String[] in) {
    this.in = in;
    long r = 0;
    visited = new boolean[in.length][in[0].length()];
    for (int row = 0; row < in.length; row++) {
      for (int col = 0; col < in[row].length(); col++) {
        if (!visited[row][col]) {
          visited[row][col] = true;
          r += fill2(new Pair<>(col, row));
        }
      }
    }
    return r;
  }

  long fill2(Pair<Integer, Integer> node) {
    ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();
    Set<Fence> fences = new HashSet<>();
    q.add(node);
    long area = 1;
    while (!q.isEmpty()) {
      Pair<Integer, Integer> n = q.poll();
      int row = n.getValue();
      int col = n.getKey();
      char c = in[row].charAt(col);
      Map<Direction, Pair<Character, Pair<Integer, Integer>>> neighs = Grid.getValidNeighboursWithCoordsMapped(col, row, in, Grid.CARDINAL);
      if (neighs.size() < 4) {
        Set<Direction> missing = new HashSet<>(List.of(Grid.CARDINAL));
        Set<Direction> present = new HashSet<>(neighs.keySet());
        missing.removeAll(present);
        missing.forEach(m -> {
          Pair<Integer, Integer> start = new Pair<>(col, row);
          Pair<Integer, Integer> end = new Pair<>(col, row);
          fences.add(new Fence(start, end, m));
        });
      }
      for (Entry<Direction, Pair<Character, Pair<Integer, Integer>>> neigh : neighs.entrySet()) {
        char neighChar = neigh.getValue().getKey();
        int nr = neigh.getValue().getValue().getValue();
        int nc = neigh.getValue().getValue().getKey();
        if (neighChar != c) {
          Pair<Integer, Integer> start = new Pair<>(col, row);
          Pair<Integer, Integer> end = new Pair<>(col, row);
          Direction dir = neigh.getKey();
          fences.add(new Fence(start, end, dir));
        } else if (!visited[nr][nc]) {
          visited[nr][nc] = true;
          area++;
          q.add(neigh.getValue().getValue());
        }
      }
    }
    return countFences(fences) * area;
  }

  public int countFences(Set<Fence> fences) {
    Map<Direction, Map<Integer, TreeSet<Integer>>> fencesByDir = new HashMap<>();
    for (Fence f : fences) {
      fencesByDir.putIfAbsent(f.dirOfNeigh, new HashMap<>());
      int groupingBy = f.dirOfNeigh == Direction.N || f.dirOfNeigh == Direction.S ? f.start.getValue() : f.start.getKey();
      if (!fencesByDir.get(f.dirOfNeigh).containsKey(groupingBy)) {
        fencesByDir.get(f.dirOfNeigh).put(groupingBy, new TreeSet<>());
      }
      fencesByDir.get(f.dirOfNeigh).get(groupingBy).add(f.dirOfNeigh == Direction.N || f.dirOfNeigh == Direction.S ? f.start.getKey() : f.start.getValue());
    }
    int countOfSegments = 0;
    for (Map<Integer, TreeSet<Integer>> fencesByDirGroup : fencesByDir.values()) {
      for (TreeSet<Integer> fencesInGroup : fencesByDirGroup.values()) {
        int prev = -10;
        for (int fence : fencesInGroup) {
          if (fence - prev > 1) {
            countOfSegments++;
          }
          prev = fence;
        }
      }
    }
    return countOfSegments;
  }

  class Fence {
    public Pair<Integer, Integer> start;
    public Pair<Integer, Integer> end;
    public Grid.Direction dirOfNeigh;

    public Fence(Pair<Integer, Integer> start, Pair<Integer, Integer> end, Grid.Direction dirOfNeigh) {
      this.start = start;
      this.end = end;
      this.dirOfNeigh = dirOfNeigh;
    }
  }

  public static void main(String[] args) {
    (new Aoc2412()).start();
  }

  @Override
  long getTestExpected1() {
    return 1930;
  }

  @Override
  long getTestExpected2() {
    return 1206;
  }

  @Override
  String getTestInput() {
    return
        "RRRRIICCFF\n" +
            "RRRRIICCCF\n" +
            "VVRRRCCFFF\n" +
            "VVRCCCJFFF\n" +
            "VVVVCJJCFE\n" +
            "VVIVCCJJEE\n" +
            "VVIIICJJEE\n" +
            "MIIIIIJJEE\n" +
            "MIIISIJEEE\n" +
            "MMMISSJEEE";

  }

  @Override
  String getRealInput() {
    return "RRRRIICCFF\n" +
        "RRRRIICCCF\n" +
        "VVRRRCCFFF\n" +
        "VVRCCCJFFF\n" +
        "VVVVCJJCFE\n" +
        "VVIVCCJJEE\n" +
        "VVIIICJJEE\n" +
        "MIIIIIJJEE\n" +
        "MIIISIJEEE\n" +
        "MMMISSJEEE";
  }
}
