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
        var fs = 0;
        var ss = 0;

        for (var i = 0; i < input.size(); i++) {
            ss += input.get(i);
            if (i < 2) continue;
            if (i == 2) {
                fs = ss;
                continue;
            }
            ss -= input.get(i - 3);
            if (ss > fs) count++;
            fs = ss;
        }

        System.out.println(count);
    }
}
