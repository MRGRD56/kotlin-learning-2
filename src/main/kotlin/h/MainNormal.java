package h;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainNormal {
    public static void main(String[] args) {
        Map<Integer, String> numberMap = IntStream.range(0, 30 + 1)
                .filter(value -> value % 2 == 0)
                .map(value -> value * 10)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        value -> Integer.toString(value, 16),
                        (a, b) -> {throw new IllegalStateException();},
                        LinkedHashMap::new));

        System.out.printf("[%s] %s%n", numberMap.getClass().getName(), numberMap);

        // an alternative way

        Map<Integer, String> numberMapClassical = new LinkedHashMap<>();
        for (int i = 0; i <= 30; i++) {
            if (i % 2 == 0) {
                int value = i * 10;
                numberMapClassical.put(value, Integer.toString(value, 16));
            }
        }

        System.out.println("\nClassic");
        System.out.printf("[%s] %s%n", numberMapClassical.getClass().getName(), numberMapClassical);
    }
}