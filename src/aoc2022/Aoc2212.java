package aoc2022;

import java.util.ArrayDeque;

public class Aoc2212 {
  public static void main(String[] args) throws Exception {
    var input = getInput(true);

    ArrayDeque<int[]> q = new ArrayDeque<>();
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[0].length(); j++) {
        if (input[i].charAt(j) == 'a') {
          q.offer(new int[]{i, j, 1});
        }
      }
    }

    int r = 0;
    int[] coord = new int[]{1, 0, -1, 0, 1};
    boolean[][] visited = new boolean[input.length][input[0].length()];
    while (!q.isEmpty()) {
      int[] next = q.poll();

      if (input[next[0]].charAt(next[1]) == 'E') {
        r = next[2];
        break;
      }

      for (int i = 0; i < 4; i++) {
        int nx = coord[i] + next[0];
        int ny = coord[i + 1] + next[1];
        if (nx < 0 || ny < 0 || nx >= input.length || ny >= input[0].length() || visited[nx][ny]) {
          continue;
        }
        if (input[next[0]].charAt(next[1]) != 'S' && ((int)input[nx].charAt(ny)) - 1 > ((int)input[next[0]].charAt(next[1]))) {
          continue;
        }
        if (input[next[0]].charAt(next[1]) != 'z' && input[next[0]].charAt(next[1]) != 'y' && input[nx].charAt(ny) == 'E') {
          continue;
        }
        visited[nx][ny] = true;
        q.offer(new int[]{nx, ny, next[2] + 1});
      }
    }

    System.out.println(r - 1);
  }

  private static String[] getInput(boolean dry) {
    String test = "Sabqponm\n" +
      "abcryxxl\n" +
      "accszExk\n" +
      "acctuvwj\n" +
      "abdefghi";

    String real = "";

    return (dry ? test : real).split("\n");
  }
}
