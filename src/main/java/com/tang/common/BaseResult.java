package com.tang.common;

import lombok.Data;

@Data
public class BaseResult<T> {
    private String msg;

    private String code;

    private boolean success = false;

    private String errorMsg;

    private T data;


    public BaseResult() {
    }

    public BaseResult(String msg, String code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
    public boolean isSuccess(){
        return this.success;
    }

}
