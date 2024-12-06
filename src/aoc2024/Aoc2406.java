package aoc2024;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.Grid;
import tools.Grid.Direction;
import tools.Pair;

public class Aoc2406 extends AbstractAoc {
  long part1(String[] in) {
    Pair<Integer, Integer> pos = Grid.find(in, '^').get(0);
    Direction[] order = {Direction.N, Direction.E, Direction.S, Direction.W};
    int curdir = 0;
    boolean[][] visited = new boolean[in.length][in[0].length()];
    visited[pos.getValue()][pos.getKey()] = true;

    while (true) {
      List<Pair<Character, Pair<Integer, Integer>>> beam = Grid.getBeams(pos.getKey(), pos.getValue(), in, in.length, order[curdir]).get(order[curdir]);
      boolean endFound = false;
      for (Pair<Character, Pair<Integer, Integer>> p : beam) {
        if (p.getKey() == '#') {
          endFound = true;
          break;
        }
        pos = p.getValue();
        visited[pos.getValue()][pos.getKey()] = true;
      }

      if (!endFound) {
        break;
      }

      curdir = (curdir + 1) % 4;
    }

    int totalVisited = 0;

    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (visited[i][j]) {
          totalVisited++;
        }
      }
    }

    return totalVisited;
  }

  long part2(String[] originalin) {
    int r = 0;

    // all variants
    for (int i = 0; i < originalin.length; i++) {
      for (int j = 0; j < originalin[i].length(); j++) {
        String[] in = new String[originalin.length];
        // duplicate new in
        for (int k = 0; k < originalin.length; k++) {
          in[k] = new StringBuilder(originalin[k]).toString();
        }
        // set one wall
        StringBuilder sb = new StringBuilder(in[i]);
        sb.setCharAt(j, '#');
        in[i] = sb.toString();

        //start main logic
        Pair<Integer, Integer> pos = Grid.find(originalin, '^').get(0);
        Direction[] order = {Direction.N, Direction.E, Direction.S, Direction.W};
        int curdir = 0;
        boolean[][] visited = new boolean[originalin.length][originalin[0].length()];
        visited[pos.getValue()][pos.getKey()] = true;

        Set<Integer>[][] dirs = new HashSet[originalin.length][originalin[0].length()];
        for (int m = 0; m < dirs.length; m++) {
          for (int n = 0; n < dirs[m].length; n++) {
            dirs[m][n] = new HashSet<>();
          }
        }
        dirs[pos.getValue()][pos.getKey()].add(0);

        boolean loopFound = false;
        while (true) {
          List<Pair<Character, Pair<Integer, Integer>>> beam = Grid.getBeams(pos.getKey(), pos.getValue(), in, in.length, order[curdir]).get(order[curdir]);
          boolean endFound = false;
          for (Pair<Character, Pair<Integer, Integer>> p : beam) {
            if (p.getKey() == '#') {
              endFound = true;
              break;
            }
            if (visited[p.getValue().getValue()][p.getValue().getKey()]) {
              if (dirs[p.getValue().getValue()][p.getValue().getKey()].contains(curdir)) {
                r++;
                loopFound = true;
                break;
              }
            }
            pos = p.getValue();
            visited[pos.getValue()][pos.getKey()] = true;
            dirs[pos.getValue()][pos.getKey()].add(curdir);
          }

          if (!endFound || loopFound) {
            break;
          }

          curdir = (curdir + 1) % 4;
        }
      }
    }

    return r;
  }

  public static void main(String[] args) {
    (new Aoc2406()).start();
  }

  @Override
  long getTestExpected1() {
    return 41;
  }

  @Override
  long getTestExpected2() {
    return 6;
  }

  @Override
  String getTestInput() {
    return "....#.....\n" +
        ".........#\n" +
        "..........\n" +
        "..#.......\n" +
        ".......#..\n" +
        "..........\n" +
        ".#..^.....\n" +
        "........#.\n" +
        "#.........\n" +
        "......#...";
  }

  @Override
  String getRealInput() {
    return "....#.....\n" +
        ".........#\n" +
        "..........\n" +
        "..#.......\n" +
        ".......#..\n" +
        "..........\n" +
        ".#..^.....\n" +
        "........#.\n" +
        "#.........\n" +
        "......#...";
  }
}
