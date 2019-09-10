package com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

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
 * @date: 2019-09-04
 * @autror: guojian
 * @description: 修改密码
 */
public class UpdatePasswordActivity extends BaseActivity {

    private EditText etOldPwd, etNetPwd, etConfirmPwd,etOldCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        findViewById(R.id.imgBack).setOnClickListener(back -> finish());
        initImmersionBar(0);
        etOldPwd = findViewById(R.id.etOldPwd);
        etNetPwd = findViewById(R.id.etNetPwd);
        etConfirmPwd = findViewById(R.id.etConfirmPwd);
        etOldCode= findViewById(R.id.etOldCode);
        etOldCode.setText(MyApplication.getLoginBean().getLoginName());
        setEvent();
    }

    private void setEvent() {

        findViewById(R.id.tvSubmit).setOnClickListener(submit -> {
            submit();
        });
    }


    private void submit() {
        if (TextUtils.isEmpty(etOldPwd.getText().toString().trim())) {
            toast("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(etNetPwd.getText().toString().trim())) {
            toast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(etConfirmPwd.getText().toString().trim())) {
            toast("请确认新密码");
            return;
        }
        if (!TextUtils.equals(etNetPwd.getText().toString().trim(), etConfirmPwd.getText().toString().trim())) {
            toast("请确认两次新密码相同");
            return;
        }
        if(etOldPwd.getText().toString().trim().length()>=6&&etNetPwd.getText().toString().trim().length()>=6&&etConfirmPwd.getText().toString().trim().length()>=6){
            Map<String, Object> map = new HashMap<>();
            map.put("oldPwd", etOldPwd.getText().toString().trim());
            map.put("newPwd", etNetPwd.getText().toString().trim());
            RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                    .updatePwd(map, MyApplication.getLoginBean().getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseObserver<Object>(getActivity()) {

                        @Override
                        protected void onNext(Object response) {
                            Log.e("onNext= ", response.toString());
                            if (response == null) return;
                            toast("修改成功");
                            finish();
                        }

                        @Override
                        protected void onError(Throwable e) {
                            super.onError(e);
                            Log.e("Throwable= ", e.getMessage());
                        }
                    }.actual());
        }else{
            toast("密码至少由6位字符组成，而且同时包含字母和数字");
        }

    }

    @Override
    protected void initData() {

    }
}
