package aoc2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Aoc1504 {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = "ckczppom";

        for (var i = 1L; i < Long.MAX_VALUE; i++) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((password + i).getBytes());
            byte[] digest = md.digest();
            char[] hexChars = new char[digest.length * 2];

            for (int j = 0; j < digest.length; j++) {
                int v = digest[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }

            if (new String(hexChars).startsWith("000000")) {
                System.out.println(i);
                break;
            }
        }
    }

    private static String[] getInput() {
        return ("").split("\n");
    }
}
