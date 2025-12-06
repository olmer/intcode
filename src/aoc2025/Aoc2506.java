package aoc2025;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Aoc2506 extends AbstractAoc {
  long part1(String[] in) {
    List<List<Integer>> data = new ArrayList<>();
    List<Character> operations = new ArrayList<>();
    for (String line : in) {
      if (line.contains("*") || line.contains("+")) {
        for (char c : line.toCharArray()) {
          if (c == '*' || c == '+') {
            operations.add(c);
          }
        }
      } else {
        List<Integer> row = new ArrayList<>();
        for (String numStr : line.trim().split("\\s+")) {
          row.add(Integer.parseInt(numStr));
        }
        data.add(row);
      }
    }

    BigInteger result = BigInteger.ZERO;

    for (int col = 0; col < data.get(0).size(); col++) {
      BigInteger colValue = BigInteger.valueOf(data.get(0).get(col));
      for (int row = 1; row < data.size(); row++) {
        char operation = operations.get(col);
        BigInteger nextValue = BigInteger.valueOf(data.get(row).get(col));
        if (operation == '*') {
          colValue = colValue.multiply(nextValue);
        } else if (operation == '+') {
          colValue = colValue.add(nextValue);
        }
      }
      result = result.add(colValue);
    }

    System.out.println("Final result: " + result);

    return Long.parseLong(result.toString());
  }

  long part2(String[] in) {
    long result = 0;

    char op = ' ';
    List<Long> nums = new ArrayList<>();
    for (int i = 0; i <= in[0].length(); i++) {
      boolean onlySpaces = true;
      long num = 0;
      if (i < in[0].length()) {
        for (String s : in) {
          char c = s.charAt(i);
          if (c != ' ') {
            onlySpaces = false;
            if (c == '*' || c == '+') {
              op = c;
            } else {
              num = num * 10 + (Character.getNumericValue(c));
            }
          }
        }
        if (num > 0) {
          nums.add(num);
        }
      }
      if (onlySpaces) {
        if (op == '*') {
          result += nums.stream().reduce(1L, (a, b) -> a * b);
        } else if (op == '+') {
          result += nums.stream().reduce(0L, Long::sum);
        } else {
          throw new IllegalStateException("No operation found for column " + i);
        }
        nums = new ArrayList<>();
      }
    }

    return result;
  }

  public static void main(String[] args) {
    (new Aoc2506()).start();
  }

  @Override
  String getTestInput() {
    return "123 328  51 64 \n" +
        " 45 64  387 23 \n" +
        "  6 98  215 314\n" +
        "*   +   *   +  ";
  }

  @Override
  String getRealInput() {
    return "123 328  51 64 \n" +
        " 45 64  387 23 \n" +
        "  6 98  215 314\n" +
        "*   +   *   +  ";
  }

  @Override
  long getTestExpected1() {
    return 4277556;
  }

  @Override
  long getTestExpected2() {
    return 3263827;
  }
}
