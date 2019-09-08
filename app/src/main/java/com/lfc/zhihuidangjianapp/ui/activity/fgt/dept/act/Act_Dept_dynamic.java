package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.bean.TabEntity;
import com.lfc.zhihuidangjianapp.ui.activity.BaseBindViewActivity;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.FragPagerAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fgt.Fgt_Dept_dynamic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date: 2019-08-03
 * @autror: guojian
 * @description: 党建动态
 */
public class Act_Dept_dynamic extends BaseBindViewActivity {

    private String[] mTitles = {"党建动态", "群团统战", "廉政建设"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private List<Fragment> fragments;

    //0党建动态 1群团统战 2廉政建设
    private int tabType = 0;

    public static final int TAB_DEPT_ACTIVE = 0;
    public static final int TAB_DEPT_GROUP = 1;
    public static final int TAB_DEPT_BUILD = 2;

    @BindView(R.id.tab)
    CommonTabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    TextView tvAppTitle;

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
        tvAppTitle.setText("党务资讯");

        tabType = getIntent().getIntExtra("tabType", 0);
        if (tabType > TAB_DEPT_BUILD) {
            tabType = TAB_DEPT_ACTIVE;
        }
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
    }

    private List<Fragment> list() {
        fragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Fgt_Dept_dynamic fgtDeptDynamic = new Fgt_Dept_dynamic();
            Bundle bundle = new Bundle();
            bundle.putInt("partyDynamicType", i);
            fgtDeptDynamic.setArguments(bundle);
            fragments.add(fgtDeptDynamic);
        }
        return fragments;
    }

    @Override
    protected void initData() {

    }
}
