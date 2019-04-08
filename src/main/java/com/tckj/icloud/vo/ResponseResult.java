package com.tckj.icloud.vo;

import com.tckj.icloud.constant.Constants;

public class ResponseResult {
    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public ResponseResult(Constants.ResultCodeConstants constants){
        this.code = constants.getCode();
        this.message = constants.getMsg();
    }
    public ResponseResult(Constants.ResultCodeConstants constants,Object data){
        this.code = constants.getCode();
        this.message = constants.getMsg();
        this.data=data;
    }
}
