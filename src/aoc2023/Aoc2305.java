package aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import tools.Pair;
import tools.Parse;
import tools.Range;

public class Aoc2305 {
  static private final boolean TEST = true;

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    System.out.println("Part 1 result: " + part1());
    System.out.println("Part 2 result: " + part2WithRanges());
  }

  private static long findBestSeed(Pair<Long, Long> seeds, List<List<List<Long>>> maps) {
    long rrr = Long.MAX_VALUE;

    for (long seed = seeds.getKey(); seed < seeds.getValue(); seed++) {

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
      if (cur < rrr) {
        System.out.println("Current min: " + cur);
        rrr = cur;
      }
    }

    return rrr;
  }

  private static long part1() throws ExecutionException, InterruptedException {
    var input = getInput();
    var seeds = Parse.longs(input[0]);
    List<List<List<Long>>> maps = Arrays.stream(input).map(e -> Arrays.stream(e.split("\n")).map(Parse::longs).collect(Collectors.toList())).collect(Collectors.toList());
    maps.remove(0);

    var seedPairs = new ArrayList<Pair<Long, Long>>();
    for (int ii = 0; ii < seeds.size(); ii += 2) {
      long from = seeds.get(ii);
      long to = seeds.get(ii) + seeds.get(ii + 1);
      seedPairs.add(new Pair<>(from, to));
    }

    // ExecutorService for parallel execution
    ExecutorService executor = Executors.newFixedThreadPool(seedPairs.size());

    // List of CompletableFutures for the async tasks
    List<CompletableFuture<Long>> futures = seedPairs.stream()
      .map(chunk -> CompletableFuture.supplyAsync(() -> findBestSeed(chunk, maps), executor))
      .toList();

    // Combine the CompletableFutures into a single CompletableFuture
    CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

    // Perform an action when all tasks are completed
    return allOf.thenApply(voidResult -> {
      // Get the minimum value from the completed tasks
      long minResult = futures.stream()
        .map(CompletableFuture::join)
        .min(Long::compareTo)
        .orElseThrow(() -> new RuntimeException("No results"));

      System.out.println("Minimum Result: " + minResult);

      // Shutdown the executor
      executor.shutdown();

      return minResult;
    }).join();
  }

  private static long part2WithRanges() {
    var input = getInput();
    var seeds = Parse.longs(input[0]);
    List<List<List<Long>>> maps = Arrays.stream(input).map(e -> Arrays.stream(e.split("\n")).map(Parse::longs).collect(Collectors.toList())).collect(Collectors.toList());
    maps.remove(0);

    for (int seedIdx = 0; seedIdx < seeds.size(); seedIdx += 2) {
      int finalSeedIdx = seedIdx;
      Set<Set<Pair<Long, Long>>> ranges = new HashSet<>();
      ranges.add(new TreeSet<>(Comparator.comparingLong(Pair::getKey)) {{
        add(new Pair<>(seeds.get(finalSeedIdx), seeds.get(finalSeedIdx) + seeds.get(finalSeedIdx + 1)));
      }});

      for (List<List<Long>> map : maps) {
        for (Set<Pair<Long, Long>> rangel : ranges) {
          Set<Pair<Long, Long>> splittedRange = new TreeSet<>(Comparator.comparingLong(Pair::getKey));
          boolean splitHappened = false;
          for (var mapLine : map) {
            if (mapLine.isEmpty()) continue;
            var destinationDiff = mapLine.get(0) - mapLine.get(1);
            var mapInterval = new Pair<>(mapLine.get(1), mapLine.get(1) + mapLine.get(2) - 1);
            for (var range : rangel) {
              var intersect = Range.intersect(range, mapInterval);
              if (intersect.isEmpty()) {
                continue;
              }
              splitHappened = true;
              // match to map range happened, map and save
              var ttt = doMap(intersect.get(), destinationDiff);
              if (ttt.getValue() == 23738615) {
                System.out.println("Adding weird range");
              }
              splittedRange.add(ttt);
              // shrink/diff curr range here, no mapping happen
              var tttt = Range.difference(range, mapInterval);
              if (tttt.size() > 1 && (tttt.get(0).getValue() == 23738615 || tttt.get(1).getValue() == 23738615)) {
                System.out.println("Adding weird range");
              }
              splittedRange.addAll(tttt);
            }
          }
          if (!splitHappened) {
            splittedRange.addAll(rangel);
          }
          rangel.clear();
          rangel.addAll(splittedRange);
        }
        var rrrrr = 2;
      }
      var rrrr = 2;
    }
    var rrr = 2;
    return 0L;
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
