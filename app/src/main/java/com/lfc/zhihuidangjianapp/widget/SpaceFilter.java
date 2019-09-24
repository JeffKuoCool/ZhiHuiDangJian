package com.lfc.zhihuidangjianapp.widget;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Administrator on 2018/12/28 0028.
 */

public class SpaceFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
        if (source.equals(" "))
            return "";
        return null;
    }
}
