package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.text.Html;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.DynamicDetail;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureauDetail;
import com.lfc.zhihuidangjianapp.utlis.WebViewUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description: 学习心得详情
 */
public class Act_Strong_Study_Experience extends BaseActivity {

    private String studyStrongBureauId;

    private TextView tvTitle, tvAuthor, tvContent, tvAppTitle;
    private WebView good_d_web;

    private String appTitle = "学习心得";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_strong_study_experience;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        tvTitle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
        tvContent = findViewById(R.id.tv_content);
        tvAppTitle = findViewById(R.id.app_title);
        good_d_web=findViewById(R.id.titleDetaile_context);
    }

    @Override
    protected void initData() {
        studyStrongBureauId = getIntent().getStringExtra("studyStrongBureauId");
        appTitle = getIntent().getStringExtra("appTitle");

        tvAppTitle.setText(appTitle);
        Map<String, Object> map = new HashMap<>();
        map.put("studyStrongBureauId", studyStrongBureauId);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryStudyStrongBureauDetail(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<StudyStrongBureauDetail>(getActivity()) {

                    @Override
                    protected void onNext(StudyStrongBureauDetail response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        StudyStrongBureau studyStrongBureau = response.getStudyStrongBureau();
                        tvTitle.setText(studyStrongBureau.getTitle());
                        tvAuthor.setText(studyStrongBureau.getCreateName());
                       // tvContent.setText(Html.fromHtml(studyStrongBureau.getComment()));
                        WebViewUtils.setWebView(studyStrongBureau.getComment(),good_d_web);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
