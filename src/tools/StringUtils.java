package tools;

public class StringUtils {
  public static String padInteger(int n, int length) {
    return String.format("%1$" + length + "s ", n);
  }
}
