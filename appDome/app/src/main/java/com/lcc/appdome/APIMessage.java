package com.lcc.appdome;

import java.io.Serializable;
import java.util.List;

public class APIMessage implements Serializable {
    private int code;
    private String  message;
    private List<ResultData> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultData> getResult() {
        return result;
    }

    public void setResult(List<ResultData> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "APIMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}

