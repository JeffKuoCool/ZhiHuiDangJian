package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.base.BaseActivity;
import com.lfc.zhihuidangjianapp.bean.TabEntity;
import com.lfc.zhihuidangjianapp.ui.activity.BaseBindViewActivity;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.FragPagerAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Study_Report;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Study_Report_new;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Weekend_Query;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Weekend_Report;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_study_craft;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_study_forestry;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fgt.Fgt_Dept_dynamic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date: 2019-08-06
 * @autror: guojian
 * @description: 学习强局更多页面
 */
public class Act_Study_ju extends BaseBindViewActivity {

    private String[] mTitles = {"林草公开课", "工匠培养", "学习心得"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private List<Fragment> fragments;

    //0林草公开课 1工匠培养 2学习心得
    private int tabType;

    /*   public static final int TAB_DEPT_ACTIVE = 0;
       public static final int TAB_DEPT_GROUP = 1;
       public static final int TAB_DEPT_BUILD = 2;
   */
    @BindView(R.id.tab)
    CommonTabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    TextView tvAppTitle;
    private ImageView create;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_dept_dynamic;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        tvAppTitle = findViewById(R.id.tv_apptitle);
        create = findViewById(R.id.create);
        tvAppTitle.setText("学习强局");
        tabType = getIntent().getIntExtra("tabType", 0);
        /*if (tabType > TAB_DEPT_BUILD) {
            tabType = TAB_DEPT_ACTIVE;
        }*/
        FragPagerAdapter fpa = new FragPagerAdapter(getSupportFragmentManager());
        fpa.setFragmentList(list());
        viewPager.setOffscreenPageLimit(mTitles.length);
        viewPager.setAdapter(fpa);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

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
        tab.setCurrentTab(tabType);
        viewPager.setCurrentItem(tabType);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tab.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setEvent();
    }

    private void setEvent() {
        findViewById(R.id.imgBack).setOnClickListener(back -> finish());
        create.setOnClickListener(ceate->{
            startActivity(new Intent(this, Act_Write_Study_Report.class));
        });
    }

    private List<Fragment> list() {
        fragments = new ArrayList<>();
        fragments.add(new Fgt_study_forestry());
        fragments.add(new Fgt_study_craft());
        fragments.add(new Fgt_Study_Report_new());
        return fragments;
    }

    @Override
    protected void initData() {

    }
}
