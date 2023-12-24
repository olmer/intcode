package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parse {
  public static List<Integer> integers(String s) {
    return regex(s, "-?\\d+").stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static List<Double> doubles(String s) {
    return regex(s, "-?\\d+").stream().map(Double::valueOf).collect(Collectors.toList());
  }

  public static List<Long> longs(String s) {
    return regex(s, "-?\\d+").stream().map(Long::valueOf).collect(Collectors.toList());
  }

  public static List<Integer> digits(String s) {
    return regex(s, "-?\\d").stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static List<String> regex(String s, String p) {
    var pattern = Pattern.compile(p);
    var matcher = pattern.matcher(s);
    var res = new ArrayList<String>();
    while (matcher.find()) {
      res.add(matcher.group(0));
    }

    return res;
  }
  public static List<String> regexWithGroups(String s, String p) {
    var pattern = Pattern.compile(p);
    var matcher = pattern.matcher(s);
    var res = new ArrayList<String>();
    while (matcher.find()) {
      res.add(matcher.group(1));
    }

    return res;
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
