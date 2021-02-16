package ru.job4j.html;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class Parsing {
    private static final Map<String, Integer> NUM_MONTH = new HashMap<>() {
        {
            put("янв", 1);
            put("фев", 2);
            put("мар", 3);
            put("апр", 4);
            put("май", 5);
            put("июн", 6);
            put("июл", 7);
            put("авг", 8);
            put("сен", 9);
            put("окт", 10);
            put("ноя", 11);
            put("дек", 12);
        }
    };

    public static LocalDateTime dateParser(String text) {
        String[] splitDates = text.split(" ");
        if (splitDates[0].contains("сегодня")) {
            return LocalDate.now().atTime(LocalTime.parse(splitDates[1]));
        } else if (splitDates[0].contains("вчера")) {
            return LocalDate.now().minus(Period.ofDays(1)).atTime(LocalTime.parse(splitDates[1]));
        } else {
            int number = Integer.parseInt(splitDates[0]);
            int month = NUM_MONTH.get(splitDates[1]);
            int year = 2000 + Integer.parseInt(splitDates[2].substring(0, 2));
            return LocalDate.of(year, month, number).atTime(LocalTime.parse(splitDates[3]));
        }
    }
}
