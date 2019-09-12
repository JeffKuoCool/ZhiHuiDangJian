package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act;

import android.text.Html;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.bean.BaseBean;
import com.lfc.zhihuidangjianapp.bean.NoticeAnnouncementsBean;
import com.lfc.zhihuidangjianapp.bean.QueryHomeNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.queryNoticeAnnouncementDetailBean;
import com.lfc.zhihuidangjianapp.utlis.WebViewUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公示公告详情
 */
public class Act_Announcement extends BaseActivity {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_titles)
    TextView tv_titles;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.good_d_web)
    WebView good_d_web;

    String noticeAnnouncementId = "";

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
        ButterKnife.bind(this);
        initImmersionBar(1);
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    protected void initData() {
        noticeAnnouncementId = getIntent().getStringExtra("id");
        queryNoticeAnnouncementDetail();
        tv_titles.setText("公告公示");
    }
    /**
     * 查看公告详情信息
     */
    public void queryNoticeAnnouncementDetail() {
        loding.show();
        HttpHelper.queryNoticeAnnouncementDetail(noticeAnnouncementId, new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
                loding.dismiss();
            }

            @Override
            public void onSucceed(String succeed) {
                loding.dismiss();
                Gson gson = new Gson();
                queryNoticeAnnouncementDetailBean entity = gson.fromJson(succeed, queryNoticeAnnouncementDetailBean.class);
                if (entity.getCode() == 0) {
//                    tvTitle.setText(entity.getData().getNoticeAnnouncement().getAnnouncementTitle());
//                    webView.loadDataWithBaseURL(null, entity.getData().getNoticeAnnouncement().getAnnouncementComtent(), "text/html", "UTF-8", null);
                    tvTitle.setText(entity.getData().getNoticeAnnouncement().getAnnouncementTitle());
                    tvAuthor.setText(entity.getData().getNoticeAnnouncement().getAuthor());
                    //tvContent.setText(Html.fromHtml(entity.getData().getNoticeAnnouncement().getAnnouncementComtent()));
                    WebViewUtils.setWebView(entity.getData().getNoticeAnnouncement().getAnnouncementComtent(),good_d_web);
                }
            }

            @Override
            public void onError(String error) {
                loding.dismiss();
            }
        });
    }
}
