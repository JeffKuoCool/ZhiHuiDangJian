package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.BaseBindViewFragment;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Dept_Dynamic_Detail;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Organizational_Life_Detail;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.MyInteagalBeabMingXI;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.OrganizationalLife;
import com.lfc.zhihuidangjianapp.ui.activity.model.OrganizationalLifeList;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseOrganizationalLife;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponsePartyDynamicList;
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

import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description: 组织生活
 */
public class Fgt_Organizational_Life extends BaseBindViewFragment {

    RecyclerView recyclerView;
    private int studyType;
    SmartRefreshLayout mRefreshLayout;
    private int size=10;
    private int num=1;
    private boolean isData = true;
    List<OrganizationalLife> datas_new = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initData() {
      
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
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
        studyType = getArguments().getInt("studyType", 0);
        Map<String, Object> map = new HashMap<>();
        map.put("studyType", studyType);
        map.put("pageSize", size);
        map.put("pageNumber", num);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryOrganizationalLifePageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponseOrganizationalLife>(getActivity()) {

                    @Override
                    protected void onNext(ResponseOrganizationalLife response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                        // setRecyclerView(response);
                        List<OrganizationalLife> datas = response.getOrganizationalLifeList().getDatas();
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

    private void setRecyclerView( List<OrganizationalLife> response ){
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<OrganizationalLife>(MyApplication.getAppContext(), R.layout.item_dept_dynamic,  response) {
            @Override
            protected void convert(ViewHolder holder, OrganizationalLife data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                holder.setText(R.id.tv_bottom, data.getDeptName());
                TextView tvContent = holder.getConvertView().findViewById(R.id.tv_content);
                tvContent.setText(data.getReleaseDate());
                ImageView image = holder.getConvertView().findViewById(R.id.image);
                String url = ApiConstant.ROOT_URL+data.getThumbnailUrl();
                Glide.with(getActivity()).load(url).into(image);
                holder.getConvertView().setOnClickListener(item->{
                    Intent intent = new Intent(getActivity(), Act_Organizational_Life_Detail.class);
                    intent.putExtra("organizationalLifeId", data.getOrganizationalLifeId()+"");
                    startActivity(intent);
                });
            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(MyApplication.getAppContext(), R.color.background),
                DispalyUtil.dp2px(MyApplication.getAppContext(), 3),
                0, 0, false
        ));
    }

}
