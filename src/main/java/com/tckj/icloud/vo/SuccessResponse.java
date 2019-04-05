package com.tckj.icloud.vo;

public class SuccessResponse extends ResponseResult {
    public SuccessResponse(Object data) {
        super(200,"请求成功",data);
    }
}
