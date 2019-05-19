package com.skypyb.model;

import java.util.List;

/**
 * layui 的表格功能需要的参数必须是这个格式
 */
public class LaydataResponse<D> {
    private int code;
    private String msg;
    private int count;
    private List<D> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
    }
}
