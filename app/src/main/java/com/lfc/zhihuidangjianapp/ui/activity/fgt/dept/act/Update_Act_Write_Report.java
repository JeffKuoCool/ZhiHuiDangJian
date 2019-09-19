package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-10
 * @autror: guojian
 * @description:
 */
public class Update_Act_Write_Report extends BaseActivity {

    private EditText etTheme, etContent;
    private TextView tv_title;
    private String title;
    private String dept;
    private String author;
    private String studyStrongBureauId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_report;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        findViewById(R.id.imgBack).setOnClickListener(back->finish());
        tv_title =findViewById(R.id.tv_title);
        tv_title.setText("修改学习心得");
        initImmersionBar(0);
        etTheme = findViewById(R.id.et_theme);
        etContent = findViewById(R.id.et_content);
        title = getIntent().getStringExtra("title");
        dept = getIntent().getStringExtra("dept");
        author = getIntent().getStringExtra("author");
        studyStrongBureauId = getIntent().getStringExtra("studyStrongBureauId");
         etTheme.setText(title);
         etContent.setText(dept);
         Log.i("yy--", title +"===" + dept +"=="+ author +"==" + studyStrongBureauId);


        setEvent();
    }

    private void setEvent() {
        findViewById(R.id.tv_submit).setOnClickListener(submit->{
            if(etTheme.getText().toString().trim().isEmpty()){
                showTextToast("请填写主题");
                return;
            }
            if(etContent.getText().toString().trim().isEmpty()){
                showTextToast("请填写内容");
                return;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("studyStrongBureauType", 2);
            map.put("title", etTheme.getText().toString().trim());
            map.put("comment", etContent.getText().toString().trim());
            map.put("author", author);
            map.put("studyStrongBureauId", studyStrongBureauId);
            RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .updateStudyStrongBureauApp( map, MyApplication.getLoginBean().getToken())
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

    @Override
    protected void initData() {

    }
}
