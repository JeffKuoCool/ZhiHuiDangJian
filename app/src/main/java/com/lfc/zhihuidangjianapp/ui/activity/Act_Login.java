package com.lfc.zhihuidangjianapp.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.bean.LoginBean;
import com.lfc.zhihuidangjianapp.chat.EazyChatApi;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.Act_WebView;
import com.lfc.zhihuidangjianapp.utlis.ACache;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_Login extends BaseActivity {


    @BindView(R.id.editAccountNumber)
    EditText editAccountNumber;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.yingsi)
    TextView yingsi;
    private TextToSpeech textToSpeech = null;//创建自带语音对象
    private ACache aCache;
    private static final String TAG = "LoginActivity";
    private String time = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initImmersionBar(0);
        ButterKnife.bind(this);
        aCache = ACache.get(this);
        captcha();
        setEvent();
        yingsi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        yingsi.getPaint().setAntiAlias(true);//抗锯齿
    }

    private void setEvent() {
        ivCode.setOnClickListener(code->{
            captcha();
        });
        yingsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Act_YinSiActivity.class);

                getActivity().startActivity(intent);
            }
        });
    }

    private void initTTS() {
        //实例化自带语音对象
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS) {
                    // Toast.makeText(MainActivity.this,"成功输出语音",
                    // Toast.LENGTH_SHORT).show();
                    // Locale loc1=new Locale("us");
                    // Locale loc2=new Locale("china");
                    textToSpeech.setPitch(1.0f);//方法用来控制音调
                    textToSpeech.setSpeechRate(1.0f);//用来控制语速
                    //判断是否支持下面两种语言
                    int result1 = textToSpeech.setLanguage(Locale.US);
                    int result2 = textToSpeech.setLanguage(Locale.
                            SIMPLIFIED_CHINESE);
                    boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED);
                    boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED);

                    Log.i("zhh_tts", "US支持否？--》" + a +
                            "\nzh-CN支持否》--》" + b);

                } else {
                    Toast.makeText(Act_Login.this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 获取验证码
     */
    private void captcha(){
        time = System.currentTimeMillis()+"";
        String code = ApiConstant.ROOT_URL+"/"+ApiConstant.captcha+"?timeStr="+time;
        Glide.with(Act_Login.this).load(code).into(ivCode);
    }

    private void startAuto(String data) {
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        textToSpeech.setPitch(2.0f);
        // 设置语速
        textToSpeech.setSpeechRate(0.4f);
        textToSpeech.speak(data,//输入中文，若不支持的设备则不会读出来
                TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (textToSpeech != null) {
            textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
            textToSpeech.shutdown(); // 关闭，释放资源
        }
    }

    @Override
    protected void initData() {
    }

    @OnClick(R.id.btnLoginCommit)
    public void onBtnLoginCommitClicked() {
        if (editAccountNumber.getText().toString().trim().equals("")) {
            ToastUtils.show("请输入您的账号");
            return;
        }
        if (editPassword.getText().toString().trim().equals("")) {
            ToastUtils.show("请输入密码");
            return;
        }
        if(time.isEmpty()){
            ToastUtils.show("验证码已失效");
            return;
        }
        if(etCode.getText().toString().trim().isEmpty()){
            ToastUtils.show("请输入验证码");
            return;
        }
        loding.show();
        HttpHelper.login(editAccountNumber.getText().toString().trim(), editPassword.getText().toString().trim(), time, etCode.getText().toString().trim(), new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
                Log.e(TAG, failure);
            }
            @Override
            public void onSucceed(String succeed) {
                loding.dismiss();
                Log.e("aa","-------------onsucceed----"+succeed);
                Gson gson = new Gson();
                LoginBean bean = gson.fromJson(succeed, LoginBean.class);
                if (bean.getCode() == 0) {
                    aCache.put("data", succeed);
                    MyApplication.setLoginBean(bean.getData());
                    ToastUtils.show("登录成功");
                    // 登录成功
                    login();
                    startActivity(Act_Main.class);
                    finish();
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }
            @Override
            public void onError(String error) {
                loding.dismiss();
                ToastUtils.show(error);
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        LoginBean.DataBean loginBean = MyApplication.getLoginBean();
        String currentUsername = loginBean.getLoginName();
        String currentPassword = loginBean.getImPwd();


    }
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出时的时间
    private long mExitTime;

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            activityManagerUtil.finishAllActivity();
//            System.exit(0);
        }

    }

}
