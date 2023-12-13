package aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Aoc2313 {

  private static long part1(String[] input) {

    long r = 0;

    for (var in : input) {
      var rows = in.split("\n");
      List<String> list = Arrays.stream(rows).toList();
      List<String> cols = new ArrayList<>();
      for (int j = 0; j < rows[0].length(); j++) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < in.split("\n").length; i++) {
          s.append(list.get(i).charAt(j));
        }
        cols.add(s.toString());
      }

      r += mirrScore(list, 100, -1);
      r += mirrScore(cols, 1, -1);
    }
    return r;
  }

  private static List<String> flipChar(String line) {
    var r = new ArrayList<String>();
    var a = line.toCharArray();

    for (int i = 0; i < a.length; i++) {
      var t = Arrays.copyOf(a, a.length);
      if (t[i] == '.') {
        t[i] = '#';
        r.add(String.valueOf(t));
      } else if (t[i] == '#') {
        t[i] = '.';
        r.add(String.valueOf(t));
      }
    }

    return r;
  }

  private static long mirrScore(List<String> list, long multiplier, long oldres) {
    for (int mirr = 1; mirr < list.size(); mirr++) {
      int lim = Math.min(mirr, list.size() - mirr);
      boolean mirrored = true;
      for (int i = 0; i < lim; i++) {
        if (!list.get(mirr - i - 1).equals(list.get(mirr + i))) {
          mirrored = false;
          break;
        }
      }
      if (mirrored && mirr * multiplier != oldres) {
        return mirr * multiplier;
      }
    }
    return 0;
  }

  private static long part2(String[] input) {
    long r = 0;

    for (var inold : input) {
      var oldrows = inold.split("\n");
      List<String> oldlist = Arrays.stream(oldrows).toList();
      List<String> oldcols = new ArrayList<>();
      for (int j = 0; j < oldrows[0].length(); j++) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < inold.split("\n").length; i++) {
          s.append(oldlist.get(i).charAt(j));
        }
        oldcols.add(s.toString());
      }

      var oldres = mirrScore(oldlist, 100, -1) != 0
        ? mirrScore(oldlist, 100, -1)
        : mirrScore(oldcols, 1, -1);

      for (var in : flipChar(inold)) {
        var rows = in.split("\n");
        List<String> list = Arrays.stream(rows).toList();
        List<String> cols = new ArrayList<>();
        for (int j = 0; j < rows[0].length(); j++) {
          StringBuilder s = new StringBuilder();
          for (int i = 0; i < in.split("\n").length; i++) {
            s.append(list.get(i).charAt(j));
          }
          cols.add(s.toString());
        }

        var t = mirrScore(list, 100, oldres);
        if (t > 0 && t != oldres) {
          r += t;
          break;
        }
        t = mirrScore(cols, 1, oldres);
        if (t > 0 && t != oldres) {
          r += t;
          break;
        }
      }
    }

    return r;
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
    return (isTest ? testStr : realStr).split("\n\n");
  }

  static int expected1 = 405;
  static int expected2 = 400;
  static String testStr =
"#.##..##.\n" +
  "..#.##.#.\n" +
  "##......#\n" +
  "##......#\n" +
  "..#.##.#.\n" +
  "..##..##.\n" +
  "#.#.##.#.\n" +
  "\n" +
  "#...##..#\n" +
  "#....#..#\n" +
  "..##..###\n" +
  "#####.##.\n" +
  "#####.##.\n" +
  "..##..###\n" +
  "#....#..#";
  static String realStr = "";
}
