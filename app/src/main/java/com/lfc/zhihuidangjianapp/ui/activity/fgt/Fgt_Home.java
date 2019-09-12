package com.lfc.zhihuidangjianapp.ui.activity.fgt;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.bean.QueryHomeNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Dept_dynamic;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Forest_List;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Membership;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_Announcement;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_AnnouncementList;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_Demonstration_Leadership;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_Emulate;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_PartyBuildingMatrix;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_WebView;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.adapter.HomeAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fragment.HomeHeadLinesFragment;
import com.lfc.zhihuidangjianapp.ui.activity.item.BannerViewHolder;
import com.lfc.zhihuidangjianapp.ui.activity.model.AppConfigLists;
import com.lfc.zhihuidangjianapp.widget.MyListView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Fgt_Home extends BaseFragment {
    @BindView(R.id.homeListView)
    MyListView homeListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    private ArrayList<QueryHomeNoticeAnnouncementPageListBean.DataBean.NoticeAnnouncementListBean.DatasBean> list = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private RecyclerView recyclerView;
    private int[] images = {R.mipmap.img_home_tab1, R.mipmap.img_home_tab2,
            R.mipmap.img_home_tab3, R.mipmap.img_home_tab4, R.mipmap.img_home_tab5,R.mipmap.img_dangwu_tab5_item1};
    private String[] lables = {"党建矩阵", "学习强局", "党务资讯", "专题专栏", "党费收缴", "林区风采"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        homeAdapter = new HomeAdapter(list, getActivity());
        homeListView.setAdapter(homeAdapter);
        homeListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), Act_Announcement.class);
            intent.putExtra("id", list.get(position).getNoticeAnnouncementId() + "");
            startActivity(intent);
        });
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        getFragmentManager().beginTransaction().add( R.id.homeHead,new HomeHeadLinesFragment()).commit();
    }

    @Override
    protected void initData() {
        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            queryHomeNoticeAnnouncementPageList();
            list.clear();
            queryAppConfigList();
            refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        });
        queryHomeNoticeAnnouncementPageList();
        queryAppConfigList();
        setFuncRecyclerView();
    }

    private void setFuncRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_home_func, Arrays.asList(lables)) {
            @Override
            protected void convert(ViewHolder holder, String data, int position) {
                holder.setText(R.id.text, data);
                holder.setImageDrawable(R.id.icon, getResources().getDrawable(images[position]));
                holder.getConvertView().setOnClickListener(func -> {
                    switch (position) {
                        case 0://党建矩阵
                            startActivity(new Intent(getContext(), Act_PartyBuildingMatrix.class));
                            break;
                        case 1://学习强局
                            startActivity(new Intent(getContext(), Act_Emulate.class));
                            break;
                        case 2://党务资讯
                            startActivity(new Intent(getActivity(), Act_Dept_dynamic.class));
                            break;
                        case 3://专题专栏
                            startActivity(new Intent(getContext(), Act_Demonstration_Leadership.class));
                            break;
                        case 4://缴纳党费
                            startActivity(new Intent(getContext(), Act_Party_Membership.class));
                            break;
                        case 5://林区风采
                            Intent intent = new Intent(getActivity(), Act_Forest_List.class);
                            intent.putExtra("tabType", 0);
                            startActivity(intent);
                            break;

                    }
                });
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void startWebView(String title, String url) {
        Intent intent = new Intent(getActivity(), Act_WebView.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.banner)
    public void onImgBannerClicked() {
        String imgPath = "";
        startWebView("详情", imgPath);
    }

    @OnClick(R.id.relativeAnnoun)//公告公示
    public void onRelativeAnnounClicked() {
        getActivity().startActivity(new Intent(getActivity(), Act_AnnouncementList.class));
    }

    /**
     * 获取公告列表
     */
    public void queryHomeNoticeAnnouncementPageList() {
        HttpHelper.queryHomeNoticeAnnouncementPageList(new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
            }

            @Override
            public void onSucceed(String succeed) {
                Gson gson = new Gson();
                QueryHomeNoticeAnnouncementPageListBean entity = gson.fromJson(succeed, QueryHomeNoticeAnnouncementPageListBean.class);
                if (entity.getCode() == 0) {
                    list.addAll(entity.getData().getNoticeAnnouncementList().getDatas());
                    homeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    public void queryAppConfigList() {
        Map<String, String> map = new HashMap<>();
        map.put("ifBanner", "0");
        map.put("position", "0");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryAppConfigList(map, MyApplication.getLoginBean().getToken())
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

}
