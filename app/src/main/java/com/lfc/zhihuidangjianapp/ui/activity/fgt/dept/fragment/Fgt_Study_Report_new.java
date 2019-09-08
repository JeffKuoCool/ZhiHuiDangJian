package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.bean.TabEntity;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpService;
import com.lfc.zhihuidangjianapp.net.http.ResponseObserver;
import com.lfc.zhihuidangjianapp.net.http.RetrofitFactory;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.DividerItemDecoration;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.FragPagerAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Write_Weekend_Log;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyCraftReportList;
import com.lfc.zhihuidangjianapp.ui.activity.model.StudyStrongBureau;
import com.lfc.zhihuidangjianapp.utlis.DispalyUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @date: 2019-08-10
 * @autror: guojian
 * @description: 学习心得界面
 */
public class Fgt_Study_Report_new extends BaseFragment {

    private ImageView create;

    private CommonTabLayout tab;

    private ViewPager viewPager;
    private RelativeLayout appbar;

    private String[] mTitles = {"我的心得","心得查询"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private List<Fragment> fragments;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_study_report_new;
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.imgBack).setOnClickListener(back->getActivity().finish());
        create = rootView.findViewById(R.id.create);
        appbar = rootView.findViewById(R.id.appbar);
        appbar.setVisibility(View.GONE);
        tab = rootView.findViewById(R.id.tab);
        viewPager = rootView.findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
       /* for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
            fragments.add(new Fgt_Weekend_Report());
        }*/
        mTabEntities.add(new TabEntity(mTitles[0]));
        fragments.add(new Fgt_Study_Report());
        mTabEntities.add(new TabEntity(mTitles[1]));
        fragments.add(new Fgt_Weekend_Query());
        tab.setTabData(mTabEntities);
        tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        FragPagerAdapter fpa = new FragPagerAdapter(getChildFragmentManager());
        fpa.setFragmentList(fragments);
        viewPager.setAdapter(fpa);

        setEvent();
    }

    private void setEvent() {
        create.setOnClickListener(ceate->{
            startActivity(new Intent(getActivity(), Act_Write_Weekend_Log.class));
        });
    }

    @Override
    protected void initData() {
    }

}
