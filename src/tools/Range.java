package tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Range {
  public static void main(String[] args) {
    var main = new Pair<>(10L, 30L);
    var fullyInside = new Pair<>(15L, 20L);
    var insideLeftMatches = new Pair<>(10L, 20L);
    var insideRightMatches = new Pair<>(20L, 30L);
    var toTheLeft = new Pair<>(0L, 5L);
    var toTheRight = new Pair<>(35L, 45L);
    var encloses = new Pair<>(5L, 35L);
    var intersectsLeft = new Pair<>(5L, 15L);
    var intersectsRight = new Pair<>(25L, 35L);
    var testPairs = List.of(
      main, fullyInside,
      main, insideLeftMatches,
      main, insideRightMatches,
      main, toTheLeft,
      main, toTheRight,
      main, encloses,
      main, intersectsLeft,
      main, intersectsRight
    );
    for (int i = 0; i < testPairs.size(); i += 2) {
      System.out.println("Input: " + testPairs.get(i) + " " + testPairs.get(i + 1));
      System.out.println("Intersect range: " + intersect(testPairs.get(i), testPairs.get(i + 1)));
      System.out.println("Is fully within: " + isFirstFullyWithinSecond(testPairs.get(i), testPairs.get(i + 1)));
      System.out.println("Difference: " + difference(testPairs.get(i), testPairs.get(i + 1)));
      System.out.println();
    }
  }

  public static Optional<Pair<Long, Long>> intersect(Pair<Long, Long> a, Pair<Long, Long> b) {
    var l = Math.max(a.getKey(), b.getKey());
    var r = Math.min(a.getValue(), b.getValue());
    if (r < l) return Optional.empty();
    return Optional.of(new Pair<>(l, r));
  }

  public static List<Pair<Long, Long>> difference(Pair<Long, Long> minuend, Pair<Long, Long> subtrahend) {
    List<Pair<Long, Long>> r = new ArrayList<>();

    if (isFirstFullyWithinSecond(minuend, subtrahend) || intersect(minuend, subtrahend).isEmpty()) {
//      return r;
    }

    Pair<Long, Long> leftRange = new Pair<>(minuend.getKey(), subtrahend.getKey() - 1);
    if (leftRange.getKey() <= leftRange.getValue()) {
      r.add(leftRange);
    }
    Pair<Long, Long> rightRange = new Pair<>(subtrahend.getValue() + 1, minuend.getValue());
    if (rightRange.getKey() <= rightRange.getValue()) {
      r.add(rightRange);
    }

    return r;
  }

  public static boolean isFirstFullyWithinSecond(Pair<Long, Long> a, Pair<Long, Long> b) {
    return a.getKey() > b.getKey() && a.getValue() < b.getValue();
  }

  public static List<Pair<Long, Long>> mergeRanges(List<Pair<Long, Long>> ranges) {
    if (ranges.isEmpty()) {
      return new ArrayList<>();
    }

    ranges.sort(Comparator.comparingLong(Pair::getKey));
    List<Pair<Long, Long>> merged = new ArrayList<>();
    Pair<Long, Long> current = ranges.get(0);

    for (int i = 1; i < ranges.size(); i++) {
      Pair<Long, Long> next = ranges.get(i);
      if (current.getValue() >= next.getKey() - 1) {
        current = new Pair<>(current.getKey(), Math.max(current.getValue(), next.getValue()));
      } else {
        merged.add(current);
        current = next;
      }
    }
    merged.add(current);

    return merged;
  }
}
