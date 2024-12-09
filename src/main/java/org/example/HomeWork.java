package org.example;


import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {

        if (actionList == null) {
            throw new IllegalArgumentException("Список не должен быть null");
        }

        if (actionList.size() > 100000) {
            throw new IllegalArgumentException("Прервышено максимальное значение журанала. Максимальное количество операций в журнале — 100000");
        }

        Treap<Double> treap = new Treap<>();
        Double profit = 0.0;

        for (String operation : actionList) {
            String[] strings = operation.split(" ");
            double money = Double.parseDouble(strings[1].trim());
            switch (strings[0].trim()) {
                case "BID":
                    treap.add(Double.valueOf(money));
                    break;

                case "SALE":
                    profit += treap.getProfit(Integer.parseInt(strings[2]), money);
                    break;

                case "DEL":
                    treap.remove(Double.valueOf(money));
                    break;

                case "QUIT":
                    return profit;
            }
        }

        return profit;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1">https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1</a><br/>
     */
    public List<Integer> getLeaveOrder(List<String> actionList) {

        if (actionList == null) {
            throw new IllegalArgumentException("Список не должен быть null");
        }

        Treap<Integer> treap = new Treap<>();
        List<Integer> result = new ArrayList<>();
        int y = 0;
        for (String action : actionList) {
            String[] strings = action.split(" ");
            switch (strings[0].trim()) {
                case "+": {
                    treap.add(((y + Integer.parseInt(strings[1])) % 1_000_000_000));
                    y = 0;
                    break;
                }
                case "?": {
                    y = treap.next(Integer.parseInt(strings[1]), -1);
                    result.add(y);
                    break;
                }
            }
        }
        return result;
    }
}
