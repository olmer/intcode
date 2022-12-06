package aoc2021;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Aoc2106 {
    public static void main(String[] args) {
        var days = 256;

        var fishes = new long[9];

        for (var tt : Arrays.stream(getInput()).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList())) {
            fishes[tt]++;
        }

        for (var day = 0; day < days; day++) {
            var bornFishes = 0L;
            for (var i = 0; i < 8; i++) {
                if (i == 0) {
                    bornFishes = fishes[i];
                }
                fishes[i] = fishes[i + 1];
            }
            fishes[8] = bornFishes;
            fishes[6] += bornFishes;
        }
        System.out.println(Arrays.stream(fishes).sum());
    }

    private static String[] getInput() {
        return ("3,5,3,5,1,3,1,1,5,5,1,1,1,2,2,2,3,1,1,5,1,1,5,5,3,2,2,5,4,4,1,5,1,4,4,5,2,4,1,1,5,3,1,1,4,1,1,1,1,4,1,1,1,1,2,1,1,4,1,1,1,2,3,5,5,1,1,3,1,4,1,3,4,5,1,4,5,1,1,4,1,3,1,5,1,2,1,1,2,1,4,1,1,1,4,4,3,1,1,1,1,1,4,1,4,5,2,1,4,5,4,1,1,1,2,2,1,4,4,1,1,4,1,1,1,2,3,4,2,4,1,1,5,4,2,1,5,1,1,5,1,2,1,1,1,5,5,2,1,4,3,1,2,2,4,1,2,1,1,5,1,3,2,4,3,1,4,3,1,2,1,1,1,1,1,4,3,3,1,3,1,1,5,1,1,1,1,3,3,1,3,5,1,5,5,2,1,2,1,4,2,3,4,1,4,2,4,2,5,3,4,3,5,1,2,1,1,4,1,3,5,1,4,1,2,4,3,1,5,1,1,2,2,4,2,3,1,1,1,5,2,1,4,1,1,1,4,1,3,3,2,4,1,4,2,5,1,5,2,1,4,1,3,1,2,5,5,4,1,2,3,3,2,2,1,3,3,1,4,4,1,1,4,1,1,5,1,2,4,2,1,4,1,1,4,3,5,1,2,1").split(",");
    }
}
