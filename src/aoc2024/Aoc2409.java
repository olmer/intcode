package aoc2024;

import java.util.ArrayList;
import java.util.List;

public class Aoc2409 extends AbstractAoc {
  long part1(String[] in) {
    int[] arr = new int[in[0].length()];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = in[0].charAt(i) - '0';
    }

    long result = 0;

    int left = 0, right = arr.length - 1;

    boolean isEmpty = false;

    long i = 0;
    long idLeft = 0;
    long idRight = arr.length / 2;

    while (left <= right) {
      if (!isEmpty) {
        while (arr[left] > 0) {
          result += idLeft * i++;
          arr[left]--;
        }
        idLeft++;
        left++;
        isEmpty = true;
      } else {
        while (arr[left] > 0) {
          result += idRight * i++;
          arr[left]--;
          arr[right]--;
          if (arr[right] == 0) {
            idRight--;
            right -= 2;
          }
        }
        left++;
        isEmpty = false;
      }
    }

    return result;
  }

  long part2(String[] in) {
    List<Integer> lengths = new ArrayList<>();
    int totalLength = 0;
    for (int i = 0; i < in[0].length(); i++) {
      int l = in[0].charAt(i) - '0';
      lengths.add(l);
      totalLength += l;
    }
    List<Integer> ids = new ArrayList<>();
    for (int i = 0; i < lengths.size(); i++) {
      if (i % 2 == 0) {
        ids.add(i / 2);
      } else {
        ids.add(-1);
      }
    }

    int right = lengths.size() - 1;
    while (right > 0) {
      if (ids.get(right) == -1) {
        right--;
        continue;
      }
      int left = 0;
      while (left < right) {
        if (ids.get(left) == -1 && lengths.get(left) >= lengths.get(right)) {
          ids.set(left, ids.get(right));
          ids.set(right, -1);
          // check if we need to split
          if (lengths.get(left) > lengths.get(right)) {
            int dif = lengths.get(left) - lengths.get(right);
            lengths.set(left, lengths.get(right));
            lengths.add(left + 1, dif);
            ids.add(left + 1, -1);
            right++;
          }
          break;
        }
        left++;
      }
      right--;
    }

    long result = 0;

    int t = 0;
    for (int i = 0; i < totalLength; ) {
      int l = lengths.get(t);
      while (l > 0) {
        result += (ids.get(t) < 0 ? 0 : ids.get(t)) * i++;
        l--;
      }
      t++;
    }

    return result;
  }

  public static void main(String[] args) {
    (new Aoc2409()).start();
  }

  @Override
  long getTestExpected1() {
    return 1928;
  }

  @Override
  long getTestExpected2() {
    return 2858;
  }

  @Override
  String getTestInput() {
    return "2333133121414131402";
  }

  @Override
  String getRealInput() {
    return "2333133121414131402";
  }
}
