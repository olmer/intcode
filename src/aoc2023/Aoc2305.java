package aoc2023;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import tools.Pair;
import tools.Parse;
import tools.Range;

public class Aoc2305 {
  static private final boolean TEST = true;

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    System.out.println("Part 1 result: " + part1());
    System.out.println("Part 2 result: " + part2());
  }

  private static long part1() throws ExecutionException, InterruptedException {
    var input = getInput();
    var seeds = Parse.longs(input[0]);
    List<List<List<Long>>> maps = Arrays.stream(input).map(e -> Arrays.stream(e.split("\n")).map(Parse::longs).collect(Collectors.toList())).collect(Collectors.toList());
    maps.remove(0);

    return seeds.stream().map(e -> mapSeed(e, maps)).min(Long::compareTo).get();
  }

  private static long mapSeed(long seed, List<List<List<Long>>> maps) {
    long cur = seed;
    for (List<List<Long>> map : maps) {
      for (List<Long> range : map) {
        if (range.isEmpty()) continue;
        var b = range.get(0);
        var a = range.get(1);
        var c = range.get(2);
        if (cur >= a && cur < a + c) {
          long dif = cur - a;
          cur = b + dif;
          break;
        }
      }
    }

    return cur;
  }

  private static long part2() {
    var input = getInput();
    var seeds = Parse.longs(input[0]);
    List<List<List<Long>>> maps = Arrays.stream(input).map(e -> Arrays.stream(e.split("\n")).map(Parse::longs).collect(Collectors.toList())).collect(Collectors.toList());
    maps.remove(0);

    Queue<Pair<Long, Long>> rangesToMap = new ArrayDeque<>();
    for (int seedIdx = 0; seedIdx < seeds.size(); seedIdx += 2) {
      int finalSeedIdx = seedIdx;
      rangesToMap.offer(new Pair<>(seeds.get(finalSeedIdx), seeds.get(finalSeedIdx) + seeds.get(finalSeedIdx + 1)));
    }

    for (List<List<Long>> map : maps) {
      List<Pair<Long, Long>> processedRanges = new ArrayList<>();
      while (!rangesToMap.isEmpty()) {
        Pair<Long, Long> range = rangesToMap.poll();
        boolean splitHappened = false;
        for (var mapLine : map) {
          if (mapLine.isEmpty()) continue;
          var destinationDiff = mapLine.get(0) - mapLine.get(1);
          var mapInterval = new Pair<>(mapLine.get(1), mapLine.get(1) + mapLine.get(2) - 1);

          var intersect = Range.intersect(range, mapInterval);
          if (intersect.isEmpty()) {
            continue;
          }
          splitHappened = true;
          // match to map range happened, map and save
          processedRanges.add(doMap(intersect.get(), destinationDiff));
          // shrink/diff curr range here, no mapping happen, push back to queue to process
          rangesToMap.addAll(Range.difference(range, mapInterval));
        }
        if (!splitHappened) {
          processedRanges.add(range);
        }
      }
      rangesToMap.addAll(processedRanges);
    }
    return rangesToMap.stream().min(Comparator.comparingLong(Pair::getKey)).get().getKey();
  }

  private static Pair<Long, Long> doMap(Pair<Long, Long> prev, long diff) {
    return new Pair<>(prev.getKey() + diff, prev.getValue() + diff);
  }

  private static String[] getInput() {
    String testStr = "seeds: 79 14 55 13\n" +
      "\n" +
      "seed-to-soil map:\n" +
      "50 98 2\n" +
      "52 50 48\n" +
      "\n" +
      "soil-to-fertilizer map:\n" +
      "0 15 37\n" +
      "37 52 2\n" +
      "39 0 15\n" +
      "\n" +
      "fertilizer-to-water map:\n" +
      "49 53 8\n" +
      "0 11 42\n" +
      "42 0 7\n" +
      "57 7 4\n" +
      "\n" +
      "water-to-light map:\n" +
      "88 18 7\n" +
      "18 25 70\n" +
      "\n" +
      "light-to-temperature map:\n" +
      "45 77 23\n" +
      "81 45 19\n" +
      "68 64 13\n" +
      "\n" +
      "temperature-to-humidity map:\n" +
      "0 69 1\n" +
      "1 0 69\n" +
      "\n" +
      "humidity-to-location map:\n" +
      "60 56 37\n" +
      "56 93 4";
    String realStr = "";
    return (TEST ? testStr : realStr).split("\n\n");
  }
}
