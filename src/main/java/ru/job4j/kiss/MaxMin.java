package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MaxMin {
    private  <T> T find(List<T> value, Comparator<T> comparator, Predicate<Integer> predicate) {
        T result = value.get(0);
        for (T t : value) {
            if (predicate.test(comparator.compare(result, t))) {
                result = t;
            }
        }

        return result;
    }

    public <T> T max(List<T> value, Comparator<T> comparator) {
        return find(value, comparator, integer -> integer < 0);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return find(value, comparator, integer -> integer > 0);
    }
}