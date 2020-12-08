import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc1903 {
    public static void main(String[] args) throws Exception {

        String input = "R993,U847,R868,D286,L665,D860,R823,U934,L341,U49,R762,D480,R899,D23,L273,D892,R43,U740,L940,U502,L361,U283,L852,D630,R384,D758,R655,D358,L751,U970,R72,D245,L188,D34,R355,U373,L786,U188,L304,D621,L956,D839,R607,U279,L459,U340,R412,D901,L929,U256,R495,D462,R369,D138,R926,D551,L343,U237,L434,U952,R421,U263,L663,D694,R687,D522,L47,U8,L399,D930,R928,U73,L581,U452,R80,U610,L998,D797,R584,U772,L521,U292,L959,U356,L940,D894,R774,U957,L813,D650,L891,U309,L254,D271,R791,D484,L399,U106,R463,D39,L210,D154,L380,U86,L136,D228,L284,D267,R195,D727,R739,D393,R395,U703,L385,U483,R433,U222,L945,D104,L605,D814,L656,U860,L474,D672,L812,U789,L29,D256,R857,U436,R927,U99,R171,D727,L244,D910,L347,U789,R49,U598,L218,D834,L574,U647,L185,U986,L273,D363,R848,U531,R837,U433,L795,U923,L182,D915,R367,D347,R867,U789,L776,U568,R969,U923,L765,D589,R772,U715,R38,D968,L845,D327,R721,D928,R267,U94,R763,U799,L946,U130,L649,U521,L569,D139,R584,D27,L823,D918,L450,D390,R149,U237,L696,U258,L757,U810,L216,U202,L966,U157,R702,D623,R740,D560,R932,D587,L197,D56,R695,U439,R655,U576,R695,D176,L800,D374,R806,U969,L664,U216,L170,D415,R485,U188,L444,D613,R728,U508,L644,U289,R831,D978,R711,U973,R3,U551,R377,U114,L15,U812,R210,D829,L536,D883,L843,D427,L311,D680,R482,D69,R125,D953,L896,D85,R376,D683,R374,U415,L3,U843,L802,D124,R299,U345,L696,D276,L87,D98,R619,D321,R348,D806,L789,U657,R590,D747,L477,U251,R854,D351,L82,D982,R906,D94,R285,U756,L737,D377,L951,U126,L852,D751,L946,U696,L44,D709,R851,D364,R222\n" +
            "L1002,D658,L695,U170,L117,U93,R700,D960,L631,U483,L640,D699,R865,U886,L59,D795,R265,U803,R705,D580,R519,U685,R126,D888,R498,U934,L980,U734,L91,D50,R805,U197,R730,U363,R337,U594,L666,U702,L237,D140,L72,U980,L167,U598,L726,U497,L340,D477,L304,U945,R956,U113,L43,D4,R890,D316,R916,D644,R704,D398,L905,U361,R420,U31,L317,U338,R703,D211,R27,D477,L746,U813,R705,U191,L504,D434,R697,D945,R835,D374,L512,U269,L299,U448,R715,U363,R266,U720,L611,U672,L509,D983,L21,U895,L340,D794,R528,U603,R154,D610,L582,U420,L696,U599,R16,U610,L134,D533,R156,D338,L761,U49,L335,D238,R146,U97,L997,U545,L896,D855,L653,D789,R516,D371,L99,D731,R868,D182,R535,D35,R190,D618,R10,D694,L567,D17,R356,U820,R671,D883,R807,U218,L738,U225,L145,D954,R588,U505,R108,U178,R993,D788,R302,D951,R697,D576,L324,U930,R248,D245,R622,U323,R667,U876,L987,D411,L989,U915,R157,D67,L968,U61,R274,D189,L53,D133,R617,D958,L379,U563,L448,D412,R940,U12,R885,U121,R746,U215,R420,U346,L469,D839,R964,D273,R265,D3,L714,D224,L177,U194,L573,U511,L795,U299,L311,U923,R815,U594,L654,U326,L547,U547,R467,D937,L174,U453,R635,D551,L365,U355,R658,U996,R458,D623,R61,U181,R340,U163,L329,D496,L787,D335,L37,D565,R318,U942,R198,U85,R328,D826,R817,D118,R138,D29,L434,D427,R222,D866,L10,D152,R822,D779,L900,D307,R723,D363,L715,D60,R661,U680,R782,U789,R311,D36,R425,U498,L910,D546,R394,D52,R803,D168,L6,U769,R856,D999,L786,U695,R568,U236,R472,U291,L530,U314,L251,D598,R648,D475,L132,D236,L915,D695,L700,U378,L685,D240,R924,D977,R627,U824,L165";


        var inputs = input.split("\\r?\\n");

        var firstWireSegments = createSegments(inputs[0].split(","));
        var secondWireSegments = createSegments(inputs[1].split(","));
        Point bestCross = null;
        for (var fwSegment : firstWireSegments) {
            for (var swSegment : secondWireSegments) {
                var pointOptional = CrossDetector.getCrossPoint(fwSegment, swSegment);
                if (pointOptional.isPresent()) {
                    var point = pointOptional.get();
                    if (bestCross == null) {
                        bestCross = point;
                    } else if (isBestCrossWorse(bestCross, point)){
                        bestCross = point;
                    }
                }
            }
        }

        System.out.println(Math.abs(bestCross.getX()) + Math.abs(bestCross.getY()));
    }

    private static boolean isBestCrossWorse(Point bestCross, Point point) {
        return Math.abs(bestCross.getX()) + Math.abs(bestCross.getY()) > Math.abs(point.getX()) + Math.abs(point.getY());
    }

    private static ArrayList<Segment> createSegments(String[] commands) throws Exception {
        Pattern commandParser = Pattern.compile("(\\w)(\\d+)");
        ArrayList<Segment> segments = new ArrayList<>();


        int currentX = 0, currentY = 0;
        for (String command : commands) {
            Matcher matcher = commandParser.matcher(command);
            if (matcher.matches()) {
                char direction = matcher.group(1).charAt(0);
                int stepsToTake = Integer.parseInt(matcher.group(2));
                if (direction == 'R') {
                    int newX = currentX + stepsToTake;
                    segments.add(SegmentFactory.create(new Point(currentX, currentY), new Point(newX, currentY)));
                    currentX = newX;
                } else if (direction == 'L') {
                    int newX = currentX - stepsToTake;
                    segments.add(SegmentFactory.create(new Point(currentX, currentY), new Point(newX, currentY)));
                    currentX = newX;
                } else if (direction == 'U') {
                    int newY = currentY + stepsToTake;
                    segments.add(SegmentFactory.create(new Point(currentX, currentY), new Point(currentX, newY)));
                    currentY = newY;
                } else if (direction == 'D') {
                    int newY = currentY - stepsToTake;
                    segments.add(SegmentFactory.create(new Point(currentX, currentY), new Point(currentX, newY)));
                    currentY = newY;
                } else {
                    throw new Exception("Unknown direction: " + direction);
                }
            } else {
                throw new Exception("Invalid command found: " + command);
            }
        }

        return segments;
    }
}

