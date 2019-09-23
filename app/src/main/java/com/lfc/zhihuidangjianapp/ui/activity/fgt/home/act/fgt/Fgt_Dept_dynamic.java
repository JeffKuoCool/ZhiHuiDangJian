package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fgt;

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
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.ZtBean;
import com.lfc.zhihuidangjianapp.ui.activity.item.BannerViewHolder;
import com.lfc.zhihuidangjianapp.ui.activity.model.AppConfigLists;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponsePartyDynamicList;
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
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description:
 */
public class Fgt_Dept_dynamic extends BaseBindViewFragment {
    RecyclerView recyclerView;
    private int partyDynamicType;
    private Banner banner;
    private int size=10;
    private int num=1;
    private boolean isData = true;
    SmartRefreshLayout mRefreshLayout;
    List<Dynamic> datas_new = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview_with_banner;
    }

    @Override
    protected void initData() {
        partyDynamicType = getArguments().getInt("partyDynamicType", 0);
        Log.i("yy--partyDynamicType",partyDynamicType+"");
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
        queryAppConfigList();
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        banner = rootView.findViewById(R.id.banner);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
    }

    public void queryAppConfigList() {
        Map<String, String> map = new HashMap<>();
        map.put("ifBanner", "1");
        map.put("position", "1");
        map.put("type", "1");
        map.put("number", partyDynamicType+"");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryAppConfigList(map,MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<AppConfigLists>(getActivity()) {

                    @Override
                    protected void onNext(AppConfigLists response) {
                        setBanner(response);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    private void setBanner(AppConfigLists response) {
        banner.setImages(response.getAppConfigList().getDatas()).setImageLoader(new BannerViewHolder()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

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

        Map<String, Object> map = new HashMap<>();
        map.put("partyDynamicType", partyDynamicType);
        map.put("pageSize", size + "");
        map.put("pageNumber", num + "");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryPartyDynamicPageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponsePartyDynamicList>(MyApplication.getAppContext()) {

                    @Override
                    protected void onNext(ResponsePartyDynamicList response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null)return;
                       // setRecyclerView(response);
                        List<Dynamic> datas = response.getPartyDynamicList().getDatas();
                        Log.i("yy00datas",datas.size()+"");
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
                            setRecyclerView( datas_new);
                        }
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());



    }

    private void setRecyclerView(List<Dynamic> response){
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<Dynamic>(MyApplication.getAppContext(), R.layout.item_home_head, response) {
            @Override
            protected void convert(ViewHolder holder, Dynamic data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                holder.setText(R.id.tv_content,data.getReleaseDate());
                ImageView image = holder.getConvertView().findViewById(R.id.image);
                Glide.with(image.getContext()).load(ApiConstant.ROOT_URL+data.getThumbnail_url()).into(image);
                //点击列表进入党建动态详情
                holder.getConvertView().setOnClickListener(item->{
                    Intent intent = new Intent(MyApplication.getAppContext(), Act_Dept_Dynamic_Detail.class);
                    intent.putExtra("partyDynamicId", data.getParty_dynamic_id()+"");
                    intent.putExtra("examineId", "");
                    intent.putExtra("type","");
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
