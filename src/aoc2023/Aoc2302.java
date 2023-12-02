package aoc2023;

import java.util.Arrays;

public class Aoc2302 {
  static private final boolean TEST = true;

  public static void main(String[] args) {
    var games = Arrays
      .stream(getInput())
      .map(e -> e.split(": ")[1])
      .map(e -> Arrays
        .stream(e.split("; "))
        .map(ee -> Arrays
          .stream(ee.split(", "))
          .map(eee -> {
            String[] t = eee.split(" ");
            int[] r = new int[3];
            switch (t[1]) {
              case "red" -> r[0] = Integer.parseInt(t[0]);
              case "green" -> r[1] = Integer.parseInt(t[0]);
              case "blue" -> r[2] = Integer.parseInt(t[0]);
            }
            return r;
          }).reduce(new int[3], (acc, val) -> {
            acc[0] += val[0];
            acc[1] += val[1];
            acc[2] += val[2];
            return acc;
          }))
        .reduce(new int[3], (acc, val) -> {
          acc[0] = Math.max(acc[0], val[0]);
          acc[1] = Math.max(acc[1], val[1]);
          acc[2] = Math.max(acc[2], val[2]);
          return acc;
        }))
      .toList();

    int r = 0;

    for (var game : games) {
      r += game[0] * game[1] * game[2];
    }

    System.out.println(r);
  }

  private static String[] getInput() {
    String testStr = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
      "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
      "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
      "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
      "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green";
    String realStr = "";
    return (TEST ? testStr : realStr).split("\n");
  }
}
