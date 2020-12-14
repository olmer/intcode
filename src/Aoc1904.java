import java.util.HashMap;

public class Aoc1904 {
    public static void main(String[] args) {
        var from = 246540;
        var to = 787419;

        var r = 0;

        for (var i = from; i <= to; i++) {
            var validinc = true;
            var t = String.valueOf(i);

            var samemap = new HashMap<Character, Integer>();

            for (var j = 1; j < t.length(); j++) {
                if (t.charAt(j - 1) == t.charAt(j)) {
                    if (!samemap.containsKey(t.charAt(j - 1))) {
                        samemap.put(t.charAt(j - 1), 1);
                    }
                    samemap.put(t.charAt(j - 1), samemap.get(t.charAt(j - 1)) + 1);
                }
                if (Short.parseShort(String.valueOf(t.charAt(j - 1))) > Short.parseShort(String.valueOf(t.charAt(j)))) {
                    validinc = false;
                }
            }
            var validsame = samemap.containsValue(2);
            if (validinc && validsame) r++;
            System.out.println(r);
        }

        System.out.println(r);
    }
}
