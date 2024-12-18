package aoc2024;

import java.util.ArrayDeque;
import java.util.List;

import tools.Pair;
import tools.Parse;

public class Aoc2418 extends AbstractAoc {
  long part1(String[] in) {
    int simulationTime = 12;
    int size = 71;
    boolean[][] visited = new boolean[size][size];

    Pair<Integer, Integer> dest = new Pair<>(size - 1, size - 1);

    ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();
    q.offer(new Pair<>(0, 0));
    visited[0][0] = true;

    int time = 0;
    while (simulationTime-- > 0) {
      List<Integer> stone = Parse.integers(in[time++]);
      visited[stone.get(1)][stone.get(0)] = true;
    }

    int dist = -1;
    int[] dirs = {0, 1, 0, -1, 0};
    while (!q.isEmpty()) {
      dist++;
      int qsize = q.size();
      while (qsize-- > 0) {
        Pair<Integer, Integer> p = q.poll();
        if (p.equals(dest)) {
          return dist;
        }
        for (int i = 0; i < 4; i++) {
          int x = dirs[i] + p.getKey();
          int y = dirs[i + 1] + p.getValue();
          if (x >= 0 && x < size && y >= 0 && y < size && !visited[y][x]) {
            visited[y][x] = true;
            q.offer(new Pair<>(x, y));
          }
        }
      }
    }

    return 0;
  }

  long part2(String[] in) {
    int size = 71;

    Pair<Integer, Integer> dest = new Pair<>(size - 1, size - 1);

    int time = 0;
    while (time++ < in.length) {
      boolean pathFound = false;
      boolean[][] visited = new boolean[size][size];
      for (int i = 0; i < time; i++) {
        List<Integer> s = Parse.integers(in[i]);
        visited[s.get(1)][s.get(0)] = true;
      }

      ArrayDeque<Pair<Integer, Integer>> q = new ArrayDeque<>();
      q.offer(new Pair<>(0, 0));
      visited[0][0] = true;

      int[] dirs = {0, 1, 0, -1, 0};
      while (!q.isEmpty()) {
        int qsize = q.size();
        while (qsize-- > 0) {
          Pair<Integer, Integer> p = q.poll();
          if (p.equals(dest)) {
            pathFound = true;
            q.clear();
            System.out.println("Path found at time " + time);
            break;
          }
          for (int i = 0; i < 4; i++) {
            int x = dirs[i] + p.getKey();
            int y = dirs[i + 1] + p.getValue();
            if (x >= 0 && x < size && y >= 0 && y < size && !visited[y][x]) {
              visited[y][x] = true;
              q.offer(new Pair<>(x, y));
            }
          }
        }
      }
      if (!pathFound) {
        System.out.println("No path found at time " + time);
        System.out.println("Last stone: " + in[time - 1]);
        break;
      }
    }

    return 0;
  }

  public static void main(String[] args) {
    (new Aoc2418()).start();
  }

  @Override
  long getTestExpected1() {
    return 22;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }

  @Override
  String getTestInput() {
    return "5,4\n" +
        "4,2\n" +
        "4,5\n" +
        "3,0\n" +
        "2,1\n" +
        "6,3\n" +
        "2,4\n" +
        "1,5\n" +
        "0,6\n" +
        "3,3\n" +
        "2,6\n" +
        "5,1\n" +
        "1,2\n" +
        "5,5\n" +
        "2,5\n" +
        "6,5\n" +
        "1,4\n" +
        "0,4\n" +
        "6,4\n" +
        "1,1\n" +
        "6,1\n" +
        "1,0\n" +
        "0,5\n" +
        "1,6\n" +
        "2,0";
  }

  @Override
  String getRealInput() {
    return "5,4\n" +
        "4,2\n" +
        "4,5\n" +
        "3,0\n" +
        "2,1\n" +
        "6,3\n" +
        "2,4\n" +
        "1,5\n" +
        "0,6\n" +
        "3,3\n" +
        "2,6\n" +
        "5,1\n" +
        "1,2\n" +
        "5,5\n" +
        "2,5\n" +
        "6,5\n" +
        "1,4\n" +
        "0,4\n" +
        "6,4\n" +
        "1,1\n" +
        "6,1\n" +
        "1,0\n" +
        "0,5\n" +
        "1,6\n" +
        "2,0";
  }
}
