package com.lfc.zhihuidangjianapp.pay;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @date: 2018/8/21
 * @autror: guojian
 * @description:
 */

public class WechatApi {

    public static String mAppid  = "wxb51c590cd7ae8242";

//    public static String mAppSecret = "b9486baa17aca0e6fb6c220d78a0dd9f";

    private static IWXAPI iwxapi; //微信支付api

        private WXPayBuilder builder;

    private WechatApi(WXPayBuilder builder) {
        this.builder = builder;
    }

    /**
     * 调起微信支付的方法,不需要在客户端签名
     **/
    public void toWXPayNotSign(Context context) {
        iwxapi = WXAPIFactory.createWXAPI(context, null);
        iwxapi.registerApp(builder.getAppId()); //注册appid
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您没有安装微信客户端", Toast.LENGTH_LONG).show();
            return;
        }

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayReq request = new PayReq();
                request.appId = builder.getAppId();
                request.partnerId = builder.getPartnerId();
                request.prepayId = builder.getPrepayId();
                request.packageValue = builder.getPackageValue();
                request.nonceStr = builder.getNonceStr();
                request.timeStamp =builder.getTimeStamp();
                request.sign = builder.getSign();
                iwxapi.sendReq(request);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    public static void handleIntent(Intent intent, IWXAPIEventHandler event){
        if(iwxapi!=null){
            iwxapi.handleIntent(intent, event);
        }
    }

    private static void regist(Context context){
        iwxapi = WXAPIFactory.createWXAPI(context,mAppid, false);
        iwxapi.registerApp(mAppid);
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您还没有安装微信客户端", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public static class WXPayBuilder {
        public String appId;
        public String partnerId;
        public String prepayId;
        public String packageValue;
        public String nonceStr;
        public String timeStamp;
        public String sign;

        public WechatApi build() {
            mAppid = appId;
            return new WechatApi(this);
        }

        public String getAppId() {
            return appId;
        }

        public WXPayBuilder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public WXPayBuilder setPartnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public WXPayBuilder setPrepayId(String prepayId) {
            this.prepayId = prepayId;
            return this;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public WXPayBuilder setPackageValue(String packageValue) {
            this.packageValue = packageValue;
            return this;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public WXPayBuilder setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public WXPayBuilder setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public String getSign() {
            return sign;
        }

        public WXPayBuilder setSign(String sign) {
            this.sign = sign;
            return this;
        }
    }

    /**
     * 微信登录
     * @param context
     */
    public static void login(Context context){
        regist(context);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        //像微信发送请求
        iwxapi.sendReq(req);
    }

}
