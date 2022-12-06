package aoc2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aoc2108 {
    public static void main(String[] args) {
        var input = getInput2();
        var r = 0;

        for (String row : input) {
            var resultDisplay = row.split(" \\| ")[1];
            var allDigits = row.split("(\\s\\|\\s|\\s)");

            var finalMap = new HashMap<Character, Character>();

            var digsByLength = new HashMap<Integer, List<Character>>();
            for (var dig : allDigits) {
                var ll = dig.length();
                if (!digsByLength.containsKey(ll)) {
                    digsByLength.put(ll, new ArrayList<>());
                }
                digsByLength.get(ll).addAll(dig.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()));
            }

            var aa = digsByLength.get(3).stream().filter(e -> !digsByLength.get(2).contains(e)).findFirst().get();

            finalMap.put(aa, 'a');

            var tmp = digsByLength.get(4)
                .stream()
                .filter(e -> !digsByLength.get(2).contains(e))
                .collect(Collectors.toList());

            var bb = 'b';
            var dd = 'd';
            for (var dig : allDigits) {
                if (dig.length() == 5) {
                    if (!dig.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()).contains(tmp.get(0))) {
                        bb = tmp.get(0);
                        dd = tmp.get(1);
                        break;
                    }
                    if (!dig.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()).contains(tmp.get(1))) {
                        bb = tmp.get(1);
                        dd = tmp.get(0);
                        break;
                    }
                }
            }

            finalMap.put(bb, 'b');
            finalMap.put(dd, 'd');

            var cc = 'c';
            var ff = 'd';
            for (var dig : allDigits) {
                if (dig.length() == 6) {
                    if (!dig.chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toSet())
                        .contains(digsByLength.get(2).get(0))) {
                        cc = digsByLength.get(2).get(0);
                        ff = digsByLength.get(2).get(1);
                        break;
                    }
                    if (!dig.chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toSet())
                        .contains(digsByLength.get(2).get(1))) {
                        cc = digsByLength.get(2).get(1);
                        ff = digsByLength.get(2).get(0);
                        break;
                    }
                }
            }

            finalMap.put(cc, 'c');
            finalMap.put(ff, 'f');

            //non-existent for 5 dig = e

            var ee = 'e';
            var gg = 'g';
            tmp = Stream.of('a', 'b', 'c', 'd', 'e', 'f', 'g')
                .filter(e -> !finalMap.containsKey(e))
                .collect(Collectors.toList());
            for (var dig : allDigits) {
                if (dig.length() == 5) {
                    if (!dig.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()).contains(tmp.get(0))) {
                        ee = tmp.get(0);
                        gg = tmp.get(1);
                        break;
                    }
                    if (!dig.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()).contains(tmp.get(1))) {
                        ee = tmp.get(1);
                        gg = tmp.get(0);
                        break;
                    }
                }
            }

            finalMap.put(ee, 'e');
            finalMap.put(gg, 'g');

            var segmentsToInt = new HashMap<Set<Character>, Integer>() {{
                put(Set.of('a', 'b', 'c', 'e', 'f', 'g'), 0);
                put(Set.of('c', 'f'), 1);
                put(Set.of('a', 'c', 'd', 'e', 'g'), 2);
                put(Set.of('a', 'c', 'd', 'f', 'g'), 3);
                put(Set.of('b', 'c', 'd', 'f'), 4);
                put(Set.of('a', 'b', 'd', 'f', 'g'), 5);
                put(Set.of('a', 'b', 'd', 'e', 'f', 'g'), 6);
                put(Set.of('a', 'c', 'f'), 7);
                put(Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g'), 8);
                put(Set.of('a', 'b', 'c', 'd', 'f', 'g'), 9);
            }};

            var tmpr = 0;
            var ii = 3;
            for (var rdpl : resultDisplay.split(" ")) {
                var rset = rdpl.chars().mapToObj(e -> finalMap.get((char) e)).collect(Collectors.toSet());
                tmpr += segmentsToInt.get(rset) * Math.pow(10, ii);
                ii--;
            }

            r += tmpr;
        }
        System.out.println(r);
    }

    private static String[] getInput2() {
        return ("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe\n" +
            "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc\n" +
            "fgaebd cg bdaec gdafb wagbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg\n" +
            "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb\n" +
            "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea\n" +
            "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb\n" +
            "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe\n" +
            "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef\n" +
            "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb\n" +
            "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce").split("\n");
    }
}
