package aoc2025;

import java.util.ArrayList;
import java.util.List;

import tools.Pair;
import tools.Range;

public class Aoc2505 extends AbstractAoc {
  long part1(String[] in) {
    List<Pair<Long, Long>> ranges = new ArrayList<>();
    List<Long> points = new ArrayList<>();
    boolean readingRanges = true;
    for (String line : in) {
      if (line.isBlank()) {
        readingRanges = false;
        continue;
      }
      if (readingRanges) {
        String[] parts = line.split("-");
        long start = Long.parseLong(parts[0]);
        long end = Long.parseLong(parts[1]);
        ranges.add(new Pair<>(start, end));
      } else {
        points.add(Long.parseLong(line));
      }
    }
    long r = 0;

    for (long p : points) {
      boolean covered = false;
      for (Pair<Long, Long> range : ranges) {
        if (p >= range.getKey() && p <= range.getValue()) {
          covered = true;
          break;
        }
      }
      if (covered) {
        r++;
      }
    }

    return r;
  }

  long part2(String[] in) {
    long r = 0;
    List<Pair<Long, Long>> ranges = new ArrayList<>();
    boolean readingRanges = true;
    for (String line : in) {
      if (line.isBlank()) {
        break;
      }
      String[] parts = line.split("-");
      long start = Long.parseLong(parts[0]);
      long end = Long.parseLong(parts[1]);
      ranges.add(new Pair<>(start, end));
    }

    List<Pair<Long, Long>> mergedRanges = Range.mergeRanges(ranges);

    for (Pair<Long, Long> range : mergedRanges) {
      r += (range.getValue() - range.getKey() + 1);
    }

    return r;
  }

  public static void main(String[] args) {
    (new Aoc2505()).start();
  }

  @Override
  String getTestInput() {
    return "3-5\n" +
        "10-14\n" +
        "16-20\n" +
        "12-18\n" +
        "\n" +
        "1\n" +
        "5\n" +
        "8\n" +
        "11\n" +
        "17\n" +
        "32";
  }

  @Override
  String getRealInput() {
    return "3-5\n" +
        "10-14\n" +
        "16-20\n" +
        "12-18\n" +
        "\n" +
        "1\n" +
        "5\n" +
        "8\n" +
        "11\n" +
        "17\n" +
        "32";
  }

  @Override
  long getTestExpected1() {
    return 3;
  }

  @Override
  long getTestExpected2() {
    return 14;
  }
}
