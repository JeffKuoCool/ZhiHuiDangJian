package com.lfc.zhihuidangjianapp.ui.activity.fgt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.lfc.zhihuidangjianapp.R;
import com.lfc.zhihuidangjianapp.app.MyApplication;
import com.lfc.zhihuidangjianapp.base.BaseFragment;
import com.lfc.zhihuidangjianapp.net.http.ApiConstant;
import com.lfc.zhihuidangjianapp.net.http.HttpHelper;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Party_Membership;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.dept.act.Act_Weekend_Report;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.CodeSafeActivity;
import com.lfc.zhihuidangjianapp.ui.activity.fgt.personal.act.bean.UserDataBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心
 */
public class Fgt_Personal extends BaseFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.min_hader)
    CircleImageView minHader;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.min_name)
    TextView minName;
    @BindView(R.id.min_naem1)
    TextView minName1;
    @BindView(R.id.min_code)
    TextView min_code;
    @BindView(R.id.min_sex)
    TextView minSex;
    @BindView(R.id.min_minzu)
    TextView minMinzu;
    @BindView(R.id.min_shenri)
    TextView minShenri;
    @BindView(R.id.min_xueli)
    TextView minXueli;
    @BindView(R.id.min_guanji)
    TextView minGuanji;
    @BindView(R.id.min_gangwei)
    TextView minGangwei;
    @BindView(R.id.min_zhiwu)
    TextView minZhiwu;
    @BindView(R.id.min_addr)
    TextView minAddr;
    @BindView(R.id.min_phone)
    TextView minPhone;
    @BindView(R.id.min_Total)
    TextView min_Total;
    @BindView(R.id.min_Total1)
    TextView min_Total1;
    @BindView(R.id.min_starPartyMember)
    TextView min_starPartyMember;
    @BindView(R.id.min_left)
    ImageView min_left;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.drawlayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.lefit)
    LinearLayout lefit;
    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    Unbinder unbinder1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        min_left.setOnClickListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        rootView.findViewById(R.id.min_shezhi).setOnClickListener(this);
        rootView.findViewById(R.id.min_zhanghuanquan).setOnClickListener(this);
        rootView.findViewById(R.id.min_gongzuozhoubao).setOnClickListener(this);
        rootView.findViewById(R.id.min_dangfei).setOnClickListener(this);
        rootView.findViewById(R.id.min_Development).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        queryJoinPartyStageDeatil();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 查询发展党员信息
     */
    public void queryJoinPartyStageDeatil() {
        HttpHelper.queryJoinPartyStageDeatil(new HttpHelper.HttpUtilsCallBack<String>() {
            @Override
            public void onFailure(String failure) {
            }

            @Override
            public void onSucceed(String succeed) {
                loding.dismiss();
                Gson gson = new Gson();
                UserDataBean entity = gson.fromJson(succeed, UserDataBean.class);
                if (entity.getCode() == 0) {
                    RequestOptions options = new RequestOptions();
                    options.placeholder(R.mipmap.iocn_moren);
                    options.error(R.mipmap.iocn_moren);
                    Glide.with(getContext()).load(ApiConstant.ROOT_URL + entity.getData().getUser().getImgAddress()).apply(options).into(minHader);
                    Glide.with(getContext()).load(ApiConstant.ROOT_URL + entity.getData().getUser().getImgAddress()).apply(options).into(profile_image);
                    minName.setText(entity.getData().getUser().getDisplayName());
                    minName1.setText(entity.getData().getUser().getDisplayName());
                    min_code.setText(entity.getData().getUser().getDeptNumber());
                    if (entity.getData().getUser().getSex() == 0) {
                        minSex.setText("女");
                    } else {
                        minSex.setText("男");
                    }
                    minMinzu.setText(entity.getData().getUser().getNation());
                    minShenri.setText(stampToDate(entity.getData().getUser().getBirthday()));
                    minXueli.setText(entity.getData().getUser().getEducation() + "");
                    minGuanji.setText(entity.getData().getUser().getAddr() + "");
                    minGangwei.setText(entity.getData().getUser().getPartyPosts() + "");
                    minAddr.setText(entity.getData().getUser().getAddr() + "");
                    minPhone.setText(entity.getData().getUser().getPhone() + "");
                    min_Total.setText(entity.getData().getTotal() + "积分");
                    min_starPartyMember.setText(entity.getData().getUser().getStarPartyMember() + "积分");
                    min_Total1.setText(entity.getData().getUser().getStarPartyMember() + "积分");
                }
            }

            @Override
            public void onError(String error) {
                loding.dismiss();
                ToastUtils.show(error);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }


    /*
     * 将时间戳转换为时间
     */
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.min_left:
                openLeftLayout();
                break;
            case R.id.min_Development://资料编辑

                break;
            case R.id.min_shezhi://设置

                break;
            case R.id.min_zhanghuanquan://账户安全
                startActivity(new Intent(getActivity(), CodeSafeActivity.class));
                break;
            case R.id.min_gongzuozhoubao://工作周报
                startActivity(new Intent(getActivity(), Act_Weekend_Report.class));
                break;
            case R.id.min_dangfei://党费缴纳
                startActivity(new Intent(getActivity(), Act_Party_Membership.class));
                break;
        }
    }

    //左边菜单开关事件
    public void openLeftLayout() {
        if (drawerLayout.isDrawerOpen(lefit)) {
            drawerLayout.closeDrawer(lefit);
        } else {
            drawerLayout.openDrawer(lefit);
        }
    }
}
