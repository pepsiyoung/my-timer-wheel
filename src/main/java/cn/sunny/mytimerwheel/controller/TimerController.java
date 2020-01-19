package cn.sunny.mytimerwheel.controller;

import cn.sunny.mytimerwheel.entity.People;
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
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                People people;
                try {
                    people = queue.take();
                    String res = String.format("name:%s time:%s", people.getName(), LocalDateTime.now().format(format));
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
                return queue.remove(current);
            }
        }
        return false;
    }
}
