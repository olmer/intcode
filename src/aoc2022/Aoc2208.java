package aoc2022;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Aoc2208 {
  private static int[] scoresOfVector(int[] arr, int[] scores, int from, int to, int direction) {
    Stack<Integer> stack = new Stack<>();
    for (int i = from; i != to; i += direction) {
      int distance;
      while (!stack.isEmpty() && arr[stack.peek()] < arr[i])
        stack.pop();
      if (stack.isEmpty())
        distance = Math.abs(i - from);
      else
        distance = Math.abs(i - stack.peek());
      scores[i] *= distance;
      stack.push(i);
    }
    return scores;
  }
  private static int[][] scoresDirectional(List<List<Integer>> input, boolean direction) {
    int n = input.size();
    int[][] scores = new int[n][n];
    for (int i = 0; i < n; i++) {
      Arrays.fill(scores[i], 1);
    }

    for (int i = 0; i < n; i++) {
      int[] temp = new int[n];
      for (int j = 0; j < n; j++) {
        temp[j] = input.get(direction ? i : j).get(direction ? j : i);
      }

      //distances left to right
      scores[i] = scoresOfVector(temp, scores[i], 0, n, 1);
      //distances right to left
      scores[i] = scoresOfVector(temp, scores[i], n - 1, -1, -1);
    }
    return scores;
  }

  public static void main(String[] args) throws Exception {
    var input = getInput();
    int n = input.size();

    int[][] scoresHorizontal = scoresDirectional(input, true);
    int[][] scoresVertical = scoresDirectional(input, false);

    int result = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        result = Math.max(result, scoresHorizontal[i][j] * scoresVertical[j][i]);
      }
    }

    System.out.println(result);
  }

  private static List<List<Integer>> getInput() {
    String s = "30373\n" +
      "25512\n" +
      "65332\n" +
      "33549\n" +
      "35390";
    return Arrays.stream(s.split("\n")).map(e -> Arrays.stream(e.split("")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
  }
}
