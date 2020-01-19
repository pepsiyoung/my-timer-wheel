package cn.sunny.mytimerwheel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@EnableScheduling
@Async
public class ScheduledService {

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        String res = String.format("=====>>>>>[%s]使用cron:%s", threadName, LocalDateTime.now().format(format));
        System.out.println(res);
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduled2() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        String res = String.format("=====>>>>>[%s]fixedDelay:%s", threadName, LocalDateTime.now().format(format));
        System.out.println(res);
    }
}
