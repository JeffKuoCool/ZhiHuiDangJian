package com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Fgt_Weekend_Details;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.MyInteagalBeabMingXI;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.MyIntegalBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.WorkReport;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Act_Integral_mingxi extends BaseActivity {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.my_RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int size=10;
    private int num=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_mingxi;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(1);

    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", size);
        map.put("pageNum", num);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyIntegralDetailPageList(map,MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<MyInteagalBeabMingXI>(getActivity()) {

                    @Override
                    protected void onNext(MyInteagalBeabMingXI response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                       setRecyclerView(response.getIntegralDetailList().getDatas());
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    private void setRecyclerView(List<MyInteagalBeabMingXI.IntegralDetailListBean.DatasBean> workReportList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.setAdapter(new CommonAdapter<MyInteagalBeabMingXI.IntegralDetailListBean.DatasBean>(MyApplication.getAppContext(), R.layout.item_integral_list, workReportList) {
            @Override
            protected void convert(ViewHolder holder, MyInteagalBeabMingXI.IntegralDetailListBean.DatasBean data, int position) {
                int type = data.getType();
                //类型(0:登录1:周报2:心得3:党费)
                if(type==0){
                    holder.setText(R.id.intergral_text1,"登录");
                }else
                if(type==1){
                    holder.setText(R.id.intergral_text1,"周报");
                }else if(type==2){
                    holder.setText(R.id.intergral_text1,"心得");
                }else{
                    holder.setText(R.id.intergral_text1,"党费");
                }

                holder.setText(R.id.intergral_text2, DateUtils.timeStampToStr(data.getCreateTime(), "yyyy-MM-dd")+"");
                holder.setText(R.id.intergral_text3,"+"+data.getTotal());
                holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{

                });
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("积分明细");
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
    }

    @OnClick({R.id.imgBack, R.id.tvRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvRight:
                break;
        }
    }
}
