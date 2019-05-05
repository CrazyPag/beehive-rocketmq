package com.beehive.rocketmq.producer.controller;

import com.alibaba.fastjson.JSONObject;
import com.beehive.rocketmq.producer.Consts.JobConst;
import com.beehive.rocketmq.producer.service.TabSysClientService;
import com.beehive.rocketmq.producer.utils.ParamsToMapUtils;
import com.beehive.rocketmq.producer.utils.RequestResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: ouyangxiang
 * @Date: 2019-5-5 19:35
 * @Description:
 */
@RestController
public class SendMessageRocketMqController {

    @Autowired
    private TabSysClientService service;
    @Autowired
    private DefaultMQProducer defaultMQProducer;
    private String topic ="fzyac-topic";
    private String tag="fzyac-tag";
    @RequestMapping("/nowClientInfo")
    public RequestResult sendEventMsg(){
        RequestResult requestResult =new RequestResult();
        try {
            List<String> list=service.findAllClient();
            JobConst.list.clear();
            JobConst.list.addAll(list);
        }catch (Exception e){
            requestResult.setCode(30056);
            requestResult.setDesc("更新失败");
        }
        return requestResult;
    }

    @RequestMapping("/sendToRock")
    public RequestResult sendToRock(String client_id,String event, HttpServletRequest request) {
        RequestResult result =new RequestResult();
        if(client_id.equals("")||client_id==null){
            result.setCode(10901);
            result.setDesc("client不能为空");
            return result;
        }
        if(event.equals("")||event==null){
            result.setCode(10901);
            result.setDesc("event不能为空");
            return result;
        }
        List<String> list=JobConst.list;
        if(!list.contains(client_id)){
            result.setCode(10901);
            result.setDesc("client_id不存在");
            return result;
        }
        try {
            Map<String,Object> map= ParamsToMapUtils.getParameterMap(request);
            SimpleDateFormat sdFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate= sdFormat.format(new Date());
            map.put("create_time",nowDate);
            map.put("id", UUID.randomUUID().toString().replace("-",""));
            JSONObject json=new JSONObject(map);
            Message sendMsg = new Message(topic, tag, json.toJSONString().getBytes(RemotingHelper.DEFAULT_CHARSET));
            //默认3秒超时
            SendResult sendResult = defaultMQProducer.send(sendMsg);
        }catch (Exception e){
            result.setCode(30057);
            result.setDesc("发送失败");
        }
        return result;
    }


    @RequestMapping("/")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RequestResult returnL(){
        RequestResult result=new RequestResult();
        result.setCode(403);
        return result;
    }
}
