import java.util.HashSet;
import java.util.Set;

public class Aoc2203 {
  public static void main(String[] args) {
    int r = 0;

    String[] inp = getInput();

    for (int j = 0; j < inp.length; j += 3) {
      Set<Integer> set1 = getSetFromString(inp[j]);
      Set<Integer> set2 = getSetFromString(inp[j + 1]);
      Set<Integer> set3 = getSetFromString(inp[j + 2]);
      set1.retainAll(set2);
      set1.retainAll(set3);

      r += set1.stream().findFirst().orElseThrow();
    }

    System.out.println(r);
  }

  private static Set<Integer> getSetFromString(String s) {
    Set<Integer> set = new HashSet<>();

    for (int i = 0; i < s.length(); i++) {
      int ii = s.charAt(i) - 'a' + 1;
      if (ii < 0)
        ii = s.charAt(i) - 'A' + 27;

      set.add(ii);
    }

    return set;
  }

  private static String[] getInput() {
    String s = "vJrwpWtwJgWrhcsFMMfFFhFp\n" +
      "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n" +
      "PmmdzqPrVvPwwTWBwg\n" +
      "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\n" +
      "ttgJtRGJQctTZtZT\n" +
      "CrZsJsPPZsGzwwsLwLmpwMDw";
    return s.split("\n");
  }
}
