package aoc2025;

public abstract class AbstractAoc {
  abstract long part1(String[] in);

  abstract long part2(String[] in);

  abstract String getTestInput();

  abstract String getRealInput();

  abstract long getTestExpected1();

  abstract long getTestExpected2();

  public void start() {
    try {
      test();
      System.out.println(part1(getInput(false)));
      System.out.println(part2(getInput(false)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void test() {
    var p1 = part1(getInput(true));
    System.out.println("Part 1 test: " + p1 + (p1 == getTestExpected1() ? " PASSED" : " FAILED"));

    var p2 = part2(getInput(true));
    System.out.println("Part 2 test: " + p2 + (p2 == getTestExpected2() ? " PASSED" : " FAILED"));
  }

  private String[] getInput(boolean isTest) {
    return (isTest ? this.getTestInput() : getRealInput()).split("\n");
  }
}
