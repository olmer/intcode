package aoc2025;

public class Aoc2501 extends AbstractAoc {
  long part1(String[] in) {
    int r = 50;
    int rr = 0;
    for (String line : in) {
      int dist = Integer.parseInt(line.substring(1));
      if (line.charAt(0) == 'L') {
        r = (r - dist + 100) % 100;
      } else {
        r = (r + dist) % 100;
      }
      if (r == 0) {
        rr++;
      }
    }
    return rr;
  }

  long part2(String[] in) {
    int r = 50;
    int rr = 0;
    for (String line : in) {
      int dist = Integer.parseInt(line.substring(1));
      int n;
      if (line.charAt(0) == 'L') {
        rr += dist / 100;
        n = r - (dist % 100);
        if (n <= 0 && r != 0) {
          rr++;
        }
        r = (n + 100) % 100;
      } else {
        n = (r + dist) % 100;
        rr += (r + dist) / 100;
        r = n;
      }
    }
    return rr;
  }

  public static void main(String[] args) {
    (new Aoc2501()).start();
  }

  @Override
  String getTestInput() {
    return "L68\n" +
        "L30\n" +
        "R48\n" +
        "L5\n" +
        "R60\n" +
        "L55\n" +
        "L1\n" +
        "L99\n" +
        "R14\n" +
        "L82";
  }

  @Override
  String getRealInput() {
    return "L68\n" +
        "L30\n" +
        "R48\n" +
        "L5\n" +
        "R60\n" +
        "L55\n" +
        "L1\n" +
        "L99\n" +
        "R14\n" +
        "L82";
  }

  @Override
  long getTestExpected1() {
    return 3;
  }

  @Override
  long getTestExpected2() {
    return 6;
  }
}
