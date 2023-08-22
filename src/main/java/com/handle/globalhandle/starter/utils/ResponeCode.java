package com.handle.globalhandle.starter.utils;

public enum ResponeCode {
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    UNAUTHORIZED(401, "未认证"),
    PARAMS_ERROR(402,"参数错误"),
    NO_PERMISSION(403,"无权限"),
    NOT_FOUND(404, "接口不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    UPLOAD_SUCESS(200, "单文件上传成功"),
    UPLOAD_FAIL(400, "单文件上传失败"),
    UPLOAD_MULTI_SUCESS(200, "多文件上传成功"),
    UPLOAD_MULTI_FAIL(400, "多文件上传全部失败");


    private int code;
    private String msg;

    ResponeCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
