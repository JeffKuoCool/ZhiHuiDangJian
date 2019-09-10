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
import com.lfc.zhihuidangjianapp.ui.activity.model.ResponseWorkReport;
import com.lfc.zhihuidangjianapp.ui.activity.model.WorkReport;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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


    private int size=10;
    private int num=1;
    @Override
    protected int getLayoutId() {
        return R.layout.parent_recyclerview;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        Log.i("yy",MyApplication.getLoginBean().getLoginName());
        map.put("createCode", MyApplication.getLoginBean().getLoginName());
        map.put("pageSize", size);
        map.put("pageNum", num);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMyWeeklyWorkReportPageList(map,MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponseWorkReport>(getActivity()) {

                    @Override
                    protected void onNext(ResponseWorkReport response) {
                        Log.e("onNext= ", response.toString());
                        if (response == null) return;
                        setRecyclerView(response.getWeeklyWorkReportList().getDatas());
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
