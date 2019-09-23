package com.lfc.zhihuidangjianapp.net.http;

import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMClient;
import com.lfc.zhihuidangjianapp.app.UserConstants;
import com.lfc.zhihuidangjianapp.helper.ActivityManager;
import com.lfc.zhihuidangjianapp.ui.activity.Act_Login;
import com.lfc.zhihuidangjianapp.ui.activity.model.BaseResponse;
import com.lfc.zhihuidangjianapp.utlis.SPUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ResponseObserver<T> {
    private final String TAG = "ResponseObserver";
    private final InternalObserver actual = new InternalObserver();
    private Context mContext;

    public ResponseObserver(Context context) {
        mContext = context;
    }

    protected void onSubscribe(Disposable d) {
    }

    protected void onNext(T response) {
    }

    protected void onError(Throwable e) {
    }

    protected void onComplete() {
    }

    protected void fail(T response){

    }

    public InternalObserver actual() {
        return actual;
    }

    private class InternalObserver implements Observer<BaseResponse<T>> {

        @Override
        public void onSubscribe(Disposable d) {
            ResponseObserver.this.onSubscribe(d);
        }

        @Override
        public void onNext(BaseResponse<T> response) {
            if (response.isSuccessful() ) {
                ResponseObserver.this.onNext(response.getData());
                return;
            }else if(response.isLoginFailure()){
                SPUtil.remove(mContext, UserConstants.AUTHORIZATION);
                EMClient.getInstance().logout(true, null);
                ActivityManager.finishAll();
                mContext.startActivity(new Intent(mContext, Act_Login.class));
            }
        }

        @Override
        public void onError(Throwable e) {
            ResponseObserver.this.onError(e);
        }

        @Override
        public void onComplete() {
            ResponseObserver.this.onComplete();
        }
    }
}
