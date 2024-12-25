package aoc2024;

import java.util.ArrayList;
import java.util.List;

public class Aoc2425 extends AbstractAoc {
  long part1(String[] in) {
    List<int[]> locks = new ArrayList<>();
    List<int[]> keys = new ArrayList<>();
    for (int i = 0; i < in.length; ) {
      int[] lines = new int[5];
      for (int j = i; j < i + 7; j++) {
        for (int k = 0; k < 5; k++) {
          if (in[j].charAt(k) == '#') {
            lines[k]++;
          }
        }
      }
      if (in[i].charAt(0) == '#') {
        locks.add(lines);
      } else {
        keys.add(lines);
      }
      i += 8;
    }
    long r = 0;
    for (int[] lock : locks) {
      for (int[] key : keys) {
        boolean ok = true;
        for (int i = 0; i < 5; i++) {
          if (lock[i] + key[i] > 7) {
            ok = false;
            break;
          }
        }
        if (ok) {
          r++;
        }
      }
    }
    return r;
  }

  long part2(String[] in) {
    return 0;
  }

  public static void main(String[] args) {
    (new Aoc2425()).start();
  }

  @Override
  long getTestExpected1() {
    return 0;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }

  @Override
  String getTestInput() {
    return "#####\n" +
        ".####\n" +
        ".####\n" +
        ".####\n" +
        ".#.#.\n" +
        ".#...\n" +
        ".....\n" +
        "\n" +
        "#####\n" +
        "##.##\n" +
        ".#.##\n" +
        "...##\n" +
        "...#.\n" +
        "...#.\n" +
        ".....\n" +
        "\n" +
        ".....\n" +
        "#....\n" +
        "#....\n" +
        "#...#\n" +
        "#.#.#\n" +
        "#.###\n" +
        "#####\n" +
        "\n" +
        ".....\n" +
        ".....\n" +
        "#.#..\n" +
        "###..\n" +
        "###.#\n" +
        "###.#\n" +
        "#####\n" +
        "\n" +
        ".....\n" +
        ".....\n" +
        ".....\n" +
        "#....\n" +
        "#.#..\n" +
        "#.#.#\n" +
        "#####";
  }

  @Override
  String getRealInput() {
    return "#####\n" +
        ".####\n" +
        ".####\n" +
        ".####\n" +
        ".#.#.\n" +
        ".#...\n" +
        ".....\n" +
        "\n" +
        "#####\n" +
        "##.##\n" +
        ".#.##\n" +
        "...##\n" +
        "...#.\n" +
        "...#.\n" +
        ".....\n" +
        "\n" +
        ".....\n" +
        "#....\n" +
        "#....\n" +
        "#...#\n" +
        "#.#.#\n" +
        "#.###\n" +
        "#####\n" +
        "\n" +
        ".....\n" +
        ".....\n" +
        "#.#..\n" +
        "###..\n" +
        "###.#\n" +
        "###.#\n" +
        "#####\n" +
        "\n" +
        ".....\n" +
        ".....\n" +
        ".....\n" +
        "#....\n" +
        "#.#..\n" +
        "#.#.#\n" +
        "#####";
  }
}
