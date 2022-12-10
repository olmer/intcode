package aoc2022;

public class Aoc2210 {
  public static void main(String[] args) throws Exception {
    var input = getInput(true);

    int cycle = 1;
    int register = 1;
    int comWidth;

    for (String in : input) {
      String com = in.split(" ")[0];
      comWidth = com.equals("addx") ? 2 : 1;
      while (comWidth > 0) {
        if (cycle % 40 == 1)
          System.out.println();
        System.out.print(Math.abs(register - cycle % 40 + 1) > 1 ? "." : "#");
        cycle++;
        comWidth--;
      }
      if (com.equals("addx"))
        register += Integer.parseInt(in.split(" ")[1]);
    }
  }

  private static String[] getInput(boolean dry) {
    String test = "addx 15\n" +
      "addx -11\n" +
      "addx 6\n" +
      "addx -3\n" +
      "addx 5\n" +
      "addx -1\n" +
      "addx -8\n" +
      "addx 13\n" +
      "addx 4\n" +
      "noop\n" +
      "addx -1\n" +
      "addx 5\n" +
      "addx -1\n" +
      "addx 5\n" +
      "addx -1\n" +
      "addx 5\n" +
      "addx -1\n" +
      "addx 5\n" +
      "addx -1\n" +
      "addx -35\n" +
      "addx 1\n" +
      "addx 24\n" +
      "addx -19\n" +
      "addx 1\n" +
      "addx 16\n" +
      "addx -11\n" +
      "noop\n" +
      "noop\n" +
      "addx 21\n" +
      "addx -15\n" +
      "noop\n" +
      "noop\n" +
      "addx -3\n" +
      "addx 9\n" +
      "addx 1\n" +
      "addx -3\n" +
      "addx 8\n" +
      "addx 1\n" +
      "addx 5\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "addx -36\n" +
      "noop\n" +
      "addx 1\n" +
      "addx 7\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "addx 2\n" +
      "addx 6\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "addx 1\n" +
      "noop\n" +
      "noop\n" +
      "addx 7\n" +
      "addx 1\n" +
      "noop\n" +
      "addx -13\n" +
      "addx 13\n" +
      "addx 7\n" +
      "noop\n" +
      "addx 1\n" +
      "addx -33\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "addx 2\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "addx 8\n" +
      "noop\n" +
      "addx -1\n" +
      "addx 2\n" +
      "addx 1\n" +
      "noop\n" +
      "addx 17\n" +
      "addx -9\n" +
      "addx 1\n" +
      "addx 1\n" +
      "addx -3\n" +
      "addx 11\n" +
      "noop\n" +
      "noop\n" +
      "addx 1\n" +
      "noop\n" +
      "addx 1\n" +
      "noop\n" +
      "noop\n" +
      "addx -13\n" +
      "addx -19\n" +
      "addx 1\n" +
      "addx 3\n" +
      "addx 26\n" +
      "addx -30\n" +
      "addx 12\n" +
      "addx -1\n" +
      "addx 3\n" +
      "addx 1\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "addx -9\n" +
      "addx 18\n" +
      "addx 1\n" +
      "addx 2\n" +
      "noop\n" +
      "noop\n" +
      "addx 9\n" +
      "noop\n" +
      "noop\n" +
      "noop\n" +
      "addx -1\n" +
      "addx 2\n" +
      "addx -37\n" +
      "addx 1\n" +
      "addx 3\n" +
      "noop\n" +
      "addx 15\n" +
      "addx -21\n" +
      "addx 22\n" +
      "addx -6\n" +
      "addx 1\n" +
      "noop\n" +
      "addx 2\n" +
      "addx 1\n" +
      "noop\n" +
      "addx -10\n" +
      "noop\n" +
      "noop\n" +
      "addx 20\n" +
      "addx 1\n" +
      "addx 2\n" +
      "addx 2\n" +
      "addx -6\n" +
      "addx -11\n" +
      "noop\n" +
      "noop\n" +
      "noop";

    String real = "";

    return (dry ? test : real).split("\n");
  }
}
