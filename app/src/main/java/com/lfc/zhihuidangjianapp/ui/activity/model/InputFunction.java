package com.lfc.zhihuidangjianapp.ui.activity.model;

/**
 * @date: 2019/3/27
 * @autror: guojian
 * @description:
 */
public class InputFunction {

    private int imgRes;

    private String title;

    public int getImgRes() {
        return imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InputFunction(int imgRes, String title) {
        this.imgRes = imgRes;
        this.title = title;
    }
}
