import java.util.HashMap;
import java.util.Map;

public class Aoc2121 {
    private static final Map<String, Pair> DP = new HashMap<>();

    static class Pair {
        long w1;
        long w2;

        Pair(long w1, long w2) {
            this.w1 = w1;
            this.w2 = w2;
        }
    }

    public static void main(String[] args) {
        var r = next(10, 6, 0, 0);

        System.out.println(Math.max(r.w1, r.w2));
    }

    private static Pair next(int pos1, int pos2, int score1, int score2) {
        if (score1 >= 21) return new Pair(1, 0);
        if (score2 >= 21) return new Pair(0, 1);
        var key = "" + pos1 + pos2 + score1 + score2;
        if (DP.containsKey(key)) return DP.get(key);

        var anw = new Pair(0, 0);

        for (var i = 1; i <= 3; i++) {
            for (var j = 1; j <= 3; j++) {
                for (var k = 1; k <= 3; k++) {
                    var newPos1 = (pos1 + i + j + k - 1) % 10 + 1;
                    var news1 = score1 + newPos1;
                    var r = next(pos2, newPos1, score2, news1);
                    anw.w1 += r.w2;
                    anw.w2 += r.w1;
                }
            }
        }

        DP.put(key, anw);

        return anw;
    }
}
