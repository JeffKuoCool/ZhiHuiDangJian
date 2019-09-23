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
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Forest_List;
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

   /* public static final int TAB_DEPT_ACTIVE = 0;
    public static final int TAB_DEPT_GROUP = 1;
    public static final int TAB_DEPT_BUILD = 2;
*/
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
       /* tvAppTitle.setText("党务资讯");

        tabType = getIntent().getIntExtra("tabType", 0);
        if (tabType >= mTitles.length) {
            tabType = 0;
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
        setEvent();*/
      /*  TextView tvAppTitle = findViewById(R.id.tv_apptitle);
        tvAppTitle.setText("林区风采");
        tabType = getIntent().getIntExtra("tabType", 0);
        if (tabType >= mTitles.length) {
            tabType = 0;
        }

        FragPagerAdapter fpa = new FragPagerAdapter(getSupportFragmentManager());
        fpa.setFragmentList(list());
        viewPager.setOffscreenPageLimit(mTitles.length);
        viewPager.setAdapter(fpa);
       // tab.setViewPager(viewPager, mTitles);
        tab.setTabData(mTabEntities);
        viewPager.setCurrentItem(tabType);
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
        setEvent();*/
        tvAppTitle.setText("党务资讯");
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
    }

    private List<Fragment> list() {

        fragments = new ArrayList<>();
        //0党建动态 1群团统战 2廉政建设
        Fgt_Dept_dynamic orderListFragment0 = new Fgt_Dept_dynamic();//0党建动态
        Fgt_Dept_dynamic orderListFragment1 = new Fgt_Dept_dynamic();//1群团统战
        Fgt_Dept_dynamic orderListFragment2 = new Fgt_Dept_dynamic();//2廉政建设
        Bundle bundle0 = new Bundle();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        bundle0.putInt("partyDynamicType", 0);
        bundle1.putInt("partyDynamicType", 1);
        bundle2.putInt("partyDynamicType", 2);
        orderListFragment0.setArguments(bundle0);
        orderListFragment1.setArguments(bundle1);
        orderListFragment2.setArguments(bundle2);
        fragments.add(orderListFragment0);
        fragments.add(orderListFragment1);
        fragments.add(orderListFragment2);
        return fragments;
    }

    @Override
    protected void initData() {

    }
}
