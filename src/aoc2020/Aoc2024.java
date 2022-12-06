package aoc2020;

import java.util.HashMap;
import java.util.Map;

public class Aoc2024 {
    public static void main(String[] args) {
        Map<String, int[]> map = new HashMap<>();
        map.put("nw", new int[]{-1, 1});
        map.put("ne", new int[]{0, 1});
        map.put("e", new int[]{1, 0});
        map.put("se", new int[]{1, -1});
        map.put("sw", new int[]{0, -1});
        map.put("w", new int[]{-1, 0});

        var raw = getraw().split("\\n");

        var floorStates = new byte[1000][1000];

        for (var com : raw) {
            var x = 0;
            var y = 0;
            for (var i = 0; i < com.length(); i++) {
                String t = "";
                if (i != com.length() - 1) {
                    t = com.substring(i, i + 2);
                }
                if (!map.containsKey(t)) {
                    t = com.substring(i, i + 1);
                } else {
                    i++;
                }
                var dir = map.get(t);

                x += dir[0];
                y += dir[1];
            }

            floorStates[500 + y][500 + x] ^= 1;
        }

        var conway = new Conway2D(floorStates, 2000, 2000);

        for (var i = 0; i < 100; i++) {
            conway.iterate();
        }

        System.out.println(conway.countActive());
    }

    static class Conway2D {

        private final int width;
        private final int height;

        byte[][] data;

        public Conway2D(byte[][] initialData, int width, int height) {
            this.width = width;
            this.height = height;

            this.data = new byte[height][width];

            var ioff = height / 2;
            for (var i = 0; i < initialData.length; i++) {
                var joff = width / 2;
                System.arraycopy(initialData[i], 0, this.data[ioff + i], joff, initialData[i].length);
            }
        }

        public void iterate() {
            var newState = deepCopy(data);

            for (var j = 1; j < height - 1; j++) {
                for (var k = 1; k < width - 1; k++) {
                    var aliveNeigh = countNeighbours(j, k);
                    if (data[j][k] == 1) {
                        if (aliveNeigh == 0 || aliveNeigh > 2) {
                            newState[j][k] = 0;
                        }
                    } else {
                        if (aliveNeigh == 2) {
                            newState[j][k] = 1;
                        }
                    }
                }
            }

            data = newState;
        }

        public int countActive() {
            var count = 0;
            for (var j = 0; j < height; j++) {
                for (var k = 0; k < width; k++) {
                    if (data[j][k] == 1) {
                        count++;
                    }
                }
            }

            return count;
        }

        private int countNeighbours(int y, int x) {
            var count = 0;

            /*
        map.put("nw", new int[]{-1, 1});
        map.put("ne", new int[]{0, 1});
        map.put("e", new int[]{1, 0});
        map.put("se", new int[]{1, -1});
        map.put("sw", new int[]{0, -1});
        map.put("w", new int[]{-1, 0});
             */

            if (data[y - 1][x + 1] == 1) {
                count++;
            }
            if (data[y][x + 1] == 1) {
                count++;
            }
            if (data[y + 1][x] == 1) {
                count++;
            }
            if (data[y + 1][x - 1] == 1) {
                count++;
            }
            if (data[y][x - 1] == 1) {
                count++;
            }
            if (data[y - 1][x] == 1) {
                count++;
            }

            return count;
        }

        byte[][] deepCopy(byte[][] matrix) {
            return java.util.Arrays.stream(matrix).map(byte[]::clone).toArray($ -> matrix.clone());
        }
    }

