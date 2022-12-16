package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import aoc2022.Pair;

public class Parse {
  public static List<Integer> integers(String s) {
    var pattern = Pattern.compile("-?\\d+");
    var matcher = pattern.matcher(s);
    var digits = new ArrayList<Integer>();
    while (matcher.find()) {
      digits.add(Integer.valueOf(matcher.group()));
    }

    return digits;
  }

  public static List<Long> longs(String s) {
    var pattern = Pattern.compile("-?\\d+");
    var matcher = pattern.matcher(s);
    var digits = new ArrayList<Long>();
    while (matcher.find()) {
      digits.add(Long.valueOf(matcher.group()));
    }

    return digits;
  }

  public static Map<String, String> map(String s) {
    return mapDelimited(s, ":", "\n");
  }

  public static Map<String, String> mapDelimited(String s, String keyValueDel, String propertiesDel) {
    var map = new HashMap<String, String>();
    for (String line : s.split(propertiesDel)) {
      var ln = Arrays.stream(line.split(keyValueDel)).map(e -> e.trim()).toList();
      if (map.containsKey(ln.get(0).trim())) {
        System.out.println("Warning: mapping contained duplicate keys: " + ln.get(0));
      }
      map.put(ln.get(0).trim(), ln.size() < 2 ? null : ln.get(1).trim());
    }

    return map;
  }

  public static List<Pair<Integer, Integer>> ranges(String s) {
    return rangesDelimited(s, ",");
  }

  public static List<Pair<Integer, Integer>> rangesDelimited(String s, String del) {
    List<Pair<Integer, Integer>> r = new ArrayList<>();
    for (String ss : s.split(del)) {
      var sss = Arrays.stream(ss.split("-")).map(Integer::valueOf).toList();
      r.add(new Pair<>(sss.get(0), sss.get(1)));
    }
    return r;
  }
}
