package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-10
 * @autror: guojian
 * @description:
 */
public class Act_Write_Log extends BaseActivity {

    private RecyclerView recyclerView;

    private TextView tvSubmit, tvBranchName, tvReportTime;

    private String[] titles = {"组织生活情况","学习教育情况",
    "承诺践诺完成情况","其他需报告党组织事宜"};

    private String[] contents = {"", "", "", ""};

    private EditText etTitle;

    private String branchName;

    private long reportTime;
    private String releaseDate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_log;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        initImmersionBar(0);
        recyclerView = findViewById(R.id.recyclerView);
        tvSubmit = findViewById(R.id.tv_submit);
        etTitle = findViewById(R.id.et_title);
        tvBranchName = findViewById(R.id.tv_branch_name);
        tvReportTime = findViewById(R.id.tv_report_time);

        branchName = MyApplication.getmUserInfo().getUser().getDeptName();
        reportTime = System.currentTimeMillis();
        tvBranchName.setText(tvBranchName.getText()+branchName);
        releaseDate = DateUtils.timeStampToStr(reportTime, "");
        tvReportTime.setText(tvReportTime.getText()+ DateUtils.timeStampToStr(reportTime, ""));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter<String>(Act_Write_Log.this, R.layout.item_write_log, Arrays.asList(titles)) {
            @Override
            protected void convert(ViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_title, data);
                EditText editText = holder.getConvertView().findViewById(R.id.et_content);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        contents[position] = s.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

        });

        setEvent();
    }

    private void setEvent() {
        tvSubmit.setOnClickListener(submit->{
            if(unWriteLog()){
                showTextToast("请填写周报内容");
                return;
            }
            Map<String, Object> map = new HashMap<>();
            for (int i=0;i<contents.length;i++){
                map.put("comment"+(i+1), contents[i]);
            }
            map.put("title", etTitle.getText().toString().trim());
            map.put("releaseDate",releaseDate);
            RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .insertWeeklyWorkReport( map,MyApplication.getLoginBean().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseObserver<Object>(getActivity()) {

                        @Override
                        protected void onNext(Object response) {
                            Log.e("onNext= ", response.toString());
                            if(response==null)return;
                            showTextToast("发布成功");
                            finish();
                        }

                        @Override
                        protected void onError(Throwable e) {
                            super.onError(e);
                            Log.e("Throwable= ", e.getMessage());
                        }
                    }.actual());
        });
    }

    private boolean unWriteLog(){
        if(etTitle.getText().toString().trim().isEmpty()){
            return false;
        }
        for (String s: contents){
            if(!s.isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void initData() {

    }
}
