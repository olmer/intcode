package aoc2022;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Aoc2209 {
  public static void main(String[] args) throws Exception {
    var input = getInput(true);

    int[][] rope = new int[10][];
    for (int i = 0; i < rope.length; i++) {
      rope[i] = new int[]{0, 0};
    }

    //U = 0, R = 1, D = 2, L = 3
    int[][] coord = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    Set<Point> visited = new HashSet<>();

    for (String com : input) {
      char dir = com.split(" ")[0].charAt(0);
      int vel = Integer.parseInt(com.split(" ")[1]);
      int d;
      if (dir == 'U')
        d = 0;
      else if (dir == 'R')
        d = 1;
      else if (dir == 'D')
        d = 2;
      else
        d = 3;

      while (vel-- > 0) {
        rope[0][0] += coord[d][0];
        rope[0][1] += coord[d][1];

        for (int i = 0; i <= 8; i++) {
          int[] head = rope[i];
          int[] tail = rope[i + 1];
          switch (Math.abs(head[0] - tail[0]) + Math.abs(head[1] - tail[1])) {
            case 2:
              if (head[0] == tail[0]) {
                if (head[1] < tail[1])
                  tail[1]--;
                else
                  tail[1]++;
              } else if (head[1] == tail[1]) {
                if (head[0] < tail[0])
                  tail[0]--;
                else
                  tail[0]++;
              }
              break;
            case 3:
            case 4:
              if (head[1] < tail[1])
                tail[1]--;
              else
                tail[1]++;
              if (head[0] < tail[0])
                tail[0]--;
              else
                tail[0]++;
              break;
          }
          rope[i + 1] = tail;
        }

        visited.add(new Point(rope[9][0], rope[9][1]));

        System.out.println(visited.size());
      }
    }


    visited.add(new Point(rope[9][0], rope[9][1]));

    print(visited);
  }

  private static void print(Set<Point> a) {
    int lim = 50;
    for (int i = -lim; i < lim; i++) {
      for (int j = -lim; j < lim; j++) {
        if (a.contains(new Point(j, i))) {
          System.out.print("#");
        } else {
          System.out.print(".");
        }
      }
      System.out.println();
    }
  }

  private static String[] getInput(boolean test) {
    String s = "";
    if (!test) {
      s = "";
    } else {
      s = "R 5\n" +
        "U 8\n" +
        "L 8\n" +
        "D 3\n" +
        "R 17\n" +
        "D 10\n" +
        "L 25\n" +
        "U 20";
    }
    return s.split("\n");
  }
}
