package aoc2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tools.Parse;

public class Aoc2417 extends AbstractAoc {
  long part1(String[] in) {
    String inin = Arrays.stream(in).collect(Collectors.joining("\n"));
    int A;
    String regs = inin.split("\n\n")[0];
    A = Parse.integers(regs.split("\n")[0]).get(0);

    List<Long> p = Parse.longs(inin.split("\n\n")[1]);
    System.out.println(execute(A, p));
    return 0;
  }

  long success = Long.MAX_VALUE;

  /*
  b = a % 8           // take lower 3 bits of a
  b = b ^ 3           // inverts lower 2 bits of b
  c = a / (2 ** b)    // remove lower B bits of A and copy to C
  a = a / (2 ** b)    // remove lower 3 bits of A
  b = c ^ b           // XORs last b bits of c
  b = b ^ 5 ( 101 )   // inverts first and last bits of 3 bits of b
  output lower 3 bits of b
  jump to 0 if a != 0
   */
  long part2(String[] in) {
    long start = System.currentTimeMillis();
    String inin = Arrays.stream(in).collect(Collectors.joining("\n"));
    List<Long> program = Parse.longs(inin.split("\n\n")[1]);
    backtrack(program, 0, 0);
    System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
    return success;
  }

  void backtrack(List<Long> program, long cur, int pos) {
    for (int i = 0; i < 8; i++) {
      long nextNum = (cur << 3) + i;
      List<Long> execResult = execute(nextNum, program);
      if (!execResult.equals(program.subList(program.size() - pos - 1, program.size()))) {
        continue;
      }
      if (pos == program.size() - 1) {
        success = Math.min(success, nextNum);
        return;
      }
      backtrack(program, nextNum, pos + 1);
    }
  }

  List<Long> execute(long a, List<Long> p) {
    long A, B, C;
    int pointer = 0;
    List<Long> output = new ArrayList<>();
    A = a;
    B = 0;
    C = 0;
    while (pointer >= 0 && pointer < p.size()) {
      long opcode = p.get(pointer);
      long literalOperand = p.get(pointer + 1);
      long combo = literalOperand < 4 ? literalOperand : (literalOperand == 4 ? A : (literalOperand == 5 ? B : C));
      switch ((int) opcode) {
        case 0:
          A = (long) (A / Math.pow(2, combo));
          pointer += 2;
          break;
        case 1:
          B = B ^ literalOperand;
          pointer += 2;
          break;
        case 2:
          B = combo % 8;
          pointer += 2;
          break;
        case 3:
          if (A != 0) {
            pointer = (int) literalOperand;
          } else {
            pointer += 2;
          }
          break;
        case 4:
          B ^= C;
          pointer += 2;
          break;
        case 5:
          output.add(combo % 8);
          pointer += 2;
          break;
        case 6:
          B = (long) (A / Math.pow(2, combo));
          pointer += 2;
          break;
        case 7:
          C = (long) (A / Math.pow(2, combo));
          pointer += 2;
          break;
      }
    }

    return output;
  }

  public static void main(String[] args) {
    (new Aoc2417()).start();
  }

  @Override
  long getTestExpected1() {
    return 0;
  }

  @Override
  long getTestExpected2() {
    return 117440;
  }

  @Override
  String getTestInput() {
    return "Register A: 2024\n" +
        "Register B: 0\n" +
        "Register C: 0\n" +
        "\n" +
        "Program: 0,3,5,4,3,0";
  }

  @Override
  String getRealInput() {
    return "Register A: 2024\n" +
        "Register B: 0\n" +
        "Register C: 0\n" +
        "\n" +
        "Program: 0,3,5,4,3,0";
  }
}
