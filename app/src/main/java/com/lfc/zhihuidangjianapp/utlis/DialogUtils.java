package com.lfc.zhihuidangjianapp.utlis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.widget.UIAlertView;



/**
 * Created by Administrator on 2016/7/28 0028.
 */
public abstract class DialogUtils {


    public DialogUtils(Activity activity, String title, String message, String btn_left, String btn_right){
        final UIAlertView deslDialog = new UIAlertView(activity, title, message, btn_left, btn_right);
        deslDialog.show();
        deslDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
            @Override
            public void doLeft() {
                deslDialog.dismiss();
                doClickLeft();
            }

            @Override
            public void doRight() {
                deslDialog.dismiss();
                doClickRight();
            }
        });
    }

    public abstract void doClickLeft();
    public abstract void doClickRight();
}
