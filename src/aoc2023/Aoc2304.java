package aoc2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import tools.Parse;

public class Aoc2304 {
  static private final boolean TEST = true;

  public static void main(String[] args) {
    int r = 0;
    String[] in = getInput();
    int[] cards = new int[in.length];
    Arrays.fill(cards, 1);
    for (int rowidx = 0; rowidx < in.length; rowidx++) {
      String row = in[rowidx];
      var winners = Parse.integers(row.split(" \\| ")[0]);
      var nums = Parse.integers(row.split(" \\| ")[1]);
      Set<Integer> fnums = new HashSet<>(nums);
      int wins = 0;
      for (int i = 1; i < winners.size(); i++) {
        if (fnums.contains(winners.get(i))) {
          wins++;
        }
      }
      for (int i = rowidx + 1; i < rowidx + 1 + wins; i++) {
        cards[i] += cards[rowidx];
      }
    }
    System.out.println(Arrays.stream(cards).sum());
  }

  private static String[] getInput() {
    String testStr = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n" +
      "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n" +
      "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n" +
      "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n" +
      "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n" +
      "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";
    String realStr = "";
    return (TEST ? testStr : realStr).split("\n");
  }
}
