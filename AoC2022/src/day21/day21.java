package day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class day21 {
    public static void main(final String[] args) throws IOException {

        final Map<String, Monkey> monkeys = Files.readAllLines(Paths.get("AoC2022/inputs/input_day21.txt"))
                .stream()
                .map(String::trim)
                .map(Monkey::new)
                .collect(Collectors.toMap(Monkey::getName,
                        Function.identity()));
        monkeys.forEach((name, monkey) -> monkey.linkMonkeys(monkeys));

        // Part 1
        System.out.println("part1:" + monkeys.get("root").getValue());

        // Part 2
        System.out.println("part2:" +  monkeys.get("humn").calculateNeededValue());
    }
}





