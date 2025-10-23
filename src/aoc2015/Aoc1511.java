package aoc2015;

public class Aoc1511 extends AbstractAoc {
  long part1(String[] in) {return 1;
  }

  long part2(String[] in) {
    return 2;
  }

  public static void main(String[] args) {
    (new Aoc1511()).start();
  }

  @Override
  String getTestInput() {
    return "3   4\n" +
        "4   3\n" +
        "2   5\n" +
        "1   3\n" +
        "3   9\n" +
        "3   3";
  }

  @Override
  String getRealInput() {
    return "3   4\n" +
        "4   3\n" +
        "2   5\n" +
        "1   3\n" +
        "3   9\n" +
        "3   3";
  }

  @Override
  long getTestExpected1() {
    return 1;
  }

  @Override
  long getTestExpected2() {
    return 2;
  }
}
