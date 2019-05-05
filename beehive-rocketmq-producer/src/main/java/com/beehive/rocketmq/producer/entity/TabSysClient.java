package com.beehive.rocketmq.producer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: ouyangxiang
 * @Date: 2019-4-30 11:50
 * @Description:
 */
public class TabSysClient implements Serializable {
    private int id;
    private String client_id;
    private String client_name;
    private Date create_time;
    private Date update_time;

    public TabSysClient() {

    }
    public TabSysClient(int id, String client_id, String client_name, Date create_time, Date update_time) {
        this.id = id;
        this.client_id = client_id;
        this.client_name = client_name;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
