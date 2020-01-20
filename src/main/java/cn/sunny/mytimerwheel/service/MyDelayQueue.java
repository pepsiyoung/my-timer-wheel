package cn.sunny.mytimerwheel.service;

import cn.sunny.mytimerwheel.entity.People;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.DelayQueue;

@Service
public class MyDelayQueue {

    private DelayQueue<People> queue = new DelayQueue<>();

    @PostConstruct
    public void init() {
        queue.clear();
        for (Integer i = 0; i < 3; i++) {
            long endTime = i * 1000 + System.currentTimeMillis();
            People people = new People(i.toString(), endTime);
            queue.add(people);
        }
    }

    public People take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("中断...");
        }
        return null;
    }

    public void add(String name, Long second) {
        long endTime = second * 1000 + System.currentTimeMillis();
        People people = new People(name, endTime);
        queue.add(people);
    }

    public String getThreadName() {
        return Thread.currentThread().getName();
    }
}
