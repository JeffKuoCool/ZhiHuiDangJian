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
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Forest_List;
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
       /* for (int i = 0; i < mTitles.length; i++) {
            Fgt_Organizational_Life fgtDeptDynamic = new Fgt_Organizational_Life();
            Bundle bundle = new Bundle();
            bundle.putInt("studyType", i);
            fgtDeptDynamic.setArguments(bundle);
            fragments.add(fgtDeptDynamic);
        }*/
        //组织生活类别(0民主生活会1组织生活会2党课3主题党日4民主评议党员5其他)
        Fgt_Organizational_Life orderListFragment0 = new Fgt_Organizational_Life();//2党课
        Fgt_Organizational_Life orderListFragment1 = new Fgt_Organizational_Life();//3主题党日
        Fgt_Organizational_Life orderListFragment2 = new Fgt_Organizational_Life();//4民主评议党员
        Fgt_Organizational_Life orderListFragment3 = new Fgt_Organizational_Life();//1组织生活会
        Fgt_Organizational_Life orderListFragment4 = new Fgt_Organizational_Life();//5其他
        Fgt_Organizational_Life orderListFragment5 = new Fgt_Organizational_Life();//0民主生活会
        Bundle bundle0 = new Bundle();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        Bundle bundle4 = new Bundle();
        Bundle bundle5 = new Bundle();
        bundle0.putInt("studyType", 2);
        bundle1.putInt("studyType", 3);
        bundle2.putInt("studyType", 4);
        bundle3.putInt("studyType", 1);
        bundle4.putInt("studyType", 5);
        bundle5.putInt("studyType", 0);
        orderListFragment0.setArguments(bundle0);
        orderListFragment1.setArguments(bundle1);
        orderListFragment2.setArguments(bundle2);
        orderListFragment3.setArguments(bundle3);
        orderListFragment4.setArguments(bundle4);
        orderListFragment5.setArguments(bundle5);
        fragments.add(orderListFragment0);
        fragments.add(orderListFragment1);
        fragments.add(orderListFragment2);
        fragments.add(orderListFragment3);
        fragments.add(orderListFragment4);
        fragments.add(orderListFragment5);
        return fragments;
    }

    @Override
    protected void initData() {

    }
}
