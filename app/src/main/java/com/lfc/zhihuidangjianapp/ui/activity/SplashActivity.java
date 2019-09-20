package com.lfc.zhihuidangjianapp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.UserConstants;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.databinding.ActivitySplashBinding;
import com.lfc.zhihuidangjianapp.utlis.SPUtil;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @date: 2019-09-19
 * @autror: guojian
 * @description: 开屏页
 */
public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding mBinding;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private int countDownTime = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initImmersionBar(0);
        startCompositeDisposable();
        mBinding.jump.setOnClickListener(jump -> goMain());
    }

    @Override
    protected void initData() {

    }

    /**
     * 开启倒计时跳转
     */
    private void startCompositeDisposable() {
        mCompositeDisposable.add(countDown(countDownTime)
                .doOnSubscribe(disposable -> mBinding.jump.setText(format(countDownTime)))
                .subscribeWith(new DisposableObserver<Integer>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(Integer t) {
                        mBinding.jump.setText(format(t));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        goMain();
                    }
                })
        );
    }

    private String format(int countDownTime) {
        return String.format(Locale.getDefault(), "跳过 %1d", countDownTime);
    }

    private void goMain() {
        if (TextUtils.isEmpty(SPUtil.getObject(this, UserConstants.AUTHORIZATION, "").toString())) {
            startActivity(new Intent(this, Act_Login.class));
        } else {
            startActivity(new Intent(this, Act_Main.class));
        }
        this.finish();
    }

    private Observable<Integer> countDown(int time) {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).map(t -> time - t.intValue())
                .take((time + 1));
    }

}
