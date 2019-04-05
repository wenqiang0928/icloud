package com.tckj.icloud.vo;

public class ErrorResponse extends  ResponseResult {
    public ErrorResponse(Object data) {
        super(500, "服务器异常请联系管理员", data);
    }
}
