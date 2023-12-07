package aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tools.Pair;

public class Aoc2307 {
  static private final boolean TEST = true;

  private static Map<Character, Integer> cardOrder = new HashMap<>() {{
    put('A', 13);
    put('K', 12);
    put('Q', 11);
    put('J', 10);
    put('T', 9);
    put('9', 8);
    put('8', 7);
    put('7', 6);
    put('6', 5);
    put('5', 4);
    put('4', 3);
    put('3', 2);
    put('2', 1);
  }};

  private static Map<Character, Integer> cardOrder2 = new HashMap<>() {{
    put('A', 13);
    put('K', 12);
    put('Q', 11);
    put('J', 0);
    put('T', 9);
    put('9', 8);
    put('8', 7);
    put('7', 6);
    put('6', 5);
    put('5', 4);
    put('4', 3);
    put('3', 2);
    put('2', 1);
  }};

  private static int compareHands(String a, String b) {
    for (int i = 0; i < a.length(); i++) {
      if (a.charAt(i) == b.charAt(i)) continue;
      return cardOrder.get(a.charAt(i)) - cardOrder.get(b.charAt(i));
    }
    return 0;
  }

  private static int compareHands2(String a, String b) {
    for (int i = 0; i < a.length(); i++) {
      if (a.charAt(i) == b.charAt(i)) continue;
      return cardOrder2.get(a.charAt(i)) - cardOrder2.get(b.charAt(i));
    }
    return 0;
  }

  public static void main(String[] args) {
    System.out.println(part1());
    System.out.println(part2());
  }

  private static long part1() {
    var input = getInput();
    //5 oak         7
    //4 oak         6
    //full house    5
    //3 oak         4
    //two pair      3
    //one pair      2
    //high          1
    List<String> cards = new ArrayList<>();
    List<Integer> cardTypes = new ArrayList<>();
    List<Integer> bets = new ArrayList<>();
    Arrays.stream(input).forEach(e -> {
      cards.add(e.split(" ")[0]);
      bets.add(Integer.parseInt(e.split(" ")[1]));
    });
    cards.stream().forEach(e -> {
      var counts = new HashMap<Character, Integer>();
      for (int i = 0; i < e.length(); i++) {
        char c = e.charAt(i);
        counts.put(c, counts.getOrDefault(c, 0) + 1);
      }
      var numOfCards = new ArrayList<Integer>();
      numOfCards.addAll(counts.values());
      numOfCards.sort(Comparator.reverseOrder());
      int type;
      if (numOfCards.get(0) == 5) {
        type = 7;
      } else if (numOfCards.get(0) == 4) {
        type = 6;
      } else if (numOfCards.get(0) == 3 && numOfCards.get(1) == 2) {
        type = 5;
      } else if (numOfCards.get(0) == 3) {
        type = 4;
      } else if (numOfCards.get(0) == 2 && numOfCards.get(1) == 2) {
        type = 3;
      } else if (numOfCards.get(0) == 2) {
        type = 2;
      } else {
        type = 1;
      }
      cardTypes.add(type);
    });
    Map<Pair<String, Integer>, Integer> map = new TreeMap<>((a, b) -> a.getValue() == b.getValue() ? compareHands(a.getKey(), b.getKey()) : a.getValue().compareTo(b.getValue()));
    for (int i = 0; i < cards.size(); i++) {
      map.put(new Pair<>(cards.get(i), cardTypes.get(i)), bets.get(i));
    }
    long r = 0;
    int i = 1;
    for (var e : map.entrySet()) {
      r += (long) (i * e.getValue());
      i++;
    }
    return r;
  }

  private static long part2() {
    var input = getInput();
    //5 oak         7
    //4 oak         6
    //full house    5
    //3 oak         4
    //two pair      3
    //one pair      2
    //high          1
    List<String> cards = new ArrayList<>();
    List<Integer> cardTypes = new ArrayList<>();
    List<Integer> bets = new ArrayList<>();
    Arrays.stream(input).forEach(e -> {
      cards.add(e.split(" ")[0]);
      bets.add(Integer.parseInt(e.split(" ")[1]));
    });
    cards.stream().forEach(e -> {
      var counts = new HashMap<Character, Integer>();
      int numOfJokers = 0;
      for (int i = 0; i < e.length(); i++) {
        char c = e.charAt(i);
        if (c == 'J') {
          numOfJokers++;
        } else {
          counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
      }
      var numOfCards = new ArrayList<Integer>();
      numOfCards.addAll(counts.values());
      numOfCards.sort(Comparator.reverseOrder());
      if (numOfJokers > 0) {
        int t;
        if (numOfCards.size() > 0) {
          t = numOfCards.remove(0);
        } else {
          t = 0;
        }
        numOfCards.add(0, t + numOfJokers);
      }
      int type;
      if (numOfCards.get(0) == 5) {
        type = 7;
      } else if (numOfCards.get(0) == 4) {
        type = 6;
      } else if (numOfCards.get(0) == 3 && numOfCards.get(1) == 2) {
        type = 5;
      } else if (numOfCards.get(0) == 3) {
        type = 4;
      } else if (numOfCards.get(0) == 2 && numOfCards.get(1) == 2) {
        type = 3;
      } else if (numOfCards.get(0) == 2) {
        type = 2;
      } else {
        type = 1;
      }
      cardTypes.add(type);
    });
    Map<Pair<String, Integer>, Integer> map = new TreeMap<>((a, b) -> a.getValue() == b.getValue() ? compareHands2(a.getKey(), b.getKey()) : a.getValue().compareTo(b.getValue()));
    for (int i = 0; i < cards.size(); i++) {
      map.put(new Pair<>(cards.get(i), cardTypes.get(i)), bets.get(i));
    }
    long r = 0;
    int i = 1;
    for (var e : map.entrySet()) {
      r += (long) (i * e.getValue());
      i++;
    }
    return r;
  }

  private static String[] getInput() {
    String testStr = "32T3K 765\n" +
      "T55J5 684\n" +
      "KK677 28\n" +
      "KTJJT 220\n" +
      "QQQJA 483";
    String realStr = "";
    return (TEST ? testStr : realStr).split("\n");
  }
}
