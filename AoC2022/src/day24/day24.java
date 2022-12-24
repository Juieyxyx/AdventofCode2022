package day24;

import day17.Coord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class day24 implements IDay {
    static String input = """
#.########################################################################################################################
#<^.>v<.vv.><<<v<vv<v^>^^v<>.^>.^v^>vv^^^><<^..>^^^>><.^>^v<v<<>.>.><.vv<^>v^<v^<v^v^><^vv^>^>^v><<v>^.^>v^v^^<..v>^..v^<#
#>^v>v^vvv^><v.v<v<.v<>^v^v.v<v..^<^<.^<^vv<><<<>.^v.v.<<^<v^^^<.<^<^v.vvv^vv.>v^>^v<>^vv^^<<v<.^<<>v>^>>vv<<v><^vvvv>^v>#
#>v^..vv><^v^^^><><^>>^<v.^.v^^^v^^<>v^v<<^><v<<v.v^^^>vv<><>v^<>^v>.vv^v<>>^<<v^.v<v^^<..>^v>^^v>^<^>^v>vv.>>v^.><>^.>v<#
#.>>.vv<.<<^v<^<<^^^><^<<^>v^v<><<v<><v>>^^<>vv>^>^vvvv^.>....>.v<^^^v^>.<^v.<vvv>v^>><>>><>.v.v^<v>^<^<v>>vv<vvvv>v<.<>>#
#<^<v>v<<v<<>v>v^<.^<v.^v^v>v<<v<><>.^.^.v>^^^<^<v.v.>>><<<^v<>>><v>^<^vv>><.><v<v<>^..vv>^v^v.v<v..<^v><>..<.<>^vvvv>v<<#
#<v>>><vvv>.>>^vv^<>>vv^<.<..<v>>>^>v>>^.^<.^..><<^v<.<<<<v.<^^^.<>^>><.^v^v.<><<^<><<v><^^.><v>^<^.^<>>>^<<<<<<>><^.vv^<#
#>v^>.v><><^><>>.<<^vvv^v^^..vv>><<^>^^<^v.><^v<^><<>>><>^^v<<.^.^<<v>^>.>.>><>^^><^^<^>^..v..<^^<>v^.>>>>^v<v>.<.^<^<<v<#
#<v.>v^<..vv^vv>>^<^>>v<<>><vv..<<<<v.v^>^v><^><v^><<>v>^>v.v>v.>v^<><^>>vv<>.>^^<<>.><>v>v.^v>^vv>v^.v>><v<^vv<<<v^><><>#
#<v^<.^^^^<<.^^^.<^.<v<<^vv.><^<<<v<<v<<>^^<<.<vvv><v>>.>>>^><.>^<>^<>^>^v^vvv>^^>v^>v>>.<v<<v><>v>v^^<v<v^<^>v.>><v^><>.#
#>.^..<<v>^><.^<<^^<>^v<.^<^>>v<^>^>>^<<^^><^><v><.>><<v>^v^.v^^^vv<<^<vv^>^>v^v<<v^v<<.v><vvv><<^<v.>>v.>v.v<.^v><>^<^<>#
#.v.<^v>>^<vv^>^.><<v^>.>v>^>>><<v>v<<^^>.^>v><>v<^v^.>>^>>^><<>^><>.^^<>^<><^v^><^^>v<^.<<.v>>^.v^^<^.<^.<^.>^<<>.^<>..>#
#.v>v>^^<^><><><v><.<>v>^.^.v<^>.v^v<v><vv.>>^>.v<v^><<>^v^.^v>v>^><>vv><<v><<v<>^^v^v<>vv>><<>vv<>^.<<.>v>v<<v>v<>><^vv<#
#>.^.v>^^.<<vv<^<^<>^v>v^v>>.<<^vv.<<>^^^<^^^<>v..<v>^>^<><^v><><>..vv<^.<>^^>vv^^<.<<><^v^><vv^>>vv.v>>^^<^<>vv><^<^^>><#
#<^.^>>.^.v>>v>.<v><>^vv<><<><<<<v<^v>v^vv>.<<<v<<>^><<>^^<>^>^>vv<<v<><^^>^<>v<v^^v>^v.vv<>v<>^<v<<^v^<>>^v<^v.>^^>.>>v.#
#<>>.^^><vv<vv^<>>^><>><^vv><^>^<<>vv><>><>^<^v^^^>>v<.vv>v^^.^vv<^^^<^>v<>>>vv>^v.<<>>>^>vv<<^<<<v<<^<<>^^v.v^v^<^<<><v<#
#<.<>v<^^<<>v>v^v>v>v..><>><^>><<v>.v<<>vv.^^<^^v^^^<.v.>.<.<>v^v^v^><<><^^>.>^^><><^^>.>.><^<^^<<v<.^^<vv>^><>>.>v^^^^v<#
#>^^<v>v>>v>^^><<^^^<v^<<<><^>>v^^><v>>>..><>>.^v<^<>>v<<^<^^^><.v.^^v<^.>v.<<.vv<<.^vv.<<^>>v>v^^v<v<<v<<<><><<<<^>^><.<#
#>><>.v>.v>.<^>><<.<>..v^<^v>v>>^.>^.v.^<vv>^<<vvv^vvv<>><^^.<<<<v^><.><>^^.<<<v>vv><<>>vv^>v>><<^>><^.v<<<^><<^vv<<..><<#
#<^><v^<^^>v><.><.^^<^>>>v<<^>^>^<<><.v^^v^v<^^v^^^<>^.<v><<^<>vv^>^^v^v<^vv.v^v<>>.v^>.v<<>><vvvvv<>^<<v>^^.v^>^^v^v<>.>#
#>^<<.^<.>.<>.^v^^<<v<.>><v.<>v<v^^>v>>v<<<^>vv<^vv^<><^v^.vv>v>^^>><^^v^>><>^>>v>v^>>v<vv.^>><<^<^.vv<><^^>>..v<v^vv><^<#
#<v><><^>>>^.>.v.^><..<v<<^>^v<>^>><^^v^><....v>>.>><>>v><vv<>^v^.><v^^^^^^v>v>.v<<v<^>><^v<^>^>.><^>vv>^>^v>.><>><v>vvv<#
#>^>v>v^^<vv^vv<vv>^^^.^><^>^vv.v^v>vvvvv>^^^>^>^<v><><><>v<<<<>^v^^<<.v^<^.^^^v<^^>^><<v<.<^v>>v.><<<>v^^v>>v^<^^^><>><<#
#>^<v^<vv.v<.^<vv.vv>^<.v>v^v>v<^.<<>.^vv.<<><v>vv<<>^.v.^<^^vv<v^^>^<v.v.<<.v^>v.^.^>v>v>^<>vvv>>^v<^^vvv.vv.<^v^><^^>><#
#>.^.vvv>vv><vvv^<^>^>.><<>v^v>vvv^v.v>>>.^<^^.^>>>>v.>v^>^>>><^>v^^<.>>>^<>v<>v.>>^v^vvv.v<^<<v<<^^vvv>>>^>^^.^>^>>.<<>.#
#<^v<^v<v.^.vv>v^>v^v.vv^<<>^<>^><>^<v^>^.>v.vv><^<.v^.^<vvvv^v>>^vv><<>>v>^v<<<.>vv<<^v^v>vvv>v..<><<v>^>>v^>^.^vv^<<<^<#
########################################################################################################################.#
            """;

    static int maxX;
    static int maxY;

    static HashSet<Coord> bounds;


    @Override
    public String part1() {
        ArrayList<Blizzard> blizzards = new ArrayList<>();
        bounds =  new HashSet<Coord>();
        String[] lines = input.split("\n");
        for(int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for(int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                    case '#' -> bounds.add(new Coord(x, y));
                    case '>' -> blizzards.add(new Blizzard(new Coord(x, y), Coord.RIGHT));
                    case '<' -> blizzards.add(new Blizzard(new Coord(x, y), Coord.LEFT));
                    case 'v' -> blizzards.add(new Blizzard(new Coord(x, y), Coord.DOWN));
                    case '^' -> blizzards.add(new Blizzard(new Coord(x, y), Coord.UP));
                }
            }
        }

        maxX = bounds.stream().map(x->x.x).max(Integer::compare).get();
        maxY = lines.length - 1;

        Coord start = null;
        Coord end = null;
        for(int x = 0; x <= maxX; x++) {
            if(!bounds.contains(new Coord(x,0)))
                start = new Coord(x,0);
            if(!bounds.contains(new Coord(x,lines.length - 1)))
                end = new Coord(x,lines.length - 1);
        }

        //make sure we cant sneak around the outside of the maze
        bounds.add(start.sum(Coord.UP));
        bounds.add(start.sum(new Coord(-1,-1)));
        bounds.add(start.sum(new Coord(1,-1)));
        bounds.add(end.sum(Coord.DOWN));
        bounds.add(end.sum(new Coord(-1,1)));
        bounds.add(end.sum(new Coord(1,1)));

        int min = 0;
        HashSet<Coord> curReachable = new HashSet<Coord>();
        curReachable.add(start);

        do {
            blizzards = new ArrayList<>(blizzards.stream().map(this::updateBlizzard).toList());
            HashSet<Coord> blizzardsPos = (HashSet<Coord>) blizzards.stream().map(x -> x.pos).collect(Collectors.toSet());
            HashSet<Coord> newReachable = new HashSet<Coord>();
            for(Coord c : curReachable) {
                for(Coord adj : c.directNeighbors())
                    if(!bounds.contains(adj) && !blizzardsPos.contains(adj))
                        newReachable.add(adj);
                if(!bounds.contains(c) && !blizzardsPos.contains(c))
                    newReachable.add(c);
            }
            curReachable = newReachable;
            min++;
        } while(!curReachable.contains(end));
        return Integer.toString(min);
    }

    public Blizzard updateBlizzard(Blizzard old) {
        Coord newPos = old.pos.sum(old.facing);
        if(bounds.contains(newPos)) {
            if(old.facing.equals(Coord.UP)) {
                return new Blizzard(new Coord(old.pos.x,maxY - 1), old.facing);
            } else if(old.facing.equals(Coord.DOWN))
                return new Blizzard(new Coord(old.pos.x,1),old.facing);
            else if(old.facing.equals(Coord.RIGHT))
                return new Blizzard(new Coord(1,old.pos.y),old.facing);
            else
                return new Blizzard(new Coord(maxX - 1, old.pos.y),old.facing);
        } else {
            return new Blizzard(newPos,old.facing);
        }
    }


    @Override
    public String part2() {
        ArrayList<Blizzard> blizzards = new ArrayList<>();
        bounds =  new HashSet<Coord>();
        String[] lines = input.split("\n");
        for(int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for(int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                    case '#' -> bounds.add(new Coord(x, y));
                    case '>' -> blizzards.add(new Blizzard(new Coord(x, y), Coord.RIGHT));
                    case '<' -> blizzards.add(new Blizzard(new Coord(x, y), Coord.LEFT));
                    case 'v' -> blizzards.add(new Blizzard(new Coord(x, y), Coord.DOWN));
                    case '^' -> blizzards.add(new Blizzard(new Coord(x, y), Coord.UP));
                }
            }
        }

        maxX = bounds.stream().map(x->x.x).max(Integer::compare).get();
        maxY = lines.length - 1;

        Coord start = null;
        Coord end = null;
        for(int x = 0; x <= maxX; x++) {
            if(!bounds.contains(new Coord(x,0)))
                start = new Coord(x,0);
            if(!bounds.contains(new Coord(x,lines.length - 1)))
                end = new Coord(x,lines.length - 1);
        }

        //make sure we cant sneak around the outside of the maze
        bounds.add(start.sum(Coord.UP));
        bounds.add(start.sum(new Coord(-1,-1)));
        bounds.add(start.sum(new Coord(1,-1)));
        bounds.add(end.sum(Coord.DOWN));
        bounds.add(end.sum(new Coord(-1,1)));
        bounds.add(end.sum(new Coord(1,1)));

        int totalTrip = 0;

        int min = 0;
        HashSet<Coord> curReachable = new HashSet<Coord>();
        curReachable.add(start);
        do {
            blizzards = new ArrayList<>(blizzards.stream().map(this::updateBlizzard).toList());
            HashSet<Coord> blizzardsPos = (HashSet<Coord>) blizzards.stream().map(x -> x.pos).collect(Collectors.toSet());
            HashSet<Coord> newReachable = new HashSet<Coord>();
            for(Coord c : curReachable) {
                for(Coord adj : c.directNeighbors())
                    if(!bounds.contains(adj) && !blizzardsPos.contains(adj))
                        newReachable.add(adj);
                if(!bounds.contains(c) && !blizzardsPos.contains(c))
                    newReachable.add(c);
            }
            curReachable = newReachable;
            min++;
        } while(!curReachable.contains(end));

        totalTrip += min;
        min = 0;
        curReachable = new HashSet<>();
        curReachable.add(end);
        do {
            blizzards = new ArrayList<>(blizzards.stream().map(this::updateBlizzard).toList());
            HashSet<Coord> blizzardsPos = (HashSet<Coord>) blizzards.stream().map(x -> x.pos).collect(Collectors.toSet());
            HashSet<Coord> newReachable = new HashSet<Coord>();
            for(Coord c : curReachable) {
                for(Coord adj : c.directNeighbors())
                    if(!bounds.contains(adj) && !blizzardsPos.contains(adj))
                        newReachable.add(adj);
                if(!bounds.contains(c) && !blizzardsPos.contains(c))
                    newReachable.add(c);
            }
            curReachable = newReachable;
            min++;
        } while(!curReachable.contains(start));

        totalTrip += min;
        min = 0;
        curReachable = new HashSet<>();
        curReachable.add(start);

        do {
            blizzards = new ArrayList<>(blizzards.stream().map(this::updateBlizzard).toList());
            HashSet<Coord> blizzardsPos = (HashSet<Coord>) blizzards.stream().map(x -> x.pos).collect(Collectors.toSet());
            HashSet<Coord> newReachable = new HashSet<Coord>();
            for(Coord c : curReachable) {
                for(Coord adj : c.directNeighbors())
                    if(!bounds.contains(adj) && !blizzardsPos.contains(adj))
                        newReachable.add(adj);
                if(!bounds.contains(c) && !blizzardsPos.contains(c))
                    newReachable.add(c);
            }
            curReachable = newReachable;
            min++;
        } while(!curReachable.contains(end));

        totalTrip += min;
        return Integer.toString(totalTrip);
    }


    public static void main(String[] args) {
        Runner.run(new day24());
    }

}

