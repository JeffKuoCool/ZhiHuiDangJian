package com.lfc.zhihuidangjianapp.ui.activity.model;


import com.google.gson.annotations.SerializedName;


public class BaseResponse<T> {
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private T data;


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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccessful() {
//        if (BuildConfig.DEBUG) {
//            Log.d("BaseResponse", "code=" + code + "," + "data=" + data);
//        }
        return getCode() == 0 && data != null;
    }

    public boolean isLoginFailure() {
        return getCode() == 1 || getCode() == 2;
    }

}
