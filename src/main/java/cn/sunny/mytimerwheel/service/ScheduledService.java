package cn.sunny.mytimerwheel.service;

import cn.sunny.mytimerwheel.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;

@Slf4j
@Component
@EnableScheduling
@Async
public class ScheduledService {

    final
    MyDelayQueue myDelayQueue;

    public ScheduledService(MyDelayQueue myDelayQueue) {
        this.myDelayQueue = myDelayQueue;
    }

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled() {
        String threadName = Thread.currentThread().getName();
//        String res = String.format("=====>>>>>[%s]使用cron:%s", threadName, LocalDateTime.now().format(format));
//        System.out.println(res);

        People people = myDelayQueue.take();
        String res = "";
        if(people != null)
            res = String.format("[%s]name:%s time:%s", threadName, people.getName(), LocalDateTime.now().format(format));
        System.out.println(res);
    }

//    @Scheduled(fixedDelay = 5000)
//    public void scheduled2() {
//        String threadName = Thread.currentThread().getName();
//        String res = String.format("=====>>>>>[%s]fixedDelay:%s", threadName, LocalDateTime.now().format(format));
//        System.out.println(res);
//    }
}
