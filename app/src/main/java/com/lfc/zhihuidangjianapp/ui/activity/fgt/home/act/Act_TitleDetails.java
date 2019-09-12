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
import com.lfc.zhihuidangjianapp.utlis.WebViewUtils;

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
    @BindView(R.id.tv_author)
    TextView tv_author;
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
            String author = getIntent().getStringExtra("author");

            TitleDetail_title.setText("专题专栏");
//            WebSettings settings = titleDetaile_context.getSettings();
//            settings.setUseWideViewPort(true);
//            settings.setLoadWithOverviewMode(true);
//            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            settings.setJavaScriptEnabled(true);
            tvTitle.setText(title);
            tv_author.setText(author);
           // titleDetaile_context.loadDataWithBaseURL(null, context, "text/html" , "utf-8", null);
            WebViewUtils.setWebView(context,titleDetaile_context);
        }

    }

    @Override
    protected void initData() {

    }
}
