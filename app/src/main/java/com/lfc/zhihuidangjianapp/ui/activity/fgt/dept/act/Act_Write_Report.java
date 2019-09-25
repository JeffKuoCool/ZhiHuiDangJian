package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.text.Editable;
import android.text.InputFilter;
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
import com.lfc.zhihuidangjianapp.widget.ContainsEmojiEditText;
import com.lfc.zhihuidangjianapp.widget.SpaceFilter;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-10
 * @autror: guojian
 * @description:
 */
public class Act_Write_Report extends BaseActivity {

    private EditText etTheme;
    private ContainsEmojiEditText etContent;
    private TextView ui_edit_text;
    private static final int MAX_NUM = 3000;
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
        initImmersionBar(0);
        etTheme = findViewById(R.id.et_theme);
        etContent = findViewById(R.id.et_content);
        ui_edit_text=findViewById(R.id.ui_edit_text);
        //输入框禁止输入空格
        etContent.setFilters(new InputFilter[]{new SpaceFilter()});
        //监听输入框
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
                Log.i("afterTextChanged", s.toString().trim());
                if (s.length() > MAX_NUM) {
                    s.delete(MAX_NUM, s.length());
                }

                ui_edit_text.setText(String.valueOf(s.length()));

            }
        });
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
            RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .insertStudyStrongBureau( map, MyApplication.getLoginBean().getToken())
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
