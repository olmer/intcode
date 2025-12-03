package aoc2025;

public class Aoc2503 extends AbstractAoc {
  long part1(String[] in) {
    long rr = 0;
    for (String s : in) {
      int resultLength = 2;
      long numberBuilder = 0;
      int left = 0;
      while (resultLength-- > 0) {
        int right = s.length() - resultLength;
        int maxVal = 0;
        int maxPos = -1;
        //search for max value in applicable range
        for (int i = left; i < right; i++) {
          int val = Character.getNumericValue(s.charAt(i));
          if (val > maxVal) {
            maxVal = val;
            maxPos = i;
          }
        }
        numberBuilder = numberBuilder * 10 + maxVal;
        left = maxPos + 1;
      }
      rr += numberBuilder;
    }
    return rr;
  }

  long part2(String[] in) {
    long rr = 0;
    for (String s : in) {
      int resultLength = 12;
      long numberBuilder = 0;
      int left = 0;
      while (resultLength-- > 0) {
        int right = s.length() - resultLength;
        int maxVal = 0;
        int maxPos = -1;
        //search for max value in applicable range
        for (int i = left; i < right; i++) {
          int val = Character.getNumericValue(s.charAt(i));
          if (val > maxVal) {
            maxVal = val;
            maxPos = i;
          }
        }
        numberBuilder = numberBuilder * 10 + maxVal;
        left = maxPos + 1;
      }
      rr += numberBuilder;
    }
    return rr;
  }

  public static void main(String[] args) {
    (new Aoc2503()).start();
  }

  @Override
  String getTestInput() {
    return "987654321111111\n" +
        "811111111111119\n" +
        "234234234234278\n" +
        "818181911112111";
  }

  @Override
  String getRealInput() {
    return "987654321111111\n" +
        "811111111111119\n" +
        "234234234234278\n" +
        "818181911112111";
  }

  @Override
  long getTestExpected1() {
    return 357;
  }

  @Override
  long getTestExpected2() {
    return 3121910778619L;
  }
}
