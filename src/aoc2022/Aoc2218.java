package aoc2022;

import java.util.ArrayDeque;
import java.util.List;

import tools.Parse;

public class Aoc2218 {
  static boolean TEST = true;

  static int SIZE = 200;

  static int[][] coords = new int[][]{
    new int[]{1, 0, 0},
    new int[]{-1, 0, 0},
    new int[]{0, 1, 0},
    new int[]{0, -1, 0},
    new int[]{0, 0, 1},
    new int[]{0, 0, -1},
  };

  public static void main(String[] args) throws Exception {
    var maxes = new int[]{Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0};
    var input = getInput();

    boolean[][][] grid = new boolean[SIZE][SIZE][SIZE];
    for (var s : input) {
      var d = Parse.integers(s);
      grid[d.get(0)][d.get(1)][d.get(2)] = true;
      maxes[0] = Math.min(maxes[0], d.get(0) - 1);
      maxes[1] = Math.max(maxes[1], d.get(0) + 1);
      maxes[2] = Math.min(maxes[2], d.get(1) - 1);
      maxes[3] = Math.max(maxes[3], d.get(1) + 1);
      maxes[4] = Math.min(maxes[4], d.get(2) - 1);
      maxes[5] = Math.max(maxes[5], d.get(2) + 1);
    }

    boolean[][][] flood = new boolean[maxes[1] + 2][maxes[3] + 2][maxes[5] + 2];

    int r = input.length * 6;

    var q = new ArrayDeque<int[]>();
    q.offer(new int[]{0,0,0});
    flood[0][0][0] = true;
    while (!q.isEmpty()) {
      int[] n = q.poll();
      for (var cc : coords) {
        int nx = n[0] + cc[0];
        int ny = n[1] + cc[1];
        int nz = n[2] + cc[2];
        if (nx < 0 || nx >= flood.length || ny < 0 || ny >= flood[0].length || nz < 0 || nz >= flood[0][0].length) {
          continue;
        }
        if (flood[nx][ny][nz]) {
          continue;
        }
        if (grid[nx][ny][nz]) {
          continue;
        }
        flood[nx][ny][nz] = true;
        q.offer(new int[]{nx, ny, nz});
      }
    }

    for (var s : input) {
      var d = Parse.integers(s);
      r -= neigh(d, grid, flood);
    }


    System.out.println(r);
  }

  static int neigh(List<Integer> cur, boolean[][][] grid, boolean[][][] flood) {
    int r = 0;
    for (var cc : coords) {
      if (cur.get(0) + cc[0] < 0 || cur.get(1) + cc[1] < 0 || cur.get(2) + cc[2] < 0) {
        continue;
      }
      if (grid[cur.get(0) + cc[0]][cur.get(1) + cc[1]][cur.get(2) + cc[2]] || !flood[cur.get(0) + cc[0]][cur.get(1) + cc[1]][cur.get(2) + cc[2]]) {
        r++;
      }
    }
    return r;
  }

  private static String[] getInput() {
    String testStr = "2,2,2\n" +
      "1,2,2\n" +
      "3,2,2\n" +
      "2,1,2\n" +
      "2,3,2\n" +
      "2,2,1\n" +
      "2,2,3\n" +
      "2,2,4\n" +
      "2,2,6\n" +
      "1,2,5\n" +
      "3,2,5\n" +
      "2,1,5\n" +
      "2,3,5";

    String realStr = "";

    return (TEST ? testStr : realStr).split("\n");
  }
}
