package aoc2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import tools.Pair;
import tools.Parse;

public class Aoc2421 extends AbstractAoc {
  /*
+---+---+---+
| 7 | 8 | 9 |
+---+---+---+
| 4 | 5 | 6 |
+---+---+---+
| 1 | 2 | 3 |
+---+---+---+
    | 0 | A |
    +---+---+

    +---+---+
    | ^ | A |
+---+---+---+
| < | v | > |
+---+---+---+
*/
  Map<String, Map<Integer, Long>> cache = new HashMap<>();

  static Map<Character, Pair<Integer, Integer>> numpad = "789456123 0A".chars()
      .mapToObj(c -> (char) c)
      .collect(HashMap::new, (m, c) -> m.put(c, new Pair<>(m.size() % 3, m.size() / 3)), HashMap::putAll);
  static Map<Character, Pair<Integer, Integer>> dirPad = " ^A<v>".chars()
      .mapToObj(c -> (char) c)
      .collect(HashMap::new, (m, c) -> m.put(c, new Pair<>(m.size() % 3, m.size() / 3)), HashMap::putAll);

  static List<String> moveList = List.of(
      "A", "A", "A",
      "A", "^", "<A",
      "A", ">", "vA",
      "A", "v", "v<A",
      "A", "<", "v<<A",
      "^", "^", "A",
      "^", "A", ">A",
      "^", ">", "v>A",
      "^", "v", "vA",
      "^", "<", "v<A",
      ">", ">", "A",
      ">", "A", "^A",
      ">", "^", "<^A",
      ">", "v", "<A",
      ">", "<", "<<A",
      "v", "v", "A",
      "v", "A", ">^A",
      "v", ">", ">A",
      "v", "^", "^A",
      "v", "<", "<A",
      "<", "<", "A",
      "<", "A", ">>^A",
      "<", ">", ">>A",
      "<", "v", ">A",
      "<", "^", ">^A"
  );
  static Map<Pair<Character, Character>, String> moves = new HashMap<>();

  List<List<String>> numPadPaths(String num, char start) {
    List<List<String>> res = new ArrayList<>();
    char pos = start;
    for (char c : num.toCharArray()) {
      Pair<Integer, Integer> p = numpad.get(pos);
      Pair<Integer, Integer> q = numpad.get(c);
      int dx = q.getKey() - p.getKey();
      int dy = q.getValue() - p.getValue();
      StringBuilder sb1 = new StringBuilder();
      while (dy != 0) {
        if (dy > 0) {
          sb1.append('v');
          dy--;
        } else {
          sb1.append('^');
          dy++;
        }
      }
      while (dx != 0) {
        if (dx > 0) {
          sb1.append('>');
          dx--;
        } else {
          sb1.append('<');
          dx++;
        }
      }

      String ssb1 = sb1.toString();
      char finalPos = pos;
      List<String> paths = permute("", ssb1).stream()
          .filter(s -> {
            int col = numpad.get(finalPos).getKey();
            int row = numpad.get(finalPos).getValue();
            for (char cc : s.toCharArray()) {
              switch (cc) {
                case '^' -> row--;
                case 'v' -> row++;
                case '<' -> col--;
                case '>' -> col++;
              }
              if (col == 0 && row == 3) {
                return false;
              }
            }
            return true;
          })
          .map(s -> new StringBuilder(s).append('A').toString()).collect(Collectors.toList());

      res.add(paths);
      pos = c;
    }
    return res;
  }

  Set<String> permute(String prefix, String remaining) {
    if (remaining.isEmpty()) {
      return new HashSet<>() {{
        add(prefix);
      }};
    } else {
      Set<String> res = new HashSet<>();
      for (int i = 0; i < remaining.length(); i++) {
        res.addAll(permute(
            prefix + remaining.charAt(i),
            remaining.substring(0, i) + remaining.substring(i + 1)
        ));
      }
      return res;
    }
  }

  List<List<String>> allPossiblePaths(String alreadyValidMove, char start) {
    List<List<String>> result = new ArrayList<>();
    char pos = start;
    for (char c : alreadyValidMove.toCharArray()) {
      List<String> validMoves = new ArrayList<>();
      String possibleMove = moves.get(new Pair<>(pos, c));
      Set<String> permuted = permute("", possibleMove);
      permuted.removeIf(ss -> ss.charAt(ss.length() - 1) != 'A');
      for (String maybeInvalidMove : permuted) {
        // skip invalid move (that goes over an empty space)
        int col = dirPad.get(pos).getKey();
        int row = dirPad.get(pos).getValue();
        boolean invalid = false;
        for (char cc : maybeInvalidMove.toCharArray()) {
          switch (cc) {
            case '^' -> row--;
            case 'v' -> row++;
            case '<' -> col--;
            case '>' -> col++;
          }
          if (col == 0 && row == 0) {
            invalid = true;
            break;
          }
        }
        if (invalid) {
          continue;
        }
        validMoves.add(maybeInvalidMove);
      }
      result.add(validMoves);
      pos = c;
    }
    return result;
  }

