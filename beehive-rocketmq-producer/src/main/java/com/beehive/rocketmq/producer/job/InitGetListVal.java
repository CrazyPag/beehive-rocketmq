package com.beehive.rocketmq.producer.job;

import com.beehive.rocketmq.producer.Consts.JobConst;
import com.beehive.rocketmq.producer.service.TabSysClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Auther: ouyangxiang
 * @Date: 2019-4-30 20:07
 * @Description:
 */
@Service
public class InitGetListVal {
    @Autowired
    private TabSysClientService service;
    @PostConstruct
    public void run(){
        System.err.println("项目启动执行任务: " + LocalDateTime.now());
        List<String> list=service.findAllClient();
        JobConst.list.clear();
        JobConst.list.addAll(list);
    }
}
