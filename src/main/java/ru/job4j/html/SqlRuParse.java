package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
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

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element el : row) {
                Element innerElement = el.child(0);
                URL url = new URL(innerElement.attr("href"));
                posts.add(detail(url.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        Post post = new Post();
        try {
            Document doc = Jsoup.connect(link).get();
            post.setTitle(doc.select(".messageHeader").get(0).text().split(" \\[")[0]);
            post.setUrl(new URL(link));
            post.setText(details(link));
            post.setLocalDateTime(date(link));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }
}