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
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Weekend_Query;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.fragment.Fgt_Weekend_Report;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.home.act.fgt.Fgt_Dept_dynamic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date: 2019-08-03
 * @autror: guojian
 * @description:林区风采
 */
public class Act_Forest_List extends BaseBindViewActivity {

   // private String[] mTitles = {"先进基层党组织", "优秀共产党员","优秀党务工作者", "优秀党建联络员"};
    private String[] mTitles = {"先进基层党组织", "优秀党务工作者","优秀共产党员", "优秀党建联络员"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private List<Fragment> fragments;

    //0先进基层党组织 1优秀共产党员 2优秀党务工作者 3优秀党建联络员
    private int tabType = 0;

    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forest_list;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        TextView tvAppTitle = findViewById(R.id.tv_apptitle);
        tvAppTitle.setText("林区风采");
        tabType = getIntent().getIntExtra("tabType", 0);
        if (tabType >= mTitles.length) {
            tabType = 0;
        }

        FragPagerAdapter fpa = new FragPagerAdapter(getSupportFragmentManager());
        fpa.setFragmentList(list());
        viewPager.setOffscreenPageLimit(mTitles.length);
        viewPager.setAdapter(fpa);
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
        tab.setCurrentTab(tabType);
        setEvent();
    }

    private void setEvent() {
        findViewById(R.id.imgBack).setOnClickListener(back -> finish());
    }

    private List<Fragment> list() {
        fragments = new ArrayList<>();
        //0先进基层党组织 1优秀共产党员 2优秀党务工作者 3优秀党建联络员
        Fgt_Forest_List orderListFragment0 = new Fgt_Forest_List();//0先进基层党组织
        Fgt_Forest_List orderListFragment1 = new Fgt_Forest_List();//2优秀党务工作者
        Fgt_Forest_List orderListFragment2 = new Fgt_Forest_List();//1优秀共产党员
        Fgt_Forest_List orderListFragment3 = new Fgt_Forest_List();//3优秀党建联络员
        Bundle bundle0 = new Bundle();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        bundle0.putInt("leadDemonstrationType", 0);
        bundle1.putInt("leadDemonstrationType", 2);
        bundle2.putInt("leadDemonstrationType", 1);
        bundle3.putInt("leadDemonstrationType", 3);
        orderListFragment0.setArguments(bundle0);
        orderListFragment1.setArguments(bundle1);
        orderListFragment2.setArguments(bundle2);
        orderListFragment3.setArguments(bundle3);
        fragments.add(orderListFragment0);
        fragments.add(orderListFragment1);
        fragments.add(orderListFragment2);
        fragments.add(orderListFragment3);
        return fragments;
    }

    @Override
    protected void initData() {

    }
}
