import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Aoc2122 {
    static class Cuboid {
        List<Cuboid> vacuums = new ArrayList<>();
        long[] bounds;

        Cuboid(long[] bounds) {
            this.bounds = bounds;
        }

        void remove(long[] bounds) throws Exception {
            var intersection = intersectBounds(this.bounds, bounds);
            if (intersection.length == 0) {
                return;
            }
            for (var vacuum : vacuums) {
                vacuum.remove(intersection);
            }
            vacuums.add(new Cuboid(intersection));
        }

        long volume() {
            var tv = (bounds[1] - bounds[0] + 1) * (bounds[3] - bounds[2] + 1) * (bounds[5] - bounds[4] + 1);
            var sumofvacuumns  = vacuums.stream().mapToLong(Cuboid::volume).sum();
            return tv - sumofvacuumns;
        }

        long[] intersectRanges(long r11, long r12, long r21, long r22) throws Exception {
            if (r12 < r11 || r22 < r21) throw new Exception();
            if (r21  > r12 || r11 > r22) return new long[0];
            var d = new long[]{r11, r12, r21, r22};
            Arrays.sort(d);
            return new long[]{d[1], d[2]};
        }

        long[] intersectBounds(long[] boundsA, long[] boundsB) throws Exception {
            var x = intersectRanges(boundsA[0], boundsA[1], boundsB[0], boundsB[1]);
            var y = intersectRanges(boundsA[2], boundsA[3], boundsB[2], boundsB[3]);
            var z = intersectRanges(boundsA[4], boundsA[5], boundsB[4], boundsB[5]);
            if (x.length == 0 || y.length == 0 || z.length == 0) return new long[0];
            return new long[]{x[0], x[1], y[0], y[1], z[0], z[1]};
        }

    }

    public static void main(String[] args) throws Exception {
        var cuboids = new ArrayList<Cuboid>();
        for (var inline : getInput()) {
            var pattern = Pattern.compile("-?\\d+");
            var matcher = pattern.matcher(inline);
            var digits = new ArrayList<Long>();
            while (matcher.find()) {
                digits.add(Long.valueOf(matcher.group()));
            }
            var bounds = new long[]{digits.get(0), digits.get(1), digits.get(2), digits.get(3), digits.get(4), digits.get(5)};
            for (var cuboid : cuboids) {
                cuboid.remove(bounds);
            }
            if (inline.startsWith("on")) {
                cuboids.add(new Cuboid(bounds));
            }
        }
        System.out.println(cuboids.stream().mapToLong(Cuboid::volume).sum());
    }

    private static String[] getInput() {
        return ("on x=10..12,y=10..12,z=10..12\n" +
            "on x=11..13,y=11..13,z=11..13\n" +
            "off x=9..11,y=9..11,z=9..11\n" +
            "on x=10..10,y=10..10,z=10..10").split("\n");
    }
}
