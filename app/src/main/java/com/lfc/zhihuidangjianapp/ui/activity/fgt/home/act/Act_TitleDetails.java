package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 条目详情
 */
public class Act_TitleDetails extends BaseActivity {
    @BindView(R.id.TitleDetailsBack)
    ImageView TitleDetailsBack;
    @BindView(R.id.TitleDetail_title)
    TextView TitleDetail_title;
    @BindView(R.id.titleDetaile_context)
    WebView titleDetaile_context;

    TextView tvTitle;
    @Override
    protected int getLayoutId() {
        return R.layout.act_titledetails;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        findViewById(R.id.TitleDetailsBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = findViewById(R.id.tv_title);


        if(getIntent().getStringExtra("title")!=null){
            String title = getIntent().getStringExtra("title");
            String context = getIntent().getStringExtra("context");
            TitleDetail_title.setText("专题专栏");
//            WebSettings settings = titleDetaile_context.getSettings();
//            settings.setUseWideViewPort(true);
//            settings.setLoadWithOverviewMode(true);
//            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            settings.setJavaScriptEnabled(true);
            tvTitle.setText(title);
           // titleDetaile_context.loadDataWithBaseURL(null, context, "text/html" , "utf-8", null);
            String ss;
            boolean contains = context.contains("\"//");
            if (contains) {
                ss = context.replaceAll("\"//", "https://");
            } else {
                ss = context;
            }
            String str = "<head lang=\"zh\"><style type=\"text/css\">div{width: 100% }img{width: 100%}</style></head><div>" + ss + "</div>";
            final AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(800);
            animation.setFillAfter(true);
            WebSettings webSettings = titleDetaile_context.getSettings();
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
            titleDetaile_context.loadDataWithBaseURL("", "" + str, "text/html", "utf-8", "");
        }

    }

    @Override
    protected void initData() {

    }
}
