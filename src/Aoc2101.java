import java.util.Arrays;
import java.util.stream.Collectors;

public class Aoc2101 {
    public static void main(String[] args) {
        var input = Arrays.stream(("199\n" +
            "200\n" +
            "208\n" +
            "210\n" +
            "200\n" +
            "207\n" +
            "240\n" +
            "269\n" +
            "260\n" +
            "263").split("\n")).map(Integer::valueOf).collect(Collectors.toList());

        var count = 0;
        var firstSum = 0;
        var secondSum = 0;

        for (var i = 0; i < input.size(); i++) {
            secondSum += input.get(i);
            if (i < 2) continue;
            if (i == 2) {
                firstSum = secondSum;
                continue;
            }
            secondSum -= input.get(i - 3);
            if (secondSum > firstSum) count++;
            firstSum = secondSum;
        }

        System.out.println(count);
    }
}
