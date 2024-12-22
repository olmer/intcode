package aoc2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Aoc2422 extends AbstractAoc {
  long part1(String[] in) {
    long r = 0;
    for (String s : in) {
      long result = Integer.parseInt(s);
      for (int i = 0; i < 2000; i++) {
        result = step(result);
      }
      r += result;
    }
    return r;
  }

  long part2(String[] in) {
    List<List<Integer>> sequences = new ArrayList<>();
    List<List<Integer>> prices = new ArrayList<>();
    for (String buyer : in) {
      long result = Integer.parseInt(buyer);
      List<Integer> seq = new ArrayList<>();
      List<Integer> price = new ArrayList<>();
      int prev = (int) (result % 10);
      for (int i = 0; i < 2000; i++) {
        result = step(result);
        int diff = (int) ((result % 10) - prev);
        seq.add(diff);
        price.add((int) (result % 10));
        prev = (int) (result % 10);
      }
      sequences.add(seq);
      prices.add(price);
    }

    Set<List<Integer>> uniqueSequences = new HashSet<>();
    for (List<Integer> seq : sequences) {
      for (int i = 4; i < seq.size(); i++) {
        uniqueSequences.add(seq.subList(i - 4, i));
      }
    }
    long r = 0;
    int c = 0;
    System.out.println("uniqueSequences.size() = " + uniqueSequences.size());
    for (List<Integer> seq : uniqueSequences) {
      if (c++ % 100 == 0)
        System.out.println("seq " + c);
      long bananas = 0;
      for (int t = 0; t < sequences.size(); t++) {
        List<Integer> buyerSeq = sequences.get(t);
        for (int i = 4; i < buyerSeq.size(); i++) {
          if (buyerSeq.subList(i - 4, i).equals(seq)) {
            bananas += prices.get(t).get(i - 1);
            break;
          }
        }
      }
      r = Math.max(r, bananas);
    }
    return r;
  }

  long step(long result) {
    //step1
    result = mix(result * 64, result);
    result = prune(result);
    //step2
    result = mix(result / 32, result);
    result = prune(result);
    //step3
    result = mix(result * 2048, result);
    result = prune(result);
    return result;
  }

  long mix(long a, long b) {
    return a ^ b;
  }

  long prune(long a) {
    return a % 16777216;
  }

  public static void main(String[] args) {
    (new Aoc2422()).start();
  }

  @Override
  long getTestExpected1() {
    return 0;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }

  @Override
  String getTestInput() {
    return "1\n" +
        "2\n" +
        "3\n" +
        "2024";
  }

  @Override
  String getRealInput() {
    return "1\n" +
        "2\n" +
        "3\n" +
        "2024";
  }
}
