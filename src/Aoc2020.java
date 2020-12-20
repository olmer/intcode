import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Aoc2020 {
    public static void main(String[] args) {
        var raw = ("Tile 2311:\n" +
            "..##.#..#.\n" +
            "##..#.....\n" +
            "#...##..#.\n" +
            "####.#...#\n" +
            "##.##.###.\n" +
            "##...#.###\n" +
            ".#.#.#..##\n" +
            "..#....#..\n" +
            "###...#.#.\n" +
            "..###..###\n" +
            "\n" +
            "Tile 1951:\n" +
            "#.##...##.\n" +
            "#.####...#\n" +
            ".....#..##\n" +
            "#...######\n" +
            ".##.#....#\n" +
            ".###.#####\n" +
            "###.##.##.\n" +
            ".###....#.\n" +
            "..#.#..#.#\n" +
            "#...##.#..\n" +
            "\n" +
            "Tile 1171:\n" +
            "####...##.\n" +
            "#..##.#..#\n" +
            "##.#..#.#.\n" +
            ".###.####.\n" +
            "..###.####\n" +
            ".##....##.\n" +
            ".#...####.\n" +
            "#.##.####.\n" +
            "####..#...\n" +
            ".....##...\n" +
            "\n" +
            "Tile 1427:\n" +
            "###.##.#..\n" +
            ".#..#.##..\n" +
            ".#.##.#..#\n" +
            "#.#.#.##.#\n" +
            "....#...##\n" +
            "...##..##.\n" +
            "...#.#####\n" +
            ".#.####.#.\n" +
            "..#..###.#\n" +
            "..##.#..#.\n" +
            "\n" +
            "Tile 1489:\n" +
            "###.##.#..\n" +
            "..##.##.##\n" +
            "##.#...##.\n" +
            "...#.#.#..\n" +
            "#..#.#.#.#\n" +
            "#####...#.\n" +
            "..#...#...\n" +
            ".##..##...\n" +
            "..##...#..\n" +
            "##.#.#....\n" +
            "\n" +
            "Tile 2473:\n" +
            "#....####.\n" +
            "#..#.##...\n" +
            "#.##..#...\n" +
            "######.#.#\n" +
            ".#...#.#.#\n" +
            ".#########\n" +
            ".###.#..#.\n" +
            "########.#\n" +
            "##...##.#.\n" +
            "..###.#.#.\n" +
            "\n" +
            "Tile 2971:\n" +
            "..#.#....#\n" +
            "#...###...\n" +
            "#.#.###...\n" +
            "##.##..#..\n" +
            ".#####..##\n" +
            ".#..####.#\n" +
            "#..#.#..#.\n" +
            "..####.###\n" +
            "..#.#.###.\n" +
            "...#.#.#.#\n" +
            "\n" +
            "Tile 2729:\n" +
            "...#.#.#.#\n" +
            "####.#....\n" +
            "..#.#.....\n" +
            "....#..#.#\n" +
            ".##..##.#.\n" +
            ".#.####...\n" +
            "####.#.#..\n" +
            "##.####...\n" +
            "##..#.##..\n" +
            "#.##...##.\n" +
            "\n" +
            "Tile 3079:\n" +
            "#.#.#####.\n" +
            ".#..######\n" +
            "..#.......\n" +
            "######....\n" +
            "####.#..#.\n" +
            ".#...#.##.\n" +
            "#.#####.##\n" +
            "..#.###...\n" +
            "..#.......\n" +
            "..#.###...").split("\\n\\n");

        Map<Integer, UnplacedTile> unplacedTiles = new HashMap<>();
        Map<Integer, Map<Integer, PictureTile>> picture = new HashMap<>();

        for (var tile : raw) {
            var lines = tile.split("\\n");
            var idEnd = lines[0].split(" ")[1];
            int id = Integer.parseInt(idEnd.substring(0, idEnd.length() - 1));
            var left = new StringBuilder();
            var right = new StringBuilder();
            for (var i = 1; i < lines.length; i++) {
                left.append(lines[i].charAt(0));
                right.append(lines[i].charAt(lines[i].length() - 1));
            }
            unplacedTiles.put(
                id,
                new UnplacedTile(
                    id,
                    new String[]{lines[1], right.toString(), lines[lines.length - 1], left.toString()},
                    Arrays.stream(Arrays.copyOfRange(lines, 2, lines.length - 1))
                        .map(e -> e.substring(1, e.length() - 1))
                        .toArray(String[]::new)
                )
            );
        }

        var first = unplacedTiles.entrySet().iterator().next();
        unplacedTiles.remove(first.getKey());

        placeTile(picture, 0, 0, first.getKey(), first.getValue().getEdges(), first.getValue().getBody());

        do {
            doPlacement(unplacedTiles, picture);
        } while (unplacedTiles.size() != 0);

        System.out.println(multCorners(picture));


        var resultingImage = new TreeMap<Integer, TreeMap<Integer, PictureTile>>();

        for (var row : picture.entrySet()) {
            var t = new TreeMap<Integer, PictureTile>();
            for (var m : row.getValue().entrySet()) {
                t.put(m.getKey(), m.getValue());
            }
            resultingImage.put(row.getKey(), t);
        }

        List<String> full =  new ArrayList<>();
        resultingImage.forEach((key, value) -> {
            var rows = new StringBuilder[]{
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
            };
            value.forEach((rkey, rvalue) -> {
                rows[0].append(rvalue.getBody()[0]);
                rows[1].append(rvalue.getBody()[1]);
                rows[2].append(rvalue.getBody()[2]);
                rows[3].append(rvalue.getBody()[3]);
                rows[4].append(rvalue.getBody()[4]);
                rows[5].append(rvalue.getBody()[5]);
                rows[6].append(rvalue.getBody()[6]);
                rows[7].append(rvalue.getBody()[7]);
            });
            full.addAll(Arrays.stream(rows).map(StringBuilder::toString).collect(Collectors.toList()));
        });

        System.out.println("Checksum image: " + (Arrays.equals((".#.#..#.##...#.##..#####\n" +
            "###....#.#....#..#......\n" +
            "##.##.###.#.#..######...\n" +
            "###.#####...#.#####.#..#\n" +
            "##.#....#.##.####...#.##\n" +
            "...########.#....#####.#\n" +
            "....#..#...##..#.#.###..\n" +
            ".####...#..#.....#......\n" +
            "#..#.##..#..###.#.##....\n" +
            "#.####..#.####.#.#.###..\n" +
            "###.#.#...#.######.#..##\n" +
            "#.####....##..########.#\n" +
            "##..##.#...#...#.#.#.#..\n" +
            "...#..#..#.#.##..###.###\n" +
            ".#.#....#.##.#...###.##.\n" +
            "###.#...#..#.##.######..\n" +
            ".#.#.###.##.##.#..#.##..\n" +
            ".####.###.#...###.#..#.#\n" +
            "..#.#..#..#.#.#.####.###\n" +
            "#..####...#.#.#.###.###.\n" +
            "#####..#####...###....##\n" +
            "#.##..#..#...#..####...#\n" +
            ".#.###..##..##..####.##.\n" +
            "...###...##...#...#..###").split("\\n"), full.toArray()) ? "equals!" : "nope"));

        var monster = ("                  # \n" +
            "#    ##    ##    ###\n" +
            " #  #  #  #  #  #   ").split("\\n");

        var variants = new String[][]{
            full.toArray(new String[0]),
            rotateBody(full.toArray(new String[0])),
            rotateBody(rotateBody(full.toArray(new String[0]))),
            rotateBody(rotateBody(rotateBody(full.toArray(new String[0])))),
            flipBody(full.toArray(new String[0])),
            rotateBody(flipBody(full.toArray(new String[0]))),
            rotateBody(rotateBody(flipBody(full.toArray(new String[0])))),
            rotateBody(rotateBody(rotateBody(flipBody(full.toArray(new String[0]))))),
        };

        var numOfHash = 0;
        for (var mmm : variants[0]) {
            numOfHash += mmm.chars().filter(ch -> ch == '#').count();
        }

        for (var variant : variants) {
            var monsters = 0;
            for (var i = 0; i < variant.length - 2; i++) {
                for (var j = 0; j < variant[i].length() - 19; j++) {
                    var valid = true;
                    for (var m = 0; m < monster[0].length(); m++) {
                        if (monster[0].charAt(m) != '#') continue;
                        if (variant[i].charAt(j + m) != '#') {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) continue;
                    for (var m = 0; m < monster[1].length(); m++) {
                        if (monster[1].charAt(m) != '#') continue;
                        if (variant[i + 1].charAt(j + m) != '#') {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) continue;
                    for (var m = 0; m < monster[2].length(); m++) {
                        if (monster[2].charAt(m) != '#') continue;
                        if (variant[i + 2].charAt(j + m) != '#') {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        monsters++;
                    }
                }
            }
            if (monsters > 0)
            System.out.println(numOfHash - monsters * 15);
        }
    }

    public static String[] rotateStringMatrixBy90(String[] newMatrixColumns) {
        int numberOfRows = newMatrixColumns.length; // this I leave as an exercise
        int numberOfColumns = newMatrixColumns[0].length(); // same with this one

        String newMatrix = "";

        int count = 0;
        while (count < numberOfColumns) {
            for (int i = numberOfRows - 1; i > -1; i--) {
                newMatrix = newMatrix + newMatrixColumns[i].charAt(count);
            }

            newMatrix = newMatrix + "\n";
            count++;
        }

        return newMatrix.split("\\n");
    }

    private static void doPlacement(
        Map<Integer, UnplacedTile> unplacedTiles,
        Map<Integer, Map<Integer, PictureTile>> picture
    ) {
        for (var entry : unplacedTiles.entrySet()) {
            var id = entry.getKey();
            var tile = entry.getValue().getEdges();
            var body = entry.getValue().getBody();
            var variants = new String[][]{
                tile,
                rotate(tile),
                rotate(rotate(tile)),
                rotate(rotate(rotate(tile))),
                flip(tile),
                rotate(flip(tile)),
                rotate(rotate(flip(tile))),
                rotate(rotate(rotate(flip(tile)))),
            };
            var variantsBody = new String[][]{
                body,
                rotateBody(body),
                rotateBody(rotateBody(body)),
                rotateBody(rotateBody(rotateBody(body))),
                flipBody(body),
                rotateBody(flipBody(body)),
                rotateBody(rotateBody(flipBody(body))),
                rotateBody(rotateBody(rotateBody(flipBody(body)))),
            };
            for (var row : picture.entrySet()) {
                for (var maybeOpenTile : row.getValue().entrySet()) {
                    if (maybeOpenTile.getValue().getId() != 0) {
                        continue;
                    }

                    for (var i = 0; i < variants.length; i++) {
                        var variant = variants[i];
                        var placed = validToPlace(variant, maybeOpenTile.getValue());
                        if (placed) {
                            placeTile(picture, maybeOpenTile.getKey(), row.getKey(), id, variant, variantsBody[i]);
                            maybeOpenTile.getValue().setId(id);
                            unplacedTiles.remove(id);
                            return;
                        }
                    }
                }
            }
        }
    }

    private static boolean validToPlace(
        String[] variant,
        PictureTile tile
    ) {
        if (tile == null) {
            return true;
        }

        var req = tile.getRequirements();

        if (req[0] != null && !req[0].equals(variant[0])) {
            return false;
        }
        if (req[1] != null && !req[1].equals(variant[1])) {
            return false;
        }
        if (req[2] != null && !req[2].equals(variant[2])) {
            return false;
        }
        return req[3] == null || req[3].equals(variant[3]);
    }

    private static void placeTile(
        Map<Integer, Map<Integer, PictureTile>> picture,
        int x,
        int y,
        int id,
        String[] requirements,
        String[] body
    ) {
        if (!picture.containsKey(y)) {
            picture.put(y, new HashMap<>());
        }
        var row = picture.get(y);

        var tile = row.get(x);
        if (tile == null) {
            tile = new PictureTile(new String[4]);
        }

        tile.setId(id);
        tile.setBody(body);
        row.put(x, tile);

        addRequirement(picture, x - 1, y, 1, requirements[3]);
        addRequirement(picture, x + 1, y, 3, requirements[1]);
        addRequirement(picture, x, y - 1, 2, requirements[0]);
        addRequirement(picture, x, y + 1, 0, requirements[2]);
    }

    private static void addRequirement(
        Map<Integer, Map<Integer, PictureTile>> picture,
        int x,
        int y,
        int direction,
        String requirement
    ) {
        if (!picture.containsKey(y)) {
            picture.put(y, new HashMap<>());
        }
        var row = picture.get(y);
        var tile = row.get(x);
        if (tile == null) {
            tile = new PictureTile(new String[4]);
        }

        tile.addRequirement(direction, requirement);
        row.put(x, tile);
    }

    private static BigInteger multCorners(
        Map<Integer, Map<Integer, PictureTile>> picture
    ) {
        var minY = Integer.MAX_VALUE;
        var minX = Integer.MAX_VALUE;
        var maxY = Integer.MIN_VALUE;
        var maxX = Integer.MIN_VALUE;

        for (var row : picture.entrySet()) {
            if (row.getKey() < minY) {
                minY = row.getKey();
            }
            if (row.getKey() > maxY) {
                maxY = row.getKey();
            }
            for (var tile : row.getValue().entrySet()) {
                if (tile.getKey() < minX) {
                    minX = tile.getKey();
                }
                if (tile.getKey() > maxX) {
                    maxX = tile.getKey();
                }
            }
        }

        var bigint = BigInteger.valueOf(picture.get(minY + 1).get(minX + 1).getId()).multiply(
            BigInteger.valueOf(picture.get(minY + 1).get(maxX - 1).getId())).multiply(
            BigInteger.valueOf(picture.get(maxY - 1).get(minX + 1).getId())).multiply(
            BigInteger.valueOf(picture.get(maxY - 1).get(maxX - 1).getId()));

        picture.remove(minY);
        picture.remove(maxY);

        for (var row : picture.entrySet()) {
            row.getValue().remove(minX);
            row.getValue().remove(maxX);
        }

        return bigint;
    }

    static class PictureTile {
        private int id = 0;
        private final String[] requirements;
        private String[] body;

        public PictureTile(String[] requirements) {
            this.requirements = requirements;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void addRequirement(int direction, String req) {
            requirements[direction] = req;
        }

        public String[] getRequirements() {
            return requirements;
        }

        public String[] getBody() {
            return body;
        }

        public void setBody(String[] body) {
            this.body = body;
        }
    }

    static class UnplacedTile {
        private final int id;
        private final String[] edges;
        private final String[] body;

        public UnplacedTile(int id, String[] edges, String[] body) {
            this.id = id;
            this.edges = edges;
            this.body = body;
        }

        public String[] getEdges() {
            return edges;
        }

        public String[] getBody() {
            return body;
        }
    }

    private static String[] rotate(String[] data) {
        var newD = data.clone();
        newD[1] = data[0];
        newD[0] = reverse(data[3]);
        newD[3] = data[2];
        newD[2] = reverse(data[1]);
        return newD;
    }

    private static String[] flip(String[] data) {
        var newD = data.clone();
        newD[1] = reverse(data[1]);
        newD[0] = data[2];
        newD[2] = data[0];
        newD[3] = reverse(data[3]);
        return newD;
    }

    private static String[] rotateBody(String[] data) {
        var newD = data.clone();
        var r = rotateStringMatrixBy90(newD);
        return r;
    }

    private static String[] flipBody(String[] data) {
        var newD = data.clone();
        var l = data.length;
        for (var i = 0; i < l / 2; i++) {
            newD[i] = data[l - i - 1];
            newD[l - i - 1] = data[i];
        }
        return newD;
    }

    private static String reverse(String s) {
        return (new StringBuilder()).append(s).reverse().toString();
    }

}
