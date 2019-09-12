package com.lfc.zhihuidangjianapp.net.http;

import android.util.Log;

import com.google.gson.Gson;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.bean.LoginBean;
import com.lfc.zhihuidangjianapp.bean.QueryHomeNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.queryNoticeAnnouncementDetailBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.bean.queryNoticeAnnouncementPageListBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.bean.Party_membershipDuesBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.demonstration_leadership.bean.QueryLeadDemonstrationPageListBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.bean.queryUserListByFirstPinYinBean;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.UserDataBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HttpHelper {

    /**
     * 验证码登录
     */
    public static void login(String phone, String loginPwd, String timeStr, String login_captcha, final HttpUtilsCallBack<String> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("loginName", phone);
        map.put("loginPwd", loginPwd);
        map.put("timeStr", timeStr);
        map.put("login_captcha", login_captcha);
        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
        httpService.LOGIN(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String succeed) {
                        Log.e("aa", "---------------succeed==" + succeed);
                        Gson gson = new Gson();
                        LoginBean entity = gson.fromJson(succeed, LoginBean.class);
                        if (entity.getCode() == 0) {
                            callBack.onSucceed(succeed);
                        } else {
                            callBack.onError(entity.getMsg() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("aa", "---------------onError==" + e.getMessage());
                        callBack.onFailure(httpFailureMsg());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 分页公告信息
     */
    public static void queryHomeNoticeAnnouncementPageList(final HttpUtilsCallBack<String> callBack) {
        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
        httpService.queryHomeNoticeAnnouncementPageList(MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String succeed) {
                        Gson gson = new Gson();
                        QueryHomeNoticeAnnouncementPageListBean entity = gson.fromJson(succeed, QueryHomeNoticeAnnouncementPageListBean.class);
                        if (entity.getCode() == 0) {
                            callBack.onSucceed(succeed);
                        } else {
                            callBack.onError(entity.getMsg() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(httpFailureMsg());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 首页app-Banner
     */
    public static void queryAppConfigList(final HttpUtilsCallBack<String> callBack) {
//        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
//        httpService.queryAppConfigList(MyApplication.getLoginBean().getToken())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(String succeed) {
//                        Gson gson = new Gson();
//                        QueryAppConfigListBean1 entity = gson.fromJson(succeed, QueryAppConfigListBean1.class);
//                        if (entity.getCode() == 0) {
//                            callBack.onSucceed(succeed);
//                        } else {
//                            callBack.onError(entity.getMsg() + "");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callBack.onFailure(httpFailureMsg());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
    }

    /**
     * 查看公告详情信息
     */
    public static void queryNoticeAnnouncementDetail(String noticeAnnouncementId, final HttpUtilsCallBack<String> callBack) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("noticeAnnouncementId", noticeAnnouncementId);
        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
        httpService.queryNoticeAnnouncementDetail(hashMap, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String succeed) {
                        Gson gson = new Gson();
                        queryNoticeAnnouncementDetailBean entity = gson.fromJson(succeed, queryNoticeAnnouncementDetailBean.class);
                        if (entity.getCode() == 0) {
                            callBack.onSucceed(succeed);
                        } else {
                            callBack.onError(entity.getMsg() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(httpFailureMsg());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    /**
     * 分页公告信息
     */
    public static void queryNoticeAnnouncementPageList(final HttpUtilsCallBack<String> callBack) {
        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
        httpService.queryNoticeAnnouncementPageList(MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String succeed) {
                        Gson gson = new Gson();
                        queryNoticeAnnouncementPageListBean entity = gson.fromJson(succeed, queryNoticeAnnouncementPageListBean.class);
                        if (entity.getCode() == 0) {
                            callBack.onSucceed(succeed);
                        } else {
                            callBack.onError(entity.getMsg() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(httpFailureMsg());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 通讯录-按首字母查询
     */
    public static void queryUserListByFirstPinYin(String partyPosts, final HttpUtilsCallBack<String> callBack) {
//        Map<String, String> map = new HashMap<>();
//        map.put("partyPosts", partyPosts);
//        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
//        httpService.queryUserListByFirstPinYin(map, MyApplication.getLoginBean().getToken())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(String succeed) {
//                        Gson gson = new Gson();
//                        queryUserListByFirstPinYinBean entity = gson.fromJson(succeed, queryUserListByFirstPinYinBean.class);
//                        if (entity.getCode() == 0) {
//                            callBack.onSucceed(succeed);
//                        } else {
//                            callBack.onError(entity.getMsg() + "");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callBack.onFailure(httpFailureMsg());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
    }


    /**
     * 专题专栏
     * 引领示范类型(0:不忘初心 牢记使命1:改革创新 奋发有为)
     */
    public static void queryLeadDemonstrationPageList(String leadDemonstrationType, int pageNum,int pageSize,final HttpUtilsCallBack<String> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("leadDemonstrationType", leadDemonstrationType);
        map.put("pageNum", pageNum+"");
        map.put("pageSize", pageSize+"");
        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
        httpService.queryLeadDemonstrationPageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String succeed) {
                        Gson gson = new Gson();
                        QueryLeadDemonstrationPageListBean entity = gson.fromJson(succeed, QueryLeadDemonstrationPageListBean.class);
                        if (entity.getCode() == 0) {
                            callBack.onSucceed(succeed);
                        } else {
                            callBack.onError(entity.getMsg() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(httpFailureMsg());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 我的党费
     */
    public static void queryMyPartyPaymentHisPageList(String pageNum, final HttpUtilsCallBack<String> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", "10");
        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
        httpService.queryMyPartyPaymentHisPageList(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String succeed) {
                        Gson gson = new Gson();
                        Party_membershipDuesBean entity = gson.fromJson(succeed, Party_membershipDuesBean.class);
                        if (entity.getCode() == 0) {
                            callBack.onSucceed(succeed);
                        } else {
                            callBack.onError(entity.getMsg() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(httpFailureMsg());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 分页查询党费缴费记录信息-已缴列表
     */
    public static void queryPartyPaymentHisPageList(final HttpUtilsCallBack<String> callBack) {
        HashMap<String, String> map=new HashMap<>();
        map.put("","");
        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
        httpService.queryPartyPaymentHisPageList(map,MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String succeed) {
                        Gson gson = new Gson();
                        Log.e("aa", "---------------succeeed=====" + succeed);
//                        UserDataBean entity = gson.fromJson(succeed, UserDataBean.class);
//                        if (entity.getCode() == 0) {
//                            callBack.onSucceed(succeed);
//                        } else {
//                            callBack.onError(entity.getMsg() + "");
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(httpFailureMsg());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    /**
     * 查询发展党员信息
     */
    public static void queryJoinPartyStageDeatil(final HttpUtilsCallBack<String> callBack) {
        HttpService httpService = RetrofitFactory.getRetrofit(15l, 15l).create(HttpService.class);
        httpService.queryJoinPartyStageDeatil(MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String succeed) {
                        Gson gson = new Gson();
                        UserDataBean entity = gson.fromJson(succeed, UserDataBean.class);
                        if (entity.getCode() == 0) {
                            callBack.onSucceed(succeed);
                        } else {
                            callBack.onError(entity.getMsg() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(httpFailureMsg());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public interface HttpUtilsCallBack<T> {
        public void onFailure(String failure);

        public void onSucceed(T succeed);

        public void onError(String error);
    }

    private static String httpFailureMsg() {
        if (NetUtils.isConnected()) {
            return "很抱歉，系统繁忙，请稍后重试。";
        } else {
            return "检查网络连接情况，请稍后重试。";
        }
    }

    public static boolean choseLoginStatis = false;

}
