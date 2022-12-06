package aoc2022;

import java.util.stream.Collectors;

public class Aoc2206 {

  public static void main(String[] args) {
    String in = getInput();
    int n = 4;
    for (int i = 0; i < in.length() - n + 1; i++) {
      if (in.substring(i, i + n).chars().mapToObj(e -> (char)e).collect(Collectors.toSet()).size() == n)
        System.out.println(i + n);
    }
  }

  private static String getInput() {
    return "bvwbjplbgvbhsrlpgdmjqwftvncz";
  }
}
