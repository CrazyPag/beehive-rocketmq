package com.beehive.rocketmq.customer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.beehive.rocketmq.customer.repository.ESRepository;
import com.beehive.rocketmq.customer.service.ESSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ESSearchServiceImpl implements ESSearchService {

    @Autowired
    private ESRepository eSRepository;

    @Override
    public boolean buildIndex(String index) {
        return eSRepository.buildIndex(index);
    }

   /* @Override
    public boolean delIndex(String index) {
        return eSRepository.deleteIndex(index);
    }*/

    @Override
    public Map<String, Object> searchDataByParam(String index, String type, String id) {
        return eSRepository.searchDataByParam(index, type, id);
    }

    @Override
    public void updateDataById(JSONObject data, String index, String type, String id) {
        eSRepository.updateDataById(data, index, type, id);
    }

    @Override
    public String addTargetDataALL(JSONObject data, String index, String type, String id) {
        return eSRepository.addTargetDataALL(data, index, type, id);
    }

    @Override
    public void delDataById(String index, String type, String id) {
        eSRepository.delDataById(index, type, id);
    }

    @Override
    public boolean isIndexExist(String index) {
        return eSRepository.isIndexExist(index);
    }

    @Override
    public String addJSONDataDoc(String index, String type, Map<String, Object> map) throws Exception {
        return eSRepository.addJSONDataDoc(index,type,map);
    }

    @Override
    public String addJSONDoc(String index, String type, JSONObject json) throws Exception {
        return eSRepository.addJSONDoc(index,type,json);
    }

}