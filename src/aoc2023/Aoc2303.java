package aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tools.Grid;
import tools.Pair;

public class Aoc2303 {
  static private final boolean TEST = true;

  private static Map<Pair<Integer, Integer>, List<Integer>> cogs = new HashMap<>();
  private static Set<Pair<Integer, Integer>> curNumberCogs = new HashSet<>();

  public static void main(String[] args) {
    String[] data = getInput();
    int r = 0;
    boolean numberStarted = false;
    boolean symbolIsAdj = false;
    int curNum = 0;
    for (int i = 0; i < data.length; i++) {
      if (numberStarted && symbolIsAdj) {
        for (Pair<Integer, Integer> cog : curNumberCogs) {
          cogs.putIfAbsent(cog, new ArrayList<>());
          cogs.get(cog).add(curNum);
        }
        curNumberCogs.clear();

        System.out.println("added num " + curNum + " to get " + r);

        numberStarted = false;
        symbolIsAdj = false;
        r += curNum;
        curNum = 0;
      }
      for (int j = 0; j < data[0].length(); j++) {
        char c = data[i].charAt(j);
        boolean isDig = Character.isDigit(c);
        if (isDig) {
          numberStarted = true;
          curNum *= 10;
          curNum += Character.getNumericValue(c);
          symbolIsAdj |= isSymbAdj(j, i, data);
        } else {
          if (numberStarted) {
            if (symbolIsAdj) {
              // not digit and number started
              for (Pair<Integer, Integer> cog : curNumberCogs) {
                cogs.putIfAbsent(cog, new ArrayList<>());
                cogs.get(cog).add(curNum);
              }
              curNumberCogs.clear();

              numberStarted = false;
              symbolIsAdj = false;
              r += curNum;

              System.out.println("added num " + curNum + " to get " + r);

              curNum = 0;
            } else {
              // discard cur num;
              numberStarted = false;
              curNum = 0;
            }
          }
        }
      }
    }

    long rr = 0L;

    for (Map.Entry<Pair<Integer, Integer>, List<Integer>> cog : cogs.entrySet()) {
      if (cog.getValue().size() != 2) {
        continue;
      }
      rr += cog.getValue().get(0) * cog.getValue().get(1);
    }

    System.out.println(rr);
  }

  private static boolean isSymbAdj(int x, int y, String[] data) {
    var neighbours = Grid.getValidNeighboursWithCoords(x, y, data);
    for (var charWithCoords : neighbours) {
      char c = charWithCoords.getKey();
      if (c == '*') {
        curNumberCogs.add(charWithCoords.getValue());
      }
      if (!Character.isDigit(c) && c != '.') {
        return true;
      }
    }
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        int ii = x + i;
        int jj = y + j;
        if (i == 0 && j == 0 || ii < 0 || jj < 0 || ii >= data[0].length() || jj >= data.length) {
          continue;
        }
        char c = data[jj].charAt(ii);
      }
    }
    return false;
  }

  private static String[] getInput() {
    String testStr = "467..114..\n" +
      "...*......\n" +
      "..35..633.\n" +
      "......#...\n" +
      "617*......\n" +
      ".....+.58.\n" +
      "..592.....\n" +
      "......755.\n" +
      "...$.*....\n" +
      ".664.598..";
    String realStr = "";
    return (TEST ? testStr : realStr).split("\n");
  }
}