class CrossDetector {
    public static Optional<Point> getCrossPoint(Segment first, Segment second) {
        if (first instanceof HorizontalSegment && second instanceof VerticalSegment) {
            return detectCrossPoint((HorizontalSegment) first, (VerticalSegment) second);
        } else if (second instanceof HorizontalSegment && first instanceof VerticalSegment) {
            return detectCrossPoint((HorizontalSegment) second, (VerticalSegment) first);
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Point> detectCrossPoint(HorizontalSegment hor, VerticalSegment ver) {
        if (hor.getA().getX() < ver.getA().getX()
            && hor.getB().getX() > ver.getA().getX()
            && hor.getA().getY() < ver.getB().getY()
            && hor.getA().getY() > ver.getA().getY()
        ) {
            return Optional.of(new Point(ver.getA().getX(), hor.getA().getY()));
        }
        return Optional.empty();
    }
}

class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Segment {
    private final Point a;
    private final Point b;

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }
}

class HorizontalSegment extends Segment {
    public HorizontalSegment(Point a, Point b) {
        super(a, b);
    }
}

class VerticalSegment extends Segment {
    public VerticalSegment(Point a, Point b) {
        super(a, b);
    }
}

class SegmentFactory {
    public static Segment create(Point a, Point b) {
        if (a.getX() == b.getX()) {
            if (a.getY() > b.getY()) {
                return new VerticalSegment(b, a);
            } else {
                return new VerticalSegment(a, b);
            }
        } else {
            if (a.getX() > b.getX()) {
                return new HorizontalSegment(b, a);
            } else {
                return new HorizontalSegment(a, b);
            }
        }
    }
}
