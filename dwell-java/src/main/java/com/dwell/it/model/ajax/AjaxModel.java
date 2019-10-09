package com.dwell.it.model.ajax;

import java.io.Serializable;
import java.util.HashMap;

public class AjaxModel implements Serializable {

    private String request_id;

    private int status;

    private String msg;

    private HashMap<String, HashMap<String, ContactModel>> data;


    public AjaxModel(String request_id, int status, String msg, HashMap<String, HashMap<String, ContactModel>> data) {
        this.request_id = request_id;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public AjaxModel() {
    }

    @Override
    public String toString() {
        return "AjaxModel{" +
                "request_id='" + request_id + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, HashMap<String, ContactModel>> getData() {
        return data;
    }

    public void setData(HashMap<String, HashMap<String, ContactModel>> data) {
        this.data = data;
    }
}
