package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        try (InputStream is = AlertRabbit.class
                .getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            List<Long> store = new ArrayList<>();
            Connection connection;
            Class.forName(properties.getProperty("jdbc.driver"));
            String url = properties.getProperty("jdbc.url");
            String login = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            connection = DriverManager.getConnection(url, login, password);
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(
                            Integer.parseInt(properties.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(store);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail()
                    .getJobDataMap().get("connection");
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into rabbit(created_date) values (?)")) {
                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}