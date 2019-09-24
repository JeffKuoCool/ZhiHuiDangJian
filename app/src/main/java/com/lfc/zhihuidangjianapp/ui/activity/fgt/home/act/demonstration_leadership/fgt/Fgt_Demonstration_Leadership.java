package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.fgt;

import android.content.Intent;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Fgt_Weekend_Details;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_TitleDetails;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.ZtBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.adapter.Demonstration_LeadershipAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.bean.QueryLeadDemonstrationPageListBean;

import com.lfc.zhihuidangjianapp.ui.activity.item.BannerViewHolder;
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseWorkReport;
import com.lfc.zhihuidangjianapp.ui.activity.model.WorkReport;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.lfc.zhihuidangjianapp.utlis.GlideImage;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 专题专栏
 */
public class Fgt_Demonstration_Leadership extends BaseFragment  {
    @BindView(R.id.my_RecyclerView)
    RecyclerView my_RecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.item_title)
    TextView item_title;
    String leadDemonstrationType = "";
    private int size=10;
    private int num=1;
    private boolean isData = true;
    List<ZtBean.LeadDemonstrationListBean.DatasBean> datas_new = new ArrayList<>();

    public static Fgt_Demonstration_Leadership getInstance(String title) {
        Fgt_Demonstration_Leadership sf = new Fgt_Demonstration_Leadership();
        sf.leadDemonstrationType = title;
        return sf;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fgt_demonstration_leadership;
    }

    Unbinder unbinder;

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);


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
       // queryLeadDemonstrationPageList();
        ArrayList<String> list =new ArrayList<>();
        list.add(ApiConstant.ROOT_URL +"Path/20190912/b64de694-5faf-40f5-8c50-3a2e5aa94f63.jpg");
        list.add(ApiConstant.ROOT_URL +"Path/20190912/286398c2-d80e-43b1-b377-382322af57cc.jpg");
        ArrayList<String> list2 =new ArrayList<>();
        list2.add(ApiConstant.ROOT_URL +"Path/20190912/99759f6a-dd6a-4846-a9e3-23df330d44f9.jpg");
        list2.add(ApiConstant.ROOT_URL +"Path/20190912/501d5375-1ed6-4e9e-b1c4-c9e1bf6a9147.jpg");

        /**
         * 专题专栏
         * 引领示范类型(0:不忘初心 牢记使命1:改革创新 奋发有为)
         */
        if(leadDemonstrationType.equals("0"))
        {
            setOneData(list);
        }else{
            setOneData(list2);
        }
    }
    //设置轮播图banner数据
    private void setOneData(final List<String> banner) {

        final ArrayList<String> p_list = new ArrayList<String>();

        mBanner.setImageLoader(new GlideImage());

        //设置图片集合
        mBanner.setImages(banner);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.i("YY_POS", position + "");

            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        map.put("leadDemonstrationType", leadDemonstrationType);
        map.put("pageSize", size + "");
        map.put("pageNumber", num + "");
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryLeadDemonstrationPageList_new(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ZtBean>(getActivity()) {

                    @Override
                    protected void onNext(ZtBean response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                        List<ZtBean.LeadDemonstrationListBean.DatasBean> datas = response.getLeadDemonstrationList().getDatas();
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

    private void setRecyclerView(List<ZtBean.LeadDemonstrationListBean.DatasBean> datas_new) {
        my_RecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        my_RecyclerView.setAdapter(new CommonAdapter<ZtBean.LeadDemonstrationListBean.DatasBean>(MyApplication.getAppContext(), R.layout.item_home_head, datas_new) {
            @Override
            protected void convert(ViewHolder holder, ZtBean.LeadDemonstrationListBean.DatasBean data, int position) {
                holder.setText(R.id.tv_title, datas_new.get(position).getTitle());
                holder.setText(R.id.tv_content,  datas_new.get(position).getReleaseDate());
                ImageView image = holder.getConvertView().findViewById(R.id.image);
                Glide.with(image.getContext()).load(ApiConstant.ROOT_URL+ datas_new.get(position).getThumbnailUrl()).into(image);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("title", datas_new.get(position).getTitle());
                        intent.putExtra("context", datas_new.get(position).getComment());
                        intent.putExtra("context", datas_new.get(position).getComment());
                        intent.setClass(getContext(), Act_TitleDetails.class);
                        startActivity(intent);
                    }
                });
            }

        });
        my_RecyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(MyApplication.getAppContext(), R.color.background),
                DispalyUtil.dp2px(MyApplication.getAppContext(), 3),
                0, 0, false
        ));
       /* adapter = new Demonstration_LeadershipAdapter(getContext(), datas_new);
        adapter.setOnItemChildClickListener(this);
        my_RecyclerView.setAdapter(adapter);
        my_RecyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(MyApplication.getAppContext(), R.color.background),
                DispalyUtil.dp2px(MyApplication.getAppContext(), 3),
                0, 0, false
        ));*/
    }
}
