package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.bean.BaseBean;
import com.lfc.zhihuidangjianapp.bean.NoticeAnnouncementsListBean;
import com.lfc.zhihuidangjianapp.bean.QueryHomeNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Craftsman_Training;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.adapter.AnnouncementListAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.AnnouncementBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.queryNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyCraftReportList;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 公告公示
 */
public class Act_AnnouncementList extends BaseActivity {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    RecyclerView recyclerView;
    private int size=10;
    private int num=1;
    private boolean isData = true;
    SmartRefreshLayout mRefreshLayout;
    List<AnnouncementBean.NoticeAnnouncementListBean.DatasBean> datas_new = new ArrayList<>();
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
        mRefreshLayout = findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        //queryNoticeAnnouncementPageList();
        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(false));
        //设置 Footer 为 普通样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        //设置 Header 为 普通样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        refresh();
        getDatas();
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
                    //setRecyclerView(datas);
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
    private void refresh() {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                num = 1;
                getDatas();
                refreshlayout.finishRefresh(2000);

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (!isData) {
                    //                mDefineBAGLoadView.setFooterTextOrImage("没有更多数据", false);
                } else {
                    //                    mDefineBAGLoadView.setFooterTextOrImage("正在玩命加载中...", true);
                    num++;
                    getDatas();
                }
                refreshLayout.finishLoadMore(2000);
            }
        });

    }
    private void getDatas() {
        Map<String, String> map = new HashMap<>();
        map.put("pageSize", size + "");
        map.put("pageNumber", num + "");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryNoticeAnnouncementPageList_new(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<AnnouncementBean>(getActivity()) {

                    @Override
                    protected void onNext(AnnouncementBean response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        // setRecyclerView(response);
                        List<AnnouncementBean.NoticeAnnouncementListBean.DatasBean> datas = response.getNoticeAnnouncementList().getDatas();
                        if (num == 1) {
                            datas_new.clear();
                        }
                        if (datas.size() == 0) {
                            if (num == 1 && datas_new.size() == 0) {
                                isData = true;
                                num--;
                            } else {
                                isData = false;
                            }
                        } else {
                            datas_new.addAll(datas);
                            if (datas.size() < 8) {
                                isData = false;
                            } else {
                                isData = true;
                            }
                            setRecyclerView(datas_new);
                        }
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());


    }

    private void setRecyclerView(List<AnnouncementBean.NoticeAnnouncementListBean.DatasBean> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter<AnnouncementBean.NoticeAnnouncementListBean.DatasBean>(Act_AnnouncementList.this, R.layout.activity_announcement_list1, data) {
            @Override
            protected void convert(ViewHolder holder, AnnouncementBean.NoticeAnnouncementListBean.DatasBean data, int position) {
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
