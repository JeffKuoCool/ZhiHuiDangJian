package com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.Act_Login;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Membership;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Write_Study_Report;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Write_Weekend_Log;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Fgt_Weekend_Details;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.MyInteagalBeabMingXI;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.MyIntegalBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseWorkReport;
import com.lfc.zhihuidangjianapp.ui.activity.model.WorkReport;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Act_Integral extends BaseActivity {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.interal_jf)
    TextView interalJf;
    @BindView(R.id.interal_jf2)
    TextView interalJf2;
    @BindView(R.id.intergral_text3)
    TextView intergral_text3;
    @BindView(R.id.intergral_text4)
    TextView intergral_text4;
    @BindView(R.id.intergral_text5)
    TextView intergral_text5;
    @BindView(R.id.intergral_text6)
    TextView intergral_text6;
    private int size = 10;
    private int num = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(1);
    }

    @Override
    protected void initData() {
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyTotal( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<MyIntegalBean>(getActivity()) {

                    @Override
                    protected void onNext(MyIntegalBean response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                        interalJf.setText(response.getIntegralManagement().getTotal() + "");
                        interalJf2.setText(response.getIntegralManagement().getTodayTotal() + "积分");
                        //loginStatus 登录状态（0：已完成1：未完成）
                        //moneyStatus 党费状态（0：已完成1：未完成）
                        //weekStatus  周报状态 （0：已完成1：未完成）
                        //   bureauCount  心得数量
                        String loginStatus = response.getLoginStatus();
                        String moneyStatus = response.getMoneyStatus();
                        String weekStatus = response.getWeekStatus();
                        Log.i("yy--loginStatus",loginStatus +"=="+moneyStatus+"=="+weekStatus);
                        setImg(loginStatus,intergral_text3);
                        setImg(moneyStatus,intergral_text6);
                        setImg(weekStatus,intergral_text4);
                        intergral_text5.setText("已完成"+response.getBureauCount()+"篇");
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    private void setImg(String loginStatus, TextView intergral_text) {
        if(loginStatus.equals("0")){
            intergral_text.setBackground(getDrawable(R.mipmap.integral3));
            intergral_text.setText("已完成");
            intergral_text.setTextColor(Color.parseColor("#333333"));
            intergral_text.setEnabled(false);
        }else{
            intergral_text.setBackground(getDrawable(R.mipmap.integral2));
            intergral_text.setText("去完成");
            intergral_text.setTextColor(Color.parseColor("#ffffff"));
            intergral_text.setEnabled(true);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @OnClick({R.id.imgBack, R.id.tvRight, R.id.intergral_text3, R.id.intergral_text4, R.id.intergral_text5, R.id.intergral_text6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvRight:
                Intent intent =new Intent(this,Act_Integral_mingxi.class);
                startActivity(intent);

                break;
            case R.id.intergral_text3:

                break;
            case R.id.intergral_text4://写周报
                startActivity(new Intent(this, Act_Write_Weekend_Log.class));
                break;
            case R.id.intergral_text5://写学习心得
                startActivity(new Intent(this, Act_Write_Study_Report.class));
                break;
            case R.id.intergral_text6://党费缴纳
                startActivity(new Intent(getActivity(), Act_Party_Membership.class));
                finish();
                break;
        }
    }
}
