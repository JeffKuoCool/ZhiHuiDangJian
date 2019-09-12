package com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.fgt;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_TitleDetails;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.adapter.Demonstration_LeadershipAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.bean.QueryLeadDemonstrationPageListBean;

import com.lfc.zhihuidangjianapp.ui.activity.item.BannerViewHolder;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.lfc.zhihuidangjianapp.utlis.GlideImage;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 专题专栏
 */
public class Fgt_Demonstration_Leadership extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.my_RecyclerView)
    RecyclerView my_RecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.item_title)
    TextView item_title;
    String leadDemonstrationType = "";

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
    private Demonstration_LeadershipAdapter adapter;

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        item_title.setOnClickListener(this);

        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {

            refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示刷新失败
        });
        my_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Demonstration_LeadershipAdapter(getContext(), datas);
        adapter.setOnItemChildClickListener(this);
        my_RecyclerView.setAdapter(adapter);
        my_RecyclerView.addItemDecoration(new DividerItemDecoration(
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getColor(MyApplication.getAppContext(), R.color.background),
                DispalyUtil.dp2px(MyApplication.getAppContext(), 3),
                0, 0, false
        ));
    }

    @Override
    protected void initData() {
        queryLeadDemonstrationPageList();
        ArrayList<String> list =new ArrayList<>();

        list.add("http://58.87.96.160:8081/PathLogo/20190909/d97e6890-d87d-4c1f-88cf-343fffaa4a97_0.5.jpg");
        list.add("http://58.87.96.160:8081/PathLogo/20190909/7e2cd9cb-da1f-4b8a-b82f-9d07b334ec8e_0.5.jpg");
        ArrayList<String> list2 =new ArrayList<>();
        list2.add("http://58.87.96.160:8081/PathLogo/20190909/8c907979-0993-44b7-b172-f298ff3e5977_0.5.jpg");
        list2.add("http://58.87.96.160:8081/PathLogo/20190909/7e2cd9cb-da1f-4b8a-b82f-9d07b334ec8e_0.5.jpg");
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
       /* for (int i = 0; i < banner.size(); i++) {
            String image = banner.get(i).getPic();
            p_list.add(image);
        }*/
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

    private List<QueryLeadDemonstrationPageListBean.DataBean.LeadDemonstrationListBean.DatasBean> datas = new ArrayList<>();

    /**
     * 专题专栏
     * 引领示范类型(0:不忘初心 牢记使命1:改革创新 奋发有为)
     */
    public void queryLeadDemonstrationPageList() {
        int pageNum = 1;
        int pageSize=10;
        HttpHelper.queryLeadDemonstrationPageList(leadDemonstrationType, pageNum ,pageSize, new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
            }

            @Override
            public void onSucceed(String succeed) {
                loding.dismiss();
                Gson gson = new Gson();
                QueryLeadDemonstrationPageListBean entity = gson.fromJson(succeed, QueryLeadDemonstrationPageListBean.class);
                if (entity.getCode() == 0) {
                    for (int i = 0; i < entity.getData().getLeadDemonstrationList().getDatas().size(); i++) {
                        datas.add(entity.getData().getLeadDemonstrationList().getDatas().get(i));
                    }
                    adapter.notifyDataSetChanged();
                    if (entity.getData().getLeadDemonstrationList().getDatas().size() > 0) {
                       // Glide.with(getContext()).load(ApiConstant.ROOT_URL + entity.getData().getLeadDemonstrationList().getDatas().get(0).getThumbnailUrl()).into(item_hader);
                        item_title.setText(Html.fromHtml(entity.getData().getLeadDemonstrationList().getDatas().get(0).getComment()));
                      //  item_title.setText(entity.getData().getLeadDemonstrationList().get);
                    }
                } else {
                    ToastUtils.show(entity.getMsg());
                }
            }

            @Override
            public void onError(String error) {
                loding.dismiss();
                ToastUtils.show(error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_title://跳转详情页
           /* case R.id.item_hader:
                if (datas.size() < 0) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("title", datas.get(0).getTitle());
                intent.putExtra("context", datas.get(0).getComment());
                intent.putExtra("author", datas.get(0).getAuthor());
                intent.setClass(getContext(), Act_TitleDetails.class);
                startActivity(intent);
                break;*/
        }
    }
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.demonstration_item:
                Intent intent = new Intent();
                intent.putExtra("title", datas.get(position).getTitle());
                intent.putExtra("context", datas.get(position).getComment());
                intent.putExtra("context", datas.get(position).getComment());
                intent.setClass(getContext(), Act_TitleDetails.class);
                startActivity(intent);
                break;
        }
    }
}
