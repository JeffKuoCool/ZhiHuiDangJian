package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页---banner详情
 */
public class Act_WebView extends BaseActivity {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tv_titles)
    TextView textTitle;
    @BindView(R.id.webView)
    WebView webView;
    private String url = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        setView();
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 设置允许JS弹窗
        webSettings.setDomStorageEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                if(newProgress==100){
//                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
//                }
//                else{
//                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
//                    pg1.setProgress(newProgress);//设置进度值
//                }

                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //view.loadUrl(url);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(url);
                }


                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                Log.e("onReceivedError", "onReceivedError" + error);
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e("onReceivedSslError", "onReceivedError" + error);

                //注意：super句话一定要删除，或者注释掉，否则又走handler.cancel()默认的不支持https的了。
                //super.onReceivedSslError(view, handler, error);
                //handler.cancel(); // Android默认的处理方式
                //handler.handleMessage(Message msg); // 进行其他处理

                handler.proceed(); // 接受所有网站的证书
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {

                Log.e("onReceivedSslError", "onReceivedError" + errorResponse);
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                Log.e("onPageFinished", "onPageFinished" + url);
                super.onPageFinished(view, url);
            }
        });
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }

    private void setView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        textTitle.setText(intent.getStringExtra("title"));
        url = intent.getStringExtra("url");
        Log.e("TAG", "开始加载URL " + url);
        webView.loadUrl(url);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        try {
            if (webView != null) {
                webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                webView.clearHistory();
                webView.removeAllViews();
                webView.destroy();
                webView = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @OnClick(R.id.imgBack)
    public void onImgBackClicked() {
        finish();
    }

}
