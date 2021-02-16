package ru.job4j.html;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String title;
    private URL url;
    private String text;
    private LocalDateTime localDateTime;

    public Post(String title, URL url, String text, LocalDateTime localDateTime) {
        this.title = title;
        this.url = url;
        this.text = text;
        this.localDateTime = localDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(title, post.title)
                && Objects.equals(url, post.url)
                && Objects.equals(text, post.text)
                && Objects.equals(localDateTime, post.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, text, localDateTime);
    }

    @Override
    public String toString() {
        return "Post{"
                + "title='" + title + '\''
                + ", url=" + url
                + ", text='" + text + '\''
                + ", localDateTime=" + localDateTime
                + '}';
    }
}
