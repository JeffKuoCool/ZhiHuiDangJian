package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Craftsman_Training;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Weekend_Report;
import com.lfc.zhihuidangjianapp.ui.activity.model.OrganizationalLife;
import com.lfc.zhihuidangjianapp.ui.activity.model.OrganizationalLifeDetail;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyCraftReportList;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyCraftTrainingList;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-10
 * @autror: guojian
 * @description:
 */
public class Fgt_Study_Report extends BaseFragment {

    private RecyclerView recyclerView;

    private int studyStrongBureauType;
    private int size=10;
    private int num=1;
    private boolean isData = true;
    SmartRefreshLayout mRefreshLayout;
    List<StudyStrongBureau> datas_new = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
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
        map.put("studyStrongBureauType", 2);
        map.put("pageSize", size + "");
        map.put("pageNumber", num + "");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyStudyStrongBureauPageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<StudyCraftReportList>(getActivity()) {

                    @Override
                    protected void onNext(StudyCraftReportList response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                        // setRecyclerView(response);
                        List<StudyStrongBureau> datas = response.getStudyStrongBureauList().getDatas();
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
    public void setRecyclerView(List<StudyStrongBureau> response) {
        if (response.isEmpty()) return;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new CommonAdapter<StudyStrongBureau>(getActivity(), R.layout.item_study_report, response) {
            @Override
            protected void convert(ViewHolder holder, StudyStrongBureau data, int position) {
                TextView xinde_author = holder.getConvertView().findViewById(R.id.xinde_author);
                TextView xinde_dept = holder.getConvertView().findViewById(R.id.xinde_dept);
                TextView xinde_title = holder.getConvertView().findViewById(R.id.xinde_title);
                //我得心得是否通过(0:是1:否)
                ImageView xinde_status =holder.getView(R.id.xinde_status);
                int type = response.get(position).getIfPass();
                if (type == 1) {
                    xinde_status.setImageResource(R.mipmap.report_zhong);
                } else {
                    //xinde_status.setImageResource(R.mipmap.report_no);

                }
                xinde_title.setText(response.get(position).getTitle());
                xinde_dept.setText(response.get(position).getDept());
                xinde_author.setText(response.get(position).getAuthor());
                holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience -> {
                    Intent intent = new Intent(getActivity(), com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Strong_Study_Experience.class);
                    intent.putExtra("studyStrongBureauId", data.getStudyStrongBureauId() + "");
                    intent.putExtra("appTitle", "学习心得");
                    startActivity(intent);
                });
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(getActivity(), R.color.background),
                DispalyUtil.dp2px(getActivity(), 0),
                0, 0, false
        ));
    }

}
