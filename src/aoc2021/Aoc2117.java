package aoc2021;

import java.util.ArrayList;

public class Aoc2117 {
    public static void main(String[] args) {
        var axa = getInput()[0];
        var axb = getInput()[1];
        var yAreaLowest = getInput()[2];
        var yAreaHighest = getInput()[3];

        var possibleYs = new ArrayList<Integer>();

        for (var i = -1000; i < 1000; i++) {
            var yVelocity = i;
            var yCurrent = 0;
            while (true) {
                yCurrent += yVelocity;
                yVelocity--;
                if (yCurrent >= yAreaLowest && yCurrent <= yAreaHighest) {
                    possibleYs.add(i);
                    break;
                }
                if (yCurrent < yAreaLowest) {
                    break;
                }
            }
        }

        var r = 0;

        for (var yVelocityPoss : possibleYs) {
            for (var i = 0; i < 1000; i++) {
                var xVelocity = i;
                var yVelocity = yVelocityPoss;
                var xCurrent = 0;
                var yCurrent = 0;
                while (true) {
                    xCurrent += xVelocity;
                    yCurrent += yVelocity;
                    if (xVelocity > 0) xVelocity--;
                    yVelocity--;
                    if (yCurrent >= yAreaLowest && yCurrent <= yAreaHighest && xCurrent >= axa && xCurrent <= axb) {
                        r++;
                        break;
                    }
                    if (yCurrent < yAreaLowest || xCurrent > axb) {
                        break;
                    }
                }
            }
        }


        System.out.println(r);
    }

    private static int[] getInput() {
        return new int[] {138, 184, -125, -71};
    }
}
