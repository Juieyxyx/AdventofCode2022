package day22;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class day22a {
    static final Map<Integer, Point> directionMap;

    static {
        directionMap = Map.of(0, new Point(1, 0),
                1, new Point(0, 1),
                2, new Point(-1, 0),
                3, new Point(0, -1));
    }

    static Map<Point, Character> map;
    static  String path;


    public static void main(String[] args) throws FileNotFoundException {
        List<String> list = new ArrayList<>();
        File file = new File("AoC2022/inputs/input_day22.txt");
        Scanner in = new Scanner(file);

        while (in.hasNext()){
           list.add(in.nextLine());
        }

        map = new HashMap<>();
        path = list.get(list.size() - 1);
        for (var y = 1; y < list.size() - 1; y++) {
            final String line = list.get(y - 1);
            for (var x = 1; x <= line.length(); x++) {
                final var space = line.charAt(x - 1);
                if (space != ' ') {
                    map.put(new Point(x, y), space);
                }
            }
        }

        System.out.println("part 1 :" +  getPassword());

    }

    public static int getPassword() {
        var position = getStart();
        var direction = 0;
        var distance = 0;
        for (var i = 0; i < path.length(); i++) {
            final var next = path.charAt(i);
            switch (next) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
                        distance = (distance * 10) + (next - '0');
                case 'R', 'L' -> {
                    position = move(position, direction, distance);
                    distance = 0;
                    direction = turn(direction, next);
                }
                default -> throw new UnsupportedOperationException();
            }
        }
        position = move(position, direction, distance);

        return (1000 * position.y) + (4 * position.x) + direction;
    }



    private static Point move(final Point position, final int direction, final int distance) {
        var nextPoint = new Point(position);
        var directionShift = directionMap.get(direction);
        for (var i = 0; i < distance; i++) {
            var testPoint = new Point(nextPoint.x + directionShift.x, nextPoint.y + directionShift.y);
            var result = map.getOrDefault(testPoint, ' ');
            switch (result) {
                case '.':
                    nextPoint = testPoint;
                    break;
                case '#':
                    return nextPoint;
                case ' ':
                    try {
                        nextPoint = attemptToWrap(nextPoint, direction);
                    } catch (final IllegalArgumentException iae) {
                        return nextPoint;
                    }
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return nextPoint;
    }

    private static Point attemptToWrap(final Point currentPoint, final int direction) {
        var testPoint = switch (direction) {
            case 0 -> new Point(
                    map.keySet().stream().filter(p -> p.getY() == currentPoint.y).mapToInt(p -> p.x).min()
                            .orElse(0), currentPoint.y);
            case 1 -> new Point(currentPoint.x,
                    map.keySet().stream().filter(p -> p.x == currentPoint.x).mapToInt(p -> p.y).min()
                            .orElse(0));
            case 2 -> new Point(
                    map.keySet().stream().filter(p -> p.getY() == currentPoint.y).mapToInt(p -> p.x).max()
                            .orElse(0), currentPoint.y);
            case 3 -> new Point(currentPoint.x,
                    map.keySet().stream().filter(p -> p.x == currentPoint.x).mapToInt(p -> p.y).max()
                            .orElse(0));
            default -> throw new UnsupportedOperationException();
        };
        var character = map.get(testPoint);
        if (character == '.') {
            return testPoint;
        }

        throw new IllegalArgumentException();
    }

    private static int turn(final int direction, final char next) {
        if ('R' == next) {
            return (direction + 1) % 4;
        }
        return (direction + 3) % 4;
    }

    private static Point getStart() {
        int minX = map.keySet().stream().filter(p -> p.getY() == 1).mapToInt(p -> p.x).min().orElse(0);
        var testPoint = new Point(minX, 1);
        while (map.get(testPoint) != '.') {
            testPoint = new Point(testPoint.x + 1, testPoint.y);
        }
        return testPoint;
    }
}

