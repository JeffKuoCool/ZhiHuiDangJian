package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.text.Html;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.facebook.imageutils.WebpUtil;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.MianDeptBeanD;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.DynamicDetail;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponsePartyDynamicList;
import com.lfc.zhihuidangjianapp.utlis.WebViewUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description: 党建动态详情
 */
public class Act_Dept_Dynamic_Detail extends BaseActivity {

    private String partyDynamicId;
    private String examineId;

    private TextView tvTitle, tvAuthor, tvContent;

    private WebView good_d_web;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_dept_dynamic_detail;
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
        good_d_web = findViewById(R.id.good_d_web);
    }

    @Override
    protected void initData() {
        //type ==1 是首页进来得详情 其余是列表进来得
        partyDynamicId = getIntent().getStringExtra("partyDynamicId");
        String type = getIntent().getStringExtra("type");
       // good_d_web.setOverScrollMode();
        examineId = getIntent().getStringExtra("examineId");
        Log.i("yy--type",type+"=="+partyDynamicId+"==="+examineId);
        if(type.equals("")){
            Map<String, Object> map = new HashMap<>();
            map.put("partyDynamicId", partyDynamicId);
            RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .queryPartyDynamicDetail(map, MyApplication.getLoginBean().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseObserver<DynamicDetail>(getActivity()) {

                        @Override
                        protected void onNext(DynamicDetail response) {
                            Log.e("onNext= ", response.toString());
                            if(response==null)return;
                            Dynamic dynamic = response.getPartyDynamic();
                            tvTitle.setText(dynamic.getTitle());
                            tvAuthor.setText(dynamic.getCreate_name());
                            //tvContent.setText(Html.fromHtml(dynamic.getComment()));

                            WebViewUtils.setWebView(dynamic.getComment(),good_d_web);

                        }

                        @Override
                        protected void onError(Throwable e) {
                            super.onError(e);
                            Log.e("Throwable= ", e.getMessage());
                        }
                    }.actual());
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("examineId",examineId );
            map.put("articleType", type);
            RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .queryExamineDetail(map, MyApplication.getLoginBean().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseObserver<MianDeptBeanD>(getActivity()) {

                        @Override
                        protected void onNext(MianDeptBeanD response) {
                            Log.e("onNext= ", response.toString());
                            if(response==null)return;
                            MianDeptBeanD.ExamineBean  dynamic = response.getExamine();
                            tvTitle.setText(dynamic.getTitle());
                            tvAuthor.setText(dynamic.getArticleAuthor());
                            //tvContent.setText(Html.fromHtml(dynamic.getComment()));

                            WebViewUtils.setWebView(dynamic.getComment(),good_d_web);
                        }

                        @Override
                        protected void onError(Throwable e) {
                            super.onError(e);
                            Log.e("Throwable= ", e.getMessage());
                        }
                    }.actual());

        }

    }
}
