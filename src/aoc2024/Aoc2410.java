package aoc2024;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import tools.Grid;
import tools.Pair;

public class Aoc2410 extends AbstractAoc {
  long part1(String[] in) {
    ArrayDeque<Pair<Integer, Integer>> starts = new ArrayDeque<>();
    long r = 0;
    for (int row = 0; row < in.length; row++) {
      for (int col = 0; col < in[row].length(); col++) {
        if (in[row].charAt(col) == '0') {
          starts.add(new Pair<>(col, row));
        }
      }
    }
    while (!starts.isEmpty()) {
      Pair<Integer, Integer> p = starts.poll();
      Set<Pair<Integer, Integer>> reachableFromStart = new HashSet<>();
      ArrayDeque<Pair<Integer, Integer>> fromCurrentStart = new ArrayDeque<>();
      fromCurrentStart.add(p);
      while (!fromCurrentStart.isEmpty()) {
        Pair<Integer, Integer> current = fromCurrentStart.poll();
        int col = current.getKey();
        int row = current.getValue();
        int v = in[row].charAt(col) - '0';
        if (v == 9) {
          reachableFromStart.add(current);
          continue;
        }
        Grid.getValidNeighboursWithCoords(col, row, in, Grid.CARDINAL).forEach(n -> {
          int ncol = n.getValue().getKey();
          int nrow = n.getValue().getValue();
          if (n.getKey() - '0' == v + 1) {
            fromCurrentStart.offer(new Pair<>(ncol, nrow));
          }
        });
      }
      r += reachableFromStart.size();
    }
    return r;
  }

  long part2(String[] in) {
    ArrayDeque<Pair<Integer, Integer>> starts = new ArrayDeque<>();
    long r = 0;
    for (int row = 0; row < in.length; row++) {
      for (int col = 0; col < in[row].length(); col++) {
        if (in[row].charAt(col) == '0') {
          starts.add(new Pair<>(col, row));
        }
      }
    }
    while (!starts.isEmpty()) {
      Pair<Integer, Integer> current = starts.poll();
      int col = current.getKey();
      int row = current.getValue();
      int v = in[row].charAt(col) - '0';
      if (v == 9) {
        r++;
        continue;
      }
      Grid.getValidNeighboursWithCoords(col, row, in, Grid.CARDINAL).forEach(n -> {
        int ncol = n.getValue().getKey();
        int nrow = n.getValue().getValue();
        if (n.getKey() - '0' == v + 1) {
          starts.offer(new Pair<>(ncol, nrow));
        }
      });
    }
    return r;
  }

  public static void main(String[] args) {
    (new Aoc2410()).start();
  }

  @Override
  long getTestExpected1() {
    return 36;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }

  @Override
  String getTestInput() {
    return "89010123\n" +
        "78121874\n" +
        "87430965\n" +
        "96549874\n" +
        "45678903\n" +
        "32019012\n" +
        "01329801\n" +
        "10456732";
  }

  @Override
  String getRealInput() {
    return "89010123\n" +
        "78121874\n" +
        "87430965\n" +
        "96549874\n" +
        "45678903\n" +
        "32019012\n" +
        "01329801\n" +
        "10456732";
  }
}
