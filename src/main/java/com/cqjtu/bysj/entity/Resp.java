package com.cqjtu.bysj.entity;

public class Resp<T> {
    private Integer status;

    private String msg;

    private T data;

    public Resp(RespCode code) {
        this.status = code.getCode();
        this.msg = code.getMsg();
    }

    public Resp(RespCode code, T data) {
        this.status = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
