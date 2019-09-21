package com.lfc.zhihuidangjianapp.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.databinding.ActivitySplashBinding;

/**
 * Created by ${TXY}
 * Created on 2019/9/21
 * PackageName com.lfc.zhihuidangjianapp.ui.activity
 * REMARK ${隐私条款}
 **/
public class Act_YinSiActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_yinsi;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

        ImmersionBar.with(this).statusBarDarkFont(true).init();

        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {

    }
}
