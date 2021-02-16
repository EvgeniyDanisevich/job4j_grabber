package ru.job4j.grabber;

import ru.job4j.html.SqlRuParse;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            cnn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cnn.prepareStatement(
                "insert into posts(name, text, link, created) "
                        + "values (?, ?, ?, ?)")) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getText());
            statement.setString(3, post.getUrl().toString());
            statement.setTimestamp(4, Timestamp.valueOf(post.getLocalDateTime()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement(
                "select * from posts"
        )) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(
                            new Post(
                                    resultSet.getString("name"),
                                    new URL(resultSet.getString("link")),
                                    resultSet.getString("text"),
                                    resultSet.getTimestamp("created").toLocalDateTime()
                            )
                    );
                }
            } catch (SQLException | MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        Post post = new Post();
        try (PreparedStatement statement = cnn.prepareStatement(
                "select * from posts where id = ?"
        )) {
            statement.setInt(1, Integer.parseInt(id));
            statement.execute();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post.setTitle(resultSet.getString("name"));
                    post.setUrl(new URL(resultSet.getString("link")));
                    post.setText(resultSet.getString("text"));
                    post.setLocalDateTime(resultSet.getTimestamp("created").toLocalDateTime());
                }
            }
        } catch (SQLException | MalformedURLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream is = PsqlStore.class.getClassLoader()
                .getResourceAsStream("grabber.properties")) {
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PsqlStore psqlStore = new PsqlStore(properties);
        Post post1 = new SqlRuParse()
                .detail("https://www.sql.ru/forum"
                        + "/1333457/vakansiya-programmist-administrator-sql-server-moskva");
        Post post2 = new SqlRuParse()
                .detail("https://www.sql.ru/forum"
                        + "/1333468/full-stack-junior-php-mysql-moskva");

        psqlStore.save(post1);
        psqlStore.save(post2);

        System.out.println(psqlStore.getAll());
        System.out.println(psqlStore.findById("2"));
    }
}