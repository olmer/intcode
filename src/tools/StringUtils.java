package tools;

public class StringUtils<T> {
  public static String padInteger(int n, int length) {
    return String.format("%1$" + length + "s ", n);
  }

  public static String toString(int[][] input, int pad) {
    StringBuilder r = new StringBuilder();
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        r.append(StringUtils.padInteger(input[i][j], pad));
      }
      r.append('\n');
    }
    return r.toString();
  }

  public static String toString(int[][] input) {
    return toString(input, 3);
  }

  public String toString(T[][] input, int padLength) {
    StringBuilder r = new StringBuilder();
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        r.append(padRight(input[i][j].toString(), padLength));
      }
      r.append('\n');
    }
    return r.toString();
  }

  public static String padRight(String s, int n) {
    return String.format("%-" + n + "s", s);
  }
}
