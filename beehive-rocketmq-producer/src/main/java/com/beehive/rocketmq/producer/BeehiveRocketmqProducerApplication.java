package com.beehive.rocketmq.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.beehive.rocketmq.producer.service.mapper")
public class BeehiveRocketmqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeehiveRocketmqProducerApplication.class, args);
    }

}
