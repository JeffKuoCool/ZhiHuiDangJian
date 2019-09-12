package com.lfc.zhihuidangjianapp.utlis;

import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewUtils {
    public  static void setWebView(String connet, WebView webView){
        String ss;
        boolean contains = connet.contains("\"//");
        if (contains) {
            ss = connet.replaceAll("\"//", "https://");
        } else {
            ss = connet;
        }
        String str = "<head lang=\"zh\"><style type=\"text/css\">.p{font-size:50pt;}div{width: 100%}img{width: 100%}</style></head><div style=\"font-size: 40px\">" + ss + "</div>";
        final AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(800);
        animation.setFillAfter(true);
        WebSettings webSettings = webView.getSettings();
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
        webView.loadDataWithBaseURL("", "" + str, "text/html", "utf-8", "");


    };


}
