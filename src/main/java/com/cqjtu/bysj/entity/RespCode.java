package com.cqjtu.bysj.entity;

public enum RespCode {

        SUCCESS(200, "操作成功"),
        PLEASE_LOGIN(401,"未登录，请先登录"),
        FAILED(500, "操作失败，请检查网络后重试"),
        EXPIRED(401001,"登录信息已过期，请重新登录");

        private Integer code;
        private String msg;

        RespCode(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
