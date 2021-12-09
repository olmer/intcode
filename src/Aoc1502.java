public class Aoc1502 {
    public static void main(String[] args) {
        var r = 0;
        for (var gift : getInput()) {
            var h = Long.parseLong(gift.split("x")[0]);
            var w = Long.parseLong(gift.split("x")[1]);
            var l = Long.parseLong(gift.split("x")[2]);
            var res = new long[3];
            res[0] = 2 * h + 2 * w;
            res[1] = 2 * h + 2 * l;
            res[2] = 2 * l + 2 * w;

            var min = Math.min(res[0], Math.min(res[1], res[2]));
            r += min + h * w * l;
        }

        System.out.println(r);
    }

    private static String[] getInput() {
        return ("2xw3x4\n1x1x10").split("\n");
    }
}
