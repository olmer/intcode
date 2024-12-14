package aoc2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import tools.Parse;

public class Aoc2413 extends AbstractAoc {
  Set<Integer> validRules;

  long part1(String[] in) {
    validRules = new TreeSet<>();
    List<Rule> rules = new ArrayList<>();
    for (int i = 0; i < in.length; i += 4) {
      List<Integer> a = Parse.integers(in[i]);
      List<Integer> b = Parse.integers(in[i + 1]);
      List<Integer> p = Parse.integers(in[i + 2]);
      Rule r = new Rule(a.get(0), a.get(1), b.get(0), b.get(1), p.get(0), p.get(1));
      rules.add(r);
    }
    long r = 0;
    for (int p = 0; p < rules.size(); p++) {
      Rule rule = rules.get(p);
      long best = Long.MAX_VALUE;
      int fi = 0;
      int fj = 0;
      for (int i = 0; i < 100; i++) {
        int costA = i * 3;
        int costB;
        long posx;
        long posy;
        int j = 0;
        do {
          posx = rule.ax * i + rule.bx * j;
          posy = rule.ay * i + rule.by * j;
          costB = j;
          if (posx == rule.x && posy == rule.y) {
            if (costA + costB < best) {
              fi = i;
              fj = j;
              best = costA + costB;
            }
          }
          j++;
          if (posx > rule.x || posy > rule.y) {
            break;
          }
        } while (posx < rule.x && posy < rule.y);
      }
      if (best == Long.MAX_VALUE) {
        best = 0;
      }
      if (best != 0) {
//        System.out.println("Rule #" + p + " " + rule + "is valid");
//        System.out.println("Cost: " + best);
//        System.out.println("Clicks A: " + fi);
//        System.out.println("Clicks B: " + fj);
        validRules.add(p);
      }
      r += best;
    }
    return r;
  }

  long part2(String[] in) {
    Set<Integer> part2validRules = new TreeSet<>();
    List<Rule> rules = new ArrayList<>();
    long result = 0;
    for (int i = 0; i < in.length; i += 4) {
      List<Integer> a = Parse.integers(in[i]);
      List<Integer> b = Parse.integers(in[i + 1]);
      List<Integer> p = Parse.integers(in[i + 2]);
//      Rule r = new Rule(a.get(0), a.get(1), b.get(0), b.get(1), p.get(0), p.get(1));
      Rule r = new Rule(a.get(0), a.get(1), b.get(0), b.get(1), p.get(0) + 10000000000000L, p.get(1) + 10000000000000L);
      rules.add(r);
    }
    // parallelize this
    for (int p = 0; p < rules.size(); p++) {// if r.x % gcd(ax,bx) != 0 or r.y % gcd(ay,by) != 0 then it is impossible to reach the result
      System.out.println("Rule #" + p);
      boolean finalSolutionFound = false;
      Rule r = rules.get(p);

      long clicksB = r.y / r.by();

      long remainingYScoreToFillByA = r.y - clicksB * r.by();
      while (remainingYScoreToFillByA <= r.y && clicksB > 1) { // @todo maybe actually it is stuck here, measure time. Or we can get rid of this completely
        if (remainingYScoreToFillByA % r.ay() == 0) {
          // found the solution for Y position
          double clicksA = ((double) remainingYScoreToFillByA) / (double) r.ay();
//          System.out.println("Found a possible solution: clicksB: " + clicksB + ", remaining: " + remaining);
          if (clicksA * r.ax() + clicksB * r.bx() == r.x) {
//            System.out.println("It is actually a final one!");
            part2validRules.add(p);
            finalSolutionFound = true;
            result += (clicksA * 3 + clicksB);
          }
          break;
        }
        clicksB--;
        remainingYScoreToFillByA += r.by();
      }

      if (finalSolutionFound) {
        continue;
      }

      long lcm = lcm(r.ay(), r.by());
      long clicksBInLCM = lcm / r.by();

      while (remainingYScoreToFillByA <= r.y && clicksB >= clicksBInLCM) {// todo check for some condition like the result is unreachable in some cases (short-circuit)
        long lcm2 = lcm(lcm, r.ay);
        long stepsSkipped = lcm2 / lcm;
        clicksB -= clicksBInLCM * stepsSkipped;
        remainingYScoreToFillByA += lcm2;
        long clicksA = remainingYScoreToFillByA / r.ay();
        if (clicksA * r.ax() + clicksB * r.bx() == r.x) {// binary search?
          part2validRules.add(p);
          result += (clicksA * 3 + clicksB);
          break;
        }
      }
    }

    Set<Integer> diff = new TreeSet<>(validRules);
    diff.removeAll(part2validRules);
//    System.out.println("Missing rules: " + diff);

    // opposite way
    diff = new TreeSet<>(part2validRules);
    diff.removeAll(validRules);
//    System.out.println("Extra rules: " + diff);

    return result;
  }

  private long lcm(long a, long b) {
    return a * b / gcd(a, b);
  }

  private long gcd(long a, long b) {
    while (b != 0) {
      long t = b;
      b = a % b;
      a = t;
    }
    return a;
  }

  public static void main(String[] args) {
    (new Aoc2413()).start();
  }

  @Override
  long getTestExpected1() {
    return 480;
  }

  @Override
  long getTestExpected2() {
    return 480;
  }

  record Rule(int ax, int ay, int bx, int by, long x, long y) {}

  @Override
  String getTestInput() {
    return "Button A: X+94, Y+34\n" +
        "Button B: X+22, Y+67\n" +
        "Prize: X=8400, Y=5400\n" +
        "\n" +
        "Button A: X+26, Y+66\n" +
        "Button B: X+67, Y+21\n" +
        "Prize: X=12748, Y=12176\n" +
        "\n" +
        "Button A: X+17, Y+86\n" +
        "Button B: X+84, Y+37\n" +
        "Prize: X=7870, Y=6450\n" +
        "\n" +
        "Button A: X+69, Y+23\n" +
        "Button B: X+27, Y+71\n" +
        "Prize: X=18641, Y=10279";
  }

  String getRealInput1() {
    return "Button A: X+91, Y+13\n" +
        "Button B: X+50, Y+61\n" +
        "Prize: X=1014, Y=845";
  }

  String getRealInput() {
    return "Button A: X+94, Y+34\n" +
        "Button B: X+22, Y+67\n" +
        "Prize: X=8400, Y=5400\n" +
        "\n" +
        "Button A: X+26, Y+66\n" +
        "Button B: X+67, Y+21\n" +
        "Prize: X=12748, Y=12176\n" +
        "\n" +
        "Button A: X+17, Y+86\n" +
        "Button B: X+84, Y+37\n" +
        "Prize: X=7870, Y=6450\n" +
        "\n" +
        "Button A: X+69, Y+23\n" +
        "Button B: X+27, Y+71\n" +
        "Prize: X=18641, Y=10279";
  }
}
