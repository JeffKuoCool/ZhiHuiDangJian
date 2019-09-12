package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.event.EventConstants;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.model.Dept;
import com.lfc.zhihuidangjianapp.ui.activity.model.MailList;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 通讯录-支部列表
 */
public class Act_Mail_list extends BaseActivity {

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
        enter = getIntent().getIntExtra("enter", 0);
    }

    @Override
    protected void initData() {

        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryDeptListToAddressBook( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<MailList>(getActivity()) {

                    @Override
                    protected void onNext(MailList response) {
                        if (response == null) return;
                        Log.e("onNext= ", response.toString());
                        setRecyclerView(response);
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

    private void setRecyclerView(MailList response) {
        //TODO 初始化操作
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new CommonAdapter<Dept>(getActivity(), R.layout.item_mail_list, response.getUserList()) {
            @Override
            protected void convert(ViewHolder holder, Dept data, int position) {
                holder.setText(R.id.tv_name, data.getAbbreviation());
                holder.getConvertView().setOnClickListener(mailList->{
                    //TODO 通讯录
                    Intent intent = new Intent(getActivity(), Act_Friend_list.class);
                    intent.putExtra("deptNumber", data.getDeptNumber());
                    intent.putExtra("enter", enter);
                    startActivityForResult( intent, 1);
//                    MyApplication.setDeptNumber(data.getDeptNumber());
//                    //创建音视频会议
//                    ConferenceActivity.startConferenceCall(getActivity(), null);
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

}
