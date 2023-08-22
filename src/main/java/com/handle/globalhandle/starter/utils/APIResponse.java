package com.handle.globalhandle.starter.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @功能描述: 响应报文，统一封装类
 * @创建人: bixd.
 * @创建时间: 2022/7/6
 * @版本: 1.0
 */
@Data
public class APIResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
    private String trace;
    private Long count;
    private Long pageSize;
    private Long pageNo;
    private Long totalPage;
    private Long timestamp;

    public APIResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public APIResponse(ResponeCode responseCode) {
        this.timestamp = System.currentTimeMillis();
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
    }

    public APIResponse(ResponeCode responseCode, String msg) {
        this(responseCode);
        this.trace = msg;
    }

    public APIResponse(ResponeCode responseCode, T data) {
        this(responseCode);
        this.data = data;
    }

    public APIResponse(ResponeCode responseCode, String msg, T data) {
        this(responseCode);
        this.trace = msg;
        this.data = data;
    }

    public APIResponse(ResponeCode responseCode, T data, Long count) {
        this(responseCode);
        this.data = data;
        this.count = count;
    }

    public APIResponse(ResponeCode responseCode, String msg, T data, Long count) {
        this(responseCode);
        this.trace = msg;
        this.data = data;
        this.count = count;
    }

    public APIResponse(ResponeCode responseCode, String msg, T data, Long count, Long pageSize, Long pageNo, Long totalPage) {
        this(responseCode);
        this.trace = msg;
        this.data = data;
        this.count = count;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "timestamp=" + timestamp +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", trace='" + trace + '\'' +
                ", data=" + data +
                ", count=" + count +
                '}';
    }
}
