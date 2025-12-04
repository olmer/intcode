package aoc2025;

import java.util.List;

import tools.Grid;
import tools.MutableGrid;

public class Aoc2504 extends AbstractAoc {
  long part1(String[] in) {
    int r = 0;
    for (int y = 0; y < in.length; y++) {
      for (int x = 0; x < in[y].length(); x++) {
        if (in[y].charAt(x) != '@') {
          continue;
        }
        List<Character> neighbors = Grid.getValidNeighbours(x, y, in);
        if (neighbors.stream().filter(c -> c == '@').count() < 4) {
          r++;
        }
      }
    }
    return r;
  }

  long part2(String[] in) {
    MutableGrid grid = new MutableGrid(in);
    int r = 0;
    boolean done = false;
    while (!done) {
      done = true;
      for (int y = 0; y < in.length; y++) {
        for (int x = 0; x < in[y].length(); x++) {
          if (grid.get(x, y) != '@') {
            continue;
          }
          List<Character> neighbors = grid.getValidNeighbours(x, y);
          if (neighbors.stream().filter(c -> c == '@').count() < 4) {
            r++;
            grid.set(x, y, '.');
            done = false;
          }
        }
      }
    }
    return r;
  }

  public static void main(String[] args) {
    (new Aoc2504()).start();
  }

  @Override
  String getTestInput() {
    return "..@@.@@@@.\n" +
        "@@@.@.@.@@\n" +
        "@@@@@.@.@@\n" +
        "@.@@@@..@.\n" +
        "@@.@@@@.@@\n" +
        ".@@@@@@@.@\n" +
        ".@.@.@.@@@\n" +
        "@.@@@.@@@@\n" +
        ".@@@@@@@@.\n" +
        "@.@.@@@.@.";
  }

  @Override
  String getRealInput() {
    return "..@@.@@@@.\n" +
        "@@@.@.@.@@\n" +
        "@@@@@.@.@@\n" +
        "@.@@@@..@.\n" +
        "@@.@@@@.@@\n" +
        ".@@@@@@@.@\n" +
        ".@.@.@.@@@\n" +
        "@.@@@.@@@@\n" +
        ".@@@@@@@@.\n" +
        "@.@.@@@.@.";
  }

  @Override
  long getTestExpected1() {
    return 0;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }
}
