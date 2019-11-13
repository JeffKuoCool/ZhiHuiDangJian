package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.MonthDetailsBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.WeekendDetailsBean;
import com.lfc.zhihuidangjianapp.utlis.WebViewUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-09-09
 * @autror: 仝小丫
 * @description: 月报详情
 */
public class Fgt_Month_Details extends BaseActivity {
    private TextView tv_text1, tv_text2,tv_text3;

    private WebView tv_text4,tv_text5,tv_text6,tv_text7,tv_text8,tv_text9;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_month_detail;
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
        tv_text8 = findViewById(R.id.tv_text8);
        tv_text9 = findViewById(R.id.tv_text9);

    }

    @Override
    protected void initData() {
        String monthWorkReportId = getIntent().getStringExtra("monthWorkReportId");
        Map<String, Object> map = new HashMap<>();
        map.put("monthWorkReportId", monthWorkReportId);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMonthWorkReportDetail(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<MonthDetailsBean>(getActivity()) {

                    @Override
                    protected void onNext(MonthDetailsBean response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        MonthDetailsBean.MonthWorkReportBean detail = response.getMonthWorkReport();
                        tv_text1.setText(detail.getDept());
                        tv_text2.setText(stampToDate(detail.getCreateTime()));
                        tv_text3.setText(detail.getCreateName());
                      /*  tv_text4.setText(detail.getDeptPartyMeetingConveneSituation());
                        tv_text5.setText(detail.getDeptCommitteeConveneSituation());
                        tv_text6.setText(detail.getCollectiveLearningConveneSituation());
                        tv_text7.setText(detail.getThemeDayConveneSituation());
                        tv_text8.setText(detail.getOtherConveneSituation());
                        tv_text9.setText(detail.getOtherReportingMatters());*/
                        WebViewUtils.setWebView(detail.getDeptPartyMeetingConveneSituation(),tv_text4);
                        WebViewUtils.setWebView(detail.getDeptCommitteeConveneSituation(),tv_text5);
                        WebViewUtils.setWebView(detail.getCollectiveLearningConveneSituation(),tv_text6);
                        WebViewUtils.setWebView(detail.getThemeDayConveneSituation(),tv_text7);
                        WebViewUtils.setWebView(detail.getOtherConveneSituation(),tv_text8);
                        WebViewUtils.setWebView(detail.getOtherReportingMatters(),tv_text9);




                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
    /*
     * 将时间戳转换为时间
     */
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
}
