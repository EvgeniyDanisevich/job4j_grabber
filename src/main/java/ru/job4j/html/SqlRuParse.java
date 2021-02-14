package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class SqlRuParse {
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

    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 6; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                Element data = td.parent().child(5);
                System.out.println(dateParser(data.text()));
            }
        }
    }

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