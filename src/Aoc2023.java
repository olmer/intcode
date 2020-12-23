import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Aoc2023 {
    public static void main(String[] args) {
        var moves = 10_000_000;
        var maxNo = 1_000_000;

        var in2 = Arrays.stream("193467258".split("")).mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
        in2.addAll(IntStream.rangeClosed(10, maxNo)
            .boxed().collect(Collectors.toList()));

        El[] cups = new El[maxNo + 1];

        El prev = null;
        El first = null;
        El last = null;
        for (var t : in2) {
            var tt = new El(t);
            cups[t] = tt;
            if (prev != null) {
                prev.setNext(tt);
                tt.setPrev(prev);
            } else {
                first = tt;
            }
            prev = tt;
            last = tt;
        }

        assert last != null;
        last.setNext(first);
        first.setPrev(last);

        var cur = first;

        for (var i = 0; i < moves; i++) {
            var a = cur.next();
            var b = a.next();
            var c = b.next();

//            var CUR_STATE = print(cur);
//            var CUR_STATE_EL = cur.value();
//            var CUR_STATE_ABC = String.valueOf(a.value()) + b.value() + c.value();

            a.prev().setNext(c.next());
            c.next().setPrev(a.prev());

            a.setPrev(null);
            c.setNext(null);

            var d = cups[getDestination(cur, maxNo, a, b, c)];

//            var CUR_D = d.value();

            d.next().setPrev(c);
            c.setNext(d.next());

            d.setNext(a);
            a.setPrev(d);
            cur = cur.next();
        }

        var b = cups[1];
        System.out.println(b.next().value() * b.next().next().value());
    }

    private static int getDestination(El cur, int maxNo, El a, El b, El c) {
        var nextN = (int)(cur.value() - 1);
        while (true) {
            if (nextN <= 0) {
                nextN = maxNo;
            }
            if (nextN != a.value() && nextN != b.value() && nextN != c.value()) {
                return nextN;
            }

            nextN--;
        }
    }

    private static String print(El e) {
        var r = new StringBuilder();
        var c = e;
        do {
            r.append(c.value()).append(", ");
            c = c.next();
        } while (!c.equals(e));
        return r.toString();
    }

    static class El {
        private El next;
        private El prev;
        private long value;

        public El(long value) {
            this.value = value;
        }

        public El(El next, El prev, long value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        public El next() {
            return next;
        }

        public void setNext(El next) {
            this.next = next;
        }

        public El prev() {
            return prev;
        }

        public void setPrev(El prev) {
            this.prev = prev;
        }

        public long value() {
            return value;
        }
    }
}
