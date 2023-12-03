package tools;

import java.util.ArrayList;
import java.util.List;

public class Grid {
  public static List<Character> getValidNeighbours(int x, int y, String[] data) {
    List<Character> r = new ArrayList<>();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        int ii = x + i;
        int jj = y + j;
        if (i == 0 && j == 0 || ii < 0 || jj < 0 || ii >= data[0].length() || jj >= data.length) {
          continue;
        }
        r.add(data[jj].charAt(ii));
      }
    }
    return r;
  }
  public static List<Pair<Character, Pair<Integer, Integer>>> getValidNeighboursWithCoords(int x, int y, String[] data) {
    List<Pair<Character, Pair<Integer, Integer>>> r = new ArrayList<>();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        int ii = x + i;
        int jj = y + j;
        if (i == 0 && j == 0 || ii < 0 || jj < 0 || ii >= data[0].length() || jj >= data.length) {
          continue;
        }
        r.add(new Pair<>(data[jj].charAt(ii), new Pair<>(ii, jj)));
      }
    }
    return r;
  }
}
