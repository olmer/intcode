package aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aoc2014 {
    public static void main(String[] args) {
        var raw = ("mask = 000000000000000000000000000000X1001X\n" +
            "mem[42] = 100\n" +
            "mask = 00000000000000000000000000000000X0XX\n" +
            "mem[26] = 1").split("\\n");

        Map<Long, Long> result = new HashMap<>();

        var mask = "";

        for (var command : raw) {
            if (command.startsWith("mask = ")) {
                mask = command.replace("mask = ", "");
            } else {
                List<String> addressMasks = new ArrayList<>();

                var t = command.split(" = ");
                var address = t[0].substring(4, t[0].length() - 1);
                address = Long.toBinaryString(Long.parseLong(address));
                address = ("000000000000000000000000000000000000" + address).substring(address.length());

                var value = Long.parseLong(t[1]);

                var maskedString = "000000000000000000000000000000000000";
                for (var i = 0; i < address.length(); i++) {
                    var a = address.charAt(i);
                    var b = mask.charAt(i);
                    if (b == 'X') {
                        char[] myNameChars = maskedString.toCharArray();
                        myNameChars[i] = 'X';
                        maskedString = String.valueOf(myNameChars);
                    } else if (a == '1' || b == '1') {
                        char[] myNameChars = maskedString.toCharArray();
                        myNameChars[i] = '1';
                        maskedString = String.valueOf(myNameChars);
                    }
                }
                addressMasks.add(maskedString);

                while (true) {
                    var xexists = false;
                    List<String> toRemove = new ArrayList<>();
                    List<String> toAdd = new ArrayList<>();

                    for (var entry : addressMasks) {
                        if (entry.indexOf('X') >= 0) {
                            toAdd.add(entry.replaceFirst("X", "0"));
                            toAdd.add(entry.replaceFirst("X", "1"));

                            toRemove.add(entry);

                            xexists = true;
                        }
                    }

                    for (var toremovee : toRemove) {
                        addressMasks.remove(toremovee);
                    }

                    addressMasks.addAll(toAdd);

                    if (!xexists) break;
                }

                for (var addressMask : addressMasks) {
                    result.put(binaryToInteger(addressMask), value);
                }
            }
        }

        System.out.println(result.values().stream().reduce(0L, Long::sum));
    }

    public static long binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        long result = 0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] == '1') {
                result += Math.pow(2, (numbers.length - i - 1));
            }
        }
        return result;
    }
}
