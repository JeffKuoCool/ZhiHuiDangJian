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
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponsePartyDynamicList;

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
        partyDynamicId = getIntent().getStringExtra("partyDynamicId");
        Log.i("yy--partyDynamicId",partyDynamicId+"");
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

                        String ss;
                        boolean contains = dynamic.getComment().contains("\"//");
                        if (contains) {
                            ss = dynamic.getComment().replaceAll("\"//", "https://");
                        } else {
                            ss = dynamic.getComment();
                        }
                        String str = "<head lang=\"zh\"><style type=\"text/css\">div{width: 100% }img{width: 100%}</style></head><div>" + ss + "</div>";
                        final AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                        animation.setDuration(800);
                        animation.setFillAfter(true);
                        WebSettings webSettings = good_d_web.getSettings();
                        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
                        webSettings.setJavaScriptEnabled(true);
                        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
                        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
                        //设置自适应屏幕，两者合用
                        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
                        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
                        //缩放操作
                        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
                        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
                        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
                        //其他细节操作
                        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
                        webSettings.setAllowFileAccess(true); //设置可以访问文件
                        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
                        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
                        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
                        good_d_web.loadDataWithBaseURL("", "" + str, "text/html", "utf-8", "");
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
