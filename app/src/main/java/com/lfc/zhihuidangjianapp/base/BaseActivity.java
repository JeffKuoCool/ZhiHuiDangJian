package com.lfc.zhihuidangjianapp.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.hwangjr.rxbus.RxBus;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.service.LoginOutBroadcastReceiver;
import com.wuxiaolong.androidutils.library.ActivityManagerUtil;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.Random;

/**
 * desc   : Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    public static ActivityManagerUtil activityManagerUtil;
    protected LoginOutBroadcastReceiver locallReceiver;
    public ImmersionBar mImmersionBar;
    public ZLoadingDialog loding;
    public Bundle savedInstanceState;

    /**
     * 获取一个 Handler 对象
     */
    public static Handler getHandler() {
        return HANDLER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
        this.savedInstanceState = savedInstanceState;
        loding = new ZLoadingDialog(this);
        loding.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        initActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void initActivity() {
        //设置强制竖屛
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置软键盘将输入框顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        activityManagerUtil = ActivityManagerUtil.getInstance();
        activityManagerUtil.pushOneActivity(this);
        initLayout();
        initView();
        initData();
    }

    /**
     * 沉浸式状态栏
     */
    protected void initImmersionBar(int type) {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        if (type == 0) {
            initImmersionBarBlack();
        } else {
            initImmersionBarWhite();
        }
    }

    protected void initImmersionBarBlack() {
        mImmersionBar.statusBarDarkFont(true).init();
    }

    protected void initImmersionBarWhite() {
        mImmersionBar.statusBarDarkFont(false).init();
    }

    /**
     * {@link ViewTreeObserver.OnGlobalLayoutListener}
     */
    @Override
    public void onGlobalLayout() {
    }//不用写任何方法

    /**
     * 获取状态栏字体颜色
     */
    public boolean statusBarDarkFont() {
        //返回false表示白色字体
        return true;
    }

    protected void initLayout() {
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
    }

    // 引入布局
    protected abstract int getLayoutId();

    // 标题栏id
    protected abstract int getTitleBarId();

    // 初始化控件
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

    @Override
    public void finish() {
        hideSoftKeyboard();
        super.finish();
    }

    /**
     * 获取一个 Context 对象
     */
    public Context getContext() {
        return getBaseContext();
    }


    /**
     * 获取当前 Activity 对象
     */
    public <A extends BaseActivity> A getActivity() {
        return (A) this;
    }


    /**
     * startActivity 方法优化
     */

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivityFinish(new Intent(this, cls));
    }

    public void startActivityFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 取消注册广播接收器
        unregisterReceiver(locallReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.gesoft.admin.loginout");
        locallReceiver = new LoginOutBroadcastReceiver();
        registerReceiver(locallReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
        //结束Activity&从栈中移除该Activity
        activityManagerUtil.popOneActivity(this);
    }

    /**
     * setResult 方法优化
     */

    public void finishResult(int resultCode) {
        finishResult(resultCode, null);
    }

    public void finishResult(int resultCode, Intent data) {
        setResult(resultCode, data);
        finish();
    }

    /**
     * startActivityForResult 方法优化
     */

    private ActivityCallback mActivityCallback;
    private int mActivityRequestCode;

    public void startActivityForResult(Class<? extends Activity> cls, ActivityCallback callback) {
        startActivityForResult(new Intent(this, cls), null, callback);
    }

    public void startActivityForResult(Intent intent, ActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, Bundle options, ActivityCallback callback) {
        if (mActivityCallback == null) {
            mActivityCallback = callback;
            // 随机生成请求码，这个请求码在 0 - 255 之间
            mActivityRequestCode = new Random().nextInt(255);
            startActivityForResult(intent, mActivityRequestCode, options);
        } else {
            // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
            throw new IllegalArgumentException("Error, The callback is not over yet");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback.onActivityResult(resultCode, data);
            mActivityCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 防 Activity 多重跳转：https://www.jianshu.com/p/579f1f118161
     */

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (startActivitySelfCheck(intent)) {
            hideSoftKeyboard();
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    private String mStartActivityTag;
    private long mStartActivityTime;

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent 用于跳转的 Intent 对象
     * @return 检查通过返回true, 检查不通过返回false
     */
    protected boolean startActivitySelfCheck(Intent intent) {
        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
        } else { // 其他方式
            return result;
        }

        if (tag.equals(mStartActivityTag) && mStartActivityTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false;
        }

        mStartActivityTag = tag;
        mStartActivityTime = SystemClock.uptimeMillis();
        return result;
    }

    @SuppressLint("ShowToast")
    public void showTextToast(@NonNull String msg) {
        try {
            if (MyApplication.getAppContext() == null) {
                return;
            }
            Toast toast = Toast.makeText(MyApplication.getAppContext(), "", Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        } catch (Exception e) {
        }
    }


    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Activity 回调接口
     */
    public interface ActivityCallback {

        /**
         * 结果回调
         *
         * @param resultCode 结果码
         * @param data       数据
         */
        void onActivityResult(int resultCode, @Nullable Intent data);
    }

    /**
     * 显示吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }

    public void toast(int id) {
        ToastUtils.show(id);
    }

    public void toast(Object object) {
        ToastUtils.show(object);
    }
}