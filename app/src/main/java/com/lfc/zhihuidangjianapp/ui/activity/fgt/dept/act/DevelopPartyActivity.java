package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.TabWithToolbarActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.develop.ApplyEducationPartyFragment;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.develop.ApplyPartyFragment;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.develop.DevelopConfirmFragment;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.develop.PrepareMainFragment;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.develop.PrepareReceiveFragment;
import com.lfc.zhihuidangjianapp.ui.activity.model.JoinPartyStage;
import com.lfc.zhihuidangjianapp.ui.activity.model.Payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-09-02
 * @autror: guojian
 * @description: 发展党员
 */
public class DevelopPartyActivity extends TabWithToolbarActivity {

    private String[] mTitles = {"申请入党阶段", "申请积极分子的确定和培养教育阶段","发展对象的确定和考察阶段", "预备党员的接收阶段", "预备党员的教育考察和转正阶段"};

    private List<Fragment> fragments = new ArrayList<>();

    private ApplyPartyFragment applyPartyFragment = new ApplyPartyFragment();
    private ApplyEducationPartyFragment applyEducationPartyFragment = new ApplyEducationPartyFragment();
    private DevelopConfirmFragment developConfirmFragment = new DevelopConfirmFragment();
    private PrepareReceiveFragment prepareReceiveFragment = new PrepareReceiveFragment();
    private PrepareMainFragment prepareMainFragment = new PrepareMainFragment();

    @Override
    public List<Fragment> getFragments() {
        initFragments();
        return fragments;
    }

    private void initFragments(){
        fragments.clear();
        fragments.add(applyPartyFragment);//申请入党阶段
        fragments.add(applyEducationPartyFragment);//申请积极分子的确定和培养教育阶段
        fragments.add(developConfirmFragment);//发展对象的确定和考察阶段
        fragments.add(prepareReceiveFragment);//预备党员的接收阶段
        fragments.add(prepareMainFragment);//预备党员的教育考察和转正
    }

    @Override
    public String[] getTitles() {
        return mTitles;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initData() {
        setAppText("发展党员");
        getTvRight().setVisibility(View.VISIBLE);
        getTvRight().setOnClickListener(submit->{
            //TODO 提交

            submitParty();
        });
        getDevelopData();
    }

    /**
     * 提交发展党员信息
     */
    private void submitParty() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(applyPartyFragment.params);
        map.putAll(applyEducationPartyFragment.params);
        map.putAll(developConfirmFragment.params);
        map.putAll(prepareReceiveFragment.params);
        map.putAll(prepareMainFragment.params);
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .updateJoinPartyStage(map, MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<Object>(getActivity()) {

                    @Override
                    protected void onNext(Object response) {
                        Log.e("onNext=已提交党员信息", response.toString());
                        if(response==null) return;
                        toast("已提交党员信息");
                        finish();
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }

    /**
     * 查询发展党员信息
     */
    private void getDevelopData() {
        RetrofitFactory.getDefaultRetrofit().create(HttpService.class)
                .queryDevelopPartyDeatil( MyApplication.getLoginBean().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<JoinPartyStage>(getActivity()) {

                    @Override
                    protected void onNext(JoinPartyStage response) {
                        Log.e("onNext= ", response.toString());
                        if(response==null||response.getJoinPartyStage()==null) return;
                        if(response.getJoinPartyStage().submitStatus==1){
                            getTvRight().setVisibility(View.GONE);
                        }else{
                            getTvRight().setVisibility(View.VISIBLE);
                        }
                        applyPartyFragment.setPartyData(response.getJoinPartyStage());
                        applyEducationPartyFragment.setPartyData(response.getJoinPartyStage());
                        developConfirmFragment.setPartyData(response.getJoinPartyStage());
                        prepareReceiveFragment.setPartyData(response.getJoinPartyStage());
                        prepareMainFragment.setPartyData(response.getJoinPartyStage());
                    }

                    @Override
                    protected void onError(Throwable e) {
                        super.onError(e);
                        Log.e("Throwable= ", e.getMessage());
                    }
                }.actual());
    }
}