    private static String getraw() {
        return "sweswseeeseseneeeeeeenwnweswe\n" +
            "neswswsenwseeswsesesenwseseeseseseseswnw\n" +
            "swswswnwwneeseneswseseneeswswseswswsenw\n" +
            "eeswwnwnwwnwnwnwwnwswnwnwnwnweswnw\n" +
            "sewwwnwwewswewswnewnwwnenesw\n" +
            "swswswwnesesweeswewwnewnewswswsw\n" +
            "nwswswnweneswswwwswswswswseswswwswsesw\n" +
            "nweneweneswswsenwseswnwwsesewnwwe\n" +
            "senwewseneswwswwsesesesenwseseesenee\n" +
            "sesenwseseseneseeseswswswweesewwse\n" +
            "swswsewswwneswseswneseswswseswswneswswsw\n" +
            "swswnwwwswswnwswsweswswswswswnweswsese\n" +
            "swnenwneeeneneswswnenewneswnwnenwneene\n" +
            "swseswswswseneeseswswswwswwsesw\n" +
            "swnwneenewweeenwenenenesenesenene\n" +
            "seseeweeeweeseeeneeeweee\n" +
            "enweseseseswseswsesesesesenewsenwsesese\n" +
            "neseswsweswswseswswswwswswnwsw\n" +
            "neneeswneswnenwneneneeewneswneenee\n" +
            "nenwwnenwseneenenwnenwenwswneneswnenwenw\n" +
            "seseseseseswseneswsesesese\n" +
            "seeseewnwewwnwwswnwswnweeweww\n" +
            "sweseseseswswseswsewsesesenwsene\n" +
            "sesenwwesweseseeseseeneseneeesewse\n" +
            "nwneneseswnenenenwne\n" +
            "nweeeswenweeeswwenweswwneeese\n" +
            "nwseswnesenwswswsewseswsenweneseeswswe\n" +
            "swseswswneewwnwweswwsenwwwwsww\n" +
            "seeseeweeeesweseneeeeenwsesw\n" +
            "swswswsweswswnwswsw\n" +
            "wwswswnwwswswwnwswneseseneseswnweesw\n" +
            "nenenwnwnweswnenwwneswneseswnwswnwnee\n" +
            "swswswswswswsweswswswswsww\n" +
            "sesesesenwsewenwsesewsesesenesese\n" +
            "seeneeeweswnenenwnwseeneseenwesw\n" +
            "wnenwnwwsweneswsewsesenenenewswnwnw\n" +
            "enwseswwnwseswswnewnwseseeseneswesew\n" +
            "neneneneenenewneenenenenewne\n" +
            "sesewewnweeeeeswe\n" +
            "wwewswwwwwewwww\n" +
            "wnesesesenewsenewsee\n" +
            "seseseenwseweseswsesesesesesenewnwse\n" +
            "swnesesewseswseswswswseswnwswsweswwsw\n" +
            "wwneswswseseswwneswnewswswswewswsww\n" +
            "swsewewnwnwswwesw\n" +
            "neeeenweneseneneeene\n" +
            "seseswnwwsewswwesesenwseswneeneenwnw\n" +
            "eesweeeseneenwneneenewewnwsenw\n" +
            "seseenwnwswswswswswneswseseswwsesenwsw\n" +
            "eeneseeeseeseseseneseesewwswsese\n" +
            "eeseseeswewnwenw\n" +
            "wwwwswseswwwwswswnw\n" +
            "swswneseswswwswswneswsewseswwswseswswswne\n" +
            "eeneeneneeneeeswe\n" +
            "wsesesewseseeesenweneseeswsesenese\n" +
            "nenwenenenenewnenweenesesesenewnenee\n" +
            "eseneeewenweeseweeswneeseneenenw\n" +
            "wswwwwswwwwne\n" +
            "nenwseseswswwsesesesesenwseeseswswsese\n" +
            "nenwsenwnenwnwwnwnwnwnwsenenwnenesenwnw\n" +
            "wseweneeswnwnwsweewswsesesesesese\n" +
            "seseseseswsweswseswseswswswnwnwsese\n" +
            "nwwswenwenenwswnene\n" +
            "neweeweeeeeneeseeeeeswee\n" +
            "nenewneweneneneneneneeeneswnenesenesw\n" +
            "nesewnwwneswnwswneseneseenesweeswe\n" +
            "nwseseneswsewseneswswewnenwseew\n" +
            "swswnwwwwswswswwneseswwswewneww\n" +
            "neeneeneseneesweeneweneenee\n" +
            "seseswswswswswsesesesesenwsee\n" +
            "wnewwwswwnwseneswsenewwewseew\n" +
            "nenesewwnenewnenwsenwnenenwewsenwnenenw\n" +
            "sesewnwnwwneswnwnewnwnwsenewww\n" +
            "neeswsewnwnewwwsenwesew\n" +
            "eeeeseeseeneweeseeewwnesenw\n" +
            "swwswswwwswwnesene\n" +
            "nenenenewwswnweeweswneneneneswnese\n" +
            "neneewenenenenenenenenene\n" +
            "swnwewnwewnwwnenwwnwwswnww\n" +
            "swenwwswneseswswswswswswwneewswswse\n" +
            "seeseeeesewseeee\n" +
            "wswwswseswwswwswwswnenwnesenwenwsew\n" +
            "wwwwwwnwwwesenewwwwewwnwsw\n" +
            "eeeeenenweese\n" +
            "nweeweeeneseneesweneswseswnwneee\n" +
            "wsenenenwseenwnenewseswnwnesene\n" +
            "wwsewwwenwweswnwwwnwwwswwnwne\n" +
            "nwsewnenenenenenenwsenwneswneenenenenenw\n" +
            "ewneswneneswenwwewneneewneseene\n" +
            "swswwswswswnweswswswenwswseswswswwsw\n" +
            "senweseseeswnenwsweweseesewse\n" +
            "wswwnenwewwwwwnesewnwnwsewww\n" +
            "nenwnenewseneneneneneneneseneeneswwnenene\n" +
            "neswseswswswwnwswswwwneneswseswswsesw\n" +
            "eneenenwneneneneneeeswnenene\n" +
            "neseseswsesesesesesesewe\n" +
            "eewnwenwseenenesenenwse\n" +
            "swswswewwswswswswwnwsww\n" +
            "wnenwsenwewnwnwnenwnwweeseswnwnwnesw\n" +
            "wwwenwnwswseswswswseseenewnewswswww\n" +
            "nwseneneeenewnenenenenenenewnenenese\n" +
            "eeswseswswseswnwseseseswsww\n" +
            "eneneenwneneeswnewnwsenwnwneswnenwnwne\n" +
            "swweswswswswwswsweswswswnwnesewswswne\n" +
            "nenwneenwwneneswnwne\n" +
            "nenewwsewwwsesewewswnwnwwsewww\n" +
            "wnwsesesesewwwswweswnwnwwnwnwsww\n" +
            "eseeswseeeeneseeswseswnwseseenwnwse\n" +
            "senwsewseseswseswneneseseseseseswswsesw\n" +
            "nenwneswnenwnenenwnenenwsenenwsewnenewne\n" +
            "sweseeneewewnesesesweweseeenee\n" +
            "wwnesenwnwwwnwswnwnwsenenwsewwenw\n" +
            "swwsewswwnewwwswswswswnwneswnewswse\n" +
            "wnwenwswnenenwnwnwseneswnwenwswnwsenesw\n" +
            "swseswseseseesewsesesesesese\n" +
            "wnwseseeseseswesewsenesesesesesesesesw\n" +
            "swswnwwnwswsenwneseseseeswsenwswwswne\n" +
            "swenwwseeneneweweseswneeeseenenw\n" +
            "swseswswneswswneewseswwswnwwseeseswsw\n" +
            "nwnwsenwsenwsenewnwnwwwnwnewwnwswnw\n" +
            "wswswwwnwwwwwwwwsewnwnewwe\n" +
            "nesenenenenwnwnwnwnwnenesenwnewnwnewsw\n" +
            "wneewwenwnwenesewsweswsweswsenw\n" +
            "neswswwswseeswwnwswwnwseswnenweneesese\n" +
            "eweneeneweseeeeeese\n" +
            "seseseenweseseseseese\n" +
            "esewneeswseswsenwneenwenwswwnenenw\n" +
            "eneseswnewneneenwnweesweswneneesw\n" +
            "nwnwnwnwwewswswewwnwwewnwnwwsw\n" +
            "wwwwsewwwwwnewwwnw\n" +
            "nwneneneneseneenewsewwsweweeseene\n" +
            "eeewsewwwneswwnwneseweswswwwww\n" +
            "nenenenenesewseneeneeneswnwnenwnenene\n" +
            "senesenwnwsenwsenwnwenwwnwnwnewwnwnw\n" +
            "swwseesesesesesenwseseeeseseseenwse\n" +
            "wwneeswsesenenesenewwneseenenenew\n" +
            "nwnwnenwnenewswnwswswneneeenwnwnwenwne\n" +
            "neneeneeewewneneeeeewneneneswne\n" +
            "nenwnewwnwneseenwswnwnwnwswnenenwnwne\n" +
            "enwswsweenenenwwwseneeeneweesw\n" +
            "wwnwwweswwwwnwewwwwwsenwnew\n" +
            "nwneneswsenwswnwnenwnwnenwsenwsenwnwenenw\n" +
            "weswnesenweseesenwse\n" +
            "eweeeenenwweeswsweneeenwe\n" +
            "wewwnewwwwwwwwwswwwwese\n" +
            "nwwneenwnwnwnwesewnwnwwenwnesw\n" +
            "swwnewswwwwseenewwwwswwwww\n" +
            "eswnenesesenwesesewswwseseneeesee\n" +
            "weneneneseswnwswneneneswnenwneeneenenee\n" +
            "wnwnwnenwneenwnenenenene\n" +
            "swswswswnwswswswewseswswseswsweeewnw\n" +
            "nwnwesesenwseswseeseseneswseee\n" +
            "swswswsesesewseesesesese\n" +
            "seswneswneesenwnwneswesesww\n" +
            "nwwswswwwswwwsewwnwewsw\n" +
            "swnwswswwswewnwswwnweswneswswwwswse\n" +
            "newesesenesesesesesenwswsesewsesesesenw\n" +
            "wswnwswswenwswswwwswswswse\n" +
            "swswswneswswsesewsese\n" +
            "swsewswwnenewwwswneswnwswenwnwne\n" +
            "eseswnwnwenwswesesweewwnwenwsesese\n" +
            "wwnwnwweswsewwwnwnwnwwne\n" +
            "swswsenwswswenwswwnewwsew\n" +
            "seeenwwnwwnwwnwweswwnwnwnenwnwnw\n" +
            "eeneswseneneneeweeew\n" +
            "enenwsenenwnenwnenwswnwnwwnwnweswnwnwnw\n" +
            "enwneeeneeeswnwneeneneneseene\n" +
            "wnwewwswseswnwwnwswneseeweewsw\n" +
            "eneneeswswneeewnwnweeeseeneee\n" +
            "newnwnwenwnwnwnwnwnenwnesenenwne\n" +
            "wswwewswswswswwswsw\n" +
            "swnwnwnewwswwwsenewwnwewnwwsewnw\n" +
            "nweeeeenwnweeeewseenwswseesew\n" +
            "swesenwswswwnweeneeseeeeeeesee\n" +
            "nenenenwnwwnwnenesenwnenwnenenwwe\n" +
            "eeeneeneweseeeeeswnewnweeswe\n" +
            "nesewwwweswswwwswnenwwwwwesww\n" +
            "nesweesweenweneeeswnweneeeeesw\n" +
            "seswseeeweseesenweneweneeswnesw\n" +
            "sesweswswswwseneeswnwwwsenwswnwwswsw\n" +
            "neneneenwnewnwseesewseneneeeeseew\n" +
            "seeswsesenwnwseseeseseseesenweenewse\n" +
            "ewswwseseswswswsene\n" +
            "nwnwnwnwwwsenwnwnwsewwnwnwnwsenenwnw\n" +
            "nwenweseenwwnwnewsweswnwwseswwnwne\n" +
            "wswswseswneswnwwneswnewwwsewswsw\n" +
            "neseswsewswswswsenenwsesesweswsweseswsw\n" +
            "nenwsewnwwnenwnenesesenwneswwnesenwwne\n" +
            "swwweswswwwnewwwwnwswwwwesw\n" +
            "nesenewsenwnweneewswnwwswwsenwseseswne\n" +
            "swwnewwwwnwwneswwsewwswwsesew\n" +
            "neneneneeseneewswneneseewneswnewsw\n" +
            "nwnenenwnenwswnwnwnwswswnwnenwnwwsesee\n" +
            "swwseswswswswwswnesw\n" +
            "enenwneeeweseseeseeswsewsewneew\n" +
            "wwwnwnwwwwnwwseenenenwwswwwsew\n" +
            "swseswsenwswswswswnwswswswnwneeswsesese\n" +
            "nwnenwsenwswenwnenwnwswnwnwnwnwseswnewnw\n" +
            "swwwwnewswnesewwnesewswwwseew\n" +
            "ewnwnenwnwnwnwnwnwnw\n" +
            "seneneswwnewnwnwsewnwnwnwseenwwwsee\n" +
            "seeseseenwseseenwseesenwesesenwswsese\n" +
            "enewnwewseswnesewwwwnwswnwnwwne\n" +
            "swswwswneswsweswnw\n" +
            "nenenewswneswenenwswneeeseewneneee\n" +
            "nwwewnwwnenenwnwnwsewnenwswsenwnwsw\n" +
            "nwwnwnenenwwsewenwswswnweswswsewnwe\n" +
            "nwnwswnwnwnwnwnwnwenwwseswwwwnenwwnwe\n" +
            "nwnesenweesewenwweseeseeeswwsw\n" +
            "wnewwwnwnwwwwsewwewsewwnewse\n" +
            "newseneenweseswesweeesweeesew\n" +
            "enwswneeesweswsenwseneseeseswnewse\n" +
            "eeseswwseneseseeeswnweeneseesee\n" +
            "sewswswwsesweswnwesenwnwswswsweswsw\n" +
            "nesenenenesenwnenwneeweneneeswneee\n" +
            "nwnweewnwnwnwnwnwnwnwnwnesewnwnwswnw\n" +
            "nwenwswswnwnenwnwnwwsenwnwnwnwenwnwnwse\n" +
            "seswswswswwsweswnewsweseseswnw\n" +
            "swseswwsesesenesesweswseseswnwwswsesw\n" +
            "wnweeeseseeeeenwneeweeseeee\n" +
            "eenwswnwwnwweenwwneswnweswwnwsw\n" +
            "wnewswewwswsenwsenwwwsewswnwwenew\n" +
            "swswneeneneneenenwnenenenesenenenwnene\n" +
            "newnwneneesenewswsewnwneseneeswsew\n" +
            "wnwewseseswwenwnenwwnwwswswwwnwnwe\n" +
            "enwnenenewneneneneneneneneneswswwnese\n" +
            "nenwswseseseswswesenwseswsene\n" +
            "nwnenwnwnwwneseneenenenesweeseneeswnese\n" +
            "nwnwswnenenwnwnwnenwnwnwnwnwnw\n" +
            "swswnwswswswswseswswneesenwswsweswwswesw\n" +
            "nwswnesewseseneneseswsesesewswneswsesw\n" +
            "nwnenwesenenesenwnwnenewnwwnwnwnwnwwnw\n" +
            "seeneneneneneenwnee\n" +
            "newsweeseesesesewnesesenwsesenesesese\n" +
            "eenewnwswnwesewnwwswnwswnwwswnwnwe\n" +
            "wewsewwwwsewnwnwnewwsewwswnw\n" +
            "swnewnwwnwswwwswwwwswewwseswwne\n" +
            "nwswnenwsewnwseswwwneweweeswwww\n" +
            "wnwnwenwnwsenwnwsewnewnwwnwnwnwenwnenw\n" +
            "swnesenwenwneswswneswsesewseseeseesene\n" +
            "nwnwnwnwewsenwswnwenwnwwenwnenw\n" +
            "ewneewseenwswneswenwsenwewwe\n" +
            "senwesenenwsewseseseseseseseswesesenw\n" +
            "nesenewwnweneneneneneneesenewnwneswne\n" +
            "neneeeswwneeeswswenenwwnewwee\n" +
            "weseeseseeneeeneesw\n" +
            "nwnwnwnenwswsenwneswenwnwsewnwneneswnenenw\n" +
            "wneweenwenesweneeweenenenewse\n" +
            "sweenwswnweneneseneeseseweswseseese\n" +
            "swnwnesewwwseewnewwwwwwwww\n" +
            "eenenenweswwnwwnw\n" +
            "swswnwsweswneneeswswswswswswswsenwnewse\n" +
            "wnenwewwewseewswswnwnesenwnwnwene\n" +
            "seswseseswnwseseswseswswsw\n" +
            "nwnwnwnwswwenwwnwwwswewwewnwnw\n" +
            "nesewsewneswwwsenewsenewnwwsw\n" +
            "nweneeeewneseseweneswseseswnesewse\n" +
            "wswswwwwsenenenewwswsewwwnw\n" +
            "eenwwwswnwwwwnwenwwewnwswswwnw\n" +
            "swsenwnwsesenwseswseseseneswseneswwese\n" +
            "sewseseseswseeneseseswsesewsesenewse\n" +
            "swswswswswswswswswswwswseneneeswwswsw\n" +
            "nenenenwneneeseeseneenewnenewneewsw\n" +
            "nwsenwnwnenenewnwswnwnwswnwwnwnwswnwwe\n" +
            "wwwnenwsenwnwnenwsewwnwsenenw\n" +
            "neeswwweseseenwsenwseseesesenwwne\n" +
            "esesewseseneneeseseenwseswswseeeee\n" +
            "neneneneswseneneweneswnenenw\n" +
            "nweswswwseswwseeneseseseswewswswswnw\n" +
            "swswwwnwswnewwnewwwwwwswwswe\n" +
            "enwwnwwnwwwnwwnwenw\n" +
            "sewwnwnwwewswwwswswnwwseswswswsw\n" +
            "swswseswnwnwswseswnwsenwswwswwswswesww\n" +
            "weewwwsewwnewsewnewnwwwww\n" +
            "nweswsenwswneseewnenwswwewnwnwnwnwenw\n" +
            "eeewnweeneseswsweswseenenweeswe\n" +
            "swswswwwswnenwsweswseswswnewneswswsw\n" +
            "swneswswseswsenwseswnewsesenesenwswsesw\n" +
            "nesewnesweswnenew\n" +
            "nenwsesenenenwweenesweseswneneneew\n" +
            "eeswnweneswseesenwwese\n" +
            "esewwseswnwneeseswnwswnwesenwnwnwnww\n" +
            "nwnwwnwwwwwnwewnwnw\n" +
            "nwnwwesweenenenwnweswnwwnwenesweswne\n" +
            "nwwswesewnwwenwswewneseseeesesenw\n" +
            "swwseenwnwsewsewseneneww\n" +
            "neswneswenwwneneneenwnwnenesewne\n" +
            "neeneenweweeneneseseswnwsweeee\n" +
            "wswnwswwswswnenwswswwwswesewswnese\n" +
            "nwwnwwsewnweswsesesweenenwswnwnee\n" +
            "sweeneneneneneneswnweneneswnesenwwnee\n" +
            "eeswnwsweenenesenenwwnwneswseneese\n" +
            "swwnwnwswseeseswswseseseswneneswsewsesene\n" +
            "swnwwseneswnenenenewsenewneneneenenesw\n" +
            "sewwseseseseseneewseeseenwseenenesee\n" +
            "eeenwwweneneeeeswnesweeeenwne\n" +
            "seseseeeneseseseswe\n" +
            "nwnenwsewnwsenwnwnwnwwnw\n" +
            "nwswnenwswneseneswnwnwesenwnenewnenene\n" +
            "swseswnenwseeseswsewswswsewseneseswnwse\n" +
            "wswwswnwnewseweneswenwswswswswnese\n" +
            "wsenwnwnwnwnweewnwnwnwnwseswe\n" +
            "eenewneseeeneeeseenwnenewneneeswe\n" +
            "neswnwnwswnwswenenwnewnenenenenenwenene\n" +
            "sewsewsesenwesesweseswswsewseneew\n" +
            "swwswseswneeweswswseswnwwewnwswwne\n" +
            "eenesweeeeenenwnwseswsesewnwsesw\n" +
            "wwwnwsewnwwnwsenwnwwewwwnewenwe\n" +
            "nwwnwneneswnwnwnwnwnwwswnenwwnwsenesesenw\n" +
            "swseewseseseseseneseswsesesenese\n" +
            "newnwsenwnwswswenwnenwnwsewswnwnwnwwnw\n" +
            "eeneeweeneeeseeewenweswesw\n" +
            "wesewwswnweseneseneweswwwwneww\n" +
            "wnenesenwnwneseneenwese\n" +
            "nenenenwnewswnenwnwswneseswswneswnwseesw\n" +
            "swswseswswnwseswswswswnesese\n" +
            "seswswnwseseseswswseseswseswsw\n" +
            "neswsesewnwswwnweneseseswenwnwswe\n" +
            "nwsesweswnesewnwswsenenenwswneseswswswsw\n" +
            "swnwneneneswswnenenwwsenwnwnwse\n" +
            "swswseseseneswneswsesesenwseneneswwsese\n" +
            "nenwenenwnwsenwneewnwswenenwwnenew\n" +
            "eewnwseeneeeeweesewseneswee\n" +
            "seswseswsenwseseesenwsewseeseseseee\n" +
            "senwwnwnwnwnenwnwwwnww\n" +
            "wneeswnwswseswswnwwewsw\n" +
            "nwsweneswneeeneenesenwneneeswnw\n" +
            "esweenwnenenwweneneneseneeneneeesw\n" +
            "seeewseeseseeseenwwseesesweese\n" +
            "swswsewswseeswnwnweswwswnenwswswswwsw\n" +
            "swnwneneneeneenenenewenenene\n" +
            "nwsenwnwwsenwsenenenwenwwneneswseneew\n" +
            "enwneswswnesenewswwe\n" +
            "sesesenwnwswsesenewsesenwenwseseseseswe\n" +
            "nwseneswneswnwneneeneeswnenenwnenwese\n" +
            "seenesesenwnwwswnewswwwnwwwneswne\n" +
            "swseswnwnwswswswswswswseswswswswneswenwsw\n" +
            "senwnweenwesweseeeeeeeeewese\n" +
            "swwseneswwswwnewwneeseswenesewsene\n" +
            "eseneweenenenenenwneenenwneneswneneswne\n" +
            "nwnesenwseeeneeesewnwweswweesw\n" +
            "neseseseseseewwsesee\n" +
            "swnwseswswswneeswswswswswswwswnwneswsw\n" +
            "wnwwnwsenenesesewnenwswneesw";
    }
}