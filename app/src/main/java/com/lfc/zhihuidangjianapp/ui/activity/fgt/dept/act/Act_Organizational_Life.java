package com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.ui.activity.BaseBindViewActivity;
import com.lfc.zhihuidangjianapp.ui.activity.adapter.FragPagerAdapter;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Organizational_Life;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fgt.Fgt_Dept_dynamic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date: 2019-08-03
 * @autror: guojian
 * @description: 组织生活
 */
public class Act_Organizational_Life extends BaseBindViewActivity {

    //private String[] mTitles = {"民主生活会", "组织生活会", "党课","主题党日","民主评议党员","其他"};
    private String[] mTitles = {"党课", "主题党日", "民主评议党员","支部查询","其他","民主生活会"};

    private List<Fragment> fragments;

    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orgnizational_life;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        TextView tvAppTitle = findViewById(R.id.tv_apptitle);
        tvAppTitle.setText("组织生活");

        FragPagerAdapter fpa = new FragPagerAdapter(getSupportFragmentManager());
        fpa.setFragmentList(list());
        viewPager.setOffscreenPageLimit(mTitles.length);
        viewPager.setAdapter(fpa);
//        for (int i = 0; i < mTitles.length; i++) {
//            mTabEntities.add(new TabEntity(mTitles[i]));
//        }
//        tab.setTabData(mTabEntities);
        tab.setViewPager(viewPager, mTitles);
        tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

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
            Fgt_Organizational_Life fgtDeptDynamic = new Fgt_Organizational_Life();
            Bundle bundle = new Bundle();
            bundle.putInt("studyType", i);
            fgtDeptDynamic.setArguments(bundle);
            fragments.add(fgtDeptDynamic);
        }
        return fragments;
    }

    @Override
    protected void initData() {

    }
}
