package com.lfc.zhihuidangjianapp.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.lfc.zhihuidangjianapp.pay.WechatApi;
import com.lfc.zhihuidangjianapp.ui.activity.Act_Main;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Membership;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_Party_membershipDues;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WechatApi.mAppid, false);
        api.registerApp(WechatApi.mAppid);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.e("onReq=", req.toString());
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        //支付回调
        switch (resp.errCode) {
            //支付成功
            case BaseResp.ErrCode.ERR_OK:
                Log.i("weiixn","支付成功");
                Intent intent =new Intent(this, Act_Party_Membership.class);
                startActivity(intent);
                finish();
                break;
            //支付异常
            case BaseResp.ErrCode.ERR_COMM:

                finish();
                break;
            //用户取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                finish();
                break;
            default:

                break;

        }

       /* Log.e("onResp=", resp.toString()+"=="+resp.errCode );
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            // 分享
        } else if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            // 支付
//            RxBus.get().post(resp);
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            // 登录
        }
        finish();*/
    }


}