package com.beehive.rocketmq.producer.service.impl;

import com.beehive.rocketmq.producer.service.TabSysClientService;
import com.beehive.rocketmq.producer.service.mapper.TabSysClientServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: ouyangxiang
 * @Date: 2019-4-30 11:56
 * @Description:
 */
@Service
public class TabSysClientServiceImpl implements TabSysClientService {
    @Autowired
    private TabSysClientServiceMapper mapper;
    @Override
    public List<String> findAllClient() {
        return mapper.getAllClient();
    }
}
