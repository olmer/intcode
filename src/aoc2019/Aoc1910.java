package aoc2019;

import java.util.HashSet;

//@todo: not done
public class Aoc1910 {
    public static void main(String[] args) {
        System.out.println(containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3));
        System.out.println(containsNearbyDuplicate(new int[]{1, 0, 1, 1}, 1));
        System.out.println(!containsNearbyDuplicate(new int[]{1, 2, 3, 1, 2, 3}, 2));
    }

    private static String getraw() {
        return ".#..#\n" +
            ".....\n" +
            "#####\n" +
            "....#\n" +
            "...##";
    }

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        var elementsWithinDistance = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                elementsWithinDistance.remove(nums[i - k - 1]);
            }
            if (!elementsWithinDistance.add(nums[i])) {
                return true;
            }
        }
        return false;
    }
}
