package aoc2022;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tools.Parse;

public class Aoc2220 {

  static class Num {
    long val;

    public Num(long val_) {
      val = val_;
    }

    @Override
    public String toString() {
      return String.valueOf(val);
    }
  }
  static boolean TEST = true;
  static int PART = 2;

  public static void main(String[] args) throws Exception {
    List<Num> l = new LinkedList<>();
    List<Num> order = new ArrayList<>();
    Num z = new Num(0);

    for (var num : Parse.longs(getInput())) {
      var n = new Num(num);
      if (PART == 2) {
        n = new Num(num * 811589153);
      }
      if (n.val == 0) {
        z = n;
      }
      l.add(n);
      order.add(n);
    }

    int mod = l.size() - 1;

    for (int i = 0; i < (PART == 2 ? 10 : 1); i++) {
      for (var num : order) {
        if (num.val == 0) {
          continue;
        }
        int idx = l.indexOf(num);
        int newPos = (int) (idx + (num.val % (mod)) + mod) % mod;

        l.remove(num);
        l.add(newPos, num);
      }
    }

    System.out.println(l.get((l.indexOf(z) + 1000) % (mod + 1)).val
      + l.get((l.indexOf(z) + 2000) % (mod + 1)).val
      + l.get((l.indexOf(z) + 3000) % (mod + 1)).val);
  }

  private static String getInput() {
    String testStr = "1\n" +
      "2\n" +
      "-3\n" +
      "3\n" +
      "-2\n" +
      "0\n" +
      "4";

    String realStr = "";

    return (TEST ? testStr : realStr);
  }
}
