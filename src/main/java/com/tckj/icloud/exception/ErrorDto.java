package com.tckj.icloud.exception;

public class ErrorDto {
    public ErrorDto(Object data){
        this.data=data;
    }
    private Integer code=500;
    private String message="服务器异常，请联系管理员";
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
}
