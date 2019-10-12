package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.util.Log;
import android.widget.TextView;

import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.event.BusEvent;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.pay.AliPayApi;
import com.lfc.zhihuidangjianapp.pay.WechatApi;
import com.lfc.zhihuidangjianapp.ui.activity.model.AliPay;
import com.lfc.zhihuidangjianapp.ui.activity.model.User;
import com.lfc.zhihuidangjianapp.ui.activity.model.WechatPay;
import com.lfc.zhihuidangjianapp.utlis.DateUtils;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-05
 * @autror: guojian
 * @description:
 */
public class Act_Party_Pay extends BaseActivity implements AliPayApi.AliPayCalback {

    private TextView tvPay, tvPayDetail;

    private TextView tvWechat, tvAlipay, tvPayTime;

    private int payType = 0;

    private int event = 0;

    private String pay;
    private String partyPaymentHisId;

    @Override
    protected int getLayoutId() {
        return R.layout.act_party_pay;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        findViewById(R.id.imgBack).setOnClickListener(back -> finish());
        tvPay = findViewById(R.id.tv_pay);
        tvWechat = findViewById(R.id.tv_wechat);
        tvAlipay = findViewById(R.id.tv_alipay);
        tvPayDetail = findViewById(R.id.tv_pay_detail);
        tvPayTime = findViewById(R.id.tv_pay_time);
        initPayView();
        setEvent();
    }

    private void setEvent() {
        tvPay.setOnClickListener(pay -> {
            if (payType == 0) {

                getWechatOrderInfo();

            } else {
                getAliPayOrderInfo();
            }

        });
        tvWechat.setOnClickListener(wechat -> {
            payType = 0;
            initPayView();
        });
        tvAlipay.setOnClickListener(wechat -> {
            payType = 1;
            initPayView();
        });
    }

    @Override
    protected void initData() {
        pay = getIntent().getStringExtra("pay");
        partyPaymentHisId = getIntent().getStringExtra("partyPaymentHisId");

        tvPayDetail.setText(pay + "元");
        tvPayTime.setText(DateUtils.timeStampToStr(System.currentTimeMillis(), "yyyy-MM-dd"));
    }

    private void initPayView() {
        if (payType == 0) {
            tvWechat.setSelected(true);
            tvAlipay.setSelected(false);
        } else {
            tvWechat.setSelected(false);
            tvAlipay.setSelected(true);
        }
    }

    // 支付宝支付
    private void getAliPayOrderInfo() {
        User user = MyApplication.getmUserInfo().getUser();
        Map<String, Object> map = new HashMap<>();
        map.put("total_fee", pay);
        map.put("body", user.getDisplayName() + "缴纳" + tvPayTime.getText().toString().trim() + "党费" + pay + "元");
        map.put("memberid", user.getLoginName());
        map.put("ifSubstitute", 1);
        map.put("attach", "payPartyPayment@" + user.getLoginName() + "@1");
        map.put("partyPaymentIds", partyPaymentHisId);

        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .alipayToApp(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<AliPay>(this) {
                    @Override
                    protected void onNext(AliPay response) {
                        Log.e("onNext=", response.toString());
                        if (response != null) {
                            AliPayApi aliPayApi = new AliPayApi(Act_Party_Pay.this, response.getOrderString());
                            aliPayApi.payV2();
                            aliPayApi.onAlipayCallback(Act_Party_Pay.this);
                        }
                    }

                    @Override
                    protected void onError(Throwable e) {
                        Log.e("onError=", e.getMessage());
                    }
                }.actual());
    }

    // 微信支付
    private void getWechatOrderInfo() {
        User user = MyApplication.getmUserInfo().getUser();
        Map<String, Object> map = new HashMap<>();
        map.put("total_fee", pay);
        map.put("body", user.getDisplayName() + "缴纳" + tvPayTime.getText().toString().trim() + "党费" + pay + "元");
        map.put("memberid", user.getLoginName());
        map.put("ifSubstitute", 1);
        map.put("attach", "payPartyPayment@" + user.getLoginName() + "@1");
        map.put("partyPaymentIds", partyPaymentHisId);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .wxPayToApp(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<WechatPay>(this) {
                    @Override
                    protected void onNext(WechatPay response) {
                        Log.e("onNext=", response.toString());
                        if (response != null) {
                            Log.i("yy--Sing==", response.getSign()
                                    + "==Prepayid==" + response.getPrepayid() + "===AppId==" + response.getAppId() + "==NonceStr==" +
                                    response.getNonceStr() + "==Timestamp===" + response.getTimestamp() + "=="
                                    + "===PackageName===" + response.getPackageName()
                                    + "==PartnerId==" + response.getPartnerId());
                            WechatApi.WXPayBuilder builder = new WechatApi.WXPayBuilder();
                            WechatApi wechatApi = builder.setPrepayId(response.getPrepayid())
                                    .setAppId(response.getAppId())
                                    .setNonceStr(response.getNonceStr())
                                    .setTimeStamp(response.getTimestamp() + "")
                                    .setPackageValue(response.getPackageName())
                                    .setSign(response.getSign())
                                    .setPartnerId(response.getPartnerId())
                                    .build();
                            wechatApi.toWXPayNotSign(Act_Party_Pay.this);

                        }
                    }

                    @Override
                    protected void onError(Throwable e) {
                        Log.e("onError=", e.getMessage());
                    }
                }.actual());
    }

    @Override
    public void success() {
        Log.e("alipay success! ", "");
        finish();
    }

    @Override
    public void error(String msg) {
        Log.e("alipay error! ", msg);
    }
}
