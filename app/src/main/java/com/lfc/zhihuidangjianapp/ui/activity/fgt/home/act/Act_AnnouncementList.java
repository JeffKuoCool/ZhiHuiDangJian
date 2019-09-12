package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.bean.BaseBean;
import com.lfc.zhihuidangjianapp.bean.NoticeAnnouncementsListBean;
import com.lfc.zhihuidangjianapp.bean.QueryHomeNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Craftsman_Training;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.adapter.AnnouncementListAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.queryNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公告公示
 */
public class Act_AnnouncementList extends BaseActivity {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_announcement_list;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initImmersionBar(1);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        queryNoticeAnnouncementPageList();
    }


    @OnClick(R.id.imgBack)
    public void onImgBackClicked() {
        finish();
    }

    @OnClick(R.id.imgSearch)
    public void onImgSearchClicked() {
    }

    List<queryNoticeAnnouncementPageListBean.DataBean.NoticeAnnouncementListBean.DatasBean> datas = new ArrayList<>();

    /**
     * 分页公告信息
     */
    public void queryNoticeAnnouncementPageList() {
        HttpHelper.queryNoticeAnnouncementPageList(new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
            }

            @Override
            public void onSucceed(String succeed) {
                Gson gson = new Gson();
                queryNoticeAnnouncementPageListBean entity = gson.fromJson(succeed, queryNoticeAnnouncementPageListBean.class);
                if (entity.getCode() == 0) {
                    for (int i = 0; i < entity.getData().getNoticeAnnouncementList().getDatas().size(); i++) {
                        datas.add(entity.getData().getNoticeAnnouncementList().getDatas().get(i));
                    }
                    setRecyclerView(datas);
                } else {
                    ToastUtils.show(entity.getMsg());
                }
            }

            @Override
            public void onError(String error) {
                ToastUtils.show(error);
            }
        });
    }


    private void setRecyclerView(List<queryNoticeAnnouncementPageListBean.DataBean.NoticeAnnouncementListBean.DatasBean> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter<queryNoticeAnnouncementPageListBean.DataBean.NoticeAnnouncementListBean.DatasBean>(Act_AnnouncementList.this, R.layout.activity_announcement_list1, data) {
            @Override
            protected void convert(ViewHolder holder, queryNoticeAnnouncementPageListBean.DataBean.NoticeAnnouncementListBean.DatasBean data, int position) {
                holder.setText(R.id.tv_title, data.getAnnouncementTitle());
                TextView tvContent = holder.getConvertView().findViewById(R.id.tv_content);
                tvContent.setText(Html.fromHtml(data.getAnnouncementComtent()));
                holder.setText(R.id.tv_time, DateUtils.timeStampToStr(data.getCreateTime(),"yyyy-MM-dd"));
                holder.getConvertView().setOnClickListener(detail->{
                    Intent intent = new Intent(getActivity(), Act_Announcement.class);
                    intent.putExtra("id", data.getNoticeAnnouncementId() + "");
                    startActivity(intent);
                });
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.background),
                DispalyUtil.dp2px(getActivity(), 3),
                0, 0, false
        ));
    }
}
