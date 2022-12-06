package aoc2021;

public class Aoc2102 {
    public static void main(String[] args) {
        var input = getInput();
        var y = 0;
        var x = 0;
        var aim = 0;
        for (var s : input) {
            var st = s.split(" ");
            if (st[0].equals("forward")) {
                x += Integer.parseInt(st[1]);
                y += Integer.parseInt(st[1]) * aim;
            }
            if (st[0].equals("up")) {
                aim-= Integer.parseInt(st[1]);
            }
            if (st[0].equals("down")) {
                aim += Integer.parseInt(st[1]);
            }
        }
        System.out.println(x * y);
    }

    private static String[] getInput() {
        return ("forward 5\n" +
            "down 5\n" +
            "forward 8\n" +
            "up 3\n" +
            "down 8\n" +
            "forward 2").split("\n");
    }
}
