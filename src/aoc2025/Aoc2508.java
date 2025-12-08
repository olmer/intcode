package aoc2025;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import tools.Pair;
import tools.Parse;

public class Aoc2508 extends AbstractAoc {
  long part1(String[] in) {
    List<Integer[]> dimensions = new ArrayList<>();

    for (String line : in) {
      Integer[] dims = new Integer[]{Parse.integers(line).get(0), Parse.integers(line).get(1), Parse.integers(line).get(2)};
      dimensions.add(dims);
    }
    Set<Set<Integer[]>> used = new HashSet<>();
    PriorityQueue<Pair<Long, Set<Integer[]>>> shortestDistances = new PriorityQueue<>(Comparator.comparingLong(Pair::getKey));

    for (Integer[] dim1 : dimensions) {
      for (Integer[] dim2 : dimensions) {
        if (dim1 == dim2) {
          continue;
        }
        Set<Integer[]> pair = new HashSet<>();
        pair.add(dim1);
        pair.add(dim2);
        if (used.contains(pair)) {
          continue;
        }
        used.add(pair);
        long dist = distance(dim1, dim2);
        shortestDistances.add(new Pair<>(dist, pair));
      }
    }

    int[] groups = new int[dimensions.size()];
    for (int i = 0; i < groups.length; i++) {
      groups[i] = i;
    }

    int size = dimensions.size();
    while (size-- > 0) {
      Pair<Long, Set<Integer[]>> p = shortestDistances.poll();
      Iterator<Integer[]> it = p.getValue().iterator();
      Integer[] dim1 = it.next();
      Integer[] dim2 = it.next();
      int idx1 = dimensions.indexOf(dim1);
      int idx2 = dimensions.indexOf(dim2);
      if (groups[idx1] != groups[idx2]) {
        int oldGroup = groups[idx2];
        int newGroup = groups[idx1];
        for (int i = 0; i < groups.length; i++) {
          if (groups[i] == oldGroup) {
            groups[i] = newGroup;
          }
        }
      }
    }

    Map<Integer, Integer> groupCounts = new HashMap<>();
    for (int g : groups) {
      groupCounts.put(g, groupCounts.getOrDefault(g, 0) + 1);
    }

    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
    for (int count : groupCounts.values()) {
      pq.add(count);
    }

    return pq.poll() * pq.poll() * pq.poll();
  }

  static Integer[] findClosest(Integer[] dim, List<Integer[]> dimensions, Set<Integer[]> used) {
    Integer[] closest = null;
    long minDist = Long.MAX_VALUE;
    for (Integer[] otherDim : dimensions) {
      if (used.contains(otherDim) || otherDim == dim) {
        continue;
      }
      long dist = distance(dim, otherDim);
      if (dist < minDist) {
        minDist = dist;
        closest = otherDim;
      }
    }
    return closest;
  }

  static long distance(Integer[] dim1, Integer[] dim2) {
    long dist = 0;
    for (int i = 0; i < 3; i++) {
      dist += (long)(dim1[i] - dim2[i]) * (dim1[i] - dim2[i]);
    }
    return dist;
  }

  long part2(String[] in) {
    List<Integer[]> dimensions = new ArrayList<>();

    for (String line : in) {
      Integer[] dims = new Integer[]{Parse.integers(line).get(0), Parse.integers(line).get(1), Parse.integers(line).get(2)};
      dimensions.add(dims);
    }
    Set<Set<Integer[]>> used = new HashSet<>();
    PriorityQueue<Pair<Long, Set<Integer[]>>> shortestDistances = new PriorityQueue<>(Comparator.comparingLong(Pair::getKey));

    for (Integer[] dim1 : dimensions) {
      for (Integer[] dim2 : dimensions) {
        if (dim1 == dim2) {
          continue;
        }
        Set<Integer[]> pair = new HashSet<>();
        pair.add(dim1);
        pair.add(dim2);
        if (used.contains(pair)) {
          continue;
        }
        used.add(pair);
        long dist = distance(dim1, dim2);
        shortestDistances.add(new Pair<>(dist, pair));
      }
    }

    int[] groups = new int[dimensions.size()];
    for (int i = 0; i < groups.length; i++) {
      groups[i] = i;
    }

    int groupNum = groups.length;
    while (groupNum > 1) {
      Pair<Long, Set<Integer[]>> p = shortestDistances.poll();
      Iterator<Integer[]> it = p.getValue().iterator();
      Integer[] dim1 = it.next();
      Integer[] dim2 = it.next();
      int idx1 = dimensions.indexOf(dim1);
      int idx2 = dimensions.indexOf(dim2);
      if (groups[idx1] != groups[idx2]) {
        groupNum--;
        if (groupNum == 1) {
          return (long) dim1[0] * dim2[0];
        }
        int oldGroup = groups[idx2];
        int newGroup = groups[idx1];
        for (int i = 0; i < groups.length; i++) {
          if (groups[i] == oldGroup) {
            groups[i] = newGroup;
          }
        }
      }
    }

    return 0;
  }

  public static void main(String[] args) {
    (new Aoc2508()).start();
  }

  @Override
  String getTestInput() {
    return "162,817,812\n" +
        "57,618,57\n" +
        "906,360,560\n" +
        "592,479,940\n" +
        "352,342,300\n" +
        "466,668,158\n" +
        "542,29,236\n" +
        "431,825,988\n" +
        "739,650,466\n" +
        "52,470,668\n" +
        "216,146,977\n" +
        "819,987,18\n" +
        "117,168,530\n" +
        "805,96,715\n" +
        "346,949,466\n" +
        "970,615,88\n" +
        "941,993,340\n" +
        "862,61,35\n" +
        "984,92,344\n" +
        "425,690,689";
  }

  @Override
  String getRealInput() {
    return "162,817,812\n" +
        "57,618,57\n" +
        "906,360,560\n" +
        "592,479,940\n" +
        "352,342,300\n" +
        "466,668,158\n" +
        "542,29,236\n" +
        "431,825,988\n" +
        "739,650,466\n" +
        "52,470,668\n" +
        "216,146,977\n" +
        "819,987,18\n" +
        "117,168,530\n" +
        "805,96,715\n" +
        "346,949,466\n" +
        "970,615,88\n" +
        "941,993,340\n" +
        "862,61,35\n" +
        "984,92,344\n" +
        "425,690,689";
  }

  @Override
  long getTestExpected1() {
    return 45;
  }

  @Override
  long getTestExpected2() {
    return 25272;
  }
}
