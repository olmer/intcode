package aoc2020;

public class Aoc2025 {
    public static void main(String[] args) {
        long start = 1;
        long puba = 4206373;
        long pubb = 15113849;
        long sbj = 7;
        long i;
        long loopa = 0;
        long loopb = 0;
        for (i = 1; i < 30_000_000; i++) {
            start *= sbj;
            start %= 20201227;
            if (start == puba) {
                loopa = i;
            }
            if (start == pubb) {
                loopb = i;
            }
        }

        start = 1;
        for (i = 0; i < loopb; i++) {
            start *= puba;
            start %= 20201227;
        }
        System.out.println(start);

        start = 1;
        for (i = 0; i < loopa; i++) {
            start *= pubb;
            start %= 20201227;
        }
        System.out.println(start);
    }
}