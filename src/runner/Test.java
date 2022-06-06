package runner;

import java.util.Arrays;

public class Test {
  public static void main(String[] args) {
    String[] input = new String[]{
      "she",
      "sells",
      "seashells",
      "by",
      "the",
      "seashore",
      "the",
      "shells",
      "she",
      "sells",
      "are",
      "surely",
      "seashells",
    };
    System.out.println(Arrays.toString(input));
    MSD.sort(input);
    System.out.println(Arrays.toString(input));
  }

  private static String printar(int[] a) {
    StringBuilder r = new StringBuilder();
    for (int i = 0; i < a.length; i++) {
      if (a[i] != 0)
        r.append((char)(i - 2) + ": " + a[i] + ", ");
    }
    return r.toString();
  }

  private static String[] getInput() {
    return ("").split("\n");
  }
}

class MSD {
  private static int R = 256;
  private static String[] aux;

  private static int charAt(String s, int d) {
    if (d < s.length()) return s.charAt(d); else return -1;
  }

  public static void sort(String[] a) {
    int N = a.length;
    aux = new String[N];
    sort(a, 0, N - 1, 0);
  }

  private static void sort(String[] a, int lo, int hi, int d) {
    int[] count = new int[R + 2];
    for (int i = lo; i <= hi; i++)
      count[charAt(a[i], d) + 2]++;

    for (int r = 0; r < R + 1; r++)
      count[r + 1] += count[r];

    for (int i = lo; i <= hi; i++)
      aux[count[charAt(a[i], d) + 1]++] = a[i];

    for (int i = lo; i <= hi; i++)
      a[i] = aux[i - lo];

    for (int r = 0; r < R; r++)
      sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
  }
}

class LSD {
  public static void sort(String[] a, int W) {
    int N = a.length;
    int R = 50;
    String[] aux = new String[N];
    for (int d = W - 1; d >= 0; d--) {
      int[] count = new int[R + 1];

      for (int i = 0; i < N; i++)
        count[a[i].charAt(d) - 'a' + 1]++;

      for (int r = 0; r < R; r++)
        count[r + 1] += count[r];

      for (int i = 0; i < N; i++)
        aux[count[a[i].charAt(d) - 'a']++] = a[i];

      for (int i = 0; i < N; i++)
        a[i] = aux[i];
    }
  }
}
