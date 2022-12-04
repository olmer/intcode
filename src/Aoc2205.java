import java.util.Arrays;
import java.util.stream.Collectors;

public class Aoc2205 {
  public static void main(String[] args) {
    int r = 0;

    for (var s : getInput()) {
      var ss = s.split(",");
      var ss1 = Arrays.stream(ss[0].split("-")).map(Integer::valueOf).collect(Collectors.toList());
      var ss2 = Arrays.stream(ss[1].split("-")).map(Integer::valueOf).collect(Collectors.toList());

      if (ss2.get(0) <= ss1.get(1) && ss2.get(0) >= ss1.get(0) || ss1.get(0) <= ss2.get(1) && ss1.get(0) >= ss2.get(0))
        r++;

    }

    System.out.println(r);
  }

  private static String[] getInput() {
    String s = "2-4,6-8\n" +
      "2-3,4-5\n" +
      "5-7,7-9\n" +
      "2-8,3-7\n" +
      "6-6,4-6\n" +
      "2-6,4-8";
    return s.split("\n");
  }
}
