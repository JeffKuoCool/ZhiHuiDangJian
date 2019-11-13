package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.event.EventConstants;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.MonthBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_Announcement;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_AnnouncementList;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.AnnouncementBean;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dept;
import com.lfc.zhihuidangjianapp.ui.activity.model.MailList;
import com.lfc.zhihuidangjianapp.ui.activity.model.WorkReport;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 党建工作月报列表
 */
public class Act_Month_list extends BaseActivity {

    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private int enter;

    @Override
    protected int getLayoutId() {
        return R.layout.act_mail_list;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        findViewById(R.id.imgBack).setOnClickListener(back -> finish());
        recyclerView = findViewById(R.id.recyclerView);
       TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("党建工作月报");
        enter = getIntent().getIntExtra("enter", 0);
    }

    @Override
    protected void initData() {

        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryMonthWorkReportPageList( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<MonthBean>(getActivity()) {

                    @Override
                    protected void onNext(MonthBean response) {
                        if (response == null) return;
                        Log.e("onNext= ", response.toString());
                        List<MonthBean.MonthWorkReportListBean.DatasBean> datas = response.getMonthWorkReportList().getDatas();

                        setRecyclerView(datas);
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            setResult(EventConstants.EVENT_APPLY,data);
            finish();
        }
    }

   private void setRecyclerView(List<MonthBean.MonthWorkReportListBean.DatasBean> data){
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerView.setAdapter(new CommonAdapter<MonthBean.MonthWorkReportListBean.DatasBean>(getContext(), R.layout.item_mine_work_report, data) {
           @Override
           protected void convert(ViewHolder holder, MonthBean.MonthWorkReportListBean.DatasBean data, int position) {
               holder.setText(R.id.tv_time,stampToDate(data.getCreateTime()));
               holder.setText(R.id.tv_content,data.getDept());
               holder.getConvertView().setOnClickListener(Act_Strong_Study_Experience->{
                   Intent intent = new Intent(getActivity(), Fgt_Month_Details.class );
                   intent.putExtra("monthWorkReportId", data.getMonthWorkReportId()+"");
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
    /*
     * 将时间戳转换为时间
     */
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

}
