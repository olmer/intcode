package aoc2025;

public class Aoc2502 extends AbstractAoc {
  long part1(String[] in) {
    String[] parts = in[0].split(",");
    long r = 0;
    for (String part : parts) {
      String[] range = part.split("-");
      long start = Long.parseLong(range[0]);
      long end = Long.parseLong(range[1]);
      for (long i = start; i <= end; i++) {
        int l = numOfCharacters(i);
        long left = i / (long) Math.pow(10, l / 2);
        long right = i % (long) Math.pow(10, l / 2);
        if (left == right) {
          r += i;
        }
      }
    }
    return r;
  }

  long part2(String[] in) {
    String[] parts = in[0].split(",");
    long r = 0;
    for (String part : parts) {
      String[] range = part.split("-");
      long start = Long.parseLong(range[0]);
      long end = Long.parseLong(range[1]);
      for (long i = start; i <= end; i++) {
        String s = Long.toString(i);
        int len = s.length();
        int halflen = len / 2;
        boolean idIsInvalid = false;
        for (int curlen = 1; curlen <= halflen; curlen++) {
          if (len % curlen != 0) {
            continue;
          }
          int sequences = len / curlen;
          String pattern = s.substring(0, curlen);
            boolean allMatch = true;
          for (int seq = 1; seq < sequences; seq++) {
            String nextPattern = s.substring(seq * curlen, (seq + 1) * curlen);
            if (!nextPattern.equals(pattern)) {
              allMatch = false;
              break;
            }
          }
          if (allMatch) {
            idIsInvalid = true;
            break;
          }
        }
        if (idIsInvalid) {
          r += i;
        }
      }
    }
    return r;
  }

  int numOfCharacters(long n) {
    int r = 0;
    while (n > 0) {
      r++;
      n /= 10;
    }
    return r;
  }

  public static void main(String[] args) {
    (new Aoc2502()).start();
  }

  @Override
  String getTestInput() {
    return "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
        "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
        "824824821-824824827,2121212118-2121212124";
  }

  @Override
  String getRealInput() {
    return "11-22";
  }

  @Override
  long getTestExpected1() {
    return 0;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }
}
