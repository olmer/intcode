import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Aoc2022 {
    public static void main(String[] args) {
        var raw = Arrays.stream(getraw().split("\\n\\n")).map(e -> e.split("\\n")).toArray(String[][]::new);

        LinkedList<Integer> fp = new LinkedList<>();
        LinkedList<Integer> sp = new LinkedList<>();

        for (var i = 1; i < raw[0].length; i++) {
            fp.add(Integer.parseInt(raw[0][i]));
        }

        for (var i = 1; i < raw[1].length; i++) {
            sp.add(Integer.parseInt(raw[1][i]));
        }

        Queue<Integer> winner;

        var wnr = subgame(fp, sp);
        if (wnr) {
            winner = fp;
        } else {
            winner = sp;
        }

        var r = 0L;

        var i = winner.size();
        System.out.println(winner);
        while (i > 0) {
            var p = winner.poll();
            r += (p * i);
            i--;
        }
        System.out.println(r);
    }

    private static boolean subgame(LinkedList<Integer> fp, LinkedList<Integer> sp) {
        Set<String> cache = new HashSet<>();

        while (true) {

            var a = fp.peek();
            if (a == null) {
                return false;
            }
            var b = sp.peek();
            if (b == null) {
                return true;
            }

            var cs = fp.toString() + sp.toString();
            if (cache.contains(cs)) {
                return true;
            }

            cache.add(cs);

            a = fp.poll();
            b = sp.poll();

            boolean wnr;
            if (fp.size() >= a && sp.size() >= b) {
                wnr = subgame(new LinkedList<>(fp.subList(0, a)), new LinkedList<>(sp.subList(0, b)));
            } else {
                wnr = a > b;
            }

            if (wnr) {
                fp.add(a);
                fp.add(b);
            } else {
                sp.add(b);
                sp.add(a);
            }
        }
    }

    private static String getraw() {
        return "Player 1:\n" +
            "25\n" +
            "37\n" +
            "35\n" +
            "16\n" +
            "9\n" +
            "26\n" +
            "17\n" +
            "5\n" +
            "47\n" +
            "32\n" +
            "11\n" +
            "43\n" +
            "40\n" +
            "15\n" +
            "7\n" +
            "19\n" +
            "36\n" +
            "20\n" +
            "50\n" +
            "3\n" +
            "21\n" +
            "34\n" +
            "44\n" +
            "18\n" +
            "22\n" +
            "\n" +
            "Player 2:\n" +
            "12\n" +
            "1\n" +
            "27\n" +
            "41\n" +
            "4\n" +
            "39\n" +
            "13\n" +
            "29\n" +
            "38\n" +
            "2\n" +
            "33\n" +
            "28\n" +
            "10\n" +
            "6\n" +
            "24\n" +
            "31\n" +
            "42\n" +
            "8\n" +
            "23\n" +
            "45\n" +
            "46\n" +
            "48\n" +
            "49\n" +
            "30\n" +
            "14";
    }
}
