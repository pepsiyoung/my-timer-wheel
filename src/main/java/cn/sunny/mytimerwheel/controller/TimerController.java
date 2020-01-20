package cn.sunny.mytimerwheel.controller;

import cn.sunny.mytimerwheel.entity.People;
import cn.sunny.mytimerwheel.service.MyDelayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.DelayQueue;

@RestController
@RequestMapping(value = "/timer")
public class TimerController {

    private DelayQueue<People> queue = new DelayQueue<>();

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/start")
    public String start() {
        String threadName = Thread.currentThread().getName();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                People people;
                try {
                    people = queue.take();
                    String res = String.format("[%s]name:%s time:%s", threadName, people.getName(), LocalDateTime.now().format(format));
                    System.out.println(res);
                } catch (InterruptedException e) {
                    System.out.println("中断");
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是每3秒执行一次
        timer.schedule(timerTask, 10, 1000);
        return "服务启动";
    }

    @GetMapping("/add/{name}/{second}")
    public void add(@PathVariable String name, @PathVariable Long second) {
        long endTime = second * 1000 + System.currentTimeMillis();
        People people = new People(name, endTime);
        queue.add(people);
    }

    @GetMapping("/list")
    public List<People> list() {

        List<People> list = new ArrayList<>();
        for (Iterator<People> it = queue.iterator(); it.hasNext(); ) {
            list.add(it.next());
        }
        return list;
    }

    @GetMapping("/remove/{name}")
    public Boolean remove(@PathVariable String name) {
        for (Iterator<People> it = queue.iterator(); it.hasNext(); ) {
            People current = it.next();
            if (current.getName().equals(name)) {
                queue.remove(current);
            }
        }
        return true;
    }

    @GetMapping("/size")
    public int size() {
        return queue.size();
    }

    @GetMapping("/pressure")
    public void pressure() {
        for (Integer i = 1; i < 50000; i++) {
            long endTime = i * 1000 + System.currentTimeMillis();
            People people = new People(i.toString(), endTime);
            queue.add(people);
        }
    }

    @Autowired
    MyDelayQueue myDelayQueue;

    @GetMapping("/show")
    public String show() {
        return myDelayQueue.getThreadName();
    }
}
