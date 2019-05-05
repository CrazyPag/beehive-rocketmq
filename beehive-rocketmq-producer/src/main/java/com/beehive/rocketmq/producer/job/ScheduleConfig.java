package com.beehive.rocketmq.producer.job;

import com.beehive.rocketmq.producer.Consts.JobConst;
import com.beehive.rocketmq.producer.service.TabSysClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

//@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
//@Component
public class ScheduleConfig {
    //3.添加定时任务
    @Autowired
    private TabSysClientService service;
    @Scheduled(cron = "0 0 23 * * ?")
    private void configureTasks() {
        System.err.println("执行定时任务1: " + LocalDateTime.now());
        List<String> list=service.findAllClient();
        JobConst.list.clear();
        JobConst.list.addAll(list);
    }

}