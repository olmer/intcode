package aoc2020;

import java.time.Duration;
import java.time.Instant;

public class Aoc2013 {
    public static void main(String[] args) {
        var raw = "23,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,383,x,x,x,x,x,x,x,x,x,x,x,x,13,17,x,x,x,x,19,x,x,x,x,x,x,x,x,x,29,x,503,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,37".split(",");

        Instant start = Instant.now();

        var increment = Long.parseLong(raw[0]);
        var answer = 0L;
        var intermediateAnswer = 0L;

        for (var busOffset = 1; busOffset < raw.length; busOffset++) {
            if (raw[busOffset].equals("x")) continue;
            var currentBus = Long.parseLong(raw[busOffset]);
            intermediateAnswer = increment;
            while ((intermediateAnswer + answer + busOffset) % currentBus != 0) {
                intermediateAnswer += increment;
            }
            increment = lcm(increment, currentBus);
            answer += intermediateAnswer;
        }

        System.out.println(answer);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }

    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        var absNumber1 = Math.abs(number1);
        var absNumber2 = Math.abs(number2);
        var absHigherNumber = Math.max(absNumber1, absNumber2);
        var absLowerNumber = Math.min(absNumber1, absNumber2);
        var lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}
