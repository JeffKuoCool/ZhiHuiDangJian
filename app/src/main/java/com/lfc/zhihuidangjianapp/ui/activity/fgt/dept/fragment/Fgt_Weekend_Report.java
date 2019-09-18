package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Dept_Detail;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Fgt_Weekend_Details;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dept;
import com.lfc.zhihuidangjianapp.ui.activity.model.OrganizationalLife;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseOrganizationalLife;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseWorkReport;
import com.lfc.zhihuidangjianapp.ui.activity.model.WorkReport;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @date: 2019-08-10
 * @autror: guojian
 * @description: 我的周报
 */
public class Fgt_Weekend_Report extends BaseFragment {

    private RecyclerView recyclerView;
    SmartRefreshLayout mRefreshLayout;
    private int size=15;
    private int num=1;
    private boolean isData = true;
    List<WorkReport> datas_new = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
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

    @Override
    protected void initData() {

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
        Map<String, Object> map = new HashMap<>();
        Log.i("yy",MyApplication.getLoginBean().getLoginName());
        map.put("createCode", MyApplication.getLoginBean().getLoginName());
        map.put("pageSize", size);
        map.put("pageNumber", num);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyWeeklyWorkReportPageList(map,MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponseWorkReport>(getActivity()) {

                    @Override
                    protected void onNext(ResponseWorkReport response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                        List<WorkReport> datas = response.getWeeklyWorkReportList().getDatas();
                        if (response == null) return;
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
    private void setRecyclerView(List<WorkReport> workReportList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<WorkReport>(MyApplication.getAppContext(), R.layout.item_mine_work_report, workReportList) {
            @Override
            protected void convert(ViewHolder holder, WorkReport data, int position) {
                 holder.setText(R.id.tv_time,data.getReleaseDate());
                holder.setText(R.id.tv_content,data.getTitle());
                holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                    Intent intent = new Intent(getActivity(), Fgt_Weekend_Details.class );
                    intent.putExtra("weeklyWorkReportId", data.getWeeklyWorkReportId()+"");
                    startActivity(intent);
                });
            }

        });
    }

}
