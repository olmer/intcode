package aoc2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Aoc2103 {
    public static void main(String[] args) {
        var input = getInput();
        var input1 = getInput();
        var l = input.get(0).length();

        var r1 = 0;
        var r2 = 0;

        for (var i = 0; i < l; i++) {
            var s = 0;
            for (var str : input) {
                if (str.charAt(i) == '1') {
                    s++;
                } else {
                    s--;
                }
            }
            var nextChar1 = s >= 0 ? '1' : '0';

            int finalI = i;
            input = input.stream().filter(e -> e.charAt(finalI) == nextChar1).collect(Collectors.toList());
            if (input.size() == 1) {
                r1 = Integer.parseInt(input.get(0), 2);
                break;
            }
        }


        for (var i = 0; i < l; i++) {
            var s = 0;
            for (var str : input1) {
                if (str.charAt(i) == '0') {
                    s++;
                } else {
                    s--;
                }
            }
            var nextChar1 = s > 0 ? '1' : '0';

            int finalI = i;
            input1 = input1.stream().filter(e -> e.charAt(finalI) == nextChar1).collect(Collectors.toList());
            if (input1.size() == 1) {
                r2 = Integer.parseInt(input1.get(0), 2);
                break;
            }
        }

        System.out.println(r1 * r2);
    }

    private static List<String> getInput() {
        return Arrays.stream(("00100\n" +
            "11110\n" +
            "10110\n" +
            "10111\n" +
            "10101\n" +
            "01111\n" +
            "00111\n" +
            "11100\n" +
            "10000\n" +
            "11001\n" +
            "00010\n" +
            "01010").split("\n")).collect(Collectors.toList());
    }
}
