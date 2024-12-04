package aoc2024;

import java.util.List;
import java.util.Map;

import tools.Grid;
import tools.Grid.Direction;
import tools.Pair;

public class Aoc2404 extends AbstractAoc {
  long part1(String[] in) {
    int r = 0;
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (in[i].charAt(j) != 'X') {
          continue;
        }
        Map<Direction, List<Pair<Character, Pair<Integer, Integer>>>> dirs = Grid.getBeams(j, i, in, 3, Direction.values());
        for (List<Pair<Character, Pair<Integer, Integer>>> beam : dirs.values()) {
          List<Character> chars = beam.stream().map(Pair::getKey).toList();
          if (chars.equals(List.of('M', 'A', 'S'))) {
            r++;
          }
        }
      }
    }

    return r;
  }

  long part2(String[] in) {
    int r = 0;
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (in[i].charAt(j) != 'A') {
          continue;
        }
        Map<Direction, List<Pair<Character, Pair<Integer, Integer>>>> dirs = Grid.getBeams(j, i, in, 1, Direction.NE, Direction.SE, Direction.SW, Direction.NW);
        int validDiagonals = 0;
        if (dirs.get(Direction.NE) == null || dirs.get(Direction.NE).isEmpty() || dirs.get(Direction.SW) == null || dirs.get(Direction.SW).isEmpty()
            || dirs.get(Direction.NW) == null || dirs.get(Direction.NW).isEmpty() || dirs.get(Direction.SE) == null || dirs.get(Direction.SE).isEmpty()
        ) {
          continue;
        }
        if (dirs.get(Direction.NE).get(0).getKey() == 'M' && dirs.get(Direction.SW).get(0).getKey() == 'S'
            || dirs.get(Direction.NE).get(0).getKey() == 'S' && dirs.get(Direction.SW).get(0).getKey() == 'M'
        ) {
          validDiagonals++;
        }
        if (dirs.get(Direction.NW).get(0).getKey() == 'M' && dirs.get(Direction.SE).get(0).getKey() == 'S'
            || dirs.get(Direction.NW).get(0).getKey() == 'S' && dirs.get(Direction.SE).get(0).getKey() == 'M'
        ) {
          validDiagonals++;
        }
        if (validDiagonals == 2) {
          r++;
        }
      }
    }

    return r;
  }

  public static void main(String[] args) {
    (new Aoc2404()).start();
  }

  @Override
  long getTestExpected1() {
    return 18;
  }

  @Override
  long getTestExpected2() {
    return 9;
  }

  @Override
  String getTestInput() {
    return "MMMSXXMASM\n" +
        "MSAMXMSMSA\n" +
        "AMXSXMAAMM\n" +
        "MSAMASMSMX\n" +
        "XMASAMXAMM\n" +
        "XXAMMXXAMA\n" +
        "SMSMSASXSS\n" +
        "SAXAMASAAA\n" +
        "MAMMMXMMMM\n" +
        "MXMXAXMASX";
  }

  @Override
  String getRealInput() {
    return "MMMSXXMASM\n" +
        "MSAMXMSMSA\n" +
        "AMXSXMAAMM\n" +
        "MSAMASMSMX\n" +
        "XMASAMXAMM\n" +
        "XXAMMXXAMA\n" +
        "SMSMSASXSS\n" +
        "SAXAMASAAA\n" +
        "MAMMMXMMMM\n" +
        "MXMXAXMASX";
  }
}
