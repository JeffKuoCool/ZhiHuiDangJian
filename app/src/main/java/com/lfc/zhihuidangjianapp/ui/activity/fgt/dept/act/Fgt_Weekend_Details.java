package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.WeekendDetailsBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.Forest;
import com.lfc.zhihuidangjianapp.ui.activity.model.ForestDetail;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseForestDetail;
import com.lfc.zhihuidangjianapp.utlis.WebViewUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/**
 * @date: 2019-09-09
 * @autror: 仝小丫
 * @description: 周报详情
 */
public class Fgt_Weekend_Details extends BaseActivity {
    private TextView tv_text1, tv_text2,tv_text3,tv_text5,tv_text6;

    private WebView tv_text4,tv_text7;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weekend_detail;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        tv_text1 = findViewById(R.id.tv_text1);
        tv_text2 = findViewById(R.id.tv_text2);
        tv_text3 = findViewById(R.id.tv_text3);
        tv_text4 = findViewById(R.id.tv_text4);
        tv_text5 = findViewById(R.id.tv_text5);
        tv_text6 = findViewById(R.id.tv_text6);
        tv_text7 = findViewById(R.id.tv_text7);

    }

    @Override
    protected void initData() {
        String weeklyWorkReportId = getIntent().getStringExtra("weeklyWorkReportId");
        Map<String, Object> map = new HashMap<>();
        map.put("weeklyWorkReportId", weeklyWorkReportId);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryWeeklyWorkReportDetail(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<WeekendDetailsBean>(getActivity()) {

                    @Override
                    protected void onNext(WeekendDetailsBean response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        WeekendDetailsBean.WeeklyWorkReportBean detail = response.getWeeklyWorkReport();
                         tv_text1.setText(detail.getAuthor());
                        tv_text2.setText(detail.getUserName());
                        tv_text3.setText(detail.getReleaseDate());
                       // tv_text4.setText(detail.getComment1());
                        tv_text5.setText(detail.getComment2());
                        tv_text6.setText(detail.getComment3());
                      //  tv_text7.setText(detail.getComment4());
                        WebViewUtils.setWebView(detail.getComment1(),tv_text4);
                        WebViewUtils.setWebView(detail.getComment4(),tv_text7);

                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
