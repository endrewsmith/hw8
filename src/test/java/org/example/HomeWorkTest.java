package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HomeWorkTest {
    HomeWork homeWork = new HomeWork();

    private static List<String> parseLines(String str) {
        return Arrays.stream(str.split("\n")).collect(Collectors.toList());
    }

    // Тесты к первой задаче

    @Test
    void checkFirst() {

        // Проверим граничные условия
        // Проверка на null
        assertThrows(IllegalArgumentException.class, () -> {
            assertEquals(0.06, homeWork.getProfit(null));
        });
        // Проверка на превышения журнала записями
        String str = "";
        for (int i = 0; i < 100002; i++) {
            str += "BID 0.01\n";
        }
        List<String> list = parseLines(str);
        assertThrows(IllegalArgumentException.class, () -> {
            homeWork.getProfit(list);
        });

        // Обычные тесты
        assertEquals(0.06, homeWork.getProfit(parseLines(
                "BID 0.01\n" +
                        "BID 10000\n" +
                        "BID 5000\n" +
                        "BID 5000\n" +
                        "SALE 7000 3\n" +
                        "DEL 5000\n" +
                        "SALE 3000 3\n" +
                        "SALE 0.01 3")), 0.009);

        assertEquals(0.07, homeWork.getProfit(parseLines(
                "BID 0.01\n" +
                        "BID 10000\n" +
                        "BID 5000\n" +
                        "BID 5000\n" +
                        "SALE 7000 3\n" +
                        "DEL 4000\n" +
                        "SALE 2000 3\n" +
                        "SALE 0.01 3")), 0.009);

    }

    @Test
    void checkSecond() {

        // Граничные условия
        // Проверка на аргумент null
        assertThrows(IllegalArgumentException.class, () -> {
            homeWork.getLeaveOrder(null);
        });

        // Если передается пустой массив, то и вернется пустой
        assertEquals(asList(), new ArrayList<String>());

        // Обычные тесты
        assertEquals(
                List.of(3),
                homeWork.getLeaveOrder(parseLines("+ 3\n" +
                        "? 3"))
        );

        assertEquals(asList(3, 4), homeWork.getLeaveOrder(parseLines("+ 1\n" +
                "+ 3\n" +
                "+ 3\n" +
                "? 2\n" +
                "+ 1\n" +
                "? 4")));
    }
}
