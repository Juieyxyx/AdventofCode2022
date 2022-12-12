package day11;

import java.math.BigInteger;
import java.security.Security;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class day11 {

    public enum Operator implements BiFunction<BigInteger, BigInteger, BigInteger> {
        ADD {
            public BigInteger apply(BigInteger x, BigInteger y) {
                return x.add(y);
            }
        },
        MULTIPLY {
            public BigInteger apply(BigInteger x, BigInteger y) {
                return x.multiply(y);
            }
        };

        public static Operator parse(final String string) {
            return switch (string) {
                case "*" -> MULTIPLY;
                case "+" -> ADD;
                default -> throw new IllegalArgumentException("Invalid operator: " + string);
            };
        }
    }

    public record Operation(Operator operator,
                            Function<BigInteger, BigInteger> lValueSupplier,
                            Function<BigInteger, BigInteger> rValueSupplier) implements Function<BigInteger, BigInteger> {

        public BigInteger apply(final BigInteger oldValue) {
            Objects.requireNonNull(oldValue);
            final var lValue = lValueSupplier.apply(oldValue);
            final var rValue = rValueSupplier.apply(oldValue);
            return operator.apply(lValue, rValue);
        }

        public static Operation parse(String line) {
            line = line.strip();
            if (!line.trim().startsWith("Operation:")) {
                throw new IllegalArgumentException("Not an operation: " + line);
            }
            final var components = line.split(" ");
            final var lValueExpression = components[3];
            final Function<BigInteger, BigInteger> lValueSupplier = "old".equalsIgnoreCase(lValueExpression)
                    ? old -> old
                    : ignored -> new BigInteger(lValueExpression);
            final var operator = Operator.parse(components[4]);
            final var rValueExpression = components[5];
            final Function<BigInteger, BigInteger> rValueSupplier = "old".equalsIgnoreCase(rValueExpression)
                    ? old -> old
                    : ignored -> new BigInteger(rValueExpression);
            return new Operation(operator, lValueSupplier, rValueSupplier);
        }
    }

    /**
     * An observation of how a single monkey behaves
     *
     * @param id             the monkey's unique identifier
     * @param items          your worry level for each belonging currently held by this monkey
     * @param operation      a function describing how your worry level changes when the monkey inspects the item
     * @param divisor        used by the monkey to evaluate your worry level and decide what to do with the item
     * @param targetIfTrue   the ID of the monkey who will receive the item should the test pass
     * @param targetIfFalse  the ID of the monkey who will receive the item should the test fail
     * @param itemsInspected the total number of times this monkey has inspected an item
     */
    public record Monkey(int id, List<BigInteger> items, Operation operation, BigInteger divisor, int targetIfTrue,
                         int targetIfFalse, AtomicReference<BigInteger> itemsInspected) {
        public static Monkey parse(final String block) {
            final var lines = block.split("\n");
            final var id = Integer.parseInt(lines[0].replaceAll("[^0-9]", ""));
            final var startingItems = Arrays.stream(lines[1].strip()
                            .replaceAll("^Starting items: ", "")
                            .split(", "))
                    .map(item -> new BigInteger(item))
                    .collect(Collectors.toList()); // must be mutable
            final var operation = Operation.parse(lines[2]);
            final var divisor = new BigInteger(lines[3].replaceAll("[^0-9]", ""));
            final var targetIfTrue = Integer.parseInt(lines[4].replaceAll("[^0-9]", ""));
            final var targetIfFalse = Integer.parseInt(lines[5].replaceAll("[^0-9]", ""));
            return new Monkey(id, startingItems, operation, divisor, targetIfTrue, targetIfFalse, new AtomicReference<>(BigInteger.ZERO));
        }

        public BigInteger countItemsInspected() {
            return itemsInspected.get();
        }

        public Throw inspectItem(BigInteger reliefFactor) {
            // this assumes monkeys can throw items to themselves
            if (items.isEmpty()) {
                return null;
            }
            var worryLevel = items().remove(0);
            worryLevel = operation().apply(worryLevel);
            worryLevel = worryLevel.divide(reliefFactor);
            final var target = worryLevel.mod(divisor()).equals(BigInteger.ZERO)
                    ? targetIfTrue()
                    : targetIfFalse();
            itemsInspected().updateAndGet(old -> old.add(BigInteger.ONE));
            return new Throw(target, worryLevel);
        }

        public List<Throw> inspectItems(Function<BigInteger, BigInteger> worryUpdater) {
            // this assumes monkeys cannot throw items to themselves
            final var result = items().stream().map(worryLevel -> {
                worryLevel = operation().apply(worryLevel);
                worryLevel = worryUpdater.apply(worryLevel);
                final var target = worryLevel.mod(divisor()).equals(BigInteger.ZERO)
                        ? targetIfTrue()
                        : targetIfFalse();
                return new Throw(target, worryLevel);
            }).toList();
            itemsInspected().updateAndGet(old -> old.add(BigInteger.valueOf(result.size())));
            items().clear();
            return result;
        }

    }

    public record Throw(int target, BigInteger itemWorryLevel) {
    }

    protected static List<Monkey> getInput() {
        final var input = """
                Monkey 0:
                  Starting items: 93, 54, 69, 66, 71
                  Operation: new = old * 3
                  Test: divisible by 7
                    If true: throw to monkey 7
                    If false: throw to monkey 1
                                
                Monkey 1:
                  Starting items: 89, 51, 80, 66
                  Operation: new = old * 17
                  Test: divisible by 19
                    If true: throw to monkey 5
                    If false: throw to monkey 7
                                
                Monkey 2:
                  Starting items: 90, 92, 63, 91, 96, 63, 64
                  Operation: new = old + 1
                  Test: divisible by 13
                    If true: throw to monkey 4
                    If false: throw to monkey 3
                                
                Monkey 3:
                  Starting items: 65, 77
                  Operation: new = old + 2
                  Test: divisible by 3
                    If true: throw to monkey 4
                    If false: throw to monkey 6
                                
                Monkey 4:
                  Starting items: 76, 68, 94
                  Operation: new = old * old
                  Test: divisible by 2
                    If true: throw to monkey 0
                    If false: throw to monkey 6
                                
                Monkey 5:
                  Starting items: 86, 65, 66, 97, 73, 83
                  Operation: new = old + 8
                  Test: divisible by 11
                    If true: throw to monkey 2
                    If false: throw to monkey 3
                                
                Monkey 6:
                  Starting items: 78
                  Operation: new = old + 6
                  Test: divisible by 17
                    If true: throw to monkey 0
                    If false: throw to monkey 1
                                
                Monkey 7:
                  Starting items: 89, 57, 59, 61, 87, 55, 55, 88
                  Operation: new = old + 7
                  Test: divisible by 5
                    If true: throw to monkey 2
                    If false: throw to monkey 5
                """;

        return Arrays.stream(input.split("\n\n"))
                .map(Monkey::parse)
                .toList();
    }

    public static void main(String[] args) {
        //part one:
//        final var monkeys = getInput();
//        final Function<BigInteger, BigInteger> worryUpdater = worryLevel -> worryLevel.divide(BigInteger.valueOf(3));
//        for (int i = 20; --i >= 0; ) {
//            for (final var monkey : monkeys) {
//                for (final var toss : monkey.inspectItems(worryUpdater)) {
//                    monkeys.get(toss.target()).items().add(toss.itemWorryLevel());
//                }
//            }
//        }
//        final var result = monkeys.stream()
//                .map(Monkey::countItemsInspected)
//                .sorted(Comparator.reverseOrder())
//                .limit(2)
//                .reduce(BigInteger::multiply)
//                .get();
//        System.out.println("Part 1: " + result);


//        part 2:
            final var monkeys = getInput();
            final var productOfDivisors = monkeys.stream().map(Monkey::divisor).reduce(BigInteger::multiply).get();
            final Function<BigInteger, BigInteger> worryUpdater = worryLevel -> worryLevel.mod(productOfDivisors);
            for (int i = 10_000; --i >= 0; ) {
                for (final var monkey : monkeys) {
                    for (final var toss : monkey.inspectItems(worryUpdater)) {
                        monkeys.get(toss.target()).items().add(toss.itemWorryLevel());
                    }
                }
            }
            final var result = monkeys.stream()
                    .map(Monkey::countItemsInspected)
                    .sorted(Comparator.reverseOrder())
                    .limit(2)
                    .reduce(BigInteger::multiply)
                    .get();
            System.out.println("Part 2: " + result);
    }
}

