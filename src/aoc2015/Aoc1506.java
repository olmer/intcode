package aoc2015;

import java.security.NoSuchAlgorithmException;

public class Aoc1506 {
  private static final boolean TEST = false;

  static int[][] data = new int[1000][1000];

  public static void main(String[] args) throws NoSuchAlgorithmException {
    String[] in = getInput();
    int[][] commands = new int[in.length][5];
    for (int i = 0; i < in.length; i++) {
      var t = in[i].split(" ");
      if (t[1].equals("on")) {
        commands[i][0] = 0;
        commands[i][1] = Integer.parseInt(t[2].split(",")[0]);
        commands[i][2] = Integer.parseInt(t[2].split(",")[1]);
        commands[i][3] = Integer.parseInt(t[4].split(",")[0]);
        commands[i][4] = Integer.parseInt(t[4].split(",")[1]);
      } else if (t[1].equals("off")) {
        commands[i][0] = 1;
        commands[i][1] = Integer.parseInt(t[2].split(",")[0]);
        commands[i][2] = Integer.parseInt(t[2].split(",")[1]);
        commands[i][3] = Integer.parseInt(t[4].split(",")[0]);
        commands[i][4] = Integer.parseInt(t[4].split(",")[1]);
      } else {
        commands[i][0] = 2;
        commands[i][1] = Integer.parseInt(t[1].split(",")[0]);
        commands[i][2] = Integer.parseInt(t[1].split(",")[1]);
        commands[i][3] = Integer.parseInt(t[3].split(",")[0]);
        commands[i][4] = Integer.parseInt(t[3].split(",")[1]);
      }
    }

    for (var com : commands) {
      for (int i = com[1]; i <= com[3]; i++) {
        for (int j = com[2]; j <= com[4]; j++) {
          switch (com[0]) {
            case 0 -> data[i][j] = data[i][j] + 1;
            case 1 -> data[i][j] = Math.max(0, data[i][j] - 1);
            default -> data[i][j] = data[i][j] + 2;
          }
        }
      }

//      print();
//      System.out.println("_________________");
    }

    int r = 0;

    for (int i = 0; i < 1000; i++) {
      for (int j = 0; j < 1000; j++) {
        r += data[i][j];
      }
    }

//    print();

    System.out.println(r);
  }

  private static void print() {
    for (int i = 0; i < 1000; i++) {
      for (int j = 0; j < 1000; j++) {
        System.out.print(data[i][j]);
      }
      System.out.println();
    }
  }

  private static String[] getInput() {
    String testString =
      "turn on 1,1 through 2,2\ntoggle 0,0 through 3,3";
    String realString = "";
    return (TEST ? testString : realString).split("\n");
  }
}
