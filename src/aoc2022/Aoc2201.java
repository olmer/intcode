package aoc2022;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Aoc2201 {
    public static void main(String[] args) {
        PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
        for (String ii : getInput()) {
            String[] cals = ii.split("\n");
            int cur = 0;
            for (String cal : cals) {
                cur+=Integer.parseInt(cal);
            }
            q.add(cur);
        }
        System.out.println(q.poll() + q.poll() + q.poll());
    }

    private static String[] getInput() {
        return ("1000\n" +
          "2000\n" +
          "3000\n" +
          "\n" +
          "4000\n" +
          "\n" +
          "5000\n" +
          "6000\n" +
          "\n" +
          "7000\n" +
          "8000\n" +
          "9000\n" +
          "\n" +
          "10000").split("\n\n");
    }
}
