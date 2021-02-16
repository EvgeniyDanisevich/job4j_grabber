package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 6; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                Element data = td.parent().child(5);
                System.out.println(Parsing.dateParser(data.text()));
                System.out.println(details(href.attr("href")));
                System.out.println(date(href.attr("href")));
            }
        }
    }

    public static String details(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc.select(".msgBody").get(1).text();
    }

    public static LocalDateTime date(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return Parsing.dateParser(doc.select(".msgFooter").get(0).text().split(" \\[")[0]);
    }
}