package com.stedu.bean;

/*
*   封装响应给浏览器的数据 --> JSON
* */
public class RespBean {
    //状态码 10000-成功 10001-失败
    private Integer code;
    //返回的附件信息
    private String msg;
    //返回的数据
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static RespBean ok() {
        RespBean respBean = new RespBean();
        respBean.setCode(10000);

        return respBean;
    }

    public static RespBean ok(String msg) {
        RespBean respBean = new RespBean();
        respBean.setCode(10000);
        respBean.setMsg(msg);

        return respBean;
    }

    public static RespBean ok(String msg, Object data) {
        RespBean respBean = new RespBean();
        respBean.setCode(10000);
        respBean.setMsg(msg);
        respBean.setData(data);

        return respBean;
    }

    public static RespBean error() {
        RespBean respBean = new RespBean();
        respBean.setCode(10001);

        return respBean;
    }

    public static RespBean error(String msg) {
        RespBean respBean = new RespBean();
        respBean.setCode(10001);
        respBean.setMsg(msg);

        return respBean;
    }

    public static RespBean error(String msg, Object data) {
        RespBean respBean = new RespBean();
        respBean.setCode(10001);
        respBean.setMsg(msg);
        respBean.setData(data);

        return respBean;
    }
}
