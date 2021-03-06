package com.beehive.rocketmq.customer.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beehive.rocketmq.customer.service.ESSearchService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * 消费者消费消息路由
 * .<br/>
 * 
 * @ClassName: RocketMQMessageListenerConcurrentlyProcessor
 * @Description: 
 * @version: v1.0.0
 */
@Component
public class RocketMQMessageListenerConcurrentlyProcessor implements MessageListenerConcurrently {
    private static final Logger logger = LoggerFactory.getLogger(RocketMQMessageListenerConcurrentlyProcessor.class);
    private String prex="log_";
    @Autowired
    private ESSearchService searchService;
    /**
     *  默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息<br/>
     *  不要抛异常，如果没有return CONSUME_SUCCESS ，consumer会重新消费该消息，直到return CONSUME_SUCCESS
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if(CollectionUtils.isEmpty(msgs)){
            logger.info("接受到的消息为空，不处理，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = msgs.get(0);
        System.out.println(context.getMessageQueue());
        logger.info("接受到的消息为："+messageExt.toString());
        if(messageExt.getTopic().equals("fzyac-topic")){
            if(messageExt.getTags().equals("fzyac-tag")){
                // 判断该消息是否重复消费（RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）
                // 获取该消息重试次数
                int reconsume = messageExt.getReconsumeTimes();
                if(reconsume >=1){//消息已经重试了1次，如果不需要再次消费，则返回成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                JSONObject json= JSON.parseObject(new String(messageExt.getBody()));
                try {
                    searchService.addJSONDoc(prex+json.getString("client_id"),json.getString("client_id"),json);
                } catch (Exception e) {
                    logger.info("插入数据失败"+json);
                }
                //JSONObject aa= (JSONObject) JSON.parse(messageExt.toString());
                // 处理对应的业务逻辑
            }
        }
        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
    /**
     * byte转对象
     * @param bytes
     * @return
     */
    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

}