  long calculateLength(String move, int depth) {
    if (depth == 0) {
      return move.length();
    }
    if (cache.containsKey(move) && cache.get(move).containsKey(depth)) {
      return cache.get(move).get(depth);
    }
    long best = Long.MAX_VALUE;
    List<List<String>> sss = allPossiblePaths(move, 'A');
    List<List<String>> allCombs = new ArrayList<>();
    generateCombinations(sss, 0, new ArrayList<>(), allCombs);
    for (List<String> comb : allCombs) {
      long sum = 0;
      for (String s : comb) {
        sum += calculateLength(s, depth - 1);
      }
      best = Math.min(best, sum);
    }
    if (!cache.containsKey(move)) {
      cache.put(move, new HashMap<>());
    }
    cache.get(move).put(depth, best);
    return best;
  }

  long part1(String[] in) {
    cache.clear();
    // init moves
    for (int i = 0; i < moveList.size(); i += 3) {
      moves.put(new Pair<>(moveList.get(i).charAt(0), moveList.get(i + 1).charAt(0)), moveList.get(i + 2));
    }

    long r = 0;
    for (String s : in) {
      List<List<String>> dirs1 = numPadPaths(s, 'A');
      Set<String> possiblePaths = combinationsOfNumPad(dirs1);
      long best = Long.MAX_VALUE;

      for (String path : possiblePaths) {// check all permutations
        long sum = 0;
        for (String dirs : splitByA(path)) {
          long len = calculateLength(dirs, 2);
          sum += len;
        }
        best = Math.min(best, sum);
      }
//      System.out.println("Num: " + Parse.integers(s).get(0) + " best: " + best);
      r += Parse.integers(s).get(0) * best;
    }
    return r;
  }

  long part2(String[] in) {
    cache.clear();
    // init moves
    for (int i = 0; i < moveList.size(); i += 3) {
      moves.put(new Pair<>(moveList.get(i).charAt(0), moveList.get(i + 1).charAt(0)), moveList.get(i + 2));
    }

    long r = 0;
    for (String s : in) {
      List<List<String>> dirs1 = numPadPaths(s, 'A');
      Set<String> possiblePaths = combinationsOfNumPad(dirs1);
      long best = Long.MAX_VALUE;

      for (String path : possiblePaths) {// check all permutations
        long sum = 0;
        for (String dirs : splitByA(path)) {
          long len = calculateLength(dirs, 25);
          sum += len;
        }
        best = Math.min(best, sum);
      }
//      System.out.println("Num: " + Parse.integers(s).get(0) + " best: " + best);
      r += Parse.integers(s).get(0) * best;
    }
    return r;
  }

  List<String> splitByA(String dirs) {
    List<String> separatedDirs = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < dirs.length(); i++) {
      if (dirs.charAt(i) != 'A') {
        sb.append(dirs.charAt(i));
      } else {
        sb.append('A');
        separatedDirs.add(sb.toString());
        sb = new StringBuilder();
      }
    }
    return separatedDirs;
  }

  void generateCombinations(List<List<String>> lists, int depth, List<String> current, List<List<String>> result) {
    if (depth == lists.size()) {
      result.add(new ArrayList<>(current));
      return;
    }

    for (String s : lists.get(depth)) {
      current.add(s);
      generateCombinations(lists, depth + 1, current, result);
      current.removeLast();
    }
  }

  Set<String> combinationsOfNumPad(List<List<String>> dirs) {
    Set<String> res = new HashSet<>();
    for (int i = 0; i < dirs.get(0).size(); i++) {
      for (int j = 0; j < dirs.get(1).size(); j++) {
        for (int k = 0; k < dirs.get(2).size(); k++) {
          for (int l = 0; l < dirs.get(3).size(); l++) {
            res.add(dirs.get(0).get(i) + dirs.get(1).get(j) + dirs.get(2).get(k) + dirs.get(3).get(l));
          }
        }
      }
    }
    return res;
  }

  public static void main(String[] args) {
    (new Aoc2421()).start();
  }

  @Override
  long getTestExpected1() {
    return 126384;
  }

  @Override
  long getTestExpected2() {
    return 154115708116294L;
  }

  @Override
  String getTestInput() {
    return "029A\n" +
        "980A\n" +
        "179A\n" +
        "456A\n" +
        "379A";
  }

  @Override
  String getRealInput() {
    return "029A\n" +
        "980A\n" +
        "179A\n" +
        "456A\n" +
        "379A";
  }
}
